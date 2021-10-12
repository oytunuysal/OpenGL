    #version 150 core

struct Material {
    sampler2D diffuse;
    sampler2D specular;
    float shininess;
};

struct Light {
    vec3 position;
    //vec3 direction;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    float constant;
    float linear;
    float quadratic;
};
    #define NR_POINT_LIGHTS 2  
uniform Light pointLights[NR_POINT_LIGHTS];

in vec3 Color;
in vec2 Texcoord;
in vec3 NormalVector;
in vec3 FragPos;
out vec4 outColor;
uniform sampler2D tex;
uniform vec3 viewPos;

uniform Material material;
vec3 CalcPointLight(Light light, vec3 normal, vec3 fragPos, vec3 viewDir);

//uniform Light light;

void main() {
    vec3 norm = NormalVector;
    vec3 viewDir = normalize(viewPos - FragPos);

    vec3 result;

    for(int i = 0; i < NR_POINT_LIGHTS; i++)
        result += CalcPointLight(pointLights[i], norm, FragPos, viewDir);

    result *=Color;
    vec4 FragColor = vec4(result, 1.0);
    outColor = FragColor * texture(tex, Texcoord);
}

vec3 CalcPointLight(Light light, vec3 normal, vec3 fragPos, vec3 viewDir)
{
    vec3 lightDir = normalize(light.position - fragPos);
    // diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    // specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
    // attenuation
    float distance    = length(light.position - fragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance + 
  			     light.quadratic * (distance * distance));    
    // combine results
    vec3 ambient  = light.ambient  * vec3(texture(material.diffuse, Texcoord));
    vec3 diffuse  = light.diffuse  * diff * vec3(texture(material.diffuse, Texcoord));
    vec3 specular = light.specular * spec * vec3(texture(material.specular, Texcoord));
    ambient  *= attenuation;
    diffuse  *= attenuation;
    specular *= attenuation;
    return (ambient + diffuse + specular);
} 