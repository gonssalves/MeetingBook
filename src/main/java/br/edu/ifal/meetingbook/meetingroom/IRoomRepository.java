package br.edu.ifal.meetingbook.meetingroom;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface IRoomRepository extends JpaRepository<RoomModel, UUID> {
    List<RoomModel> findByCapacity(int capacity);
    List<RoomModel> findByRoomType(String roomType);
}
