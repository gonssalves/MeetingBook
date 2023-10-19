package br.edu.ifal.meetingbook.meetingroom;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tbl_resources")
public class ResourceModel {
    
    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;

    private String resourceName;
    
    private UUID roomId;
}
