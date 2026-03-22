package minecraft.fr.clemdefrance.Screen;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;


import minecraft.fr.clemdefrance.World.World;

public class Frame {

    private double lastMouseX = 400;
    private double lastMouseY = 300;
    private boolean firstMouse = true;
    private float cloudOffset = 0f;


    public Frame() {
        if(!GLFW.glfwInit()) {
            throw new IllegalStateException("Impossible D'initié  OpenGL: error 0");
        }

        long window = GLFW.glfwCreateWindow(800, 600, "Minecraft", 0, 0);
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        if(window == 0) {
            throw new RuntimeException("Error: impossible d'initié la fenêtre: error 1");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS, 24);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);


        World world = new World();
        Camera camera = new Camera(0, 3, 3);

        int texID = loadTexture.getTexture("Env/clouds.png");
        int sun = loadTexture.getTexture("Env/sun.png");


while (!GLFW.glfwWindowShouldClose(window)) {

    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

    drawSkyGradient();

    moove(window, camera);
    rot(window, camera);
    cloudOffset += 0.0001f;

    Matrix4f proj = camera.getProjectionMatrix(800, 600);
    float[] projArr = new float[16];
    proj.get(projArr);
    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadMatrixf(projArr);

    Matrix4f view = camera.getViewMatrix();
    float[] viewArr = new float[16];
    view.get(viewArr);
    GL11.glMatrixMode(GL11.GL_MODELVIEW);
    GL11.glLoadMatrixf(viewArr);

    drawClouds3D(texID, camera);
    drawSkyObjectFixed(sun, 0f, 255f, 0f, 25f);

    GLFW.glfwSetWindowTitle(window, "Minecraft - X: " + camera.position.x + " Y: " + camera.position.y + " Z: " + camera.position.z);
    gravity(window, camera, (int)camera.position.x, (int)camera.position.z, (int)camera.position.y);

    world.render();

    GLFW.glfwSwapBuffers(window);
    GLFW.glfwPollEvents();
}




        GLFW.glfwTerminate();
    }


    public void moove(long window, Camera camera) {

    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
        camera.moveForward(0.8f);
    }

    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
        camera.moveForward(-0.8f);
    }

    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
        camera.moveRight(-0.8f);
    }

    if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
        camera.moveRight(0.8f);
    }

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
        System.exit(0);
    }
}


   public void rot(long window, Camera camera) {

    double[] mx = new double[1];
    double[] my = new double[1];

    GLFW.glfwGetCursorPos(window, mx, my);

    if (firstMouse) {
        lastMouseX = mx[0];
        lastMouseY = my[0];
        firstMouse = false;
    }

    float sens = 0.1f;

    float dx = (float)(mx[0] - lastMouseX);
    float dy = (float)(lastMouseY - my[0]); 

    lastMouseX = mx[0];
    lastMouseY = my[0];

    camera.yaw += dx * sens;
    camera.pitch -= dy * sens;

    if (camera.pitch > 89) camera.pitch = 89;
    if (camera.pitch < -89) camera.pitch = -89;
}

public void drawSkyGradient() {

    GL11.glDisable(GL11.GL_DEPTH_TEST);

    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();

    GL11.glMatrixMode(GL11.GL_MODELVIEW);
    GL11.glLoadIdentity();

    GL11.glBegin(GL11.GL_QUADS);

    GL11.glColor3f(0.49f, 0.75f, 0.93f);
    GL11.glVertex2f(-1, 1);
    GL11.glVertex2f(1, 1);

    GL11.glColor3f(1f, 1f, 1f);
    GL11.glVertex2f(1, -1);
    GL11.glVertex2f(-1, -1);

    GL11.glEnd();

    GL11.glEnable(GL11.GL_DEPTH_TEST);
    GL11.glColor3f(1, 1, 1);
}

public void drawSkyObjectFixed(int texture, float x, float y, float z, float size) {

    GL11.glEnable(GL11.GL_TEXTURE_2D);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);

    GL11.glEnable(GL11.GL_BLEND);
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

    GL11.glDepthMask(false);
    GL11.glPushMatrix();

    GL11.glTranslatef(x, y, z);
    GL11.glRotatef(-90f, 1, 0, 0);

    float half = size / 2f;

    GL11.glBegin(GL11.GL_QUADS);
    GL11.glTexCoord2f(0, 0); GL11.glVertex3f(-half,  half, 0);
    GL11.glTexCoord2f(1, 0); GL11.glVertex3f( half,  half, 0);
    GL11.glTexCoord2f(1, 1); GL11.glVertex3f( half, -half, 0);
    GL11.glTexCoord2f(0, 1); GL11.glVertex3f(-half, -half, 0);
    GL11.glEnd();

    GL11.glPopMatrix();
    GL11.glDepthMask(true);
    GL11.glDisable(GL11.GL_BLEND);
}


public void drawClouds3D(int cloudTexture, Camera camera) {

    GL11.glEnable(GL11.GL_TEXTURE_2D);
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, cloudTexture);

    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

    GL11.glEnable(GL11.GL_BLEND);
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

    GL11.glDepthMask(false);

    GL11.glPushMatrix();

    float cloudHeight = 100f;
    GL11.glTranslatef(camera.position.x, cloudHeight, camera.position.z);

    GL11.glRotatef(camera.yaw, 0, 1, 0);

    float size = 400f;

    float frameSize = 0.25f; 
    float u = cloudOffset % 1.0f; 

    float uMin = u;
    float uMax = u + frameSize;

    float vMin = 0f;
    float vMax = frameSize;

    GL11.glBegin(GL11.GL_QUADS);

    GL11.glTexCoord2f(uMin, vMin); GL11.glVertex3f(-size, 0, -size);
    GL11.glTexCoord2f(uMax, vMin); GL11.glVertex3f( size, 0, -size);
    GL11.glTexCoord2f(uMax, vMax); GL11.glVertex3f( size, 0,  size);
    GL11.glTexCoord2f(uMin, vMax); GL11.glVertex3f(-size, 0,  size);

    GL11.glEnd();

    GL11.glPopMatrix();

    GL11.glDepthMask(true);
    GL11.glDisable(GL11.GL_BLEND);
}


public void gravity(long window, Camera camera, int x, int z, int y) {
    if(!World.isBlock(x, z, y, World.id)) {
        camera.position.y -= 0.5f;
    } else {
        if(camera.position.y > 2) {
            camera.position.y -= 0.5f;
        }
    }

    if(camera.position.y == -50f) {
        camera.position.x = 0f;
        camera.position.y = 2f;
        camera.position.z = 0f;
        
}

}

}


