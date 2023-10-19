package br.edu.ifal.meetingbook.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifal.meetingbook.meetingroom.IRoomRepository;

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
}
