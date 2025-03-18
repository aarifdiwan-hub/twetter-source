package com.test.twt.source.route;

import com.test.twt.source.model.Tweet;
import com.test.twt.source.service.TweetProcessingService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TweetRouteBuilder extends RouteBuilder {
    private static final Logger logger = LoggerFactory.getLogger(TweetRouteBuilder.class);

    @Value("${kafka.topic.tweeter-source}")
    private String kafkaTopic;

    private final TweetProcessingService processingService;

    public TweetRouteBuilder(TweetProcessingService processingService) {
        this.processingService = processingService;
    }

    @Override
    public void configure() throws Exception {
        // Error handling
        onException(Exception.class)
            .handled(true)
            .maximumRedeliveries(3)
            .redeliveryDelay(1000)
            .backOffMultiplier(2)
            .useExponentialBackOff()
            .log("Error processing message: ${exception.message}")
            .process(exchange -> {
                Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                logger.error("Failed to process message", cause);
                // Log the raw message for debugging
                String rawMessage = exchange.getIn().getBody(String.class);
                logger.error("Raw message content: {}", rawMessage);
            })
            .to("direct:error");

        // Main route
        from("kafka:" + kafkaTopic + 
             "?brokers={{camel.component.kafka.brokers}}" +
             "&valueDeserializer=org.apache.kafka.common.serialization.StringDeserializer" +
             "&autoOffsetReset=earliest" +
             "&groupId={{camel.component.kafka.group-id}}")
            .routeId("tweet-processing-route")
            .log("Received message: ${body}")
            .unmarshal().json(JsonLibrary.Jackson, Tweet.class)
            .process(exchange -> {
                Tweet tweet = exchange.getIn().getBody(Tweet.class);
                logger.debug("Processing tweet ID: {}", tweet.id());
                processingService.processTweet(tweet);
            })
            .log("Successfully processed tweet ${body.id}");
    }
}