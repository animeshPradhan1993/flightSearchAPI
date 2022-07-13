package com.animesh.flights.flightsearch.service.applicationService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.animesh.flights.flightsearch.Constant;
import com.animesh.flights.flightsearch.FlightComparators;
import com.animesh.flights.flightsearch.Params;
import com.animesh.flights.flightsearch.SortConstants;
import com.animesh.flights.flightsearch.exception.BadRequestException;
import com.animesh.flights.flightsearch.model.domain.FlightDTO;
import com.animesh.flights.flightsearch.model.domain.Graph;
import com.animesh.flights.flightsearch.model.domain.ResultDTO;

public class SearchFlightService {

	private Set<String> allowedFilters = new HashSet<>();
	@Autowired
	Graph<FlightDTO> flightGraph;

	private Map<String, Comparator<ResultDTO>> sortParameters = new HashMap<>();

	@PostConstruct
	public void init() {
		allowedFilters.add(Constant.ORIGIN);
		allowedFilters.add(Constant.DESTINATION);
// adds all the comparators to sort parameters map
		sortParameters.put(SortConstants.PRICE, FlightComparators.sortByPriceAsc);
		sortParameters.put(SortConstants.PRICEASC, FlightComparators.sortByPriceAsc);
		sortParameters.put(SortConstants.PRICEPLUS, FlightComparators.sortByPriceAsc);
		sortParameters.put(SortConstants.PRICEDESC, FlightComparators.sortByPriceDesc);
		sortParameters.put(SortConstants.PRICEMINUS, FlightComparators.sortByPriceDesc);

		sortParameters.put(SortConstants.TRAVELTIME, FlightComparators.sortBytravelTimeAsc);
		sortParameters.put(SortConstants.TRAVELTIMEDESC, FlightComparators.sortBytravelTimeDesc);
		sortParameters.put(SortConstants.TRAVELTIMEMINUS, FlightComparators.sortBytravelTimeDesc);
		sortParameters.put(SortConstants.TRAVELTIMEASC, FlightComparators.sortBytravelTimeAsc);
		sortParameters.put(SortConstants.TRAVELTIMEPLUS, FlightComparators.sortBytravelTimeAsc);

		sortParameters.put(SortConstants.ARRIVALTIME, FlightComparators.sortByArrivalTimeAsc);
		sortParameters.put(SortConstants.ARRIVALTIMEASC, FlightComparators.sortByArrivalTimeAsc);
		sortParameters.put(SortConstants.ARRIVALTIMEPLUS, FlightComparators.sortByArrivalTimeAsc);
		sortParameters.put(SortConstants.ARRIVALTIMEDESC, FlightComparators.sortByArrivalTimeDesc);
		sortParameters.put(SortConstants.ARRIVALTIMEMINUS, FlightComparators.sortByArrivalTimeDesc);

		sortParameters.put(SortConstants.DEPARTURETIME, FlightComparators.sortByDepartureTimeAsc);
		sortParameters.put(SortConstants.DEPARTURETIMEASC, FlightComparators.sortByDepartureTimeAsc);
		sortParameters.put(SortConstants.DEPARTURETIMEASC, FlightComparators.sortByDepartureTimeAsc);
		sortParameters.put(SortConstants.DEPARTURETIMEDESC, FlightComparators.sortByDepartureTimeDesc);
		sortParameters.put(SortConstants.DEPARTURETIMEASC, FlightComparators.sortByDepartureTimeDesc);

	}
	// retrieves a list of all possible connections between two cities and sort the
	// list based on sort parameter provided

	public List<ResultDTO> searchFlights(Params params) throws BadRequestException {

		if (params.getOrigin() == null) {

			throw new BadRequestException("Please enter the origin");
		}
		if (params.getDestination() == null) {

			throw new BadRequestException("Please enter the destination");
		}
		if (!StringUtils.isEmpty(params.getSort())) {
			Optional<Comparator<ResultDTO>> c1 = Optional
					.ofNullable(Optional.ofNullable(sortParameters.get(params.getSort().toLowerCase().trim()))
							.orElseThrow(() -> new BadRequestException("Invalid sort Parameter")));

			List<List<String>> connections = flightGraph.getConnections(params.getOrigin(), params.getDestination());

			List<ResultDTO> retrievedFlights = mapListToResultDTO(retrieveAllFlights(connections));
			if (c1 != null) {
				Collections.sort(retrievedFlights, c1.get());
			}

			return retrievedFlights;
		} else {
			return mapListToResultDTO(
					retrieveAllFlights(flightGraph.getConnections(params.getOrigin(), params.getDestination())));
		}

	}
//retrieves all the possible connections
	private List<List<FlightDTO>> retrieveAllFlights(List<List<String>> connections) {
		List<List<FlightDTO>> allConnections = new ArrayList<>();
		for (List<String> list : connections) {
			List<List<FlightDTO>> listOfFinalFLights = new ArrayList<>();
			for (int i = 0; i < list.size() - 1; i++) {
				List<FlightDTO> flightsBetweenTwoPoints = flightGraph.getFlightMap().get(list.get(i))
						.get(list.get(i + 1));
				findConnectingFlightsBetweenPoints(listOfFinalFLights, flightsBetweenTwoPoints);

			}
			allConnections.addAll(listOfFinalFLights);

		}

		return allConnections;
	}
// retrieves connections between 2 points
	private void findConnectingFlightsBetweenPoints(List<List<FlightDTO>> list,
			List<FlightDTO> flightsBetweenTwoPoints) {
		List<List<FlightDTO>> returnList = new ArrayList<>();
		if (list.isEmpty()) {
			for (int i = 0; i < flightsBetweenTwoPoints.size(); i++) {
				List<FlightDTO> flights = new ArrayList<>();
				flights.add(flightsBetweenTwoPoints.get(i));
				returnList.add(flights);
			}
		} else {

			for (List<FlightDTO> l : list) {
				for (FlightDTO dto1 : flightsBetweenTwoPoints) {
					if (dto1.getDepartureTime().after(l.get(l.size() - 1).getArrivalTime())) {
						List<FlightDTO> flightList = new ArrayList<>();
						flightList.addAll(l);
						flightList.add(dto1);
						returnList.add(flightList);
					}
				}
			}

		}
		list.clear();
		list.addAll(returnList);
	}
// maps the list of list of Flights to the Result DTO
	private List<ResultDTO> mapListToResultDTO(List<List<FlightDTO>> list) {
		List<ResultDTO> resultList = new ArrayList<>();
		for (List<FlightDTO> l1 : list) {
			ResultDTO r1 = new ResultDTO();
			r1.setFlightList(l1);

			r1.setTotalPrice(l1.stream().map(l -> l.getPrice()).reduce(new BigDecimal(0), (a, b) -> a.add(b)));
			r1.setTravelTime(l1.stream().map(l -> l.getTravelTime()).reduce(0l, (a, b) -> a + b) / 1000 / 60 / 60);
			resultList.add(r1);

		}
		return resultList;
	}

}
