package rml.dungeon;

import com.badlogic.gdx.Game;

public class DungeonGame extends Game {
    private EntityManager entityMgr;
    private TextureManager textureMgr;

    public void create(){
        entityMgr = new EntityManager();
        textureMgr = new TextureManager();

        showTopWorld();
    }

    public EntityManager entityManager(){
        return entityMgr;
    }

    public TextureManager textureManager(){
        return textureMgr;
    }

    public void showTopWorld(){
        setScreen(new TopWorldScreen(this));
    }
}
