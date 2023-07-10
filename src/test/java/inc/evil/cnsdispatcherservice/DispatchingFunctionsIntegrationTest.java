package inc.evil.cnsdispatcherservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@FunctionalSpringBootTest
class DispatchingFunctionsIntegrationTest {

    @Autowired
    private FunctionCatalog functionCatalog;

    @Test
    void packAndLabel() {
        Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> packAndLabel =
                functionCatalog.lookup(Function.class, "pack|label");
        long orderId = 121;
        StepVerifier.create(packAndLabel.apply(new OrderAcceptedMessage(orderId)))
                .expectNextMatches(orderDispatchedMessage -> orderDispatchedMessage.equals(new OrderDispatchedMessage(orderId)))
                .verifyComplete();
    }
}
