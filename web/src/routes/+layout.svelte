<script>
  import "../app.css";
</script>

<slot></slot>

<style lang="postcss">
  :global(body) {
    @apply w-[100vw] h-[100svh];
    @apply grid !important;
    @apply place-items-center;
  }
</style>
