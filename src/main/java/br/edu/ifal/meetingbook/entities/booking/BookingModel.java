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
    
    @Column(name = "user_id") // "userId" é o nome da coluna de FK
    private UUID userId; // Usando UserModel como tipo de chave estrangeira
    
    @Column(name = "room_id")
    private UUID roomId;

    public BookingModel() {
    }
}
