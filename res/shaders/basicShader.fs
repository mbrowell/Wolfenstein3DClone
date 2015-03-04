#version 330

in vec2 textCoord0;

out vec4 fragColour;

uniform vec3 colour;
uniform sampler2D sampler;

void main() {

    vec4 textureColour = texture2D(sampler, textCoord0.xy);

    if (textureColour == vec4(0, 0, 0, 0)) {
    
        fragColour = vec4(colour, 1);
    
    } else {
    
        fragColour = textureColour * vec4(colour, 1);
        
    }
    
}
