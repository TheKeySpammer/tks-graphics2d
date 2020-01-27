package com.tks.graphics;

import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;
import static org.lwjgl.opengl.GL32.*;

import java.nio.FloatBuffer;

public class Triangle implements Shape {
    private int vaoId;
    private int vboId;
    private Vector4f color;

    public Triangle(float[] vertices) {
        color = new Vector4f(1.f, 1.f, 1.f, 1.f);

        FloatBuffer verticesBuffer = null;
        try {
            verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
            verticesBuffer.put(vertices).flip();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            vboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(0);


            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer);
            }
        }
    }

    @Override
    public void draw() {
        glBindVertexArray(this.vaoId);
        glDrawArrays(GL_TRIANGLES, 0, 3);
        glBindVertexArray(0);
    }

    @Override
    public void cleanup() {
        glDisableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(vboId);
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        color.x = r;
        color.y = g;
        color.z = b;
        color.w = a;
    }

    @Override
    public Vector4f getColor() {
        return this.color;
    }
}
