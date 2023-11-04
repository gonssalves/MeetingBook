package br.edu.ifal.meetingbook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.edu.ifal.meetingbook.entities.booking.BookingModel;
import br.edu.ifal.meetingbook.entities.booking.IBookingRepository;
import br.edu.ifal.meetingbook.entities.meetingroom.IRoomRepository;
import br.edu.ifal.meetingbook.entities.meetingroom.concrete.LargeRoom;
import br.edu.ifal.meetingbook.entities.resource.IResourceRepository;
import br.edu.ifal.meetingbook.entities.resource.ResourceModel;
import br.edu.ifal.meetingbook.entities.user.IUserRepository;
import br.edu.ifal.meetingbook.entities.user.UserModel;

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
            if (resourceRepository.count() == 0) {
                ResourceModel resource = new ResourceModel();
                resource.setResourceName("Computador");
                resource.setResourceNumber(1);
                resource.setResourceType("Eletrônico");
                resource.setRoomID(room.getId());
            
                // Salve o recurso no banco de dados
                resourceRepository.save(resource);

                ResourceModel resource2 = new ResourceModel();
                resource2.setResourceName("Máquina de Café");
                resource2.setResourceNumber(5);
                resource2.setResourceType("Eletrdoméstico");
                resource2.setRoomID(room.getId());
                
                resourceRepository.save(resource2);
                
                ResourceModel resource3 = new ResourceModel();
                resource3.setResourceName("Projetor");
                resource3.setResourceNumber(3);
                resource3.setResourceType("Eletrônico");
                resource3.setRoomID(room.getId());
                
                resourceRepository.save(resource3);

            } if (bookingRepository.count() == 0) {
                BookingModel booking = new BookingModel();
                
                booking.setBookingNumber(7);
                booking.setBookingDate("25-10-2023");
                booking.setBookingStartTime(1300);
                booking.setBookingEndTime(1400);
                booking.setBookingPrice(79.8f);
                booking.setBookingStatus("Pendente");
                
                UserModel user = this.userRepository.findByUsername("admin");
    
                booking.setRoomId(room.getId());
                booking.setUserId(user.getId());
    
                bookingRepository.save(booking);

                BookingModel booking1 = new BookingModel();

                booking1.setBookingNumber(4);
                booking1.setBookingDate("25-10-2023");
                booking1.setBookingStartTime(1430);
                booking1.setBookingEndTime(1800);
                booking1.setBookingPrice(79.8f);
                booking1.setBookingStatus("Confirmado");
                    
                booking1.setRoomId(room.getId());
                booking1.setUserId(user.getId());
    
                bookingRepository.save(booking1);
            }
            }
            
    }
}
