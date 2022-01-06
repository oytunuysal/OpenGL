#version 150 core
    in vec2 TexCoords;
    out vec4 outColor;
    layout (binding = 0) uniform sampler2D tex;
    void main()
    {
        vec4 FragColor = texture(tex, TexCoords);
        float average = (FragColor.r + FragColor.g + FragColor.b) / 3.0;
        outColor = vec4(average, average, average, 1.0);
    }  