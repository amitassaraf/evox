#version 120

// The colour we're going to pass to the fragment shader.
varying vec4 vertColor;
varying vec2 tex_coord;

uniform vec3 defColor;

void main(){

	//vec3 vertexPosition = (gl_Vertex).xyz;

	//vec3 lightDirection = normalize(gl_LightSource[0].position.xyz - vertexPosition);

	//vec4 surfaceNormal  = (gl_NormalMatrix * gl_Normal);
	
	//float diffuseLightIntensity = max(0, dot(surfaceNormal, lightDirection));
	//vertColor.rgb = diffuseLightIntensity * gl_FrontMaterial.diffuse.rgb;
	//vertColor += gl_LightModel.ambient.rgb;
	
	vertColor = gl_Color.rgba;
    //vertColor *= defColor.rgb;
	
	tex_coord = gl_MultiTexCoord0.st;
	
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}