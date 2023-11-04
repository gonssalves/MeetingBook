package br.edu.ifal.meetingbook.entities.booking;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tbl_bookings")
public class BookingModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    
    private int bookingNumber;
    private String bookingDate;
    private int bookingStartTime;
    private int bookingEndTime;
    private float bookingPrice;
    private String bookingStatus; // "Pendente", "Confirmada", "Cancelada"
    
    // @ManyToOne
    // @JoinColumn(name = "user_id", referencedColumnName = "id")
    // private UserModel user;

    @Column(name = "user_id")
    private UUID userId;
    
    @Column(name = "room_id")
    private UUID roomId;

    public BookingModel() {
    }
}
