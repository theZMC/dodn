package dev.zmc.dodn.api.entity.task;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TaskService {
  private TaskRepository repo;

  public TaskService(@Autowired TaskRepository repo) {
    this.repo = repo;
  }

  public List<Task> list() {
    List<Task> tasks = repo.findAll();
    return tasks;
  }

  public Task create(Task task) {
    task.setCreatedAt(Instant.now());
    task.setUpdatedAt(Instant.now());
    task.setId(UUID.randomUUID().toString());
    Task newTask = repo.save(task);
    return newTask;
  }

  public Task get(String id) {
    return repo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
  }

  public Task update(String id, Task task) {
    Task existingTask = repo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    existingTask.setName(task.getName());
    existingTask.setDescription(task.getDescription());
    existingTask.setIsCompleted(task.getIsCompleted());
    existingTask.setUpdatedAt(Instant.now());
    return repo.save(existingTask);
  }

  public Task delete(String id) {
    Task existingTask = repo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    repo.deleteById(id);
    return existingTask;
  }
}
