package br.edu.ifal.meetingbook.meetingroom.concrete;

import java.util.List;

import br.edu.ifal.meetingbook.meetingroom.RoomModel;

public class MediumRoom extends RoomModel{

    public MediumRoom(int roomNumber, int capacity, float hourPrice, String roomType, List<String> resources){
        super(roomNumber, capacity, hourPrice, roomType, resources);
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
}
