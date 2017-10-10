package rml.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import java.util.HashMap;
import java.util.Map;

public class TopWorldScreen extends InputAdapter implements Screen {
    private static Map<String, Boolean> keys;
    private static String UP = "U";
    private static String DOWN = "D";
    private static String LEFT = "L";
    private static String RIGHT = "R";
    private int en_hero;

    //private DungeonGame game;
    private GameMap gameMap;
    private GraphicsSystem2D graphics;
    private PhysicsSystem physics;


    TopWorldScreen(DungeonGame game){
        //this.game = game;
        gameMap = new GameMap(); // TODO: extract out the level to a separate data structure / system

        graphics = new GraphicsSystem2D(gameMap);
        physics = new PhysicsSystem(gameMap);

        keys = new HashMap<String, Boolean>();
        keys.put(LEFT, false);
        keys.put(RIGHT, false);
        keys.put(UP, false);
        keys.put(DOWN, false);

        // Textures
        int tx_hero = gameMap.textureMgr.load("hero","smiley.png");

        // Entities
        en_hero = gameMap.entityMgr.create_entity(gameMap.start_x * 64f + 32f, gameMap.start_y * 64f + 32f, 0f);
        gameMap.entityMgr.get(en_hero).txtID = tx_hero;
        gameMap.entityMgr.get(en_hero).radius = 31;
    }


    @Override
    public void show(){
//        float w = Gdx.graphics.getWidth();
//        float h = Gdx.graphics.getHeight();

        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void resize(int width, int height) {
//        float w = Gdx.graphics.getWidth();
//        float h = Gdx.graphics.getHeight();
    }


    @Override
    public void hide(){

    }


    @Override
    public void pause(){

    }


    @Override
    public void resume(){

    }


    private void handle_input() {
        int dx = 0;
        int dy = 0;
        int spd = 512;
        if (keys.get(UP))
            dy += spd;
        if (keys.get(DOWN))
            dy -= spd;
        if (keys.get(LEFT))
            dx -= spd;
        if (keys.get(RIGHT))
            dx += spd;

        gameMap.entityMgr.set_velocity(en_hero, dx, dy, 0);
    }


    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.handle_input();

        physics.update(delta);
        graphics.render(en_hero);
    }


    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.LEFT)
            keys.put(LEFT,true);
        if(keycode == Input.Keys.RIGHT)
            keys.put(RIGHT,true);
        if(keycode == Input.Keys.UP)
            keys.put(UP,true);
        if(keycode == Input.Keys.DOWN)
            keys.put(DOWN,true);

        return false;
    }


    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT)
            keys.put(LEFT,false);
        if(keycode == Input.Keys.RIGHT)
            keys.put(RIGHT,false);
        if(keycode == Input.Keys.UP)
            keys.put(UP,false);
        if(keycode == Input.Keys.DOWN)
            keys.put(DOWN,false);
        if(keycode == Input.Keys.Q)
            Gdx.app.exit();
        if(keycode == Input.Keys.ESCAPE)
            Gdx.app.exit();
        return false;
    }


    @Override
    public void dispose(){

    }

}
