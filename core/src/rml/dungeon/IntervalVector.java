package rml.dungeon;

import java.util.Vector;

class IntervalVector {
    private static final int start = 1;
    private static final int stop = 0;

    private class Transition{
        double pos = 0.0;
        int state = 0;

        Transition(double p, int s){
            pos = p;
            state = s;
        }
    }

    private Vector<Transition> intervals;


    /*===========================================================================
     * Constructor
     *==========================================================================*/
    IntervalVector(){
        intervals = new Vector<Transition>();
        reset();
    }


    /*===========================================================================
     * Reset to an open interval
     *==========================================================================*/
    void reset(){
        intervals.removeAllElements();
    }


    /*===========================================================================
     * Block the interval between p1 and p2
     *   Assumes that p1 < p2
     *==========================================================================*/
    void block(double p1, double p2){
        int i;
        double orig_right = -1.0;

        // clip to the interval range
        p1 = Math.max(p1, -1.0);
        p2 = Math.min(p2, 1.0);

        // PART I: Add in the two new transitions for P1 and P2

        // Identify where to insert the transition for p1
        i = 0;
        while(i < intervals.size() && intervals.get(i).pos < p1) i++;

        // Insert p1
        intervals.insertElementAt(new Transition(p1, start), i);

        // Identify where to insert p2
        while(i < intervals.size() && intervals.get(i).pos < p2) i++;

        // Insert p2
        intervals.insertElementAt(new Transition(p2, stop), i);

        // PART II: Remove redundant transitions
        int depth = 0;
        i = 0;
        while(i < intervals.size()){
            if(intervals.get(i).state == start){
                if(depth > 0){
                    intervals.removeElementAt(i);
                }
                else{
                    i++;
                }
                depth++;
            }
            else { // state == stop
                if(depth > 1){
                    intervals.removeElementAt(i);
                }
                else{
                    i++;
                }
                depth--;
            }
        }

    }


    /*===========================================================================
     * Query to see if there are any blocked intervals between p1 and p2
     *==========================================================================*/
    boolean is_blocked(double p1, double p2){
        int i = 0;
        int depth = 0;
        int query_depth = 0;

        while(i < intervals.size()){
            if(p1 <= intervals.get(i).pos){
                query_depth++;
                if(depth == 0 && p1 < intervals.get(i).pos){
                    return false;
                }
                else{
                    p1 = 999; // far greater than our limit of 1.0 for this intervalVector
                }
            }
            if(p2 <= intervals.get(i).pos){
                query_depth--;
                if(depth == 0){
                    return false;
                }
                else{
                    p2 = 999; // far greater than our limit of 1.0 for this intervalVector
                }
            }
            if(intervals.get(i).state == start){
                depth++;
            }
            if(intervals.get(i).state == stop){
                depth--;
                if(query_depth == 0){
                    return false;
                }
            }
            i++;
        }

        return true;
    }
}
