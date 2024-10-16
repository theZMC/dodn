<script lang="ts">
  import { Button } from "@/components/ui/button";
  import { type Task } from "@/types/task";
  import { Check, Trash } from "svelte-radix";
  import Reload from "svelte-radix/Reload.svelte";
  import { slide } from "svelte/transition";

  export let task: Task = {};

  let taskName = task.name || "";

  export let onEdit: (task: Task) => Promise<void> = async (_: Task) => {};
  export let onComplete: (task: Task) => Promise<void> = async (_: Task) => {};
  export let onDelete: (task: Task) => Promise<void> = async (_: Task) => {};

  export let transition = slide;

  const onBlur = (_: FocusEvent) => {
    if (!taskName) {
      taskName = task.name || "";
    }
  };

  const onNameChange = (event: KeyboardEvent) => {
    if (event.key !== "Enter" && event.key !== "Escape") return;

    (event.target as HTMLHeadingElement).blur();

    if (event.key === "Escape") {
      taskName = task.name || "";
      return;
    }

    if (event.key === "Enter") {
      onEdit({
        ...task,
        name: taskName,
      });
      if (!task.name) {
        taskName = "";
      }
    }
    (event.target as HTMLHeadingElement).blur();
  };

  const onCompleteClicked = async () => {
    await onComplete(task);
  };

  const onDeleteClicked = async () => {
    await onDelete(task);
  };
</script>

<li transition:transition>
  {#if task.isSaving}
    <div transition:slide={{ axis: "x" }}>
      <Reload class="w-6 h-6 animate-spin mr-3 ml-1" />
    </div>
  {/if}
  <!-- svelte-ignore a11y-missing-content -->
  {#if !task.isCompleted}
    <h3
      class={task.name && !taskName
        ? "emptied"
        : !task.name && !taskName
          ? "initial"
          : ""}
      bind:innerText={taskName}
      contenteditable
      on:keydown={onNameChange}
      on:blur={onBlur}
    ></h3>
  {:else}
    <h3>{task.name}</h3>
  {/if}
  {#if !task.isSaving && task.name}
    <div class="icons">
      {#if !task.isCompleted}
        <Button variant="outline" class="p-2" on:click={onCompleteClicked}>
          <Check class="w-4 h-4" />
        </Button>
      {/if}
      <Button
        variant="outline"
        class="p-2 text-destructive"
        on:click={onDeleteClicked}
      >
        <Trash class="w-4 h-4" />
      </Button>
    </div>
  {/if}
</li>

<style lang="postcss">
  h3.initial,
  h3.emptied {
    @apply text-gray-400 italic font-thin;
  }
  h3.initial::after {
    content: "create a task";
  }
  h3.emptied::after {
    content: "can't be empty";
  }
  h3 {
    @apply p-1 text-lg;
  }
  h3[contenteditable] {
    @apply transition-colors;
    @apply rounded-sm;
    @apply border border-transparent;
  }
  h3[contenteditable]:hover {
    @apply bg-gray-100;
    @apply border-gray-300;
  }
  h3[contenteditable]:focus {
    @apply bg-white;
    @apply border-transparent;
    @apply outline-gray-500;
  }
  li {
    @apply border rounded-md px-1 py-1 w-full flex gap-1 justify-between items-center;
    @apply transition-colors;
  }
  li:hover {
    @apply border-gray-500;
  }
  h3 {
    @apply font-semibold w-full;
  }
  div.icons {
    @apply opacity-0;
    @apply transition-opacity;
    @apply flex gap-1;
  }
  li:hover div.icons {
    @apply opacity-100;
  }
</style>
