import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.ifal.meetingbook.resource.IResourceRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class IResourceRepositoryTest {

    @Autowired
    private IResourceRepository resourceRepository;

    @Test
    public void testFindByResourceNumberWithInvalidNumber() {
        // Tente buscar um recurso com um número inválido
        int invalidResourceNumber = 999;
        
        // Use assertThrows para capturar a exceção lançada pelo método
        assertThrows(Exception.class, () -> {
            resourceRepository.findByResourceNumber(invalidResourceNumber);
        });
    }
}
