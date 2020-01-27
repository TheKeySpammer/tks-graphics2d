package com.tks.graphics;

import org.apache.commons.io.IOUtils;
import org.joml.Matrix4f;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private ShaderProgram shaderProgram;
    private Shape shape;

    public void init(Shape shape)throws Exception {
        this.shape = shape;
        shaderProgram = new ShaderProgram();
        InputStream vShaderStream = getClass().getClassLoader().getResourceAsStream("shader/vertexShader.shader");
        assert vShaderStream != null;
        String vShader = IOUtils.toString(vShaderStream, StandardCharsets.UTF_8.name());
        InputStream fShaderStream = getClass().getClassLoader().getResourceAsStream("shader/fragmentShader.shader");
        assert fShaderStream != null;
        String fShader = IOUtils.toString(fShaderStream, StandardCharsets.UTF_8.name());
        shaderProgram.createVertexShader(vShader);
        shaderProgram.createFragmentShader(fShader);
        shaderProgram.link();

        shaderProgram.createUniform("color");
    }

    public void render(Window window) {
        clear();
        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }
        shaderProgram.bind();

        // Render Shape
        shaderProgram.setUniform("color", shape.getColor());
        shape.draw();

        shaderProgram.unbind();
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
        if (shape != null) {
            shape.cleanup();
        }
    }
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

}
