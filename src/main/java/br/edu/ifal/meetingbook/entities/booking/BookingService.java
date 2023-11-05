package br.edu.ifal.meetingbook.entities.booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.ifal.meetingbook.entities.meetingroom.IRoomRepository;
import br.edu.ifal.meetingbook.entities.user.IUserRepository;

@Service
public class BookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoomRepository roomRepository;

    public BookingModel createBooking(BookingModel bookingModel) throws Exception {
        var booking = this.bookingRepository.findById(bookingModel.getId());
        
        if (booking != null) {
            throw new Exception("Já existe uma reserva com este ID.");
        }

        if (bookingModel.getRoomId() == null) {
            throw new Exception("O ID da sala não pode ser nulo.");
        }

        var room = this.roomRepository.findById(bookingModel.getRoomId());

        if (room == null) {
            throw new Exception("Sala de reserva informada não existe.");
        }

        // UserModel user = bookingModel.getUser();
        // UUID userId = user.getId();

        UUID userId = bookingModel.getUserId();
        
        var bookingUser = this.userRepository.findById(userId);

        if (bookingUser == null) {
            throw new Exception("Usuário dono da reserva não existe.");
        }

        int startTime = bookingModel.getBookingStartTime();
        int endTime = bookingModel.getBookingEndTime();

        if(startTime < 0000 || startTime > 2359 || endTime < 0000 || endTime > 2359) {
            throw new Exception("Horário inválido");
        }

        if(startTime > endTime) {
            throw new Exception("Horário de início não pode ser maior que o horário de fim.");
        }

        /*
         * POSSÍVEIS CONFLITOS NO HORÁRIO DA RESERVA
         * Listar reservas por data V
         * Verificar status da reserva V
         * Verificar horário de início e fim V
        */
        
        List<BookingModel> bookingList = this.bookingRepository.findByBookingDate((bookingModel.getBookingDate()));

        for(BookingModel bookingItem : bookingList) {
            if(bookingItem.getBookingStatus() != "Cancelada") {
                if(bookingItem.getBookingStartTime() == bookingModel.getBookingStartTime() || bookingItem.getBookingEndTime() == bookingModel.getBookingEndTime()) {
                    throw new Exception("Horário indisponível para reserva.");
                }  
            }
        }

        return this.bookingRepository.save(bookingModel);
    }

    public Optional<BookingModel> listOneBookingModel(UUID id) throws Exception{
        Optional<BookingModel> booking = this.bookingRepository.findById(id);
        
        if (!booking.isPresent()) {
            throw new Exception("Reserva informada não foi encontrada.");
        }

        return booking;
    }
}