package it.polito.tdp.food.model;

public class FoodAndCalories implements Comparable<FoodAndCalories>{

	private Food f;
	private double calories;
	
	public FoodAndCalories(Food f, double calories) {
		super();
		this.f = f;
		this.calories = calories;
	}

	public Food getF() {
		return f;
	}

	public void setF(Food f) {
		this.f = f;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public int compareTo(FoodAndCalories o) {
		return - (int)(this.calories - o.getCalories());
	}	
	
}
