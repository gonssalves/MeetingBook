package br.edu.ifal.meetingbook.meetingroom.concrete;

<<<<<<< HEAD
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import org.junit.Test;

import br.edu.ifal.meetingbook.entities.meetingroom.concrete.MediumRoom;

=======
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import br.edu.ifal.meetingbook.entities.meetingroom.concrete.MediumRoom;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
>>>>>>> inss
public class MediumRoomUnitTest {

    @Test
    public void testMediumRoomConstructor() {
        MediumRoom mediumRoom = new MediumRoom(1, 20, 40, "Medium");

        assertEquals(1, mediumRoom.getRoomNumber());
        assertEquals(20, mediumRoom.getCapacity());
        assertEquals(40, mediumRoom.getHourPrice(), 0.001);
        assertEquals("Medium", mediumRoom.getRoomType());
    }

    @Test
    public void testMediumRoomGettersAndSetters() {
        MediumRoom mediumRoom = new MediumRoom(1, 20, 40, "Medium");

        mediumRoom.setRoomNumber(2);
        mediumRoom.setCapacity(25);
        mediumRoom.setHourPrice(50);
        mediumRoom.setRoomType("Average");

        assertEquals(2, mediumRoom.getRoomNumber());
        assertEquals(25, mediumRoom.getCapacity());
        assertEquals(50, mediumRoom.getHourPrice(), 0.001);
        assertEquals("Average", mediumRoom.getRoomType());
    }

    @Test
    public void testMediumRoomMethods() {
        MediumRoom mediumRoom = new MediumRoom(1, 20, 40, "Medium");

<<<<<<< HEAD
        assertFalse(mediumRoom.hasVideoConference());
        assertFalse(mediumRoom.hasCoffeMachine());
        assertEquals(0, mediumRoom.numOfComputers());
        //assertEquals(0, mediumRoom.listResources());
=======
        assertTrue(mediumRoom.hasVideoConference());
        assertTrue(mediumRoom.hasCoffeMachine());
        assertEquals(5, mediumRoom.numOfComputers());
        // assertEquals(0, mediumRoom.listResources());
>>>>>>> inss
    }
}
