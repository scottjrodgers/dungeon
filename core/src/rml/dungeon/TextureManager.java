package rml.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.Vector;
import java.util.HashMap;

public class TextureManager {

    private int max_ID = 0;
    private Vector<Texture> textures;
    private HashMap<String, Integer> index;

    public TextureManager(){
        textures = new Vector<Texture>();
        index = new HashMap<String, Integer>();
    }

    public int load(String name, String fname){
        if(index.containsKey(name)) return index.get(name);
        else{
            int ID = max_ID;
            max_ID++;

            Texture t = new Texture(Gdx.files.internal(fname));
            textures.add(ID, t);
            return ID;
        }
    }

    public Texture get(int ID){
        return textures.get(ID);
    }

    public Texture get(String name){
        int ID = index.get(name);
        return get(ID);
    }
}
