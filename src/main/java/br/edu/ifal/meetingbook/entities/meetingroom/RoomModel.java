package br.edu.ifal.meetingbook.entities.meetingroom;

import java.util.List;
import java.util.UUID;

import br.edu.ifal.meetingbook.entities.resource.ResourceModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Entity(name = "tbl_rooms")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Permite que subclasses sejam armazenadas na mesma tabela
@Data
public abstract class RoomModel {
    
    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;

    @Column(unique=true)
    private int roomNumber;
    private int capacity;
    private float hourPrice;
    private String roomType;

     /**
     * Construtor da classe RoomModel.
     *
     * @param roomNumber    O número da sala de reunião exclusivo.
     * @param capacity      A número de pessoas que a sala acomoda.
     * @param hourPrice     O preço hora do aluguel da sala.
     * @param roomType      O tipo da sala de acordo com o tamanho.
     */
    public RoomModel(int roomNumber, int capacity, float hourPrice, String roomType){
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.hourPrice = hourPrice;
        this.roomType = roomType;
    }

    public abstract boolean hasVideoConference();

    public abstract boolean hasCoffeMachine();

    public abstract int numOfComputers();

}
