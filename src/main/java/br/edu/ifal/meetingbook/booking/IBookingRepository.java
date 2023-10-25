package br.edu.ifal.meetingbook.booking;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookingRepository extends JpaRepository<BookingModel, UUID>{
    BookingModel findByBookingNumber(int bookingNumber);
    List<BookingModel> findByUserId(UUID userId);
    List<BookingModel> findByRoomId(UUID roomId);
    List<BookingModel> findByBookingDate(String bookingDate);
    List<BookingModel> findByBookingStatus(String bookingStatus);
    List<BookingModel> findByUserAndRoomId(UUID userId, UUID roomId);
    List<BookingModel> findByUserIdAndBookingStatus(UUID userId, String bookingStatus);
    List<BookingModel> findByRoomIdAndBookingStatus(UUID roomId, String bookingStatus);   
}
