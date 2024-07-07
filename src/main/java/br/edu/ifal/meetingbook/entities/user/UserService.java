package br.edu.ifal.meetingbook.entities.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.edu.ifal.meetingbook.entities.booking.BookingModel;
import br.edu.ifal.meetingbook.entities.booking.IBookingRepository;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    private String validateUserFields(UserModel userModel) throws Exception{
        if (userModel == null) {
            throw new Exception("O usuário não pode estar em branco.");
        }

        if (userModel.getName() == null || userModel.getName().isEmpty()) {
            throw new Exception("O campo name não pode estar em branco.");
        }
        if (userModel.getUsername() == null || userModel.getUsername().isEmpty()) {
            throw new Exception("O campo username não pode estar em branco.");
        }
        if (userModel.getEmail() == null || userModel.getEmail().isEmpty()) {
            throw new Exception("O campo email não pode estar em branco.");
        }
        // Adicione validações para outros campos, se necessário.

        return null; // Retorna null se todos os campos estiverem preenchidos corretamente
    }

    public UserModel createOrUpdateUser(UserModel userModel, String methodHttp) throws Exception{
        validateUserFields(userModel);

        var user = this.userRepository.findByEmail(userModel.getEmail()); // Verifica se o usuário já existe no banco de dados.
        
        if (methodHttp.equals("POST") && user != null) {
            throw new Exception("O usuário informado já existe");
        } else if (methodHttp.equals("PUT") && user == null) {
            throw new Exception("Usuário não existe não existe");
        }

        // Realize o hash da senha do usuário antes de armazená-lo no banco de dados.
        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashed);
        // Salve o novo usuário no banco de dados.
        return this.userRepository.save(userModel);
    }

    public UserModel listOneUser(UUID id) throws Exception{
        var user = this.userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new Exception("Usuário não encontrado");
        }

        return user;
    }

    public void deleteUser(UUID id) throws Exception{
        var user = this.userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new Exception("Usuário não encontrado");
        }

        this.userRepository.delete(user);
    }

    public List<BookingModel> listUserBookings(UUID userId) throws Exception{
        UserModel user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new Exception("Usuário não encontrado");
        }

        return this.bookingRepository.findByUserId(userId);
    }

}
