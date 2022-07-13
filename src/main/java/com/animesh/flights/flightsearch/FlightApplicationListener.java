package com.animesh.flights.flightsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.animesh.flights.flightsearch.exception.BadRequestException;
import com.animesh.flights.flightsearch.model.domain.FlightDTO;
import com.animesh.flights.flightsearch.model.domain.Graph;

@Component
@Order(0)
public class FlightApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	private Graph<FlightDTO> flightGraph;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		try {
			this.createInitialFLightEntries();
		} catch (BadRequestException e) {

		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}

	}

	public void createInitialFLightEntries() throws FileNotFoundException, IOException, BadRequestException {
		List<String> flightsEntries = this.readFileLines("src/main/resources/dataFile/Flight-API.txt");
		for (int i = 1; i < flightsEntries.size(); i++) {
			String[] dataArray = flightsEntries.get(i).split("\\|");
			FlightDTO flight = createFlightData(dataArray[0].trim(), dataArray[1].trim(), dataArray[2].trim(),
					dataArray[3].trim(), dataArray[4].trim(), dataArray[5].trim().split(" ")[0]);
			flightGraph.addConnections(flight.getOrigin(), flight);
		}

	}

	public ArrayList<String> readFileLines(String filepath) throws FileNotFoundException, IOException {
		File fp = new File(filepath);
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);

		ArrayList<String> lines = new ArrayList<>();
		String line;
		while ((line = br.readLine()) != null) {
			if (line.matches(Constant.PATTERN)) {
				lines.add(line);
			}
		}

		fr.close();

		return lines;
	}

	public FlightDTO createFlightData(String flightNumber, String origin, String destination, String departureTime,
			String arrivalTime, String price) {
		FlightDTO f1 = new FlightDTO();
		f1.setArrivalTime(createDateObject(arrivalTime));
		f1.setDepartureTime(createDateObject(departureTime));
		f1.setDestination(destination);
		f1.setFlightNumber(flightNumber);
		f1.setOrigin(origin);
		f1.setPrice(new BigDecimal(price));
		f1.setTravelTime(f1.getArrivalTime().getTime() - f1.getDepartureTime().getTime());
		return f1;
	}

	public Date createDateObject(String date) {
		SimpleDateFormat format = new SimpleDateFormat(Constant.TIMEFORMAT);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("Error parsing Date" + date);
		}
	}

}
