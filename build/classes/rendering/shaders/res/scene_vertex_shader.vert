    #version 150 core
in vec3 position;
in vec2 texcoord;
in vec3 normalVector;
out vec2 Texcoord;
out vec3 NormalVector;
out vec3 FragPos;
uniform mat4 model;
uniform mat4 inverseTransposedModel;
layout (std140) uniform Matrices
{
    mat4 proj;
    mat4 view;
};
//uniform mat4 view;
//uniform mat4 proj;
void main() {
    NormalVector = normalize(mat3(inverseTransposedModel) * normalVector);
    Texcoord = texcoord;
    FragPos = vec3(model * vec4(position, 1.0));
    gl_Position = proj * view * model * vec4(position, 1.0);
}