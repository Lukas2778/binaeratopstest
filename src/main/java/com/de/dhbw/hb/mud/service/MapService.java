package com.de.dhbw.hb.mud.service;


import com.de.dhbw.hb.mud.model.Room;
import com.de.dhbw.hb.mud.model.RoomCords;
import com.de.dhbw.hb.mud.model.Tupel;
import com.de.dhbw.hb.mud.service.dungeondata.DungeonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class MapService {

    @Autowired
    DungeonDataService dungeonDataService;

    private ArrayList<RoomCords>cords;
    private HashMap<Long,Room> room;
    private HashMap<Long,Room> addedRooms;

    public BufferedImage createMap(long dungeonID){
        cords=new ArrayList();
        room=new HashMap<>();
        addedRooms=new HashMap<>();

        List<Room> rooms= dungeonDataService.getAllRooms(dungeonID);
        for (Room r:
             rooms) {
            room.put(r.getId(),r);
        }
        generateArray(rooms.get(0),0,0);
        normalize();
        BufferedImage map=concatMap();


        return map;

    }

    private void generateArray(Room r,int x, int y){
        Room west = room.get(r.getWestRoomID());
        Room east = room.get(r.getEastRoomID());
        Room north = room.get(r.getNorthRoomID());
        Room south= room.get(r.getSouthRoomID());


        if (west!=null && !addedRooms.containsKey(west.getId())){
            addedRooms.put(west.getId(),west);
            cords.add(new RoomCords(west,x-1,y));
            generateArray(west,x-1,y);
        }
        if (north!=null && !addedRooms.containsKey(north.getId())){
            addedRooms.put(north.getId(),north);
            cords.add(new RoomCords(north,x,y+1));
            generateArray(north,x,y+1);
        }
        if (east!=null && !addedRooms.containsKey(east.getId())){
            addedRooms.put(east.getId(),east);
            cords.add(new RoomCords(east,x+1,y));
            generateArray(east,x+1,y);
        }
        if (south!=null && !addedRooms.containsKey(south.getId())){
            addedRooms.put(south.getId(),south);
            cords.add(new RoomCords(south,x,y-1));
            generateArray(south,x,y-1);
        }

    }

    private void normalize(){
        int minX=0;
        int minY=0;

        for (RoomCords c :
                cords) {
            if (c.getX()<minX)
                minX=c.getX();
            if(c.getY()<minY)
                minY=c.getY();
        }
        if(minX == 0 && minY == 0)
            return;

        int absX = Math.abs(minX);
        int absY = Math.abs(minY);

        for (RoomCords c :
                cords) {
            c.setX(c.getX()+absX);
            c.setY(c.getY()+absY);
        }
    }

    private Tupel<Integer> mapSize(){
        int maxX=0;
        int maxY=0;

        for (RoomCords c :
                cords) {
            if (c.getX()>maxX)
                maxX=c.getX();
            if(c.getY()>maxY)
                maxY=c.getY();
        }
        return new Tupel<>(maxX+1,maxY+1);
    }


    private BufferedImage concatMap(){

        Tupel size=mapSize();
        int height=(Integer) size.getValue();
        int width=(Integer) size.getKey();

        int totalHeight=500*height;
        int totalWidth=500*width;

        BufferedImage images[][]= new BufferedImage[height][width];
        for(int x=0;x<height ; x++){
            for (int y=0;y<width;y++){
                try{
                    images[x][y] = ImageIO.read(new File("src/main/resources/META-INF/resources/MapImages/KarteBack.png"));
                }catch (Exception e){}
            }
        }
        for (RoomCords c :
                cords) {
            try {
                images[c.getY()][c.getX()] = ImageIO.read(new File("src/main/resources/META-INF/resources/MapImages/KarteNOSW.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedImage map=new BufferedImage(totalWidth,totalHeight,BufferedImage.TYPE_INT_RGB);
        Graphics2D dMap=map.createGraphics();


        for(int x=0;x<height ; x++){
            for (int y=0;y<width;y++){
               dMap.drawImage(images[x][y],y*500,totalHeight-x*500-500,null);
            }
        }
        return map;
    }

}
