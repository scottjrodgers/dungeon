package rml.dungeon;
import java.util.Random;

public class Level {
    public static final short dirt = 1;
    public static final short wall = 100;

    public short tiles[][];
    public int start_x = 0;
    public int start_y = 0;
    public int rows = 128;
    public int cols = 128;

    public Level(){
        init();
    }

    public void init(){
        Random rnd = new Random();
        tiles = new short[128][128];
        for(int r=1; r<64; r++){
            tiles[r][0] = wall;
            tiles[r][64] = wall;
            tiles[0][r] = wall;
            tiles[64][r] = wall;
            for(int c=1; c<64; c++){
                tiles[r][c] = dirt;
            }
        }
        for(int i=0; i<400; i++){
            int r = rnd.nextInt(63) + 1;
            int c = rnd.nextInt(63) + 1;
            tiles[r][c] = wall;
        }

        tiles[32][32] = dirt;
        start_x = 32;
        start_y = 32;
    }
}
