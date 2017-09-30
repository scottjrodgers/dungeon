package rml.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import java.util.HashMap;
import java.util.Map;

public class TopWorldScreen extends InputAdapter implements Screen {
    private Texture hero;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;
    protected static Map<String, Boolean> keys;
    private static String UP = "U";
    private static String DOWN = "D";
    private static String LEFT = "L";
    private static String RIGHT = "R";
    private int px, py;
    private DungeonGame game;
    private SpriteBatch batch;

    public TopWorldScreen(DungeonGame game){
        keys = new HashMap<String, Boolean>();
        keys.put(LEFT, false);
        keys.put(RIGHT, false);
        keys.put(UP, false);
        keys.put(DOWN, false);
        px = 136*64;
        py = 157*64;
        tiledMap = new TmxMapLoader().load("maps/map1 .tmx");
        this.game = game;
        hero = new Texture(Gdx.files.internal("hero-2.png"));

        batch = new SpriteBatch();
    }

    @Override
    public void show(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.translate(px, py);
        camera.update();
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera.setToOrtho(false, w, h);
        camera.translate(px, py);
        camera.update();
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

    private void update(){
        int dx = 0;
        int dy = 0;
        if(keys.get(UP))
            dy += 3;
        if(keys.get(DOWN))
            dy -= 3;
        if(keys.get(LEFT))
            dx -= 3;
        if(keys.get(RIGHT))
            dx += 3;
        camera.translate(dx, dy);

    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.update();
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.begin();
        batch.draw(hero, 640, 512);
        batch.end();
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
    public void dispose(){

    }

}
