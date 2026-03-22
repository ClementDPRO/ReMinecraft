package minecraft.fr.clemdefrance.Screen;

import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

public class loadTexture {

    public static int getTexture(String path) {

        try {
            InputStream in = loadTexture.class.getClassLoader().getResourceAsStream(path);

            if (in == null) {
                throw new RuntimeException("Texture introuvable : " + path);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            byte[] bytes = out.toByteArray();

            ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.flip();

             
            IntBuffer width = BufferUtils.createIntBuffer(1);
            IntBuffer height = BufferUtils.createIntBuffer(1);
            IntBuffer channels = BufferUtils.createIntBuffer(1);

            ByteBuffer image = STBImage.stbi_load_from_memory(buffer, width, height, channels, 4);

            if (image == null) {
                throw new RuntimeException("STBImage error : " + STBImage.stbi_failure_reason());
            }

            int textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA,
                    width.get(0), height.get(0), 0,
                    GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            STBImage.stbi_image_free(image);

            return textureID;

        } catch (Exception e) {
            throw new RuntimeException("Erreur chargement texture : " + path, e);
        }
    }
}
