package com.animesh.flights.flightsearch.model.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.animesh.flights.flightsearch.utils.TimeDeserializer;
import com.animesh.flights.flightsearch.utils.TimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class FlightDTO {
	
	private String flightNumber;

	
	private String origin;

	
	private String destination;
	@JsonDeserialize(using = TimeDeserializer.class)
	@JsonSerialize(using = TimeSerializer.class)
	private Date departureTime;
	@JsonDeserialize(using = TimeDeserializer.class)
	@JsonSerialize(using = TimeSerializer.class)

	private Date arrivalTime;

	
	private BigDecimal price;

	@JsonIgnore
	private Long travelTime;

	public Long getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Long travelTime) {
		this.travelTime = travelTime;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "FlightDTO [flightNumber=" + flightNumber + ", origin=" + origin + ", destination=" + destination
				+ ", departureTime=" + departureTime + ", arrivalTime=" + arrivalTime + ", price=" + price
				+ ", travelTime=" + travelTime + "]";
	}
}
