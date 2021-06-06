package guru.springframework.msscbeerservice.services.brewing;

import guru.springframework.msscbeerservice.config.JmsConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.sfg.brewery.model.events.BrewBeerEvent;
import guru.sfg.brewery.model.events.NewInventoryEvent;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.sfg.brewery.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    //Transactional anno fixes below exception at startup
    //org.springframework.jms.listener.adapter.ListenerExecutionFailedException: Listener method 'public void guru.springframework.msscbeerservice.services.brewing.BrewBeerListener.listen(guru.sfg.brewery.model.events.BrewBeerEvent)' threw exception; nested exception is org.hibernate.LazyInitializationException: could not initialize proxy [guru.springframework.msscbeerservice.domain.Beer#f17a77be-2829-443c-875a-48e9089c7c6c] - no Session
    //Caused by: org.hibernate.LazyInitializationException: could not initialize proxy [guru.springframework.msscbeerservice.domain.Beer#c208e05f-a380-4dd7-a2b1-66502126f6fe] - no Session
    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event) {
        BeerDto beerDto = event.getBeerDto();

        Beer beer = beerRepository.getOne(beerDto.getId());

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed beer : " + beer.getMinOnHand() + ", QOH : " + beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
