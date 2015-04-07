#version 120

// The colour that we passed in through the vertex shader.
uniform sampler2D texture1;
varying vec2 tex_coord;
varying vec4 vertColor;

void main(){
	const float LOG2 = 1.442695;
	float z = gl_FragCoord.z / gl_FragCoord.w;
	float fogFactor = 0;
	if (z > 1900) {
		fogFactor = exp2( -gl_Fog.density * 
						   gl_Fog.density * 
						   z * 
						   z * 
						   LOG2 );
		fogFactor = clamp(fogFactor, 0.0, 1.0);
	}
	

	vec4 finalColor;
	// Turns the varying color into a 4D color and stores in the built-in output gl_FragColor.
    if (tex_coord.xy != vec2(-1,-1))
    	finalColor = texture2D(texture1, tex_coord) * vertColor;
    else
    	finalColor = vertColor;
    if (z > 1900) {
    	gl_FragColor = mix(gl_Fog.color, finalColor, fogFactor );
    } else {
    	gl_FragColor = finalColor;
    }
}