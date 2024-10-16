<script>
  import { Toaster } from "@/components/ui/sonner";
  import "../app.css";
</script>

<Toaster class="absolute top-4 right-4" />
<slot></slot>

<style lang="postcss">
  :global(body) {
    @apply w-[100vw] h-[100svh];
    @apply grid !important;
    @apply place-items-center;
  }
</style>
