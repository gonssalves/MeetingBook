package br.edu.ifal.meetingbook.entities.meetingroom;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifal.meetingbook.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/rooms")
public class RoomController {
    
    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody RoomModel roomModel) {
        try {
            RoomModel createdRoom = roomService.createOrUpdateRoom(roomModel, "POST");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

   @GetMapping("/")
    public List<RoomModel> listAll(HttpServletRequest request) {
        var rooms = this.roomRepository.findAll();
        return rooms;
    }

     @GetMapping("/{id}")
    public ResponseEntity<Object> listOne(@PathVariable UUID id) {
        try {
            var room = roomService.listOneRoom(id);
            return ResponseEntity.ok().body(room);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody RoomModel sourceRoom, HttpServletRequest request, @PathVariable UUID id) {
        try {
            RoomModel targetRoom = roomService.createOrUpdateRoom(sourceRoom, "PUT");
            Utils.copyNonNullProperties(sourceRoom, targetRoom);
            var roomUpdated = this.roomRepository.save(targetRoom);
            return ResponseEntity.ok().body(roomUpdated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }   

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAll() {
        roomRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable UUID id) {
        try {
            roomService.deleteOneRoom(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());// Resposta sem corpo
        }
    }
    
}
