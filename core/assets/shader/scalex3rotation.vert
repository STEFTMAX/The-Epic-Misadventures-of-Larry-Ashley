attribute vec4 a_position;      
attribute vec4 a_color; 
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;
uniform ivec2 u_textureSize;

varying vec4 v_color;  
varying vec2 v_texCoords[9];

void main (void) {
	v_color = a_color;
	v_color.a = v_color.a * (255.0/254.0);
	
	
	float dx = 1.0 / u_textureSize.x;
	float dy = 1.0 / u_textureSize.y;
	
	vec2 texCoord0 = a_texCoord0;
	
	//this gets the different pixel coordinates for the surrounding pixels, unless the u_texturesize is incorrect 
	
	v_texCoords[0] = vec2(texCoord0.x - dx, texCoord0.y +dy);
	v_texCoords[1] = vec2(texCoord0.x, texCoord0.y +dy);
	v_texCoords[2] = vec2(texCoord0.x + dx, texCoord0.y +dy);
	v_texCoords[3] = vec2(texCoord0.x - dx, texCoord0.y);
	v_texCoords[4] = vec2(texCoord0.x , texCoord0.y);
	v_texCoords[5] = vec2(texCoord0.x + dx, texCoord0.y );
	v_texCoords[6] = vec2(texCoord0.x - dx, texCoord0.y -dy);
	v_texCoords[7] = vec2(texCoord0.x, texCoord0.y -dy);
	v_texCoords[8] = vec2(texCoord0.x + dx, texCoord0.y -dy);

	gl_Position = u_projTrans * a_position;
}