package dev.zmc.dodn.api.aspect;

import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LatencyAspect {

  private AtomicInteger requestCount = new AtomicInteger(0);

  @Before("execution(* dev.zmc.dodn.api.entity.task.TaskController.*(..))")
  public void addLatency() throws InterruptedException {
    if (requestCount.incrementAndGet() % 3 == 0) {
      Thread.sleep(5000); // Add 5 seconds latency
    }
  }
}
