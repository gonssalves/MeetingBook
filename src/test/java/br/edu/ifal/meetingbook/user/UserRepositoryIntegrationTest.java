package br.edu.ifal.meetingbook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import br.edu.ifal.meetingbook.entities.user.IUserRepository;
import br.edu.ifal.meetingbook.entities.user.UserModel;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IUserRepository userRepository;

    @Test
    @Rollback(false) // Desfaz as alterações no banco de dados após o teste
    public void testFindByEmail() {
        // Crie um usuário de exemplo e persista-o no banco de dados
        UserModel user = new UserModel("Mr Test", "test", "test@gmail.com", "1234", "Cliente");
        entityManager.persist(user);

        // Use o método findByEmail para buscar o usuário no banco de dados
        UserModel foundUser = userRepository.findByEmail("test@gmail.com");

        // Verifique se o usuário foi encontrado com sucesso
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    @Rollback(false)
    public void testFindByUsername() {
        UserModel user = new UserModel("Mr Test", "test", "test@gmail.com", "1234", "Cliente");
        entityManager.persist(user);

        UserModel foundUser = userRepository.findByUsername("test");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("test");
    }
}
