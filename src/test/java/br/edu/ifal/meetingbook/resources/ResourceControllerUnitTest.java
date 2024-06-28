package br.edu.ifal.meetingbook.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifal.meetingbook.entities.resource.IResourceRepository;
import br.edu.ifal.meetingbook.entities.resource.ResourceController;
import br.edu.ifal.meetingbook.entities.resource.ResourceModel;

public class ResourceControllerUnitTest {

    @InjectMocks
    private ResourceController resourceController;

    @Mock
    private IResourceRepository resourceRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateResource() {
        ResourceModel resource = new ResourceModel();
        resource.setResourceNumber(1);
        resource.setResourceName("Projector");
        resource.setResourceType("Electronic");

        when(resourceRepository.save(resource)).thenReturn(resource);

        ResponseEntity<Object> response = resourceController.create(resource);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testListAllResources() {
        List<ResourceModel> resources = new ArrayList<>();
        ResourceModel resource1 = new ResourceModel();
        resource1.setResourceNumber(1);
        resource1.setResourceName("Projector");
        resource1.setResourceType("Electronic");
        resources.add(resource1);

        ResourceModel resource2 = new ResourceModel();
        resource2.setResourceNumber(2);
        resource2.setResourceName("Whiteboard");
        resource2.setResourceType("Stationery");
        resources.add(resource2);

        when(resourceRepository.findAll()).thenReturn(resources);

        List<ResourceModel> response = resourceController.listAll(null);
        assertEquals(2, response.size());
    }

    @Test
    public void testGetResourceById() {
        ResourceModel resource = new ResourceModel();
        resource.setResourceNumber(1);
        resource.setResourceName("Projector");
        resource.setResourceType("Electronic");

        UUID resourceId = UUID.randomUUID();
        when(resourceRepository.findById(resourceId)).thenReturn(java.util.Optional.ofNullable(resource));

        ResponseEntity<Object> response = resourceController.listOne(resourceId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateResource() {
        ResourceModel existingResource = new ResourceModel();
        existingResource.setResourceNumber(1);
        existingResource.setResourceName("Projector");
        existingResource.setResourceType("Electronic");

        ResourceModel updatedResource = new ResourceModel();
        updatedResource.setResourceNumber(1);
        updatedResource.setResourceName("Projector Pro");
        updatedResource.setResourceType("Electronic");

        UUID resourceId = UUID.randomUUID();
        when(resourceRepository.findById(resourceId)).thenReturn(java.util.Optional.ofNullable(existingResource));
        when(resourceRepository.save(existingResource)).thenReturn(updatedResource);

        ResponseEntity<Object> response = resourceController.update(updatedResource, null, resourceId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResourceModel responseResource = (ResourceModel) response.getBody();
        assertEquals("Projector Pro", responseResource.getResourceName());
    }

    @Test
    public void testDeleteResourceById() {
        UUID resourceId = UUID.randomUUID();
        when(resourceRepository.findById(resourceId)).thenReturn(java.util.Optional.empty());

        ResponseEntity<Object> response = resourceController.deleteOne(resourceId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
