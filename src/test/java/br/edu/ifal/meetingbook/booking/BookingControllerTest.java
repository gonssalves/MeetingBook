package br.edu.ifal.meetingbook.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifal.meetingbook.entities.booking.BookingController;
import br.edu.ifal.meetingbook.entities.booking.BookingModel;
import br.edu.ifal.meetingbook.entities.booking.BookingService;
import br.edu.ifal.meetingbook.entities.booking.IBookingRepository;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private IBookingRepository bookingRepository;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private BookingModel bookingModel;
    private UUID roomId;
    private UUID userId;

    @BeforeEach
    public void setUp() {
        roomId = UUID.randomUUID();
        userId = UUID.randomUUID();
        bookingModel = new BookingModel();
        bookingModel.setId(UUID.randomUUID());
        bookingModel.setRoomId(roomId);
        bookingModel.setUserId(userId);
        bookingModel.setBookingStartTime(900);
        bookingModel.setBookingEndTime(1100);
        bookingModel.setBookingDate("2023-07-05");
        bookingModel.setBookingStatus("Confirmada");
    }

    @Test
    public void testCreateBookingSuccess() throws Exception {
        when(bookingService.createOrUpdateBooking(bookingModel, "POST")).thenReturn(bookingModel);

        ResponseEntity<Object> response = bookingController.create(bookingModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookingModel, response.getBody());
    }

    @Test
    public void testCreateBookingBadRequest() throws Exception {
        when(bookingService.createOrUpdateBooking(bookingModel, "POST")).thenThrow(new Exception("Já existe uma reserva com esse ID"));

        ResponseEntity<Object> response = bookingController.create(bookingModel);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Já existe uma reserva com esse ID", response.getBody());
    }

    @Test
    public void testListAllBookings() {
        List<BookingModel> bookings = Arrays.asList(bookingModel, new BookingModel());
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<BookingModel> response = bookingController.listAll(null);
        assertEquals(bookings, response);
    }

    @Test
    public void testListOneBookingSuccess() throws Exception {
        UUID bookingId = bookingModel.getId();
        when(bookingService.listOneBooking(bookingId)).thenReturn(bookingModel);

        ResponseEntity<Object> response = bookingController.listOne(bookingId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookingModel, response.getBody());
    }

    @Test
    public void testListOneBookingNotFound() throws Exception {
        UUID bookingId = UUID.randomUUID();
        when(bookingService.listOneBooking(bookingId)).thenThrow(new Exception("Reserva informada não foi encontrada"));

        ResponseEntity<Object> response = bookingController.listOne(bookingId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Reserva informada não foi encontrada", response.getBody());
    }

    @Test
    public void testUpdateBookingSuccess() throws Exception {
        UUID bookingId = bookingModel.getId();
        when(bookingService.createOrUpdateBooking(bookingModel, "PUT")).thenReturn(bookingModel);
        when(bookingRepository.save(bookingModel)).thenReturn(bookingModel);

        ResponseEntity<Object> response = bookingController.update(bookingModel, null, bookingId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookingModel, response.getBody());
    }

    @Test
    public void testUpdateBookingBadRequest() throws Exception {
        UUID bookingId = UUID.randomUUID();
        when(bookingService.createOrUpdateBooking(bookingModel, "PUT")).thenThrow(new Exception("Reserva não existe"));

        ResponseEntity<Object> response = bookingController.update(bookingModel, null, bookingId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Reserva não existe", response.getBody());
    }

    @Test
    public void testDeleteAllBookings() {
        doNothing().when(bookingRepository).deleteAll();

        ResponseEntity<Void> response = bookingController.deleteAll();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteOneBookingSuccess() throws Exception {
        UUID bookingId = bookingModel.getId();
        when(bookingService.deleteOneBooking(bookingId)).thenReturn(bookingModel);

        ResponseEntity<Object> response = bookingController.deleteOne(bookingId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteOneBookingNotFound() throws Exception {
        UUID bookingId = UUID.randomUUID();
        when(bookingService.deleteOneBooking(bookingId)).thenThrow(new Exception("Reserva informada não foi encontrada"));

        ResponseEntity<Object> response = bookingController.deleteOne(bookingId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Reserva informada não foi encontrada", response.getBody());
    }
}
