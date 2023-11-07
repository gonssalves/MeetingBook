package br.edu.ifal.meetingbook.entities.resource;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifal.meetingbook.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    
    @Autowired
    private IResourceRepository resourceRepository;

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody ResourceModel resourceModel) {
        try {
            ResourceModel createdResource = resourceService.createOrUpdateResource(resourceModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdResource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    } 

    @GetMapping("/")
    public List<ResourceModel> listAll(HttpServletRequest request) {
        var resources = this.resourceRepository.findAll();
        return resources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listOne(@PathVariable UUID id) {
        try {
            var resource = resourceService.listOneResource(id);
            return ResponseEntity.ok().body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody ResourceModel sourceResource, HttpServletRequest request, @PathVariable UUID id) {
        try {
            ResourceModel targetResource = resourceService.createOrUpdateResource(sourceResource);
            Utils.copyNonNullProperties(sourceResource, targetResource);
            var resourceUpdated = this.resourceRepository.save(targetResource);
            return ResponseEntity.ok().body(resourceUpdated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }    

     @DeleteMapping("/")
    public ResponseEntity<Void> deleteAll() {
        resourceRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Endpoint para excluir um usuário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable UUID id) {
        try {
            resourceService.deleteOneResource(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
