package br.edu.ifal.meetingbook.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifal.meetingbook.entities.resource.ResourceController;
import br.edu.ifal.meetingbook.entities.resource.ResourceModel;
import br.edu.ifal.meetingbook.entities.resource.ResourceService;
import br.edu.ifal.meetingbook.entities.resource.IResourceRepository;

public class ResourceControllerUnitTest {

    @InjectMocks
    private ResourceController resourceController;

    @Mock
    private ResourceService resourceService;

    @Mock
    private IResourceRepository resourceRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateResourceSuccess() throws Exception {
        ResourceModel resourceModel = new ResourceModel();
        resourceModel.setResourceNumber(1);
        resourceModel.setResourceName("Projetor");
        resourceModel.setResourceType("Eletrônico");

        when(resourceService.createOrUpdateResource(resourceModel, "POST")).thenReturn(resourceModel);

        ResponseEntity<Object> response = resourceController.create(resourceModel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(resourceModel, response.getBody());
    }

    @Test
    public void testCreateResourceFailure() throws Exception {
        ResourceModel resourceModel = new ResourceModel();
        resourceModel.setResourceNumber(1);
        resourceModel.setResourceName("Projetor");
        resourceModel.setResourceType("Eletrônico");

        doThrow(new Exception("Erro ao criar recurso")).when(resourceService).createOrUpdateResource(resourceModel, "POST");

        ResponseEntity<Object> response = resourceController.create(resourceModel);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao criar recurso", response.getBody());
    }
    @Test
    public void testListAllResourcesSuccess() {
        List<ResourceModel> resources = new ArrayList<>();
        ResourceModel resource1 = new ResourceModel();
        resource1.setResourceNumber(1);
        resource1.setResourceName("Projetor");
        resource1.setResourceType("Eletrônico");
        resources.add(resource1);

        ResourceModel resource2 = new ResourceModel();
        resource2.setResourceNumber(2);
        resource2.setResourceName("Quadro negro");
        resource2.setResourceType("Fixo");
        resources.add(resource2);

        when(resourceRepository.findAll()).thenReturn(resources);

        List<ResourceModel> response = resourceController.listAll(null);
        assertEquals(2, response.size());
    }

    @Test
    public void testListAllResourcesFailure() {
        doThrow(new RuntimeException("Erro ao listar recursos")).when(resourceRepository).findAll();

        try {
            resourceController.listAll(null);
        } catch (Exception e) {
            assertEquals("Erro ao listar recursos", e.getMessage());
        }
    }
    
    @Test
    public void testListOneResourceSuccess() throws Exception {
        UUID resourceId = UUID.randomUUID();
        ResourceModel resourceModel = new ResourceModel();
        resourceModel.setResourceNumber(1);
        resourceModel.setResourceName("Projetor");
        resourceModel.setResourceType("Eletrônico");

        when(resourceService.listOneResource(resourceId)).thenReturn(resourceModel);

        ResponseEntity<Object> response = resourceController.listOne(resourceId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(resourceModel, response.getBody());
    }

    @Test
    public void testListOneResourceFailure() throws Exception {
        UUID resourceId = UUID.randomUUID();

        doThrow(new Exception("Recurso não encontrado")).when(resourceService).listOneResource(resourceId);

        ResponseEntity<Object> response = resourceController.listOne(resourceId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Recurso não encontrado", response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    public void testUpdateResourceSuccess() throws Exception {
        UUID resourceId = UUID.randomUUID();
        
        ResourceModel existingResource = new ResourceModel();
        existingResource.setResourceNumber(1);
        existingResource.setResourceName("Projetor");
        existingResource.setResourceType("Eletrônico");
        
        ResourceModel updatedResource = new ResourceModel();
        updatedResource.setResourceNumber(1);
        updatedResource.setResourceName("Projetor Pro");
        updatedResource.setResourceType("Eletrônico");

        when(resourceService.createOrUpdateResource(updatedResource, "PUT")).thenReturn(updatedResource);
        when(resourceRepository.save(updatedResource)).thenReturn(updatedResource);

        ResponseEntity<Object> response = resourceController.update(updatedResource, null, resourceId);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResourceModel responseResource = (ResourceModel) response.getBody();
        assertEquals("Projetor Pro", responseResource.getResourceName());
    }

    @Test
    public void testUpdateResourceFailure() throws Exception {
        UUID resourceId = UUID.randomUUID();
        
        ResourceModel updatedResource = new ResourceModel();
        updatedResource.setResourceNumber(1);
        updatedResource.setResourceName("Projetor Pro");
        updatedResource.setResourceType("Eletrônico");

        doThrow(new Exception("Erro ao atualizar recurso")).when(resourceService).createOrUpdateResource(updatedResource, "PUT");

        ResponseEntity<Object> response = resourceController.update(updatedResource, null, resourceId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao atualizar recurso", response.getBody());
    }

    @Test
    public void testDeleteAllResourcesSuccess() {
        ResponseEntity<Void> response = resourceController.deleteAll();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteAllResourcesFailure() {
        doThrow(new RuntimeException("Erro ao deletar todos os recursos")).when(resourceRepository).deleteAll();

        try {
            resourceController.deleteAll();
        } catch (Exception e) {
            assertEquals("Erro ao deletar todos os recursos", e.getMessage());
        }
    }
    
    @Test
    public void testDeleteResourceByIdSuccess() throws Exception {
        UUID resourceId = UUID.randomUUID();

        ResponseEntity<Object> response = resourceController.deleteOne(resourceId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteResourceByIdFailure() throws Exception {
        UUID resourceId = UUID.randomUUID();
        
        doThrow(new Exception("Recurso não encontrado")).when(resourceService).deleteOneResource(resourceId);

        ResponseEntity<Object> response = resourceController.deleteOne(resourceId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Recurso não encontrado", response.getBody());
    }
}
