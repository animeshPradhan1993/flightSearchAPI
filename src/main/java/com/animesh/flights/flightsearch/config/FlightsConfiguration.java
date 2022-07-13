package com.animesh.flights.flightsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.animesh.flights.flightsearch.model.domain.FlightDTO;
import com.animesh.flights.flightsearch.model.domain.Graph;
import com.animesh.flights.flightsearch.service.applicationService.SearchFlightService;

@Configuration
public class FlightsConfiguration {
	@Bean
	public SearchFlightService searchFlightService() {
		return new SearchFlightService();
	}

	@Bean
	public Graph<FlightDTO> graph() {
		return new Graph<FlightDTO>();
	}
}
