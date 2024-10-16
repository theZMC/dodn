# (TO)DO OR DO NOT OTEL AUTO-INSTRUMENTATION DEMO

This is the dumbest toy application you've ever seen. The only goal of this project is to provide a contrived example
K8s app that can be instrumented via OTEL auto-instrumentation. It's designed to work with the big-bang project
and so the k8s manifests are hard-coded with values that match the defaults provided there. I've commented these
values for alternative setups if necessary.

## How to deploy with OTEL auto-instrumentation

### (Recommended) Deploy Big-Bang

- Follow the applicable directions in [Big Bang Quickstart](https://docs-bigbang.dso.mil/latest/docs/guides/deployment-scenarios/quickstart/).
- Provide the following as a values overlay:
  ```yaml
  addons:
    alloy:
      enabled: true
  # Disabling kyverno policies allows you to use cert-manager and otel-operator
  # installation manifests as-is
  kyvernoPolicies:
    enabled: false
  ```

### Install the OTEL operator

1. Make sure `cert-manager` is installed first, otherwise the otel operator won't be able to install its webhook configurations.

   - `cert-manager` can be installed with the following:

     ```bash
     kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.16.1/cert-manager.yaml
     ```

2. Wait for all of `cert-manager` to be ready:

   ```bash
   ❯ kubectl get deployments -n cert-manager
   NAME                      READY   UP-TO-DATE   AVAILABLE   AGE
   cert-manager              1/1     1            1           3h21m
   cert-manager-cainjector   1/1     1            1           3h21m
   cert-manager-webhook      1/1     1            1           3h21m
   ```

3. Install the otel operator:

   ```bash
   kubectl apply -f https://github.com/open-telemetry/opentelemetry-operator/releases/latest/download/opentelemetry-operator.yaml
   ```

4. Wait for the otel operator to be ready:

   ```bash
   ❯ kubectl get deployments -n opentelemetry-operator-system
   NAME                                        READY   UP-TO-DATE   AVAILABLE   AGE
   opentelemetry-operator-controller-manager   1/1     1            1           5h21m
   ```

5. Install the demo application:

   ```bash
   kubectl apply -f https://raw.githubusercontent.com/theZMC/dodn/refs/heads/main/k8s/manifests.yml -n dodn
   ```

6. Navigate to your observability frontend (for bigbang with developer ingress certs, this is https://grafana.dev.bigbang.mil)
