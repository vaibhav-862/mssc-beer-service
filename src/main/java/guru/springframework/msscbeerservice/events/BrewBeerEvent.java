package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

//this annotation fixes below exception at startup as Jackson requires a no-args constructor to be passed to the listener
//Caused by: com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `guru.springframework.msscbeerservice.events.BrewBeerEvent` (no Creators, like default construct, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }

}
