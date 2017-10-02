package rml.dungeon;
import java.util.Collection;
import java.util.HashMap;
//import rml.dungeon.Component;

public class ComponentMap<E> {
    HashMap<Integer, E> hashmap;

    public ComponentMap(){
        hashmap = new HashMap<Integer, E>();
    }

    public E get(int EID){
        return hashmap.get(EID);
    }

    public void add(int EID, E element){
        hashmap.put(EID, element);
    }

    public Collection<E> entries(){
        return hashmap.values();
    }

    public boolean exists(int EID){
        return hashmap.containsKey(EID);
    }

    public void remove(int EID){
        hashmap.remove(EID);
    }
}
