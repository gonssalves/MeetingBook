package br.edu.ifal.meetingbook.entities.resource;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IResourceRepository extends JpaRepository<ResourceModel, UUID> {
    ResourceModel findByResourceNumber(int resourceNumber);
    ResourceModel findByResourceName(String resourceName);
    ResourceModel findByResourceType(String resourceType);
    List<ResourceModel> findByRoomID(UUID roomID);
}
