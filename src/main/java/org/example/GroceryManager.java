package org.example;

import java.util.ArrayList;

public class GroceryManager {
	ArrayList<Grocery> groceries;

	void add(Grocery grocery){
		if(groceries == null){
			groceries = new ArrayList<Grocery>();
		}
		this.groceries.add(grocery);
	}
	Grocery findQt(String fruit
	){
		if(groceries == null){
			return null;
		}
		 for(Grocery grocery : groceries){
			if (grocery.name.equalsIgnoreCase(fruit)){
				return grocery;
			}
		 }
		 return null;
	}
}
