package br.edu.ifal.meetingbook.meetingroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/rooms")
public class RoomController {
    
    @Autowired
    private IRoomRepository roomRepository;
    @PostMapping("/")
    public ResponseEntity create(@RequestBody RoomModel roomModel) {
        var room = this.roomRepository.findByRoomNumber(roomModel.getRoomNumber());

        if(room != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sala de reunião ");
        }

        var roomCreated = this.roomRepository.save(roomModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomCreated);
    }
}
