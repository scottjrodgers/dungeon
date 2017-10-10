package rml.dungeon;
import com.badlogic.gdx.Gdx;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

class GameMap {
    static final short dirt = 1;
    static final short wall = 100;
    static final short empty = 0;

    int MaxRooms = 300;
    int MaxPortalQueue = 50;
    int InitialPercentMany = 20;
    int IncrementalPercentMany = 10;
    int InitialPercentTwo = 80;

    Vector<MapFragment> one_portal;
    Vector<MapFragment> two_portal;
    Vector<MapFragment> many_portal;

    private short tiles[][];
    int start_x = 0;
    int start_y = 0;
    private int rows = 0;
    private int cols = 0;

    EntityManager entityMgr;
    TextureManager textureMgr;

    // TODO: add spatial grid containing entities (each has: capacity, count, EIDS[])

    GameMap() {
        this(2048, 2048);
    }

    GameMap(int r, int c) {
        rows = r;
        cols = c;
        entityMgr = new EntityManager();
        textureMgr = new TextureManager();
        tiles = new short[r][c];
        for(int rr = 0; rr < rows; rr++)
            for(int cc = 0; cc < cols; cc++)
                tiles[rr][cc] = 0;

        start_x = cols / 2;
        start_y = rows / 2;


        load();
        build();
    }

    short get_tile(int x, int y) {
        if (x >= 0 && x < cols && y >= 0 && y < rows) {
            return tiles[y][x];
        } else {
            return -1;
        }
    }

    private void load() {
        one_portal = new Vector<MapFragment>();
        two_portal = new Vector<MapFragment>();
        many_portal = new Vector<MapFragment>();
        String path = Gdx.files.getLocalStoragePath() + "fragments/";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".map")) {
                    MapFragment fragment = new MapFragment(file.getName());
                    if (fragment.portals.size() < 1) {
                        assert (false);
                    } else if (fragment.portals.size() == 1) {
                        one_portal.add(fragment);
                        System.out.println("one: " + file.getName());
                    } else if (fragment.portals.size() == 2) {
                        two_portal.add(fragment);
                        System.out.println("two: " + file.getName());
                    } else {
                        many_portal.add(fragment);
                        System.out.println("many: " + file.getName());
                    }
                }
            }
        }
        System.out.println("Map fragments loaded...");
    }


    private void place_room(MapFragment room, int xs, int ys, LinkedList<Portal> portal_queue) {
        place_room(room, xs, ys, portal_queue, -1);
    }


    private void place_room(MapFragment room, int xs, int ys, LinkedList<Portal> portal_queue, int source_portal_id){
        for(int r=0; r<room.rows; r++){
            for(int c=0; c<room.cols; c++){
                int cell = room.tiles[r][c];
                short tile = empty;
                switch(cell){
                    case MapFragment.ground:
                        tile = dirt;
                        tiles[ys+r][xs+c] = tile;
                        break;
                    case MapFragment.wall:
                        tile = wall;
                        if(tiles[ys+r][xs+c] != dirt)
                            tiles[ys+r][xs+c] = tile;
                        break;
                }
            }
        }

        for(int i=0; i<room.portals.size(); i++){
            if(i != source_portal_id) {
                Portal portal = room.portals.get(i).copy();
                portal.x1 += xs;
                portal.y1 += ys;
                portal.x2 += xs;
                portal.y2 += ys;
                portal_queue.addLast(portal);
            }
        }
    }


    private void build(){
        LinkedList<Portal> portal_queue = new LinkedList<Portal>();
        Random rnd = new Random();
        int room_count = 1;

        // Choose first room:
        int room_ID;
        //room_ID = rnd.nextInt(many_portal.size());
        room_ID = 2;
        MapFragment first_room = many_portal.get(room_ID).copy();

        // RANDOMIZE ORIENTATION:
        if(rnd.nextInt(10) > 4){
            first_room.flip_x();
        }
        if(rnd.nextInt(10) > 4){
            first_room.flip_y();
        }
        if(rnd.nextInt(10) > 4){
            first_room.flip_diag();
        }

        // Place first room;
        int xs = start_x - first_room.cols/2;
        int ys = start_y - first_room.rows/2;
        place_room(first_room, xs, ys, portal_queue);

        while(!portal_queue.isEmpty()){
            Portal source_portal = portal_queue.remove();

            MapFragment room;
            int room_type = rnd.nextInt(100) + 1;
            int queue_size = portal_queue.size();
            int thresholdMany = 100 - Math.max(0, InitialPercentMany - 2 * InitialPercentMany * room_count / MaxRooms +
                                Math.max(IncrementalPercentMany, IncrementalPercentMany * queue_size / MaxPortalQueue));
            int thresholdTwo = thresholdMany - Math.max(0, InitialPercentTwo - InitialPercentTwo * room_count / MaxRooms);
            if(room_type < thresholdTwo){
                room = one_portal.get(rnd.nextInt(one_portal.size()));
            } else if(room_type < thresholdMany){
                room = two_portal.get(rnd.nextInt(two_portal.size()));
            } else {
                room = many_portal.get(rnd.nextInt(many_portal.size()));
            }

            int portal_count = room.portals.size();
            int portal_num = rnd.nextInt(portal_count);

            int direction_difference = (room.portals.get(portal_num).direction - source_portal.direction + 4) % 4;
            switch(direction_difference){
                case 0:
                    room.rotate_180();
                    break;
                case 1:
                    room.rotate_cw();
                    break;
                case 3:
                    room.rotate_ccw();
                    break;
            }

            if(rnd.nextInt(10) > 4){
                switch(source_portal.direction){
                    case Portal.NORTH:
                    case Portal.SOUTH:
                        room.flip_x();
                        break;
                    case Portal.EAST:
                    case Portal.WEST:
                        room.flip_y();
                        break;
                }
            }

            Portal dest_portal = room.portals.get(portal_num);
            xs = source_portal.x1 - dest_portal.x1;
            ys = source_portal.y1 - dest_portal.y1;
            place_room(room, xs, ys, portal_queue, portal_num);
            room_count++;
            System.out.println("Rooms: " + room_count + ", Queue: " + queue_size);

        }
        System.out.println("Done!");
    }
}