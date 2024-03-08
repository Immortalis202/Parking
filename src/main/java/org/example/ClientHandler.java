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
	GroceryManager groceryManager;

	public ClientHandler(Socket clientSocket, GroceryManager groceryManager) {
		this.clientSocket = clientSocket;
		this.groceryManager = groceryManager;
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
				Grocery grocery = getFruit(s);
				if(grocery != null) {
					out.println(grocery);
					groceryManager.add(grocery);
					System.out.println("Frutta inserita " + groceryManager.groceries.size());
				}else{
					String qt = getQt(s);
					if(qt != null){
						Grocery foundGrocery = groceryManager.findQt(qt);
						if(foundGrocery != null){
							out.println("Found qt: " + foundGrocery.qt);
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

	private Grocery getFruit(String s){
		Gson gson = new Gson();

		Grocery grocery = null;
		try {
			grocery = gson.fromJson(s, Grocery.class);
			System.out.println(grocery);
			if(grocery.name == null){
				return null;
			}
			return grocery;
		} catch(JsonSyntaxException e) {
			throw new RuntimeException(e);

		}




	}
	private String getQt(String s){
		Gson gson = new Gson();
		try{
			FindCommand findCommand = gson.fromJson(s, FindCommand.class);
			System.out.println(findCommand.fruitToFind);
			Grocery grocery = groceryManager.findQt(findCommand.fruitToFind);
			if(grocery == null){
				return null;
			}
			return grocery.name;
		}catch(JsonSyntaxException e){
			throw new RuntimeException(e);
		}



	}

}