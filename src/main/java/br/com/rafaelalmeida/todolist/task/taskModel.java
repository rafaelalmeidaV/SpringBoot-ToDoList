package br.com.rafaelalmeida.todolist.task;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks")
public class taskModel {
    
    @Id
    @GeneratedValue(generator = "UUID" )
    private UUID id;
    private String description;
    private String title;
    private LocalDate startAt;
    private LocalDate endAt;
    private String priority;
    
    private LocalDate createdAt;

    @CreationTimestamp
    private UUID userId;

    public void setIdUser(UUID idUser) {
    }

}
