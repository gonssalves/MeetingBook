package br.edu.ifal.meetingbook.meetingroom;

import org.junit.Test;

import br.edu.ifal.meetingbook.meetingroom.concrete.LargeRoom;
import br.edu.ifal.meetingbook.meetingroom.concrete.MediumRoom;
import br.edu.ifal.meetingbook.meetingroom.concrete.SmallRoom;

public class RoomModelUnitTest {
    
    @Test
    public void testRoomModelConstructor() {
        LargeRoom largeRoom = new LargeRoom(1, 30, 50, "Large");

        MediumRoom mediumRoom = new MediumRoom(2, 15, 25, "Medium");

        SmallRoom SmallRoom = new SmallRoom(3, 10, 12.5f, "Small");
        
    }
}
