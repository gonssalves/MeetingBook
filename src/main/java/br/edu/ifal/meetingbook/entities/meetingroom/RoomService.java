package br.edu.ifal.meetingbook.entities.meetingroom;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    private IRoomRepository roomRepository;

     private String validateRoomFields(RoomModel room) throws Exception{

        if (room.getRoomNumber() == 0) {
            throw new Exception("O campo número não pode estar em branco.");
        } if (room.getCapacity() == 0) {
            throw new Exception("O campo número não pode estar em branco.");
        } if (room.getHourPrice() == 0) {
            throw new Exception("O campo preço hora não pode estar em branco.");
        } if (room.getRoomType() == null || room.getRoomType().isEmpty()) {
            throw new Exception("O campo tipo não pode estar em branco.");
        }
  
        return null; // Retorna null se todos os campos estiverem preenchidos corretamente
    }

    public RoomModel createOrUpdateRoom(RoomModel roomModel, String methodHttp) throws Exception{
        var room = this.roomRepository.findByRoomNumber(roomModel.getRoomNumber());

        if (methodHttp == "POST" && room != null) {
            throw new Exception("Sala já existe");
        } else if (methodHttp == "PUT" && room == null) {
            throw new Exception("Sala não existe");
        }
       
        validateRoomFields(roomModel);

        var roomRoom = this.roomRepository.findById(roomModel.getId());

        if(roomRoom == null) {
            throw new Exception("Sala que contém o recurso não existe");
        }

        return this.roomRepository.save(roomModel);
                
    }
    
    public RoomModel listOneRoom(UUID id) throws Exception{
        var room = this.roomRepository.findById(id).orElse(null);

        if (room == null) {
            throw new Exception("Sala não foi encontrada");
        }

        return room;
    }

    public void deleteOneRoom(UUID id) throws Exception{
        var room = this.roomRepository.findById(id).orElse(null);

        if (room == null) {
            throw new Exception("Sala não encontrada");
        }

        this.roomRepository.delete(room);
    }
}
