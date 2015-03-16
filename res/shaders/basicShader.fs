#version 330

in vec2 textCoord0;

out vec4 fragColour;

uniform vec3 colour;
uniform sampler2D sampler;

void main() {

    fragColour = texture2D(sampler, textCoord0.xy) * vec4(colour, 1);
    
}
