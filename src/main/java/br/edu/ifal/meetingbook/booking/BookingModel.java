package br.edu.ifal.meetingbook.booking;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tbl_bookings")
public class BookingModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private String id;
    
    private int bookingNumber;
    private String bookingDate;
    private String bookingTime;
    private float bookingPrice;
    private String bookingStatus; // "Pendente", "Confirmada", "Cancelada"
    
    private UUID userId;
    private UUID roomId;
}
