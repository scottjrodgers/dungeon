package rml.dungeon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.HashMap;
import java.util.Map;

public class DungeonGame extends ApplicationAdapter implements InputProcessor {
    private Texture img;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    protected static Map<String, Boolean> keys;
    private static String UP = "U";
    private static String DOWN = "D";
    private static String LEFT = "L";
    private static String RIGHT = "R";


    @Override
    public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        keys = new HashMap<String, Boolean>();
        keys.put(LEFT, false);
        keys.put(RIGHT, false);
        keys.put(UP, false);
        keys.put(DOWN, false);


        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.translate(128*64, 128*64);
        camera.update();
        tiledMap = new TmxMapLoader().load("maps/map1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);
    }

    private void update(){
        int dx = 0;
        int dy = 0;
        if(keys.get(UP))
            dy += 4;
        if(keys.get(DOWN))
            dy -= 4;
        if(keys.get(LEFT))
            dx -= 4;
        if(keys.get(RIGHT))
            dx += 4;
        camera.translate(dx, dy);

    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.update();
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
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
        if(keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        if(keycode == Input.Keys.Q)
            Gdx.app.exit();
        if(keycode == Input.Keys.ESCAPE)
            Gdx.app.exit();
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
