#version 330

layout(location = 0) out vec4 color;

in DATA{
    vec4 position;
    vec4 color;
}fs_in;

void main() {

        color = fs_in.color;

}
