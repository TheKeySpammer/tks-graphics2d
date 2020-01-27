package com.tks.graphics;

import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Line implements Shape
{
    private Vector4f color;
    private int vaoId;
    private int vboId;


    public Line(float x1, float y1, float x2, float y2) {
        color = new Vector4f(1.f, 1.f, 1.f, 1.f);
        float[] vertices = new float[]{x1, y1, 0.f, x2, y2, 0.f};
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
        glDrawArrays(GL_LINE, 0, 2);
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
