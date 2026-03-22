package minecraft.fr.clemdefrance.World;

import java.util.ArrayList;
import java.util.List;

import minecraft.fr.clemdefrance.Screen.loadTexture;
import minecraft.fr.clemdefrance.Content.*;

public class World {

    int stone;
    public List<Block> blocks = new ArrayList<>();
    public static List<IdBlock> id = new ArrayList<>();

    public World() {
        stone = loadTexture.getTexture("Blocks/cobblestone.png");

for(int i = 0; i < 100; i++) {
    for(int j = 0; j < 100; j++) {

        Block b = new Block(1, stone, stone, stone, stone, stone, stone, i, j);

        b.x = i;
        b.y = 0;
        b.z = j;

        blocks.add(b);
        id.add(new IdBlock(i, j, "Cobblestone"));

        System.out.println("Block Charge : " + i + " , " +  j);
    }
}

}
    

    public void render() {
        for (Block b : blocks) {
            b.render();
        }
    }


public static boolean isBlock(int x, int z, int y, List<IdBlock> id) {
    return id.stream().anyMatch(b -> b.id1 == x && b.id2 == z && y == 1);
}

}