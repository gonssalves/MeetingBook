package br.edu.ifal.meetingbook.user;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

// A interface IUserRepository estende JpaRepository, que é um componente do Spring Data JPA
public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    // Método para buscar um usuário por email
    UserModel findByEmail(String email);

    // Método para buscar um usuário por nome de usuário (username)
    UserModel findByUsername(String username);
}
