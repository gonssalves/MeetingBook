package br.edu.ifal.meetingbook.meetingroom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import br.edu.ifal.meetingbook.entities.meetingroom.concrete.LargeRoom;
import br.edu.ifal.meetingbook.entities.meetingroom.concrete.MediumRoom;
import br.edu.ifal.meetingbook.entities.meetingroom.concrete.SmallRoom;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomModelUnitTest {
    
    @Test
    public void testRoomModelConstructor() {
        LargeRoom largeRoom = new LargeRoom(1, 30, 50, "Large");

        MediumRoom mediumRoom = new MediumRoom(2, 15, 25, "Medium");

        SmallRoom SmallRoom = new SmallRoom(3, 10, 12.5f, "Small");
        
    }
}
