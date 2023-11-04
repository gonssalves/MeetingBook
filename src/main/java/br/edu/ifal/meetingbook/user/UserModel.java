package br.edu.ifal.meetingbook.user;

import java.util.List;
import java.util.UUID;

import br.edu.ifal.meetingbook.entities.booking.BookingModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingModel> bookings;
    
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

    public UserModel() {
    }
}
