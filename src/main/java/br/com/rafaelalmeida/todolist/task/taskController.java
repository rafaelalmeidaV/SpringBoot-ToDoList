package br.com.rafaelalmeida.todolist.task;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelalmeida.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class taskController {

    @Autowired
    private ITaskRepository taskRepository;
    private List<taskModel> tasks;
    
    @PostMapping("/")   
    public ResponseEntity create(@RequestBody taskModel taskModel, HttpServletRequest request){

        var IdUser = request.getAttribute("userId");
        taskModel.setIdUser((UUID) IdUser);

        var currentDate = LocalDate.now();
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End date must be greater than current date");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End date must be greater than start date ");
        }
           
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    public List<taskModel> list(HttpServletRequest request) {
        var IdUser = request.getAttribute("userId");
        this.taskRepository.findByUserId((UUID) IdUser);
        return tasks;
    }

    @PutMapping("/{id}")                                                                            
    public ResponseEntity update(@RequestBody taskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var IdUser = request.getAttribute("userId");
        var task = this.taskRepository.findById(id).orElse(null);  
        
        if(task == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }   
        
        if(!task.getUserId().equals(IdUser)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You don't have permission to update this task");
        }

        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
        
    }
}
