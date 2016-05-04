
// algorithm scales pattern of the nine pixels
//
// A B C
// D E F
// G H I
//
// to a pattern of
//
// E0 E1 E2
// E3 E4 E5
// E6 E7 E8
//
// more info: http://www.scale2x.it/algorithm

#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

const float onethird = 1.0 / 3.0;
const float twothird = 2.0 / 3.0;

uniform sampler2D u_texture;
uniform ivec2 u_textureSize;

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

void main (void) {

	vec2 realTexPos = v_texCoords * u_textureSize;
	
	float dx = 1.0 / u_textureSize.x;
	float dy = 1.0 / u_textureSize.y;
	
	vec4 A = texture2D(u_texture, vec2(v_texCoords.x - dx, v_texCoords.y - dy));
	vec4 B = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y - dy));
	vec4 C = texture2D(u_texture, vec2(v_texCoords.x + dx, v_texCoords.y - dy));
	vec4 D = texture2D(u_texture, vec2(v_texCoords.x - dx, v_texCoords.y));
	vec4 E = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y));
	vec4 F = texture2D(u_texture, vec2(v_texCoords.x + dx, v_texCoords.y));
	vec4 G = texture2D(u_texture, vec2(v_texCoords.x - dx, v_texCoords.y + dy));
	vec4 H = texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y + dy));
	vec4 I = texture2D(u_texture, vec2(v_texCoords.x + dx, v_texCoords.y + dy));
	
	vec2 frac = fract(realTexPos);
	vec4 sampledColor;
	
	if (frac.x < onethird) { 			//E0 E3 E6
		if (frac.y < onethird) {		//E0
			sampledColor = (D == B && B != F && D != H ? D : E);
			
		} else if (frac.y < twothird) {	//E3
			sampledColor = ((D == B && B != F && D != H && E != G) || (D == H && D != B && H != F && E != A) ? D : E);
			
		} else {						//E6
			sampledColor = (D == H && D != B && H != F ? D : E);
			
		}
	} else if (frac.x < twothird) { 	//E1 E4 E7
		if (frac.y < onethird) {		//E1
			sampledColor = ((D == B && B != F && D != H && E != C) || (B == F && B != D && F != H && E != A) ? B : E);
			
		} else if (frac.y < twothird) {	//E4 
			sampledColor = E;			
			
		} else {						//E7
			sampledColor = ((D == H && D != B && H != F && E != I) || (H == F && D != H && B != F && E != G) ? H : E);
			
		}
	} else { 							//E2 E5 E8
		if (frac.y < onethird) {		//E2
			sampledColor = (B == F && B != D && F != H ? F : E);
			
		} else if (frac.y < twothird) {	//E5
			sampledColor = ((B == F && B != D && F != H && E != I) || (H == F && D != H && B != F && E != C) ? F : E);
			
		} else {						//E8
			sampledColor = (H == F && D != H && B != F ? F : E);
			
		}
	}

	gl_FragColor = v_color * sampledColor;
}