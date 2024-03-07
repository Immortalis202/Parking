package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler {
	Socket clientSocket = null;
	ParkingManager parkingManager;

	public ClientHandler(Socket clientSocket, ParkingManager parkingManager) {
		this.clientSocket = clientSocket;
		this.parkingManager = parkingManager;
	}

	void handle(){
		BufferedReader in;
		in = getBufferedReader();
		PrintWriter out = null;
		out = getPrintWriter(out);
		readerLoop(in, out);

	}
	private BufferedReader getBufferedReader() {
		BufferedReader in;
		try {
			in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return in;
	}
	private PrintWriter getPrintWriter(PrintWriter out) {
		try {
			out = new PrintWriter(clientSocket.getOutputStream(),
					true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	private void readerLoop(BufferedReader in, PrintWriter out) {
		String s = "";
		try {
			while ((s = in.readLine()) != null) {
				//System.out.println(s);
				Ticket ticket = getTicket(s);
				if(ticket != null) {
					out.println(ticket);
					parkingManager.add(ticket);
					System.out.println("Posti occupati " + parkingManager.tickets.size());
				}else{
					String plate = getPlate(s);
					if(plate != null){
						Ticket foundTicket = parkingManager.findPlate(plate);
						if(foundTicket != null){
							out.println("Found at slot: " + foundTicket.slot);
						}else{
							out.println("Not found");
						}
					}else {
						out.println("Not found");
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Ticket getTicket(String s){
		Gson gson = new Gson();

		Ticket ticket = null;
		try {
			ticket = gson.fromJson(s, Ticket.class);
			System.out.println(ticket);
			if(ticket.plate == null){
				return null;
			}
			return ticket;
		} catch(JsonSyntaxException e) {
			throw new RuntimeException(e);

		}




	}
	private String getPlate(String s){
		Gson gson = new Gson();
		try{
			FindCommand findCommand = gson.fromJson(s, FindCommand.class);
			Ticket ticket = parkingManager.findPlate(findCommand.plateToFind);
			if(ticket == null){
				return null;
			}
			return ticket.plate;
		}catch(JsonSyntaxException e){
			throw new RuntimeException(e);
		}



	}

}