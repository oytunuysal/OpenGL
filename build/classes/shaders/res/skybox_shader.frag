#version 330 core
out vec4 FragColor;

in vec3 TexCoords;

layout (binding = 0) uniform samplerCube skybox;

void main()
{    
    FragColor = texture(skybox, TexCoords);
}