package br.edu.ifal.meetingbook.resources;

<<<<<<< HEAD
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.edu.ifal.meetingbook.entities.resource.ResourceModel;

=======
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import br.edu.ifal.meetingbook.entities.resource.ResourceModel;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
>>>>>>> inss
public class ResourceModelUnitTest {
    
    @Test
    public void testResourceModelConstructor() {
        ResourceModel resource = new ResourceModel();
        resource.setResourceNumber(1);
        resource.setResourceName("Projector");
        resource.setResourceType("Electronic");

        assertEquals(1, resource.getResourceNumber());
        assertEquals("Projector", resource.getResourceName());
        assertEquals("Electronic", resource.getResourceType());
    }

    @Test
    public void testResourceModelSetters() {
        ResourceModel resource = new ResourceModel();

        resource.setResourceNumber(1);
        resource.setResourceName("Projector");
        resource.setResourceType("Electronic");

        assertEquals(1, resource.getResourceNumber());
        assertEquals("Projector", resource.getResourceName());
        assertEquals("Electronic", resource.getResourceType());
    }
}
