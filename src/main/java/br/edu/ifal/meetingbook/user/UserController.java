package br.edu.ifal.meetingbook.user;

import java.util.List;
import java.util.Optional;
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

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.edu.ifal.meetingbook.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    // Endpoint para criar um novo usuário
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        // Verifique se o usuário já existe no banco de dados.
        var user = this.userRepository.findByEmail(userModel.getEmail());
        
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        // Realize o hash da senha do usuário antes de armazená-lo no banco de dados.
        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashed);

        // Salve o novo usuário no banco de dados.
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    // Endpoint para listar todos os usuários
    @GetMapping("/")
    public List<UserModel> listAll(HttpServletRequest request) {
        var users = this.userRepository.findAll();
        return users;
    }

    // Endpoint para listar um único usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity listOne(@PathVariable UUID id) {
        var user = userRepository.findById(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/update")
    public ResponseEntity update(HttpServletRequest request, @RequestBody UserModel updatedUser) {
        // Obtenha o ID do usuário autenticado a partir do atributo da requisição.
        UUID authenticatedUserId = (UUID) request.getAttribute("idUser");

        // Verifique se o usuário que está sendo atualizado existe.
        Optional<UserModel> existingUser = userRepository.findById(authenticatedUserId);
        if (!existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        // Realize a atualização dos campos permitidos.
        UserModel userToUpdate = existingUser.get();
        Utils.copyNonNullProperties(updatedUser, userToUpdate);
        updatedUser = userRepository.save(userToUpdate);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    // Endpoint para excluir todos os usuários
    @DeleteMapping("/")
    public ResponseEntity deleteAll() {
        userRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Endpoint para excluir um usuário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOne(@PathVariable UUID id) {
        var user = userRepository.findById(id);
    
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
