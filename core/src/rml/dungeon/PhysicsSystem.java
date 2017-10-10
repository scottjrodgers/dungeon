package rml.dungeon;

import java.util.Collection;

@SuppressWarnings("ConstantConditions")
class PhysicsSystem {
    private GameMap gameMap;

    PhysicsSystem(GameMap gm){
        gameMap = gm;
    }

    void update(float delta){
        if(delta > 0.1f) delta = 0.1f;

        Collection<VelocityComp> velocities = gameMap.entityMgr.get_all_velocities();
        for(VelocityComp v : velocities){
            Entity e = gameMap.entityMgr.get(v.EID);
            e.x = e.x + v.vx * delta;
            e.y = e.y + v.vy * delta;
            e.theta += v.vth * delta;

            float r = e.radius;
            float r2 = r * r;

            // Check for collisions with tile map
            int tile_x = ((int)e.x) / 64;
            int tile_y = ((int)e.y) / 64;
            int x1 = tile_x * 64;
            int x2 = x1 + 64;
            int y1 = tile_y * 64;
            int y2 = y1 + 64;

            boolean collision_detection = true;

            if(collision_detection) {
                // check neighbors
                if((e.x - r < x1) && gameMap.get_tile(tile_x - 1, tile_y) != GameMap.dirt){
                    e.x = x1 + r + 1;
                }
                if((e.x + r > x2) && gameMap.get_tile(tile_x+1, tile_y) != GameMap.dirt){
                    e.x = x2 -r - 1;
                }
                if((e.y - r < y1) && gameMap.get_tile(tile_x, tile_y-1) != GameMap.dirt){
                    e.y = y1 + r + 1;
                }
                if((e.y + r > y2) && gameMap.get_tile(tile_x, tile_y+1) != GameMap.dirt){
                    e.y = y2 - r - 1;
                }
                // now the diagonals
                if(Math.pow(x1 - e.x, 2) + Math.pow(y1 - e.y, 2) < r2 &&
                        gameMap.get_tile(tile_x-1,tile_y-1) != GameMap.dirt){
                    double d1 = Math.sqrt(Math.pow(x1 - e.x, 2) + Math.pow(y1 - e.y, 2));
                    e.x += ((r - d1) * (e.x - x1)) / d1;
                    e.y += ((r - d1) * (e.y - y1)) / d1;
                }
                if(Math.pow(x2 - e.x, 2) + Math.pow(y1 - e.y, 2) < r2 &&
                        gameMap.get_tile(tile_x+1,tile_y-1) != GameMap.dirt){
                    double d1 = Math.sqrt(Math.pow(x2 - e.x, 2) + Math.pow(y1 - e.y, 2));
                    e.x += ((r - d1) * (e.x - x2)) / d1;
                    e.y += ((r - d1) * (e.y - y1)) / d1;
                }
                if(Math.pow(x2 - e.x, 2) + Math.pow(y2 - e.y, 2) < r2 &&
                        gameMap.get_tile(tile_x+1,tile_y+1) != GameMap.dirt){
                    double d1 = Math.sqrt(Math.pow(x2 - e.x, 2) + Math.pow(y2 - e.y, 2));
                    e.x += ((r - d1) * (e.x - x2)) / d1;
                    e.y += ((r - d1) * (e.y - y2)) / d1;
                }
                if(Math.pow(x1 - e.x, 2) + Math.pow(y2 - e.y, 2) < r2 &&
                        gameMap.get_tile(tile_x-1, tile_y+1) != GameMap.dirt){
                    double d1 = Math.sqrt(Math.pow(x1 - e.x, 2) + Math.pow(y2 - e.y, 2));
                    e.x += ((r - d1) * (e.x - x1)) / d1;
                    e.y += ((r - d1) * (e.y - y2)) / d1;
                }
            }
        }
        // TODO: Implement collision detection
    }
}
