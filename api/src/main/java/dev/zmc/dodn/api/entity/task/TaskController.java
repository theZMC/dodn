package dev.zmc.dodn.api.entity.task;

import java.util.List;

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

@RestController
@RequestMapping("/tasks")
class TaskController {
  private TaskService service;

  public TaskController(@Autowired TaskService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<Task>> list() {
    List<Task> tasks = this.service.list();
    return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> get(@PathVariable String id) {

    Task task = this.service.get(id);
    return new ResponseEntity<Task>(task, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Task> create(@RequestBody Task task) {
    Task newTask = this.service.create(task);
    return new ResponseEntity<Task>(newTask, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> update(@PathVariable String id, @RequestBody Task task) {
    Task updatedTask = this.service.update(id, task);
    return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Task> delete(@PathVariable String id) {
    Task deletedTask = this.service.delete(id);
    return new ResponseEntity<Task>(deletedTask, HttpStatus.OK);
  }
}
