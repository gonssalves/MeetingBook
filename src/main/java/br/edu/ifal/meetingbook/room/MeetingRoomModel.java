package br.edu.ifal.meetingbook.room;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tbl_rooms")
public abstract class MeetingRoomModel {

    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;

    private int capacity;
    private double hourPrice;
    private String roomType;
    
    // Método vai retornar a Preço Hora * Tempo da Reserva
    public abstract double calculateHourPrice(); 

    public abstract boolean hasVideoConference();

    public abstract boolean hasComputer();
}