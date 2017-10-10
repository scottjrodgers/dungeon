package rml.dungeon;

class VisibilityMask {

    private class Location {
        int side;
        double pos;
    }

    private class LocationPair{
        Location from;
        Location to;
    }

    private static final int top = 0;
    private static final int right = 1;
    private static final int bottom = 2;
    private static final int left = 3;

    private IntervalVector sides[];


    /*===========================================================================
    * Constructor
    *==========================================================================*/
    VisibilityMask(){
        sides = new IntervalVector[4];
        sides[top] = new IntervalVector();
        sides[right] = new IntervalVector();
        sides[bottom] = new IntervalVector();
        sides[left] = new IntervalVector();
    }


    /*===========================================================================
    * Reset to 100% open
    *==========================================================================*/
    void reset(){
        for(int i=0; i<4; i++){
            sides[i].reset();
        }
    }


    /*===========================================================================
    * Determine which side and which position on that side these coordinates
    *   correspond to
    *==========================================================================*/
    private Location calculate_location(double x, double y){
        Location loc = new Location();

        if(Math.abs(x) > Math.abs(y)){
            loc.pos = y/x;
            if(x > 0){
                loc.side = right;
            }
            else {
                loc.side = left;
            }
        }
        else {
            loc.pos = x/y;
            if(y > 0){
                loc.side = top;
            }
            else {
                loc.side = bottom;
            }
        }

        return loc;
    }


    /*===========================================================================
     * Determine the order of the points - which sides they're on, and which
     * direction gives the shortest interval.  Points will be walked in a
     * clockwise manner.
     *==========================================================================*/
    private LocationPair get_loc_pair(double x1, double y1, double x2, double y2){
        Location p1 = calculate_location(x1,y1);
        Location p2 = calculate_location(x2,y2);
        LocationPair pair = new LocationPair();

        switch(Math.abs(p2.side - p1.side)) {
            case 0:
                // Same Side
                if(p1.pos < p2.pos){
                    pair.from = p1;
                    pair.to = p2;
                }
                else{
                    pair.from = p2;
                    pair.to = p1;
                }
                break;
            case 1:
            case 3:
                // Adjacent sides
                int delta = p2.side - p1.side;
                if(delta == 1 || delta == -3) {
                    pair.from = p1;
                    pair.to = p2;
                }
                else{
                    pair.from = p2;
                    pair.to = p1;
                }
                break;
            case 2:
                // Opposite sides
                if(p1.pos > p2.pos) {
                    pair.from = p1;
                    pair.to = p2;
                }
                else{
                    pair.from = p2;
                    pair.to = p1;
                }
                break;
        }

        return pair;
    }


    /*===========================================================================
     * block the interval corresponding to x1,y1 to x2,y2
     *==========================================================================*/
    void block(double x1, double y1, double x2, double y2){
        LocationPair pair = get_loc_pair(x1,y1,x2,y2);

        int i = pair.from.side;
        double p1, p2;

        p1 = pair.from.pos;

        if(i == pair.to.side){
            p2 = pair.to.pos;
        }
        else{
            p2 = 1.0;
        }
        sides[i].block(p1, p2);
        System.out.printf("blocked from %f to %f on side %d\n", p1,p2, i);

        while(i != pair.to.side){
            i = (i+1) % 4;
            p1 = -1.0;

            if(i == pair.to.side){
                p2 = pair.to.pos;
            }
            else{
                p2 = 1.0;
            }

            System.out.printf("blocked from %f to %f on side %d\n", p1,p2, i);
            sides[i].block(p1, p2);
        }
    }


    /*===========================================================================
     * check if interval from x1,y1 to x2,y2 is blocked
     *==========================================================================*/
    boolean visible(double x1, double y1, double x2, double y2){
        LocationPair pair = get_loc_pair(x1,y1,x2,y2);

        int i = pair.from.side;
        double p1, p2;

        p1 = pair.from.pos;

        if(i == pair.to.side){
            p2 = pair.to.pos;
        }
        else{
            p2 = 1.0;
        }
        if(!sides[i].is_blocked(p1, p2)) return true;

        while(i != pair.to.side){
            i = (i+1) % 4;
            p1 = -1.0;

            if(i == pair.to.side){
                p2 = pair.to.pos;
            }
            else{
                p2 = 1.0;
            }

            if(!sides[i].is_blocked(p1, p2)) return true;
        }

        return false;
    }
}
