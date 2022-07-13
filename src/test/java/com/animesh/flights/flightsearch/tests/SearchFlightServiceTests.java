/*
 * package com.animesh.flights.flightsearch.tests;
 * 
 * import static org.mockito.Mockito.times;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.junit.Test; import org.junit.runner.RunWith; import
 * org.mockito.Mockito; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.mock.mockito.MockBean; import
 * org.springframework.data.domain.Page; import
 * org.springframework.data.domain.PageImpl; import
 * org.springframework.test.context.ContextConfiguration; import
 * org.springframework.test.context.junit4.SpringRunner;
 * 
 * import com.animesh.flights.flightsearch.Params; import
 * com.animesh.flights.flightsearch.config.FlightsConfiguration; import
 * com.animesh.flights.flightsearch.exception.BadRequestException; import
 * com.animesh.flights.flightsearch.model.domain.FlightDTO; import
 * com.animesh.flights.flightsearch.repository.FlightRepository; import
 * com.animesh.flights.flightsearch.service.applicationService.
 * SearchFlightService;
 * 
 * @ContextConfiguration(classes = FlightsConfiguration.class)
 * 
 * @RunWith(SpringRunner.class) public class SearchFlightServiceTests {
 * 
 * @Autowired private SearchFlightService searchFlightService;
 * 
 * @MockBean private FlightRepository flightrepository;
 * 
 * @Test(expected = BadRequestException.class) public void
 * testSearchWithoutMandatoryFilters() throws BadRequestException { Params
 * params = new Params(); params.setDestination(null); params.setOrigin("AMS");
 * ; searchFlightService.searchFlights(params); }
 * 
 * @Test(expected = BadRequestException.class) public void
 * testSearchWithoutMandatoryOrigin() throws BadRequestException { Params params
 * = new Params(); params.setDestination("DEL"); params.setOrigin(null); ;
 * searchFlightService.searchFlights(params); }
 * 
 * @Test public void verifyRepositoryMethodInvoked() throws BadRequestException
 * {
 * 
 * Params params = new Params(); params.setDestination("DEL");
 * params.setOrigin("AMS"); params.setSort(null);
 * searchFlightService.searchFlights(params);
 * Mockito.verify(flightrepository).searchFLightsBetweenOriginAndDestination(
 * Mockito.anyString(), Mockito.anyString()); }
 * 
 * @Test public void verifyRepositoryMethodInvokedwithsort() throws
 * BadRequestException {
 * 
 * Params params = new Params(); params.setDestination("DEL");
 * params.setOrigin("AMS"); params.setSort("traveltime,-");
 * 
 * List<FlightDTO> list = new ArrayList<>(); Page<FlightDTO> pages= new
 * PageImpl<>(list);
 * Mockito.when(flightrepository.searchFLightsBetweenOriginAndDestinationAndSort
 * (Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(pages);
 * 
 * searchFlightService.searchFlights(params); Mockito.verify(flightrepository,
 * times(1)).searchFLightsBetweenOriginAndDestinationAndSort(Mockito.anyString()
 * , Mockito.anyString(), Mockito.any());
 * 
 * Mockito.verify(flightrepository,
 * times(0)).searchFLightsBetweenOriginAndDestination(Mockito.anyString(),
 * Mockito.anyString()); } }
 */