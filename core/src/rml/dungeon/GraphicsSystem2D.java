package rml.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Vector;

class GraphicsSystem2D {
    private GameMap gameMap;
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private int tx_dirt, tx_wall, tx_mask;

    GraphicsSystem2D(GameMap gm){
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();

        gameMap = gm;
        tx_dirt = gm.textureMgr.load("dirt","dirt.png");
        tx_wall = gm.textureMgr.load("wall","wall.png");
        tx_mask = gm.textureMgr.load("mask", "mask.png");

    }

    //================ Draw Tile =====================
    private void draw_tile(int tx, int ty, short tile, int px, int py){
        int x = 640 + tx * 64 - px;
        int y = 512 + ty * 64 - py;

        switch(tile){
            case GameMap.dirt:
                batch.draw(gameMap.textureMgr.get(tx_dirt), x,y);
                break;
            case GameMap.wall:
                batch.draw(gameMap.textureMgr.get(tx_wall), x,y);
                break;
            case -1:
                batch.draw(gameMap.textureMgr.get(tx_mask), x,y);
        }
    }

    //================= Render ====================
    void render(int en_hero){
        Entity eh = gameMap.entityMgr.get(en_hero);
        int px = (int)eh.x;
        int py = (int)eh.y;
        int tx = px / 64;
        int ty = py / 64;
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        VisibilityMask next_mask = new VisibilityMask();
        VisibilityMask mask = next_mask;

//        System.out.printf("\n\n**** Frame Begin:  Player at (%d,%d) == (%d,%d) **********************************\n",
//                px, py, tx, ty);

        // Begin sprite batch
        batch.begin();

        // draw current tile
        draw_tile(tx, ty, gameMap.get_tile(tx, ty), px, py);

//        batch.end();

        for(int i = 1; i < 11; i++) {
//            mask = next_mask;
            //next_mask = new VisibilityMask();

//            batch.begin();

            // draw up
            for (int j = -i; j < i + 1; j++) {
                int ttx = tx + j;
                int tty = ty + i;
                double dx1 = 64 * ttx - px;
                double dy1 = 64 * tty - py;
                double dx2 = dx1 + 64;
                double dy2 = dy1 + 64;

//                boolean vis_bottom = mask.visible(dx1,dy1,dx2,dy1);
//                boolean vis_top = mask.visible(dx1,dy2,dx2,dy2);
//                boolean vis_left = mask.visible(dx1,dy1,dx1,dy2);
//                boolean vis_right = mask.visible(dx2,dy1,dx2,dy2);
                short tile = gameMap.get_tile(ttx, tty);
//                System.out.printf("check (%d,%d)(%d) => (%f,%f),(%f,%f) top:%b, right:%b, bottom:%b, left:%b\n",
//                        ttx,tty, tile, dx1,dy1, dx2,dy2, vis_top, vis_right, vis_bottom, vis_left);
//                if(vis_top || vis_bottom || vis_left || vis_right){
                    draw_tile(ttx, tty, tile, px, py);
//                    if(tile != GameMap.dirt) {
//                        System.out.printf("Block: (%d,%d) => (%f,%f),(%f,%f)\n", ttx,tty, dx1,dy1, dx2,dy2);
//                        next_mask.block(dx1, dy1, dx2, dy1);
//                        next_mask.block(dx1, dy2, dx2, dy2);
//                        next_mask.block(dx1, dy1, dx1, dy2);
//                        next_mask.block(dx2, dy1, dx2, dy2);
//                    }
//                }
//                else{
//                    boolean chk_bottom = mask.visible(dx1,dy1,dx2,dy1);
//                    boolean chk_top = mask.visible(dx1,dy2,dx2,dy2);
//                    boolean chk_left = mask.visible(dx1,dy1,dx1,dy2);
//                    boolean chk_right = mask.visible(dx2,dy1,dx2,dy2);
//                    draw_tile(ttx, tty, tile, px, py);
//                    draw_tile(ttx, tty, (short)-1, px, py);
//                }
            }

            // draw down
            for (int j = -i; j < i + 1; j++) {
                int ttx = tx + j;
                int tty = ty - i;
                double dx1 = 64 * ttx - px;
                double dy1 = 64 * tty - py;
                double dx2 = dx1 + 64;
                double dy2 = dy1 + 64;

//                boolean vis_bottom = mask.visible(dx1,dy1,dx2,dy1);
//                boolean vis_top = mask.visible(dx1,dy2,dx2,dy2);
//                boolean vis_left = mask.visible(dx1,dy1,dx1,dy2);
//                boolean vis_right = mask.visible(dx2,dy1,dx2,dy2);
                short tile = gameMap.get_tile(ttx, tty);
//                System.out.printf("check (%d,%d)(%d) => (%f,%f),(%f,%f) top:%b, right:%b, bottom:%b, left:%b\n",
//                        ttx,tty, tile, dx1,dy1, dx2,dy2, vis_top, vis_right, vis_bottom, vis_left);
//                if(vis_top || vis_bottom || vis_left || vis_right){
                    draw_tile(ttx, tty, tile, px, py);
//                    if(tile != GameMap.dirt) {
//                        System.out.printf("Block: (%d,%d) => (%f,%f),(%f,%f)\n", ttx,tty, dx1,dy1, dx2,dy2);
//                        next_mask.block(dx1, dy1, dx2, dy1);
//                        next_mask.block(dx1, dy2, dx2, dy2);
//                        next_mask.block(dx1, dy1, dx1, dy2);
//                        next_mask.block(dx2, dy1, dx2, dy2);
//                    }
//                }
//                else{
//                    boolean chk_bottom = mask.visible(dx1,dy1,dx2,dy1);
//                    boolean chk_top = mask.visible(dx1,dy2,dx2,dy2);
//                    boolean chk_left = mask.visible(dx1,dy1,dx1,dy2);
//                    boolean chk_right = mask.visible(dx2,dy1,dx2,dy2);
//                    draw_tile(ttx, tty, tile, px, py);
//                    draw_tile(ttx, tty, (short)-1, px, py);
//                }
            }

            // draw left
            for (int j = -i + 1; j < i; j++) {
                int ttx = tx - i;
                int tty = ty + j;
                double dx1 = 64 * ttx - px;
                double dy1 = 64 * tty - py;
                double dx2 = dx1 + 64;
                double dy2 = dy1 + 64;

//                boolean vis_bottom = mask.visible(dx1,dy1,dx2,dy1);
//                boolean vis_top = mask.visible(dx1,dy2,dx2,dy2);
//                boolean vis_left = mask.visible(dx1,dy1,dx1,dy2);
//                boolean vis_right = mask.visible(dx2,dy1,dx2,dy2);
                short tile = gameMap.get_tile(ttx, tty);
//                System.out.printf("check (%d,%d)(%d) => (%f,%f),(%f,%f) top:%b, right:%b, bottom:%b, left:%b\n",
//                        ttx,tty, tile, dx1,dy1, dx2,dy2, vis_top, vis_right, vis_bottom, vis_left);
//                if(vis_top || vis_bottom || vis_left || vis_right){
                    draw_tile(ttx, tty, tile, px, py);
//                    if(tile != GameMap.dirt) {
//                        System.out.printf("Block: (%d,%d) => (%f,%f),(%f,%f)\n", ttx,tty, dx1,dy1, dx2,dy2);
//                        next_mask.block(dx1, dy1, dx2, dy1);
//                        next_mask.block(dx1, dy2, dx2, dy2);
//                        next_mask.block(dx1, dy1, dx1, dy2);
//                        next_mask.block(dx2, dy1, dx2, dy2);
//                    }
//                }
//                else{
//                    boolean chk_bottom = mask.visible(dx1,dy1,dx2,dy1);
//                    boolean chk_top = mask.visible(dx1,dy2,dx2,dy2);
//                    boolean chk_left = mask.visible(dx1,dy1,dx1,dy2);
//                    boolean chk_right = mask.visible(dx2,dy1,dx2,dy2);
//                    draw_tile(ttx, tty, tile, px, py);
//                    draw_tile(ttx, tty, (short)-1, px, py);
//                }
            }

            // draw right
            for (int j = -i + 1; j < i; j++) {
                int ttx = tx + i;
                int tty = ty + j;
                double dx1 = 64 * ttx - px;
                double dy1 = 64 * tty - py;
                double dx2 = dx1 + 64;
                double dy2 = dy1 + 64;

//                boolean vis_bottom = mask.visible(dx1,dy1,dx2,dy1);
//                boolean vis_top = mask.visible(dx1,dy2,dx2,dy2);
//                boolean vis_left = mask.visible(dx1,dy1,dx1,dy2);
//                boolean vis_right = mask.visible(dx2,dy1,dx2,dy2);
                short tile = gameMap.get_tile(ttx, tty);
//                System.out.printf("check (%d,%d)(%d) => (%f,%f),(%f,%f) top:%b, right:%b, bottom:%b, left:%b\n",
//                        ttx,tty, tile, dx1,dy1, dx2,dy2, vis_top, vis_right, vis_bottom, vis_left);
//                if(vis_top || vis_bottom || vis_left || vis_right){
                    draw_tile(ttx, tty, tile, px, py);
//                    if(tile != GameMap.dirt) {
//                        System.out.printf("Block: (%d,%d) => (%f,%f),(%f,%f)\n", ttx,tty, dx1,dy1, dx2,dy2);
//                        next_mask.block(dx1, dy1, dx2, dy1);
//                        next_mask.block(dx1, dy2, dx2, dy2);
//                        next_mask.block(dx1, dy1, dx1, dy2);
//                        next_mask.block(dx2, dy1, dx2, dy2);
//                    }
//                }
//                else{
//                    boolean chk_bottom = mask.visible(dx1,dy1,dx2,dy1);
//                    boolean chk_top = mask.visible(dx1,dy2,dx2,dy2);
//                    boolean chk_left = mask.visible(dx1,dy1,dx1,dy2);
//                    boolean chk_right = mask.visible(dx2,dy1,dx2,dy2);
//                    draw_tile(ttx, tty, tile, px, py);
//                    draw_tile(ttx, tty, (short)-1, px, py);
//                }
            }

//            batch.end();
//
//            // debug
//            shapes.begin(ShapeRenderer.ShapeType.Line);
//
//            // debug up
//            for (int j = -i; j < i + 1; j++) {
//                int ttx = tx + j;
//                int tty = ty - i;
//                double dx1 = 64 * ttx - px + 640;
//                double dy1 = 64 * tty - py + 512;
//                double dx2 = dx1 + 64;
//                double dy2 = dy1 + 64;
//
//                boolean vis_bottom = mask.visible(dx1,dy1,dx2,dy1);
//                if(!vis_bottom){
//                    shapes.setColor(1,0,0,1);
//                    shapes.line((float)dx1,(float)dy1,(float)dx2,(float)dy1);
//                    shapes.line((float)dx1,(float)dy1-1,(float)dx2,(float)dy1-1);
//                }
//            }
//
//            // debug down
//            for (int j = -i; j < i + 1; j++) {
//                int ttx = tx + j;
//                int tty = ty + i;
//                double dx1 = 64 * ttx - px + 640;
//                double dy1 = 64 * tty - py + 512;
//                double dx2 = dx1 + 64;
//                double dy2 = dy1 + 64;
//
//                boolean vis_top = mask.visible(dx1,dy2,dx2,dy2);
//                if(!vis_top){
//                    shapes.setColor(1,0,0,1);
//                    shapes.line((float)dx1,(float)dy2,(float)dx2,(float)dy2);
//                    shapes.line((float)dx1,(float)dy2+1,(float)dx2,(float)dy2+1);
//                }
//            }
//
//            // debug left
//            for (int j = -i + 1; j < i; j++) {
//                int ttx = tx - i;
//                int tty = ty + j;
//                double dx1 = 64 * ttx - px + 640;
//                double dy1 = 64 * tty - py + 512;
//                double dx2 = dx1 + 64;
//                double dy2 = dy1 + 64;
//
//                boolean vis_right = mask.visible(dx2,dy1,dx2,dy2);
//                if(!vis_right){
//                    shapes.setColor(1,0,0,1);
//                    shapes.line((float)dx2,(float)dy1,(float)dx2,(float)dy2);
//                    shapes.line((float)dx2+1,(float)dy1,(float)dx2+1,(float)dy2);
//                }
//            }
//
//            // debug right
//            for (int j = -i + 1; j < i; j++) {
//                int ttx = tx + i;
//                int tty = ty + j;
//                double dx1 = 64 * ttx - px + 640;
//                double dy1 = 64 * tty - py + 512;
//                double dx2 = dx1 + 64;
//                double dy2 = dy1 + 64;
//
//                boolean vis_left = mask.visible(dx1,dy1,dx1,dy2);
//                if(!vis_left){
//                    shapes.setColor(1,0,0,1);
//                    shapes.line((float)dx1,(float)dy1,(float)dx1,(float)dy2);
//                    shapes.line((float)dx1-1,(float)dy1,(float)dx1-1,(float)dy2);
//                }
//            }
//
//            shapes.end();
        }

//        batch.begin();

        Vector<Entity> elist = gameMap.entityMgr.get_entities();
        for (Entity ent : elist) {
            if (ent.txtID >= 0) {
                batch.draw(gameMap.textureMgr.get(ent.txtID),
                        640 + (int)ent.x - px - 32,
                        512 + (int)ent.y - py - 32);  // TODO: make this be a non-hardcoded 1/2 width
            }
        }

        batch.end();

    }
}
