package br.edu.ifal.meetingbook.user;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Esta classe representa um usuário no sistema.
 */

// Cria getters e setters de forma automática
@Data
@Entity(name = "tbl_users")
public class UserModel {

    // ID gerado automaticamente
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;

    // O nome de usuário é único
    @Column(unique = true)
    private String username;

    // O endereço de e-mail é único
    @Column(unique = true)
    private String email;
    private String password;
    private String type;

    /**
     * Construtor da classe UserModel.
     *
     * @param name     O nome do usuário.
     * @param username O nome de usuário exclusivo.
     * @param email    O endereço de e-mail exclusivo.
     * @param password A senha do usuário.
     * @param type     O tipo de usuário (por exemplo, "Gerente").
     */
    public UserModel(String name, String username, String email, String password, String type) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.type = type;
    }
}
