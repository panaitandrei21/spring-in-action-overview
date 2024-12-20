package tacos.web;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.
        ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import lombok.Data;
@Component
@ConfigurationProperties(prefix="taco.orders")
@Data
@Validated
@Slf4j
public class OrderProps {

    @Min(value=5, message="must be between 5 and 25")
    @Max(value=25, message="must be between 5 and 25")
    private int pageSize;
    @EventListener(ApplicationReadyEvent.class)
    public void test() {
        log.info("--------test------------- ${}", pageSize);
    }
}