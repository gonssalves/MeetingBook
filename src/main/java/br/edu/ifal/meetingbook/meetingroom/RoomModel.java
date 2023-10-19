package br.edu.ifal.meetingbook.meetingroom;

import java.util.List;
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

    private List<String> resources;

     /**
     * Construtor da classe RoomModel.
     *
     * @param roomNumber    O número da sala de reunião exclusivo.
     * @param capacity      A número de pessoas que a sala acomoda.
     * @param hourPrice     O preço hora do aluguel da sala.
     * @param roomType      O tipo da sala de acordo com o tamanho.
     */
    public RoomModel(int roomNumber, int capacity, float hourPrice, String roomType, List<String> resources){
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.hourPrice = hourPrice;
        this.roomType = roomType;
        this.resources = resources;
    }

    public abstract boolean hasVideoConference();

    public abstract boolean hasCoffeMachine();

    public abstract int numOfComputers();

}
