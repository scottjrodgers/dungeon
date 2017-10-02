/*
    Entity manager.
    In the future, i'll want to optimize this for cache coherence, for instance using arrays of Entities, not a vector
 */

package rml.dungeon;

import java.util.Collection;
import java.util.Vector;
import java.util.Stack;

class EntityManager {
    private int max_EID = 0;
    private Vector<Entity> entities;
    private Stack<Integer> freelist;
    private ComponentMap<VelocityComp> velocity_comp;

    EntityManager(){
        entities = new Vector<Entity>();
        freelist = new Stack<Integer>();
        velocity_comp = new ComponentMap<VelocityComp>();
        ComponentMap<VelocityComp> velocity_comp = new ComponentMap<VelocityComp>();
    }

    /*=============================================================*
     * Entity Functions
     *=============================================================*/
    int create_entity(float x, float y, float theta){
        int eid;
        if(freelist.empty()){
            eid = max_EID;
            max_EID += 1;
            entities.add(eid, new Entity());
        }
        else{
            eid = freelist.pop();
        }
        set_pos(eid, x, y, theta);
        return eid;
    }

    Entity get(int EID){
        return entities.get(EID);
    }

    void set_pos(int eid, float x, float y, float theta){
        Entity e = get(eid);
        e.x = x;
        e.y = y;
        e.theta = theta;
    }

    void set_texture(int EID, int txtID){
        entities.get(EID).txtID = txtID;
    }

    // TODO: add bounding-box setter / getter

    void delete(int EID){
        set_pos(EID, 0,0,0);
        set_texture(EID, -1);
        freelist.push(EID);
    }

    Vector<Entity> get_entities(){
        return entities;
    }


    /*=============================================================*
     * Velocity Component Functions
     *=============================================================*/
    void set_velocity(int EID, float vx, float vy, float vth){
        if(velocity_comp.exists(EID)){
            VelocityComp v = velocity_comp.get(EID);
            v.EID = EID;
            v.vx = vx;
            v.vy = vy;
            v.vth = vth;
        }
        else{
            VelocityComp v = new VelocityComp();
            v.EID = EID;
            v.vx = vx;
            v.vy = vy;
            v.vth = vth;
            velocity_comp.add(EID, v);
        }
    }

    VelocityComp get_velocity(int EID){
        return velocity_comp.get(EID);
    }

    Collection<VelocityComp> get_all_velocities(){
        return velocity_comp.entries();
    }
}
