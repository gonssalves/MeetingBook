package br.edu.ifal.meetingbook.entities.booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifal.meetingbook.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    
    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody BookingModel bookingModel) {
        try {
            BookingModel createdBooking = bookingService.createOrUpdateBooking(bookingModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/")
    public List<BookingModel> listAll(HttpServletRequest request) {
        var bookings = this.bookingRepository.findAll();
        return bookings;
    }    

    @GetMapping("/{id}")
    public ResponseEntity<Object> listOne(@PathVariable UUID id) {
        try {
            Optional<BookingModel> booking = bookingService.listOneBooking(id);
            return ResponseEntity.ok().body(booking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }    

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody BookingModel sourceBooking, HttpServletRequest request, @PathVariable UUID id) {
        try {
            BookingModel targetBooking = bookingService.createOrUpdateBooking(sourceBooking);
            Utils.copyNonNullProperties(sourceBooking, targetBooking);
            var bookingUpdated = this.bookingRepository.save(targetBooking);
            return ResponseEntity.ok().body(bookingUpdated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    } 

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAll() {
        bookingRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Resposta sem corpo
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable UUID id) {
        try {
            bookingService.deleteOneBooking(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
