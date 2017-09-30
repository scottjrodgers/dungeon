package rml.dungeon;

import com.badlogic.gdx.Game;

public class DungeonGame extends Game {

    public void create(){
        showTopWorld();
    }

    public void showTopWorld(){
        setScreen(new TopWorldScreen(this));
    }
}
