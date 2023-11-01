package br.edu.ifal.meetingbook.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.edu.ifal.meetingbook.resource.ResourceModel;

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
