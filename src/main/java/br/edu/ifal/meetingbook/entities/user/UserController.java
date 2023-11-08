package br.edu.ifal.meetingbook.entities.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifal.meetingbook.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserService userService;

    // Endpoint para criar um novo usuário
    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody UserModel userModel) {
        try {
            UserModel user = userService.createOrUpdateUser(userModel);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint para listar todos os usuários
    @GetMapping("/")
    public List<UserModel> listAll(HttpServletRequest request) {
        var users = this.userRepository.findAll();
        return users;
    }

    // Endpoint para listar um único usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> listOne(@PathVariable UUID id) {
        try {
            var user = userService.listOneUser(id);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody UserModel sourceUser, HttpServletRequest request, @PathVariable UUID id) {
        try {
            UserModel targetUser = userService.createOrUpdateUser(sourceUser);
            Utils.copyNonNullProperties(sourceUser, targetUser);
            var userUpdated = this.userRepository.save(targetUser);
            return ResponseEntity.ok().body(userUpdated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    } 

    // Endpoint para excluir todos os usuários
    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAll() {
        userRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Endpoint para excluir um usuário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/bookings/")
    public ResponseEntity<Object> listBooking(@PathVariable UUID userId) {
        try {
            var bookings = userService.listUserBookings(userId);
            return ResponseEntity.ok().body(bookings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
