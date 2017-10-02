package rml.dungeon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

class GraphicsSystem2D {
    private EntityManager entityMgr;
    private TextureManager textureMgr;
    private SpriteBatch batch;
    private int tx_dirt, tx_wall;


    GraphicsSystem2D(EntityManager em, TextureManager tm){
        entityMgr = em;
        textureMgr = tm;
        batch = new SpriteBatch();
        tx_dirt = textureMgr.load("dirt","dirt.png");
        tx_wall = textureMgr.load("wall","wall.png");

    }

    void render(int en_hero, GameMap level){
        Entity eh = entityMgr.get(en_hero);
        int px = (int)eh.x;
        int py = (int)eh.y;
        batch.begin();
        for(int r=0; r < level.rows; r++){
            for(int c = level.cols-1; c >= 0; c--){
                int x = 640 + c * 64 - px;
                int y = 512 + r * 64 - py;
                short t = level.tiles[r][c];
                switch(t){
                    case GameMap.dirt:
                        batch.draw(textureMgr.get(tx_dirt), x,y);
                        break;
                    case GameMap.wall:
                        batch.draw(textureMgr.get(tx_wall), x,y);
                        break;
                }
            }
        }

        Vector<Entity> elist = entityMgr.get_entities();
        for (Entity ent : elist) {
            if (ent.txtID >= 0) {
                batch.draw(textureMgr.get(ent.txtID),
                        640 + (int)ent.x - px - 32,
                        512 + (int)ent.y - py - 32);  // TODO: make this be a non-hardcoded 1/2 width
            }
        }
        batch.end();

    }
}
