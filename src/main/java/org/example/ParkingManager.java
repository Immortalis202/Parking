package org.example;

import java.util.ArrayList;

public class ParkingManager {
	ArrayList<Ticket> tickets;

	void add(Ticket ticket){
		if(tickets == null){
			tickets = new ArrayList<Ticket>();
		}
		this.tickets.add(ticket);
	}
	Ticket findPlate(String plate){
		if(tickets == null){
			return null;
		}
		 for(Ticket ticket : tickets){
			if (ticket.plate.equals(plate)){
				return ticket;
			}
		 }
		 return null;
	}
}
