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
uniform vec2 u_invTextureSize;

varying LOWP vec4 v_color;
varying vec2 v_texCoords;

void main (void) {// implemention scalex3 :O

	vec2 realTexPos = v_texCoords * u_textureSize - vec2(0.5, 0.5);
	
	vec2 frac = fract(realTexPos);
	
	vec3 stepper = vec3(u_invTextureSize, 0.0);
	
	vec4 A = texture2D(u_texture, vec2(v_texCoords.y + stepper.y, v_texCoords.x - stepper.x));
	vec4 B = texture2D(u_texture, v_texCoords + stepper.zy);
	vec4 C = texture2D(u_texture, v_texCoords + stepper.xy);
	vec4 D = texture2D(u_texture, v_texCoords - stepper.xz);
	vec4 E = texture2D(u_texture, v_texCoords);
	vec4 F = texture2D(u_texture, v_texCoords + stepper.xz);
	vec4 G = texture2D(u_texture, v_texCoords - stepper.xy);
	vec4 H = texture2D(u_texture, v_texCoords - stepper.zy);
	vec4 I = texture2D(u_texture, vec2(v_texCoords.y - stepper.y, v_texCoords.x + stepper.x));
	
	vec4 sampledColor;
	
	if (frac.x < onethird) { 			//E0 E3 E6
		if (frac.y < onethird) {		//E6
			sampledColor = D == H && D != B && H != F ? D : E;
		} else if (frac.y < twothird) {	//E3
			sampledColor = (D == B && B != F && D != H && E != G) || (D == H && D != B && H != F && E != A) ? D : E;
		} else {						//E0
			sampledColor = D == B && B != F && D != H ? D : E;
		}
	} else if (frac.x < twothird) { 	//E1 E4 E7
		if (frac.y < onethird) {		//E7
			sampledColor = (D == H && D != B && H != F && E != I) || (H == F && D != H && B != F && E != G) ? H : E;
		} else if (frac.y < twothird) {	//E4
			sampledColor = E;
		} else {						//E1
			sampledColor = (D == B && B != F && D != H && E != C) || (B == F && B != D && F != H && E != A) ? B : E;
		}
	} else { 							//E2 E5 E8
		if (frac.y < onethird) {		//E8
			sampledColor = H == F && D != H && B != F ? F : E;
		} else if (frac.y < twothird) {	//E5
			sampledColor = (B == F && B != D && F != H && E != I) || (H == F && D != H && B != F && E != C) ? F : E;
		} else {						//E2
			sampledColor = B == F && B != D && F != H ? F : E;
		}
	}



//	if (u_textureSize == vec2(0.0, 0.0)) {
//		sampledColor = vec4(0.0,0.0,0.0,1.0);
//	} debug shit u know



	gl_FragColor = v_color * sampledColor;
};