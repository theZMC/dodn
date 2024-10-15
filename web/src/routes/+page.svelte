<script lang="ts">
  import { TodoList } from "@/components/todo-list";
  import { taskFromJSON, type Task } from "@/types/task";
  import { onMount } from "svelte";
  let tasks: Task[] = [];

  onMount(async () => {
    let resp = await fetch("/api/tasks");
    tasks = ((await resp.json()) as []).map((taskJSON) =>
      taskFromJSON(taskJSON),
    );
  });

  const onEdit = async (task: Task) => {
    task.isSaving = true;
    let id = task.id;
    let resp = await fetch(`/api/tasks/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(task),
    });

    let updatedTask = taskFromJSON(await resp.json());

    tasks = tasks.map((t) =>
      t.id === id ? { ...updatedTask, isSaving: false } : t,
    );
  };

  const onCreate = async (task: Task) => {
    let id = crypto.randomUUID();
    task.isSaving = true;
    task.id = id;
    task.createdAt = new Date();

    tasks = [...tasks, task];

    let resp = await fetch("/api/tasks", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(task),
    });

    let newTask = taskFromJSON(await resp.json());
    tasks = tasks.map((t) =>
      t.id === id ? { ...newTask, isSaving: false } : t,
    );
  };

  const onComplete = async (task: Task) => {
    task.isSaving = true;
    task.isCompleted = true;
    let id = task.id;

    let resp = await fetch(`/api/tasks/${task.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(task),
    });

    let completedTask = taskFromJSON(await resp.json());

    tasks = tasks.map((t) =>
      t.id === id ? { ...completedTask, isSaving: false } : t,
    );
  };

  const onDelete = async (task: Task) => {
    task.isSaving = true;
    let id = task.id;
    tasks = tasks.filter((t) => t.id !== id);
  };
</script>

<main>
  <img src="compose.png" alt="Baby Yoda Peeking Sticker Art" />
  <h1>Todo or do not</h1>
  <TodoList {tasks} {onEdit} {onCreate} {onComplete} {onDelete}></TodoList>
</main>

<style lang="postcss">
  main {
    @apply flex flex-col items-center justify-center w-[clamp(300px,90vw,600px)] gap-6;
  }
  h1 {
    @apply text-6xl font-thin uppercase mb-6;
  }
</style>
