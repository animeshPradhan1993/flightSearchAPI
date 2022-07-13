package com.animesh.flights.flightsearch;

import java.util.Comparator;

import com.animesh.flights.flightsearch.model.domain.ResultDTO;

public class FlightComparators {
	public static Comparator<ResultDTO> sortByPriceAsc = (r1, r2) -> {
		return r1.getTotalPrice().compareTo(r2.getTotalPrice());
	};
	public static Comparator<ResultDTO> sortByPriceDesc = (r1, r2) -> {
		return r2.getTotalPrice().compareTo(r1.getTotalPrice());
	};
	public static Comparator<ResultDTO> sortBytravelTimeDesc = (r1, r2) -> {
		return r2.getTravelTime().compareTo(r1.getTravelTime());
	};
	public static Comparator<ResultDTO> sortBytravelTimeAsc = (r1, r2) -> {
		return r1.getTravelTime().compareTo(r2.getTravelTime());
	};
	public static Comparator<ResultDTO> sortByArrivalTimeAsc = (r1, r2) -> {
		return r1.getFlightList().get(r1.getFlightList().size() - 1).getArrivalTime()
				.compareTo(r2.getFlightList().get(r2.getFlightList().size() - 1).getArrivalTime());
	};
	public static Comparator<ResultDTO> sortByArrivalTimeDesc = (r2, r1) -> {
		return r2.getFlightList().get(r2.getFlightList().size() - 1).getArrivalTime()
				.compareTo(r1.getFlightList().get(r1.getFlightList().size() - 1).getArrivalTime());
	};
	public static Comparator<ResultDTO> sortByDepartureTimeAsc = (r1, r2) -> {
		return r1.getFlightList().get(0).getDepartureTime().compareTo(r2.getFlightList().get(0).getDepartureTime());
	};
	public static Comparator<ResultDTO> sortByDepartureTimeDesc = (r2, r1) -> {
		return r2.getFlightList().get(0).getDepartureTime().compareTo(r1.getFlightList().get(0).getDepartureTime());
	};
}
