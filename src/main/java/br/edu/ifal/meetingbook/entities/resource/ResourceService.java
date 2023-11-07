package br.edu.ifal.meetingbook.entities.resource;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifal.meetingbook.entities.meetingroom.IRoomRepository;
import br.edu.ifal.meetingbook.entities.user.UserModel;

@Service
public class ResourceService {
    @Autowired
    private IResourceRepository resourceRepository;

    @Autowired
    private IRoomRepository roomRepository;

     private String validateResourceFields(ResourceModel resource) throws Exception{

        if (resource.getResourceName() == null || resource.getResourceName().isEmpty()) {
            throw new Exception("O campo nome não pode estar em branco.");
        } if (resource.getResourceNumber() == 0) {
            throw new Exception("O campo número não pode estar em branco.");
        } if (resource.getResourceType() == null || resource.getResourceType().isEmpty()) {
            throw new Exception("O campo tipo não pode estar em branco.");
        } 
  
        return null; // Retorna null se todos os campos estiverem preenchidos corretamente
    }

    public ResourceModel createOrUpdateResource(ResourceModel resourceModel) throws Exception{
        var resource = this.resourceRepository.findByResourceNumber(resourceModel.getResourceNumber());

        if(resource != null) {
            throw new Exception("Recurso já existe");
        }

        validateResourceFields(resourceModel);

        var resourceRoom = this.roomRepository.findById(resourceModel.getRoomID());

        if(resourceRoom == null) {
            throw new Exception("Sala que contém o recurso não existe");
        }

        return this.resourceRepository.save(resourceModel);
                
    }
    
    public ResourceModel listOneResource(UUID id) throws Exception{
        var resource = this.resourceRepository.findById(id).orElse(null);

        if (resource == null) {
            throw new Exception("Recurso não foi encontrado não encontrado");
        }

        return resource;
    }

    public void deleteOneResource(UUID id) throws Exception{
        var resource = this.resourceRepository.findById(id).orElse(null);

        if (resource == null) {
            throw new Exception("Recurso não encontrado");
        }

        this.resourceRepository.delete(resource);
    }
}
