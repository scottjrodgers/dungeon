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

    EntityManager entityManager(){
        return entityMgr;
    }

    TextureManager textureManager(){
        return textureMgr;
    }

    private void showTopWorld(){
        setScreen(new TopWorldScreen(this));
    }
}
