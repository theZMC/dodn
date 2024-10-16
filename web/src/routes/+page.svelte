<script lang="ts">
  import { TodoList } from "@/components/todo-list";
  import { taskFromJSON, type Task } from "@/types/task";
  import { toast } from "svelte-sonner";
  import { onMount } from "svelte";
  import { ExclamationTriangle } from "svelte-radix";
  let tasks: Task[] = [];

  const fail = (msg: string) => {
    toast(msg, {
      duration: 5000,
      icon: ExclamationTriangle,
    });
  };

  onMount(async () => {
    let resp: Awaited<ReturnType<typeof fetch>>;
    try {
      resp = await fetch("/api/tasks");
    } catch (error) {
      fail("Failed to fetch tasks!");
      console.error(error);
      return;
    }
    if (!resp.ok) {
      fail("Failed to fetch tasks!");
      return;
    }
    tasks = ((await resp.json()) as []).map((taskJSON) =>
      taskFromJSON(taskJSON),
    );
  });

  const onEdit = async (task: Task) => {
    task.isSaving = true;
    let id = task.id;

    let resp: Awaited<ReturnType<typeof fetch>>;

    try {
      resp = await fetch(`/api/tasks/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(task),
      });
    } catch (error) {
      fail("Failed to fetch tasks!");
      console.error(error);
      task.isSaving = false;
      return;
    }

    if (!resp.ok) {
      fail(`[${resp.status}] Failed to fetch tasks!`);
      task.isSaving = false;
      return;
    }

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

    let resp: Awaited<ReturnType<typeof fetch>>;

    try {
      resp = await fetch("/api/tasks", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(task),
      });
    } catch (error) {
      fail("Failed to create task!");
      console.error(error);
      tasks = tasks.filter((t) => t.id !== id);
      return;
    }

    if (!resp.ok) {
      fail(`[${resp.status}] Failed to create task!`);
      tasks = tasks.filter((t) => t.id !== id);
      return;
    }

    let newTask = taskFromJSON(await resp.json());
    tasks = tasks.map((t) =>
      t.id === id ? { ...newTask, isSaving: false } : t,
    );
  };

  const onComplete = async (task: Task) => {
    task.isSaving = true;
    task.isCompleted = true;
    let id = task.id;

    let resp: Awaited<ReturnType<typeof fetch>>;
    try {
      resp = await fetch(`/api/tasks/${task.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(task),
      });
    } catch (error) {
      fail("Failed to complete task!");
      console.error(error);
      task.isSaving = false;
      return;
    }

    if (!resp.ok) {
      fail(`[${resp.status}] Failed to complete task!`);
      task.isSaving = false;
      return;
    }

    let completedTask = taskFromJSON(await resp.json());

    tasks = tasks.map((t) =>
      t.id === id ? { ...completedTask, isSaving: false } : t,
    );
  };

  const onDelete = async (task: Task) => {
    task.isSaving = true;
    let id = task.id;
    let resp: Awaited<ReturnType<typeof fetch>>;
    try {
      resp = await fetch(`/api/tasks/${id}`, {
        method: "DELETE",
      });
    } catch (error) {
      fail("Failed to delete task!");
      console.error(error);
      return;
    }

    if (!resp.ok) {
      fail(`[${resp.status}] Failed to delete task!`);
      return;
    }

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
