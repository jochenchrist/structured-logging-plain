import static net.logstash.logback.argument.StructuredArguments.keyValue;
import static net.logstash.logback.argument.StructuredArguments.kv;
import static net.logstash.logback.argument.StructuredArguments.v;
import static net.logstash.logback.argument.StructuredArguments.value;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

class StructuredLoggingTests {

  private static final Logger log = LoggerFactory.getLogger("MyApplication");

  @Test
  void simpleLog() {
    log.info("Order {} saved", 123);
  }

  @Test
  void simpleLog2() {
    log.info("Order saved orderId", 123);
  }

  @Test
  void logValue() {
    String orderId = "123";
    log.info("Order {} saved", value("orderId", orderId));
  }

  @Test
  void logMdc() {
    String orderId = "123";
    try (var ignored = MDC.putCloseable("orderId", orderId)) {
      log.info("Order saved");
    }
  }

  @Test
  void logMdc2() {
    String orderId = "123";
    MDC.put("orderId", orderId);
    log.info("Order saved");
    MDC.remove("orderId");
  }

  @Test
  void logValue2() {
    String orderId = "123";
    log.info("Order saved {}", v("orderId", orderId));
  }

  @Test
  void logValue3() {
    String oldStatus = "NEW";
    String newStatus = "READY";
    log.info("Status changed {}->{}.", v("oldStatus", oldStatus), v("newStatus", newStatus));
  }

  @Test
  void logKeyValue() {
    String orderId = "123";
    log.info("Order saved {}", keyValue("orderId", orderId));
  }

  @Test
  void logKeyValue2() {
    String orderId = "123";
    String status = "NEW";
    log.info("Order saved", kv("orderId", orderId), kv("status", status));
  }

  @Test
  void logObject() {
    Order order = new Order("123", "NEW", null);
    log.info("Order saved", kv("order", order));
  }

  static class Order {
    String orderId;
    String status;
    String canceled;

    Order(String orderId, String status, String canceled) {
      this.orderId = orderId;
      this.status = status;
      this.canceled = canceled;
    }

    public String getOrderId() {
      return orderId;
    }

    public String getStatus() {
      return status;
    }

    public String getCanceled() {
      return canceled;
    }
  }
}
