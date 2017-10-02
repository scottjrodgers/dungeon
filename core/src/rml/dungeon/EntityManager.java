/*
    Entity manager.
    In the future, i'll want to optimize this for cache coherence, for instance using arrays of Entities, not a vector
 */

package rml.dungeon;

import rml.dungeon.Entity;

import java.util.Collection;
import java.util.Vector;
import java.util.Stack;

public class EntityManager {
    private int max_EID = 0;
    private Vector<Entity> entities;
    private Stack<Integer> freelist;
    private ComponentMap<VelocityComp> velocity_comp;

    public EntityManager(){
        entities = new Vector<Entity>();
        freelist = new Stack<Integer>();
        ComponentMap<VelocityComp> velocity_comp = new ComponentMap<VelocityComp>();
    }

    /*=============================================================*
     * Entity Functions
     *=============================================================*/
    public int create_entity(float x, float y, float theta){
        int eid = 0;
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

    public Entity get(int EID){
        return entities.get(EID);
    }

    public void set_pos(int eid, float x, float y, float theta){
        Entity e = get(eid);
        e.x = x;
        e.y = y;
        e.theta = theta;
    }

    public void set_texture(int EID, int txtID){
        entities.get(EID).txtID = txtID;
    }

    // TODO: add bounding-box setter / getter

    public void delete(int EID){
        set_pos(EID, 0,0,0);
        set_texture(EID, -1);
        freelist.push(EID);
    }

    public Vector<Entity> get_entities(){
        return entities;
    }


    /*=============================================================*
     * Velocity Component Functions
     *=============================================================*/
    public void set_velocity(int EID, float vx, float vy, float vth){
        if(velocity_comp.exists(EID)){
            VelocityComp v = velocity_comp.get(EID);
            v.vx = vx;
            v.vy = vy;
            v.vth = vth;
        }
    }

    public VelocityComp get_velocity(int EID){
        return velocity_comp.get(EID);
    }

    public Collection<VelocityComp> get_all_velocities(){
        return velocity_comp.entries();
    }
}
