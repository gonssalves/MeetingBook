package br.edu.ifal.meetingbook.meetingroom.concrete;

import br.edu.ifal.meetingbook.meetingroom.RoomModel;

public class MediumRoom extends RoomModel{

    public MediumRoom(int roomNumber, int capacity, float hourPrice, String roomType){
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
