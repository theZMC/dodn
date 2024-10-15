package dev.zmc.dodn.api.entity.task;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class TaskService {
  private TaskRepository repo;

  private void chaosEwok() {
    Double random = Math.random();

    if (random < 0.1) { // 10% chance of chaos
      String message = new StringBuilder().append("\n")
          .append("                                           ⣿⣿⣿⣿⡇\n")
          .append("                                           ⣿⣿⣿⣿⠃\n")
          .append("                                          ⢀⡏⠉⢹⡏ \n")
          .append("        ⣀⣀⣀      ⣀⣀⣀⣀⣀⣀⣀⣀⣀                ⢸⡇ ⣾  \n")
          .append("     ⣠⣶⣿⣿⣿⣿⣿⣶⡶⠶⠛⠋⠉⠉⠉⠉⠉⠉⠉⠉⠉⠙⠛⠶⢤⣤⣶⣶⣶⣶⣦⣄     ⣼ ⢀⡏  \n")
          .append("    ⣸⣿⣿⠟⠉⠉⠉⠉⢀⣠⣤⣴⠶⠛⠛⠛⠛⠛⠛⠛⠛⠿⠶⣦⣤⣄⣈⠉⠉⠙⠛⢿⣿⣷⡀   ⡟ ⢸⠃  \n")
          .append("    ⣿⣿⡏    ⠛⠻⠋⣁⣠⣤⣶⣶⣶⣶⣶⣶⣶⣶⣶⣦⣤⣈⠙⢻⡷⠦   ⣿⣿⡇  ⢸⡇ ⣿   \n")
          .append("    ⢻⣿⡇    ⣠⣶⣿⣿⣿⣿⣿⠿⠿⠿⠿⠿⠿⢿⣿⣿⣿⣿⣿⣶⣅    ⢸⣿⡇  ⣼ ⢠⡇   \n")
          .append("     ⣿⠃  ⣠⣾⣿⣿⠿⠛⠉⠁         ⠈⠉⠛⢿⣿⣿⣷⣄  ⠘⣿  ⢀⡟ ⢸⠁   \n")
          .append("     ⣿  ⣼⣿⣿⠟⠁ ⣀⣀⡀          ⣀⡀ ⠈⠻⣿⣿⣧  ⣿  ⢸⠇ ⡿    \n")
          .append("     ⣿ ⢀⣿⣿⠏ ⢠⣿⣿⣿⣿⡆       ⢰⣿⣿⣿⣷⡄ ⠹⣿⣿⡇ ⣿  ⣾ ⢰⡇    \n")
          .append("     ⣿ ⠘⣿⣿  ⠈⢿⣿⣿⣿⠏ ⣰⣾⣿⣿⣆ ⠘⣿⣿⣿⡿⠃  ⣿⣿⡇ ⣿ ⢀⡏ ⣼⠁    \n")
          .append("     ⣿  ⣿⣿⡄        ⠙⠿⠿⠿⠟   ⠉⠁   ⢰⣿⣿⠃ ⣿ ⢸⠃ ⡟     \n")
          .append("    ⣠⣿  ⠸⣿⣷⣀       ⠛⠲⠶⠖⠛⠁      ⢀⣿⣿⡟  ⣿⣀⣾ ⢸⠇     \n")
          .append("   ⣾⣿⡇   ⠉⠻⢿⣷⣦⣤⣀⡀         ⣀⣀⣤⣴⣿⣿⠿⠋   ⣿⣿⣃⣀⣼⡄     \n")
          .append("  ⢸⣿⣿⡇       ⠉⠙⠛⠿⠿⣿⣶⣶⣶⣶⣾⣿⡿⠿⠟⠛⠉⠁      ⣰⣿⣿⣿⣿⣿⡄    \n")
          .append("  ⣾⣿⣿⡇              ⣀⣀              ⣼⣿⣿⣿⣿⣿⣿⣷    \n")
          .append(" ⢠⣿⣿⣿⡇             ⢸⣿⣿             ⢰⣿⣿⣿⣿⣿⣿⣿⣿    \n")
          .append(" ⢸⣿⣿⣿⡇            ⣠⡿⠃⠹⣆            ⢸⣿⣿⣿⣿⣿⣿⣿⡿    \n")
          .append(" ⣿⣿⣿⣿⡇          ⠒⠛⠉   ⠙⢷⣦⡀         ⢸⣿⣿⣿⣿⣿⣿⣿⠃    \n")
          .append("⢠⣿⣿⣿⣿⣧⡀                 ⠈⠉         ⠘⣿⣿⣿⣿⣿⣿⠇     \n")
          .append("⢸⣿⣿⣿⣿⣿⣿⣷⣦⣄⡀                    ⢀⣀⣤⣴⡆⠈⠛⢿⡿⠟⠁      \n")
          .append("⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣆                 ⢀⣴⣿⣿⣿⣿⡇  ⣾⠁        \n")
          .append("⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣄             ⢀⣴⣿⣿⣿⣿⣿⣿⡇ ⢠⡏         \n")
          .append(" ⠙⠻⠿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣦⣄⣀     ⢀⣀⣴⣾⣿⣿⣿⣿⣿⣿⣿⣿⡇ ⢸⠇         \n")
          .append("     ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃ ⡿          \n")
          .append("     ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿ ⢰⡇          \n")
          .append("     ⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏ ⣸           \n")
          .append("     ⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇   ⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠇ ⡏           \n")
          .append("      ⠙⠛⠛⠻⠿⠿⠿⠿⠿⠿⠟⠋     ⠙⠛⠿⠿⠿⠿⠿⠿⠿⠿⣿ ⢸⠇           \n")
          .append("\n")
          .append("         Chaos Ewok has struck!                 \n").append("\n")
          .toString();

      throw new RuntimeException(message);
    }
    try {
      if (random > 0.5) { // 50% chance of delay
        Thread.sleep((long) (random * 1000));
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public TaskService(@Autowired TaskRepository repo) {
    this.repo = repo;
  }

  public List<Task> list() {
    chaosEwok();
    List<Task> tasks = repo.findAll();
    return tasks;
  }

  public Task create(Task task) {
    chaosEwok();
    task.setCreatedAt(Instant.now());
    task.setUpdatedAt(Instant.now());
    task.setId(UUID.randomUUID().toString());
    Task newTask = repo.save(task);
    return newTask;
  }

  public Task get(String id) {
    chaosEwok();
    return repo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
  }

  public Task update(String id, Task task) {
    chaosEwok();
    Task existingTask = repo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    existingTask.setName(task.getName());
    existingTask.setDescription(task.getDescription());
    existingTask.setIsCompleted(task.getIsCompleted());
    existingTask.setUpdatedAt(Instant.now());
    return repo.save(existingTask);
  }

  public Task delete(String id) {
    chaosEwok();
    Task existingTask = repo.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    repo.deleteById(id);
    return existingTask;
  }
}
