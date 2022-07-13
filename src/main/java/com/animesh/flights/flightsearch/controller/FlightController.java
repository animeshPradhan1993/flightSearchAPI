package com.animesh.flights.flightsearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animesh.flights.flightsearch.Params;
import com.animesh.flights.flightsearch.exception.BadRequestException;
import com.animesh.flights.flightsearch.model.domain.ResultDTO;
import com.animesh.flights.flightsearch.service.applicationService.SearchFlightService;

@RestController
@RequestMapping("/flight/v1")
public class FlightController {

	@Autowired
	private SearchFlightService searchFlightService;

	@GetMapping("/flight")
	public List<ResultDTO> getAllFlights(Params params) throws BadRequestException {

		List<ResultDTO> dtoList = searchFlightService.searchFlights(params);

		return dtoList;
	}

}
