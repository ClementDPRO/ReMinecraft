package minecraft.fr.clemdefrance.Content;

import org.lwjgl.opengl.GL11;

public class Block {

    public float x, y, z;
    private int north, south, east, west, high, down;
    private float s;
    private int id1;
    private int id2;

    public Block(int taille,
                 int north, int south, int east, int west, int high, int down, int id1, int id2) {

        this.s = taille / 2f;
        this.id1 = id1;
        this.id2 = id2;

        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.high = high;
        this.down = down;
    }

    public void render() {

        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, north);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); GL11.glVertex3f(-s, -s,  s);
        GL11.glTexCoord2f(1, 0); GL11.glVertex3f( s, -s,  s);
        GL11.glTexCoord2f(1, 1); GL11.glVertex3f( s,  s,  s);
        GL11.glTexCoord2f(0, 1); GL11.glVertex3f(-s,  s,  s);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, south);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); GL11.glVertex3f(-s, -s, -s);
        GL11.glTexCoord2f(1, 0); GL11.glVertex3f( s, -s, -s);
        GL11.glTexCoord2f(1, 1); GL11.glVertex3f( s,  s, -s);
        GL11.glTexCoord2f(0, 1); GL11.glVertex3f(-s,  s, -s);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, east);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); GL11.glVertex3f( s, -s, -s);
        GL11.glTexCoord2f(1, 0); GL11.glVertex3f( s, -s,  s);
        GL11.glTexCoord2f(1, 1); GL11.glVertex3f( s,  s,  s);
        GL11.glTexCoord2f(0, 1); GL11.glVertex3f( s,  s, -s);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, west);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); GL11.glVertex3f(-s, -s, -s);
        GL11.glTexCoord2f(1, 0); GL11.glVertex3f(-s, -s,  s);
        GL11.glTexCoord2f(1, 1); GL11.glVertex3f(-s,  s,  s);
        GL11.glTexCoord2f(0, 1); GL11.glVertex3f(-s,  s, -s);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, high);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); GL11.glVertex3f(-s,  s, -s);
        GL11.glTexCoord2f(1, 0); GL11.glVertex3f( s,  s, -s);
        GL11.glTexCoord2f(1, 1); GL11.glVertex3f( s,  s,  s);
        GL11.glTexCoord2f(0, 1); GL11.glVertex3f(-s,  s,  s);
        GL11.glEnd();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, down);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0); GL11.glVertex3f(-s, -s, -s);
        GL11.glTexCoord2f(1, 0); GL11.glVertex3f( s, -s, -s);
        GL11.glTexCoord2f(1, 1); GL11.glVertex3f( s, -s,  s);
        GL11.glTexCoord2f(0, 1); GL11.glVertex3f(-s, -s,  s);
        GL11.glEnd();

        GL11.glPopMatrix();
    }
    
}
