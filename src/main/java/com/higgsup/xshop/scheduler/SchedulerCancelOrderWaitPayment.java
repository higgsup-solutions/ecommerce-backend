package com.higgsup.xshop.scheduler;

import com.higgsup.xshop.service.IOrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerCancelOrderWaitPayment {

  private final IOrderService orderService;

  public SchedulerCancelOrderWaitPayment(
      IOrderService orderService) {
    this.orderService = orderService;
  }

  @Scheduled(fixedRate = 3600000)
  public void scheduleFixedRateTask() {
    orderService.cancelOrderWaitPayment();
  }
}
