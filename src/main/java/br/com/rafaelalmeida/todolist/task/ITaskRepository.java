package br.com.rafaelalmeida.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ITaskRepository extends JpaRepository<taskModel, UUID>{
    
    List<taskModel> findByUserId(UUID userId);
}
