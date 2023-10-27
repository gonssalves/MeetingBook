package br.edu.ifal.meetingbook.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.edu.ifal.meetingbook.booking.BookingModel;
import br.edu.ifal.meetingbook.booking.IBookingRepository;
import br.edu.ifal.meetingbook.meetingroom.IRoomRepository;
import br.edu.ifal.meetingbook.meetingroom.RoomModel;
import br.edu.ifal.meetingbook.meetingroom.concrete.LargeRoom;
import br.edu.ifal.meetingbook.resource.IResourceRepository;
import br.edu.ifal.meetingbook.resource.ResourceModel;
import br.edu.ifal.meetingbook.user.IUserRepository;
import br.edu.ifal.meetingbook.user.UserModel;

@Component
public class DataInitializer implements CommandLineRunner { // Permite executar código durante a inicialização do app

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IResourceRepository resourceRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o repositório de usuários está vazio (sem registros)
        if (userRepository.count() == 0) {
            // Se estiver vazio, cria um usuário padrão para inicialização
            // Hash da senha "alice" usando BCrypt com força de trabalho 12
            var hashedPassword = BCrypt.withDefaults().hashToString(12, "admin".toCharArray());
            
            // Cria um novo objeto UserModel para o usuário inicial
            UserModel user = new UserModel("Ademar da Silva", "admin", "admin@gerencia.com", hashedPassword, "Gerente");

            // Salva o usuário no repositório
            userRepository.save(user);

        } if (roomRepository.count() == 0) {
            LargeRoom room = new LargeRoom(4, 13, 79.8f, "Large");
            roomRepository.save(room);

        // } if (resourceRepository.count() == 0) {
        //     RoomModel room = this.roomRepository.findByRoomNumber(4);
            
        //     ResourceModel resource = new ResourceModel();
        //     resource.setResourceName("Computador");
        //     resource.setResourceNumber(1);
        //     resource.setResourceType("Eletrônico");
        //     resource.setRoomID(room.getId());

        //     resourceRepository.save(resource);

        //     resource = new ResourceModel();
        //     resource.setResourceName("Máquina de Café");
        //     resource.setResourceNumber(5);
        //     resource.setResourceType("Eletrônico");
        //     resource.setRoomID(room.getId());

        //     resourceRepository.save(resource);
            
        //     resource.setResourceName("Projetor");
        //     resource.setResourceNumber(3);
        //     resource.setResourceType("Eletrônico");
        //     resource.setRoomID(room.getId());

        //     resourceRepository.save(resource);

        // } if (bookingRepository.count() == 0) {
        //     BookingModel booking = new BookingModel();
            
        //     booking.setBookingNumber(7);
        //     booking.setBookingDate("25-10-2023");
        //     booking.setBookingStartTime(13);
        //     booking.setBookingEndTime(14);
        //     booking.setBookingPrice(79.8f);
        //     booking.setBookingStatus("Confirmado");
            
        //     RoomModel room = this.roomRepository.findByRoomNumber(4);
        //     UserModel user = this.userRepository.findByUsername("admin");

        //     booking.setRoomId(room.getId());
        //     booking.setUserId(user.getId());

        //     bookingRepository.save(booking);
        // }
            
}
}
}
