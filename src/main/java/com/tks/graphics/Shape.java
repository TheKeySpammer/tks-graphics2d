package com.tks.graphics;

import org.joml.Vector4f;

public interface Shape {
    void draw();
    void cleanup();
    void setColor(float r, float g, float b, float a);
    Vector4f getColor();
}
