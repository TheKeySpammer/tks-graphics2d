package com.tks.graphics;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL32.*;

public class ShaderProgram {
    private final static Logger LOGGER = Logger.getLogger(ShaderProgram.class);

    private final int programId;

    private int vertexShaderId;
    private int fragmentShaderId;

    private final Map<String, Integer> uniforms;

    public ShaderProgram() throws Exception {
        uniforms = new HashMap<>();
        programId = glCreateProgram();
        if (programId == 0) {
            throw new Exception("Count not create Shader");
        }
    }

    public void createVertexShader(String code) throws Exception {
        vertexShaderId = createShader(code, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String code) throws Exception {
        fragmentShaderId = createShader(code, GL_FRAGMENT_SHADER);
    }

    private int createShader(String code, int type) throws Exception {
        int shaderId = glCreateShader(type);
        String shaderType = "Unknown";
        switch (type) {
            case GL_VERTEX_SHADER:
                shaderType = "Vertex Shader";
                break;
            case GL_FRAGMENT_SHADER:
                shaderType = "Fragment Shader";
                break;
        }
        if (shaderId == 0) {
            throw new Exception ("Error create shader. Type : "+shaderType);
        }

        glShaderSource(shaderId, code);
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling shader code: "+glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);
        return shaderId;
    }

    public void createUniform(String name) throws Exception {
        int uniformId = glGetUniformLocation(programId, name);
        if (uniformId < 0) {
            throw new Exception("Count not find uniform "+name);
        }
        uniforms.put(name, uniformId);
    }

    public void setUniform(String name, Vector4f value) {
        glUniform4f(uniforms.get(name), value.x, value.y, value.z, value.w);
    }

    public void link() throws Exception {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception ("Error Linking shader code: "+glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }

        if (fragmentShaderId != 0){
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            LOGGER.warn("Warning validating shader code: "+glGetProgramInfoLog(programId, 1024));
        }
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}
