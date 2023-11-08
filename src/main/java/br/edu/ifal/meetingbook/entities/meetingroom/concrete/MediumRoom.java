package br.edu.ifal.meetingbook.entities.meetingroom.concrete;

import br.edu.ifal.meetingbook.entities.meetingroom.RoomModel;
import jakarta.persistence.Entity;

@Entity
public class MediumRoom extends RoomModel{

    public MediumRoom(int roomNumber, int capacity, float hourPrice, String roomType){
        super(roomNumber, capacity, hourPrice, roomType);
    }
    @Override
    public boolean hasVideoConference() {
        return true;
    }

    @Override
    public boolean hasCoffeMachine() {
        return true;
    }

    @Override
    public int numOfComputers() {
        return 5;
    }
}
