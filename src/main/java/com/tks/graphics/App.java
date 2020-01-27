package com.tks.graphics;

import org.joml.Vector4f;

public class App
{
    public static void main( String[] args ) throws Exception {
        String title = "Hello World";
        int w = 640;
        int h = 480;
        Window window = new Window(title, w, h, true);
        window.init();
        drawLine(window);
    }

    private static void drawLine(Window window) throws Exception {
        Shape shape = new Line(0.5f, 0.5f, -0.5f, -0.5f);
        Renderer renderer = new Renderer();
        renderer.init(shape);
        while (!window.windowShouldClose()) {
            renderer.render(window);
            window.update();
        }
    }

    private static void drawTriangle(Window window) throws Exception {
        float[] vertices = new float[]{
                0.0f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };
        Shape shape = new Triangle(vertices);
        Renderer renderer = new Renderer();
        renderer.init(shape);
        int direction = 1;
        while (!window.windowShouldClose()) {
            Vector4f color = shape.getColor();
            if (color.x >= 1.f) {
                direction = -1;
            } else if (color.x <= 0.f) {
                direction = 1;
            }
            color.x = color.x + 0.01f * direction;
            renderer.render(window);
            window.update();
        }
    }
}
