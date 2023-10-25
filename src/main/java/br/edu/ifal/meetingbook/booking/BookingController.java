package br.edu.ifal.meetingbook.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifal.meetingbook.meetingroom.IRoomRepository;
import br.edu.ifal.meetingbook.user.IUserRepository;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IUserRepository userRepository;
    

    @PutMapping("/")
    public ResponseEntity create(@RequestBody BookingModel bookingModel) {
        var booking = this.bookingRepository.findByBookingNumber(bookingModel.getBookingNumber());   
        
        if(booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva não existe");
        }
        
        if(bookingModel.getRoomId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sala não informada");
        }
        
        var bookingRoom = this.roomRepository.findById(bookingModel.getRoomId());
        
        if(bookingRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala da reserva não existe");
        }   
        
        var bookingUser = this.userRepository.findById(bookingModel.getUserId());

        if(bookingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário dono da reserva não existe");
        }

        var bookingCreated = this.bookingRepository.save(bookingModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingCreated);
    }
}
