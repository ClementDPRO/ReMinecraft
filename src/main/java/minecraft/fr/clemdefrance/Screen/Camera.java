package minecraft.fr.clemdefrance.Screen;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    public Vector3f position;
    public float pitch = 0;
    public float yaw = 0;

    public Camera(float x, float y, float z) {
        position = new Vector3f(x, y, z);
        pitch = -20;
        yaw = 45;
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f()
                .rotate((float)Math.toRadians(pitch), 1, 0, 0)
                .rotate((float)Math.toRadians(yaw),   0, 1, 0)
                .translate(-position.x, -position.y, -position.z);
    }

    public Matrix4f getProjectionMatrix(float width, float height) {
        return new Matrix4f().perspective(
                (float)Math.toRadians(70.0f),
                width / height,
                0.1f,
                1000f
        );
    }

    public void moveForward(float speed) {
        position.x += (float)Math.sin(Math.toRadians(yaw)) * speed;
        position.z -= (float)Math.cos(Math.toRadians(yaw)) * speed;
    }

    public void moveRight(float speed) {
        position.x += (float)Math.sin(Math.toRadians(yaw + 90)) * speed;
        position.z -= (float)Math.cos(Math.toRadians(yaw + 90)) * speed;
    }
}

