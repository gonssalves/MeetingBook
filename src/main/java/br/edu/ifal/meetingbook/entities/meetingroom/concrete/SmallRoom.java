package br.edu.ifal.meetingbook.entities.meetingroom.concrete;

import br.edu.ifal.meetingbook.entities.meetingroom.RoomModel;
import jakarta.persistence.Entity;

@Entity
public class SmallRoom extends RoomModel{
    
    public SmallRoom(int roomNumber, int capacity, float hourPrice, String roomType){
        super(roomNumber, capacity, hourPrice, roomType);
    }

    @Override
    public boolean hasVideoConference() {
        return false;
    }

    @Override
    public boolean hasCoffeMachine() {
        return false;
    }

    @Override
    public int numOfComputers() {
        return 1;
    }

 
}
