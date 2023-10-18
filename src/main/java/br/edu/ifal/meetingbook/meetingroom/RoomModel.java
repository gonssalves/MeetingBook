package br.edu.ifal.meetingbook.meetingroom;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tbl_rooms")
public abstract class RoomModel {
    
    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;

    @Column(unique=true)
    private int roomNumber;
    private int capacity;
    private float hourPrice;
    private String roomType;

    public abstract boolean hasVideoConference();

    public abstract boolean hasCoffeMachine();

    public abstract int numOfComputers();

}
