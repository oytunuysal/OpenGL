#version 330 core
layout (location = 0) in vec3 aPos;

out vec3 TexCoords;

//uniform mat4 proj;
uniform mat4 viewWithoutTransformations;

layout (std140) uniform Matrices
{
    mat4 proj;
    mat4 view;
};

void main()
{
    TexCoords = aPos;
    gl_Position = proj * viewWithoutTransformations * vec4(aPos, 1.0);
} 