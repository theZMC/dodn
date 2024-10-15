package dev.zmc.dodn.api.entity.task;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TaskService {
  private TaskRepository repo;

  private void chaosMonkey() {
    if (Math.random() < 0.3) {
      throw new RuntimeException("Chaos Monkey strikes!");
    }
    try {
      if (Math.random() > 0.7) {
        Thread.sleep(5000); // Add 5 seconds latency
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public TaskService(@Autowired TaskRepository repo) {
    this.repo = repo;
  }

  public List<Task> list() {
    chaosMonkey();
    List<Task> tasks = repo.findAll();
    return tasks;
  }

  public Task create(Task task) {
    chaosMonkey();
    task.setCreatedAt(Instant.now());
    task.setUpdatedAt(Instant.now());
    task.setId(UUID.randomUUID().toString());
    Task newTask = repo.save(task);
    return newTask;
  }

  public Task get(String id) {
    chaosMonkey();
    return repo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
  }

  public Task update(String id, Task task) {
    chaosMonkey();
    Task existingTask = repo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    existingTask.setName(task.getName());
    existingTask.setDescription(task.getDescription());
    existingTask.setIsCompleted(task.getIsCompleted());
    existingTask.setUpdatedAt(Instant.now());
    return repo.save(existingTask);
  }

  public Task delete(String id) {
    chaosMonkey();
    Task existingTask = repo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    repo.deleteById(id);
    return existingTask;
  }
}
