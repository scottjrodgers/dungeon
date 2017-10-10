package rml.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.util.Vector;

@SuppressWarnings({"SuspiciousNameCombination", "WeakerAccess"})
class MapFragment {
    static final short empty = 0;
    static final short ground = 1;
    static final short wall = 2;
    static final short portal = 3;

    int rows = 0;
    int cols = 0;
    short tiles[][];

    Vector<Portal> portals;


    MapFragment(String fname){
        portals = new Vector<Portal>();
        load(fname);
        discover_portals();
        //System.out.println("Fragment done!");
    }


    MapFragment(){
        portals = new Vector<Portal>();
    }


    MapFragment copy() {
        MapFragment new_frag = new MapFragment();
        new_frag.rows = rows;
        new_frag.cols = cols;
        new_frag.tiles = new short[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                new_frag.tiles[r][c] = tiles[r][c];

        for (Portal p : portals) {
            new_frag.portals.add(p.copy());
        }
        return new_frag;
    }


    void load(String fname){
        Vector<String> map_input = new Vector<String>();
        int max_width = 0;
        BufferedReader br = Gdx.files.internal("fragments/"+fname).reader(1024);
        try {
            String line = br.readLine();
            while(line != null){
                int line_length = line.length();
                if(line_length > max_width){
                    max_width = line_length;
                }
                map_input.add(line);
                line = br.readLine();
            }

            // initialize map
            rows = map_input.size();
            cols = max_width;
            tiles = new short[rows][cols];

            // fill map
            for(int r = 0; r < rows; r++){
                String row = map_input.get(r);
                for(int c = 0; c < cols; c++){
                    if(c < row.length()){
                        String ch = row.substring(c,c+1);
                        if(ch.equals("X")) tiles[r][c] = wall;
                        else if(ch.equals(".")) tiles[r][c] = ground;
                        else if(ch.equals("o")) tiles[r][c] = portal;
                        else tiles[r][c] = empty;
                    }
                    else{
                        tiles[r][c] = empty;
                    }
                }
            }
        } catch(java.io.IOException e){
            e.printStackTrace();
        }
    }


    private void discover_portals(){
        int x1,y1,x2,y2;
        int direction = 0;
        for(int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if(tiles[r][c] == portal) {
                    short up = empty;
                    short down = empty;
                    short left = empty;
                    short right = empty;
                    if (r < rows - 1) up = tiles[r + 1][c];
                    if (r > 0) down = tiles[r - 1][c];
                    if (c < cols - 1) right = tiles[r][c + 1];
                    if (c > 0) left = tiles[r][c - 1];

                    /* Because we are looping from bottom to top and from left to right, we should always find the
                     * leftmost and bottom-most cell of a portal first.
                     *
                     * Approach: identify neighbors for full portal.
                     *           create the portal record and insert into list
                     *           replace the 'portal' id for each affected cell with that for 'ground'.
                     */

                    if(left == wall){
                        // this is a horizontal portal starting at r,c
                        x1 = c;
                        y1 = r;
                        x2 = c;
                        y2 = r;
                        while(tiles[y2][x2+1] == portal){
                            x2++;
                        }
                        if(up == empty){
                            direction = Portal.NORTH;
                            assert(down == ground);
                        }
                        else if(down == empty){
                            direction = Portal.SOUTH;
                            assert(up == ground);
                        }
                        portals.add(new Portal(x1,y1,x2,y2,direction));
                        for(int x = x1; x <= x2; x++) tiles[y1][x] = ground;
                    }
                    else if(down == wall){
                        // this is the vertical case
                        x1 = c;
                        y1 = r;
                        x2 = c;
                        y2 = r;
                        while(tiles[y2+1][x2] == portal){
                            y2++;
                        }
                        if(left == empty){
                            direction = Portal.WEST;
                            assert(right == ground);
                        } else if(right == empty){
                            direction = Portal.EAST;
                            assert(left == ground);
                        }
                        portals.add(new Portal(x1,y1,x2,y2,direction));
                        for(int y = y1; y <= y2; y++) tiles[y][x1] = ground;
                    }
                }
            }
        }
    }


    void flip_diag(){
        short[][] new_tiles = new short[cols][rows];
        for(int c=0; c < cols; c++)
            for(int r=0; r < rows; r++)
                new_tiles[c][r] = tiles[r][c];
        tiles = new_tiles;

        int temp = rows;
        rows = cols;
        cols = temp;

        for(Portal p : portals){
            int tmp = p.x1;
            p.x1 = p.y1;
            p.y1 = tmp;
            tmp = p.x2;
            p.x2 = p.y2;
            p.y2 = tmp;

            if(p.x2 < p.x1){
                tmp = p.x1;
                p.x1 = p.x2;
                p.x2 = tmp;
            }
            if(p.y2 < p.y1){
                tmp = p.y1;
                p.y1 = p.y2;
                p.y2 = tmp;
            }


            switch(p.direction){
                case Portal.NORTH:
                    p.direction = Portal.EAST;
                    break;
                case Portal.EAST:
                    p.direction = Portal.NORTH;
                    break;
                case Portal.WEST:
                    p.direction = Portal.SOUTH;
                    break;
                case Portal.SOUTH:
                    p.direction = Portal.WEST;
                    break;
            }
        }
    }


    void flip_x(){
        // Flip horizontally
        short[][] new_tiles = new short[rows][cols];
        for(int r=0; r < rows; r++)
            for(int c=0; c < cols; c++)
                new_tiles[r][c] = tiles[r][cols-1-c];
        tiles = new_tiles;

        for(Portal p : portals) {
            p.x1 = cols - p.x1 - 1;
            p.x2 = cols - p.x2 - 1;

            if(p.x2 < p.x1){
                int tmp = p.x1;
                p.x1 = p.x2;
                p.x2 = tmp;
            }
            if(p.y2 < p.y1){
                int tmp = p.y1;
                p.y1 = p.y2;
                p.y2 = tmp;
            }

            switch (p.direction) {
                case Portal.WEST:
                    p.direction = Portal.EAST;
                    break;
                case Portal.EAST:
                    p.direction = Portal.WEST;
                    break;
            }
        }
    }


    void flip_y(){
        // Flip vertical
        short[][] new_tiles = new short[rows][cols];
        for(int r=0; r < rows; r++)
            for(int c=0; c < cols; c++)
                new_tiles[r][c] = tiles[rows-1-r][c];
        tiles = new_tiles;

        for(Portal p : portals) {
            p.y1 = rows - p.y1 - 1;
            p.y2 = rows - p.y2 - 1;

            if(p.x2 < p.x1){
                int tmp = p.x1;
                p.x1 = p.x2;
                p.x2 = tmp;
            }
            if(p.y2 < p.y1){
                int tmp = p.y1;
                p.y1 = p.y2;
                p.y2 = tmp;
            }

            switch (p.direction) {
                case Portal.NORTH:
                    p.direction = Portal.SOUTH;
                    break;
                case Portal.SOUTH:
                    p.direction = Portal.NORTH;
                    break;
            }
        }
    }


    void rotate_ccw(){
        flip_diag();
        flip_x();
    }


    void rotate_cw(){
        flip_diag();
        flip_y();
    }


    void rotate_180(){
        flip_x();
        flip_y();
    }
}

