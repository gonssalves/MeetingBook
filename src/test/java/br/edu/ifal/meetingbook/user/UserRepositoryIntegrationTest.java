package br.edu.ifal.meetingbook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IUserRepository userRepository;

    @Test
    @Rollback(false) // Se você não deseja desfazer as alterações no banco de dados após o teste
    public void testFindByEmail() {
        // Crie um usuário de exemplo e persista-o no banco de dados
        UserModel user = new UserModel("Kanye West", "kimye", "kimye@gmail.com", "1234", "Cliente");
        user.setEmail("test@example.com");
        entityManager.persist(user);

        // Use o método findByEmail para buscar o usuário no banco de dados
        UserModel foundUser = userRepository.findByEmail("test@example.com");

        // Verifique se o usuário foi encontrado com sucesso
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
    }
}
