package rml.dungeon;
import java.util.Random;

class GameMap {
    static final short dirt = 1;
    static final short wall = 100;

    short tiles[][];
    int start_x = 0;
    int start_y = 0;
    int rows = 128;
    int cols = 128;

    GameMap(){
        init();
    }


    // DEBUG
/*    private void init(){
        tiles = new short[128][128];
        for(int r = 0; r < 7; r++)
            for(int c = 0; c < 7; c++)
                tiles[r][c] = wall;
        for(int r = 1; r < 6; r++)
            for(int c = 1; c < 6; c++)
                tiles[r][c] = dirt;

        tiles[1][1] = wall;
        tiles[5][5] = wall;
        tiles[1][5] = wall;
        tiles[5][1] = wall;

        start_x = 3;
        start_y = 3;
    }*/

    private void init(){
        Random rnd = new Random();
        tiles = new short[rows][cols];
        for(int r=1; r<rows; r++){
            tiles[r][0] = wall;
            tiles[r][64] = wall;
            tiles[0][r] = wall;
            tiles[64][r] = wall;
            for(int c=1; c<cols; c++){
                tiles[r][c] = dirt;
            }
        }
        for(int i=0; i<1000; i++){
            int r = rnd.nextInt(63) + 1;
            int c = rnd.nextInt(63) + 1;
            tiles[r][c] = wall;
        }

        tiles[64][64] = dirt;
        start_x = 64;
        start_y = 64;
    }
}
