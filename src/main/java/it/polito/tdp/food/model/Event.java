package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		INIZIO, FINE
	}
	
	private EventType type;
	private Double time;
	private Stazione stazione;
	private Food food;
	
	public Event(EventType type, Double time, Stazione stazione, Food food) {
		super();
		this.type = type;
		this.time = time;
		this.stazione = stazione;
		this.food = food;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Stazione getStazione() {
		return stazione;
	}

	public void setStazione(Stazione stazione) {
		this.stazione = stazione;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public int compareTo(Event o) {
		return this.time.compareTo(o.getTime());
	}

}
