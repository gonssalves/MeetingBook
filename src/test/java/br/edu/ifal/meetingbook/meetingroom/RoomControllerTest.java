package br.edu.ifal.meetingbook.meetingroom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifal.meetingbook.entities.meetingroom.IRoomRepository;
import br.edu.ifal.meetingbook.entities.meetingroom.RoomController;
import br.edu.ifal.meetingbook.entities.meetingroom.RoomModel;
import br.edu.ifal.meetingbook.entities.meetingroom.RoomService;
import br.edu.ifal.meetingbook.entities.meetingroom.concrete.LargeRoom;
import br.edu.ifal.meetingbook.entities.meetingroom.concrete.MediumRoom;

@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {

    @Mock
    private IRoomRepository roomRepository;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    private RoomModel roomModel;

    @BeforeEach
    public void setUp() {
        roomModel = new LargeRoom(1, 10, 100.0f, "Conference");
    }

    @Test
    public void testCreateRoomSuccess() throws Exception {
        when(roomService.createOrUpdateRoom(roomModel, "POST")).thenReturn(roomModel);

        ResponseEntity<Object> response = roomController.create(roomModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(roomModel, response.getBody());
    }

    @Test
    public void testCreateRoomBadRequest() throws Exception {
        when(roomService.createOrUpdateRoom(roomModel, "POST")).thenThrow(new Exception("Sala já existe"));

        ResponseEntity<Object> response = roomController.create(roomModel);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Sala já existe", response.getBody());
    }

    @Test
    public void testListAllRooms() {
        List<RoomModel> rooms = Arrays.asList(new LargeRoom(1, 10, 100.0f, "Conference"), new MediumRoom(2, 5, 50.0f, "Meeting"));
        when(roomRepository.findAll()).thenReturn(rooms);

        List<RoomModel> response = roomController.listAll(null);
        assertEquals(rooms, response);
    }

    @Test
    public void testListOneRoomSuccess() throws Exception {
        UUID roomId = UUID.randomUUID();
        when(roomService.listOneRoom(roomId)).thenReturn(roomModel);

        ResponseEntity<Object> response = roomController.listOne(roomId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roomModel, response.getBody());
    }

    @Test
    public void testListOneRoomNotFound() throws Exception {
        UUID roomId = UUID.randomUUID();
        when(roomService.listOneRoom(roomId)).thenThrow(new Exception("Sala não foi encontrada"));

        ResponseEntity<Object> response = roomController.listOne(roomId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Sala não foi encontrada", response.getBody());
    }

    @Test
    public void testUpdateRoomSuccess() throws Exception {
        UUID roomId = UUID.randomUUID();
        when(roomService.createOrUpdateRoom(roomModel, "PUT")).thenReturn(roomModel);
        when(roomRepository.save(roomModel)).thenReturn(roomModel);

        ResponseEntity<Object> response = roomController.update(roomModel, null, roomId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roomModel, response.getBody());
    }

    @Test
    public void testUpdateRoomBadRequest() throws Exception {
        UUID roomId = UUID.randomUUID();
        when(roomService.createOrUpdateRoom(roomModel, "PUT")).thenThrow(new Exception("Sala não existe"));

        ResponseEntity<Object> response = roomController.update(roomModel, null, roomId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Sala não existe", response.getBody());
    }

    @Test
    public void testDeleteAllRooms() {
        doNothing().when(roomRepository).deleteAll();

        ResponseEntity<Void> response = roomController.deleteAll();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteOneRoomSuccess() throws Exception {
        UUID roomId = UUID.randomUUID();
        doNothing().when(roomService).deleteOneRoom(roomId);

        ResponseEntity<Object> response = roomController.deleteOne(roomId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteOneRoomNotFound() throws Exception {
        UUID roomId = UUID.randomUUID();
        doThrow(new Exception("Sala não encontrada")).when(roomService).deleteOneRoom(roomId);

        ResponseEntity<Object> response = roomController.deleteOne(roomId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Sala não encontrada", response.getBody());
    }
}