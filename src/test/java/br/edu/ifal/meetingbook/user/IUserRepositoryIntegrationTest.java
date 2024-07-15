package br.edu.ifal.meetingbook.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import br.edu.ifal.meetingbook.entities.user.IUserRepository;
import br.edu.ifal.meetingbook.entities.user.UserModel;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
public class IUserRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IUserRepository userRepository;

    @Test
    @Rollback(true)
    public void testFindByEmail() {
        UserModel user = new UserModel("Mr Test", "test", "test@gmail.com", "1234", "Cliente");
        entityManager.persist(user);
        entityManager.flush();

        UserModel foundUser = userRepository.findByEmail("test@gmail.com");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    @Rollback(true)
    public void testFindByUsername() {
        UserModel user = new UserModel("Mr Test", "test", "test@gmail.com", "1234", "Cliente");
        entityManager.persist(user);
        entityManager.flush();

        UserModel foundUser = userRepository.findByUsername("test");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("test");
    }
}


