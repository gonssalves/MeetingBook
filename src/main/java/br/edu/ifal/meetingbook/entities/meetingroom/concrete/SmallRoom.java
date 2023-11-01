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
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'hasVideoConference'");
    }

    @Override
    public boolean hasCoffeMachine() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasCoffeMachine'");
    }

    @Override
    public int numOfComputers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'numOfComputers'");
    }

    @Override
    public int listResources() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listResources'");
    }
}
