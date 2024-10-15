<script lang="ts">
  import { type Task } from "@/types/task";

  import { TodoListItem } from "@/components/todo-list/item";
  import { slide } from "svelte/transition";
  export let tasks: Task[];

  $: undoneTasks = tasks.filter((task) => !task.isCompleted);
  $: doneTasks = tasks.filter((task) => task.isCompleted);

  let dates: Map<string, Task[]>;

  $: {
    let map = new Map<string, Task[]>();
    for (let task of undoneTasks) {
      let date = task.createdAt!.toLocaleDateString();
      if (!map.has(date)) {
        map.set(date, []);
      }
      map.get(date)!.push(task);
    }

    dates = map;
  }

  export let onEdit: (task: Task) => Promise<void> = async () => {};
  export let onCreate: (task: Task) => Promise<void> = async () => {};
  export let onComplete: (task: Task) => Promise<void> = async () => {};
  export let onDelete: (task: Task) => Promise<void> = async () => {};
</script>

<section>
  {#each dates as [date, dateTasks]}
    <ul transition:slide>
      <div class="relative">
        <div class="absolute inset-0 flex items-center">
          <span class="w-full border-t"></span>
        </div>
        <div class="relative flex justify-center text-xs uppercase">
          <h2 class="bg-card text-muted-foreground px-2">{date}</h2>
        </div>
      </div>
      {#each dateTasks as task}
        <TodoListItem {task} {onEdit} {onComplete} {onDelete} />
      {/each}
    </ul>
  {/each}
  <div class="relative">
    <div class="absolute inset-0 flex items-center">
      <span class="w-full border-t"></span>
    </div>
    <div class="relative flex justify-center text-xs uppercase">
      <h2 class="bg-card text-muted-foreground px-2">New Task</h2>
    </div>
  </div>
  <TodoListItem onEdit={onCreate} />
  {#if doneTasks.length}
    <ul transition:slide>
      <div class="relative">
        <div class="absolute inset-0 flex items-center">
          <span class="w-full border-t"></span>
        </div>
        <div class="relative flex justify-center text-xs uppercase">
          <h2 class="bg-card text-muted-foreground px-2">Completed Tasks</h2>
        </div>
      </div>
      {#each doneTasks as task}
        <TodoListItem {task} {onDelete} />
      {/each}
    </ul>
  {/if}
</section>

<style lang="postcss">
  section {
    @apply flex flex-col gap-2 w-full;
  }
  ul {
    @apply flex flex-col gap-2;
  }
</style>
