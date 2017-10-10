package rml.dungeon;

import com.badlogic.gdx.Game;

public class DungeonGame extends Game {

    public void create(){
        showTopWorld();
    }

    private void showTopWorld(){
        setScreen(new TopWorldScreen(this));
    }
}
