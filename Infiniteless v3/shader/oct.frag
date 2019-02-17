#version 400 core

uniform vec2 resolution;
uniform vec3 eyePosition;
uniform vec3 eyeRotation;
uniform float time;


//TODO
//Important: Fix y = 12 artifact
//Implement a array of objects which have defined color and positions
// - Use these positions to define color in final rendering

out vec4 out_Color;

in vec4 gl_FragCoord;

const int MAX_STEPS = 255;
const float MIN_DIST = 0.0;
const float MAX_DIST = 100.0;
const float EPSILON = 0.0001;

const vec3 lightPos = vec3(4.0, 2.0, 4.0);
const vec3 lightDirection = vec3(0.2, -1.0, 0);
const vec3 lightIntensity = vec3(0.4, 0.4, 0.4);

float union(float a, float b)
{
	return min(a, b);
}

float sphere(vec3 p, float v, vec3 pos)
{
	p = p + pos;
	return length(p) - v;
}

float box(vec3 p, vec3 b, vec3 pos)
{
	p = p + pos;
	vec3 d = abs(p) - b;
	return length(max(d, 0.0)) + min(max(d.x, max(d.y, d.z)), 0.0);
}

float plane(vec3 p)
{
	return p.y;
}

vec3 repeat(vec3 p, vec3 c)
{
	return mod(p, c) - 0.5 * c;
}

float smin(float a, float b, float k)
{
	float r = exp2(-k * a) + exp2(-k * b);
	return -log2(r) / k;
}

float amin(float a, float b)
{
	return min(a, b);
}

mat3 rotateX(float angle) {
	float c = cos(angle), s = sin(angle);
    return mat3(1, 0, 0, 0, c, -s, 0, s, c);
}

mat3 rotateY(float angle) {
	float c = cos(angle), s = sin(angle);
    return mat3(c, 0, -s, 0, 1, 0, s, 0, c);
}

mat3 rotateZ(float angle) {
	float c = cos(angle), s = sin(angle);
    return mat3(c,-s,0,s,c,0,0,0,1);
}

#define Iterations 22.


float scene(vec3 p)
{
	//float f = plane(p);
	
	//for(int i = 0; i < 25; i++)
	//{
	//	for(int j = 0; j < 25; j++)
	//	{
	//		//f = smin(f, sphere(p, 1.0, vec3(i * 5, -2, j * 5)), 32.0);
	//		f = amin(f, box(p, vec3(1.0), vec3(i * 5, -2, j * 5)));
	//	}
	//}
	
	
	p.x = mod(p.x, 1) - 0.5;
	p.z = mod(p.z, 1) - 0.5;
	return box(p, vec3(0.5), vec3(0.0));

	
	//return f;
	
	//return box(repeat(p, vec3(2)), vec3(0.2), vec3(0.0));
	//return modSphere(p, vec3(0.0), 1, vec3(0.0), 5);
	//return amin(f, box(p, vec3(1.0), vec3(0.0)));
	//return sphere(p, 1.0, vec3(0.0));
}


float checkerGrid(vec3 ps)
{
	vec2 p = 5.0 * vec2(ps.x, ps.z);
    vec2 w = fwidth(p) + 0.001;
    vec2 i = 2.0*(abs(fract((p-0.5*w)*0.5)-0.5)-abs(fract((p+0.5*w)*0.5)-0.5))/w;
    return 0.5 - 0.5*i.x*i.y;  
}

float shadow(vec3 e, vec3 direction, float max, float k)
{
	float r = 1.0;

	for(float t = 0.01; t < max;)
	{
		float h = scene(e + direction * t);
		if(h < 0.001)
		{
			return 0.0;
		}
		r = min(r, k * h / t);
		t += h;
	}

	return r;
}

mat4 rotationMatrix(vec3 axis, float angle)
{
	angle = radians(-angle);
	axis = normalize(axis);
	float s = sin(angle);
	float c = cos(angle);
	float oc = 1.0 - c;
	
	return mat4(oc * axis.x * axis.x + c,			oc * axis.x * axis.y - axis.z * s,	oc * axis.z * axis.x + axis.y * s,	0.0,
				oc * axis.x * axis.y + axis.z * s,	oc * axis.y * axis.y + c,			oc * axis.y * axis.z - axis.x * s,	0.0,
				oc * axis.z * axis.x - axis.y * s,	oc * axis.y * axis.z + axis.x * s,	oc * axis.z * axis.z + c,			0.0,
				0.0,								0.0,								0.0,								1.0);
}

float shortestDistance(vec3 eye, vec3 direction, float start, float end)
{
	float depth = start;
	for(int i = 0; i < MAX_STEPS; i++)
	{
		float dist = scene(eye + depth * direction);
		if(dist < EPSILON)
		{
			return depth;
		}
		depth += dist;
		if(depth >= end)
		{
			return end;
		}
	}
	return end;
}

vec3 rayDirection(float fov, vec2 size, vec2 fragCoord)
{
	vec2 xy = fragCoord - size / 2.0;
	float z = size.y / tan(radians(fov) / 2.0);
	return normalize(vec3(xy, -z));
}


vec3 estimateNormal(vec3 p)
{
	return normalize(vec3(
		scene(vec3(p.x + EPSILON, p.y, p.z)) - scene(vec3(p.x - EPSILON, p.y, p.z)),
		scene(vec3(p.x, p.y + EPSILON, p.z)) - scene(vec3(p.x, p.y - EPSILON, p.z)),
		scene(vec3(p.x, p.y, p.z  + EPSILON)) - scene(vec3(p.x, p.y, p.z - EPSILON))
    ));
}

vec3 phongLight(vec3 k_d, vec3 k_s, float alpha, vec3 p, vec3 eye, vec3 lightPos, vec3 lightIntensity)
{
	vec3 N = estimateNormal(p);
	vec3 L = normalize(lightPos - p);
	vec3 V = normalize(eye - p);
	vec3 R = normalize(reflect(-L, N));
	
	float dotLN = dot(L, N);
	float dotRV = dot(R, V);
	
	if(dotLN < 0.0)
	{
		return vec3(0.0, 0.0, 0.0);
	}
	
	if(dotRV < 0.0)
	{
		return lightIntensity * (k_d * dotLN);
	}
	return lightIntensity * (k_d * dotLN + k_s * pow(dotRV, alpha));
}

vec3 phongIllumination(vec3 k_a, vec3 k_d, vec3 k_s, float alpha, vec3 p, vec3 eye)
{
	const vec3 ambientLight = 0.5 * vec3(1.0, 1.0, 1.0);
	vec3 color = ambientLight * k_a;
	
	color += phongLight(k_d, k_s, alpha, p, eye, lightPos, lightIntensity);
	return color;
}

mat4 viewMatrix(vec3 eye, vec3 center, vec3 up)
{
	vec3 f = normalize(center - eye);
	vec3 s = normalize(cross(f, up));
	vec3 u = cross(s, f);
	return mat4(
		vec4(s, 0.0),
		vec4(u, 0.0),
		vec4(-f, 0.0),
		vec4(0.0, 0.0, 0.0, 1)
	);
}

void main(void)
{
	//## Inputs ##
	bool useSoftShadows = true;
	bool useFog = true;
	bool useGlobalIlluminiation = false;

	//## CAMERA MOVEMENT ##

	vec3 eye = eyePosition;
	vec3 localDirection = rayDirection(90.0, vec2(1080.0, 720.0), vec2(gl_FragCoord.x, gl_FragCoord.y));
		
	mat4 rotPitch = rotationMatrix(vec3(0.0, 1.0, 0.0), eyeRotation.x);
	mat4 rotYaw = rotationMatrix(vec3(1.0, 0.0, 0.0), eyeRotation.y);
	
	vec3 worldDirection = localDirection;
	worldDirection = (vec4(worldDirection, 1.0) * rotYaw).xyz; //Yaw
	worldDirection = (vec4(worldDirection, 1.0) * rotPitch).xyz; //Pitch
	
	float dist = shortestDistance(eye, worldDirection, MIN_DIST, MAX_DIST);
	

	//## SKY COLORING ##

	//if(dist > MAX_DIST - EPSILON)

	vec3 skyColor = vec3(0.7, 0.82, 1.0);

	if(dist > MAX_DIST - (4 * EPSILON))
	{
		out_Color = vec4(skyColor, 1.0);
		return;
	}
	
	vec3 p = eye + dist * worldDirection;

	//## PHONG REFLECTION ##

	//vec3 K_a = vec3(1.0, 1.0, 1.0); //Ambient Color
	vec3 K_a = skyColor;
	vec3 K_d = vec3(1.0, 0.0, 0.0); //Diffuse Color
	vec3 K_s = vec3(1.0, 1.0, 1.0); //Specular Color
	float shininess = 100.0;
	
	vec3 color;
	if(useGlobalIlluminiation)
	{
		color = K_d;
	}
	else
	{
		color = phongIllumination(K_a, K_d, K_s, shininess, p, eye);
	}
	

	//## SOFT SHADOWS ##
	float shad = 1.0;
	if(useSoftShadows)
	{
		shad = shadow(p, normalize(vec3(1.0, 1.0, 0.5)), 5, 8.0);
		float minShad = 0.2;
		shad = (shad / 2.0) + minShad;
	}

	//## FOG ##
	float fogDesntiy = 0.03;
	float d = fogDesntiy * dist;
	float fogMix = 0;
	if(useFog)
	{
		fogMix = 1.0 - clamp(exp2(d * d * -1.442695), 0.0, 1.0);
	}


	out_Color = mix(vec4(color * K_d * shad, 1.0), vec4(skyColor, 1.0), fogMix);
}
