package br.edu.ifal.meetingbook.resource;

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

import br.edu.ifal.meetingbook.meetingroom.IRoomRepository;
import br.edu.ifal.meetingbook.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    
    @Autowired
    private IResourceRepository resourceRepository;

    @Autowired
    private IRoomRepository roomRepository;

    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody ResourceModel resourceModel) {
        var resource = this.resourceRepository.findByResourceNumber(resourceModel.getResourceNumber());

        if(resource != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Recurso já existe");
        }

        var resourceRoom = this.roomRepository.findById(resourceModel.getRoomID());

        if(resourceRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sala que contém o recurso não existe");
        }

        var resourceCreated = this.resourceRepository.save(resourceModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceCreated);
    } 

    @GetMapping("/")
    public List<ResourceModel> listAll(HttpServletRequest request) {
        var resources = this.resourceRepository.findAll();
        return resources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listOne(@PathVariable UUID id) {
        var resource = resourceRepository.findById(id);
        
        if (resource == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody ResourceModel toBeUpdatedResource, HttpServletRequest request, @PathVariable UUID id) {
        var resource = this.resourceRepository.findById(id).orElse(null);

        System.out.println(request);

        if(resource == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso não encontrado");
        }
        
        Utils.copyNonNullProperties(toBeUpdatedResource, resource);
        var resourceUpdated = this.resourceRepository.save(resource);

        return ResponseEntity.ok().body(resourceUpdated);
    }    

     @DeleteMapping("/")
    public ResponseEntity<Void> deleteAll() {
        resourceRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Endpoint para excluir um usuário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOne(@PathVariable UUID id) {
        var resource = resourceRepository.findById(id);
    
        if (resource == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso não encontrado");
        }
    
        resourceRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
