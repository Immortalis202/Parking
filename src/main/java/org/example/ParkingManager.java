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

}
