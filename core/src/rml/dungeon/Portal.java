package rml.dungeon;

class Portal {
    static final int NORTH = 1;
    static final int EAST = 2;
    static final int SOUTH = 3;
    static final int WEST = 4;

    int x1,y1,x2,y2;
    int direction;
    int width;

    Portal(int ax, int ay, int bx, int by, int dir){
        if(ay == by){
            y1 = ay;
            y2 = by;
            if(ax < bx){
                x1 = ax;
                x2 = bx;
            } else {
                x1 = bx;
                x2 = ax;
            }
        } else if(ax == bx){
            x1 = ax;
            x2 = bx;
            if(ay < by){
                y1 = ay;
                y2 = by;
            } else {
                y1 = by;
                y2 = ay;
            }
        }

        width = 1 + Math.max(bx-ax, by-ay);

        direction = dir;
    }

    Portal copy(){
        Portal p = new Portal(x1,y1,x2,y2,direction);
        return p;
    }
}
