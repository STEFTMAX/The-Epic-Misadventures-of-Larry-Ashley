
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

uniform sampler2D u_texture;
uniform ivec2 u_textureSize;
const float sixt = 1.0 / 6.0;
varying LOWP vec4 v_color;
varying vec2 v_texCoords[9];

void main (void) {

	vec2 realTexPos = v_texCoords[4] * u_textureSize;
	
	vec4 A = texture2D(u_texture, vec2(v_texCoords[0]));
	vec4 B = texture2D(u_texture, vec2(v_texCoords[1]));
	vec4 C = texture2D(u_texture, vec2(v_texCoords[2]));
	vec4 D = texture2D(u_texture, vec2(v_texCoords[3]));
	vec4 E = texture2D(u_texture, vec2(v_texCoords[4]));
	vec4 F = texture2D(u_texture, vec2(v_texCoords[5]));
	vec4 G = texture2D(u_texture, vec2(v_texCoords[6]));
	vec4 H = texture2D(u_texture, vec2(v_texCoords[7]));
	vec4 I = texture2D(u_texture, vec2(v_texCoords[8]));
	
	vec2 frac = fract(realTexPos);
	vec4 sampledColor;
	
	if (frac.x < -sixt) { 			//E0 E3 E6
		if (frac.y < -sixt) {		//E0
			sampledColor = (D == B && B != F && D != H ? D : E);
			
		} else if (frac.y < sixt) {	//E3
			sampledColor = ((D == B && B != F && D != H && E != G) || (D == H && D != B && H != F && E != A) ? D : E);
		} else {						//E6
			sampledColor = (D == H && D != B && H != F ? D : E);
			
		}
	} else if (frac.x < sixt) { 	//E1 E4 E7
		if (frac.y < -sixt) {		//E1
			sampledColor = ((D == B && B != F && D != H && E != C) || (B == F && B != D && F != H && E != A) ? B : E);
		} else if (frac.y < sixt) {	//E4 
			sampledColor = E;
		} else {						//E7
			sampledColor = ((D == H && D != B && H != F && E != I) || (H == F && D != H && B != F && E != G) ? H : E);
			
		}
	} else { 							//E2 E5 E8
		if (frac.y < -sixt) {		//E2
			sampledColor = (B == F && B != D && F != H ? F : E);
			
		} else if (frac.y < sixt) {	//E5
			sampledColor = ((B == F && B != D && F != H && E != I) || (H == F && D != H && B != F && E != C) ? F : E);
			
		} else {						//E8
			sampledColor = (H == F && D != H && B != F ? F : E);
			
		}
	}

	//if (abs(frac.x) > 0.5 || abs(frac.y) > 0.5) {
	//if (v_color == vec4(1.0)) {
	//	sampledColor = vec4(abs(frac), 0.0, 1.0);
	//}
	
	gl_FragColor = v_color * sampledColor;
}