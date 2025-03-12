package com.test.twt.source.route;

import com.test.twt.source.model.Tweet;
import com.test.twt.source.service.TweetProcessingService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TweetRouteBuilder extends RouteBuilder {

    @Value("${kafka.topic.tweeter-source}")
    private String kafkaTopic;

    private final TweetProcessingService processingService;

    public TweetRouteBuilder(TweetProcessingService processingService) {
        this.processingService = processingService;
    }

    @Override
    public void configure() throws Exception {
        // Error handling
        errorHandler(deadLetterChannel("direct:error")
            .maximumRedeliveries(3)
            .redeliveryDelay(1000)
            .backOffMultiplier(2)
            .useExponentialBackOff());

        // Error route
        from("direct:error")
            .log("Error processing message: ${body}")
            .to("log:error");

        // Main route
        from("kafka:" + kafkaTopic + "?brokers={{camel.component.kafka.brokers}}")
            .routeId("tweet-processing-route")
            .log("Received message: ${body}")
            .unmarshal().json(JsonLibrary.Jackson, Tweet.class)
            .process(exchange -> {
                Tweet tweet = exchange.getIn().getBody(Tweet.class);
                processingService.processTweet(tweet);
            })
            .log("Successfully processed tweet ${body.id}");
    }
}