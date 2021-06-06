package guru.springframework.msscbeerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String BREWING_REQUEST_QUEUE = "brewing-request";
    public static final String NEW_INVENTORY_QUEUE = "new-inventory";

    //passing ObjectMapper as argument and setting it into the converter forces to inject SpringBoot managed jackson instance and avoids below error at startup
    //Caused by: org.springframework.jms.support.converter.MessageConversionException: Failed to convert JSON message content; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `guru.sfg.brewery.model.events.BrewBeerEvent` (no Creators, like default construct, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
    // at [Source: (String)"{"beerDto":{"id":"f17a77be-2829-443c-875a-48e9089c7c6c","version":0,"createdDate":{"offset":{"totalSeconds":0,"id":"Z","rules":{"fixedOffset":true,"transitions":[],"transitionRules":[]}},"year":2021,"monthValue":5,"month":"MAY","dayOfMonth":24,"dayOfYear":144,"dayOfWeek":"MONDAY","hour":20,"minute":32,"second":37,"nano":618000000},"lastModifiedDate":{"offset":{"totalSeconds":0,"id":"Z","rules":{"fixedOffset":true,"transitions":[],"transitionRules":[]}},"year":2021,"monthValue":5,"month":"MAY","d"[truncated 211 chars]; line: 1, column: 2]
    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
