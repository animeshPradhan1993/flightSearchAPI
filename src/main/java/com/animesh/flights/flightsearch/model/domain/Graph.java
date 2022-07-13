package com.animesh.flights.flightsearch.model.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph<T> {
	private Map<String, Set<String>> flightRoutes = new HashMap<>();
	private Map<String, Map<String, List<FlightDTO>>> flightMap = new HashMap<>();

	public void addConnections(String origin, FlightDTO flight) {

		if (flightMap.containsKey(origin)) {
			if (flightMap.get(origin).containsKey(flight.getDestination())) {
				flightMap.get(origin).get(flight.getDestination()).add(flight);

			} else {
				List<FlightDTO> flightDTOlist = new ArrayList<>();

				flightDTOlist.add(flight);
				flightMap.get(origin).put(flight.getDestination(), flightDTOlist);
			}
			flightRoutes.get(origin).add(flight.getDestination());

		} else {
			List<FlightDTO> flightDTOlist = new ArrayList<>();
			Set<String> destinationSet = new HashSet<>();
			destinationSet.add(flight.getDestination());
			Map<String, List<FlightDTO>> map = new HashMap<>();
			flightDTOlist.add(flight);
			map.put(flight.getDestination(), flightDTOlist);
			flightMap.put(origin, map);

			flightRoutes.put(origin, destinationSet);

		}

	}

	public Map<String, Map<String, List<FlightDTO>>> getFlightMap() {
		return flightMap;
	}

	public void setFlightMap(Map<String, Map<String, List<FlightDTO>>> flightMap) {
		this.flightMap = flightMap;
	}

	public Map<String, Set<String>> getFlightRoutes() {
		return flightRoutes;
	}

	public void setFlightRoutes(Map<String, Set<String>> flightRoutes) {
		this.flightRoutes = flightRoutes;
	}

	public List<List<String>> getConnections(String origin, String destination) {
		Map<String, Boolean> visitedMap = new HashMap<>();
		List<String> pathList = new ArrayList<>();
		pathList.add(origin);
		List<List<String>> paths = new ArrayList<>();
		findAllConnections(origin, destination, visitedMap, pathList, paths);

		Collections.sort(paths, (l1, l2) -> {
			return Integer.valueOf(l1.size()).compareTo(Integer.valueOf(l2.size()));
		});
		return paths;

	}

	public void findAllConnections(String origin, String destination, Map<String, Boolean> visitedMap,
			List<String> pathList, List<List<String>> paths) {
		if (origin.equals(destination)) {
			List<String> list = new ArrayList<>(pathList);
			paths.add(list);
		}

		visitedMap.put(origin, true);
		if (flightRoutes.containsKey(origin)) {
			for (String s : flightRoutes.get(origin)) {
				if (visitedMap.get(s) == null || !visitedMap.get(s)) {
					pathList.add(s);
					findAllConnections(s, destination, visitedMap, pathList, paths);
					pathList.remove(s);

				}
			}
			visitedMap.put(origin, false);

		}

	}
}
