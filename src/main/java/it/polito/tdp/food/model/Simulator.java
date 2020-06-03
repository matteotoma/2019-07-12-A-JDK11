package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import it.polito.tdp.food.model.Event.EventType;
import it.polito.tdp.food.model.Food.StatoPreparazione;

public class Simulator {
	
	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;
	
	// MODELLO DEL MONDO
	private List<Stazione> stazioni;
	
	// PARAMETRI DELLA SIMULAZIONE
	private Model model;
	
	// OUTPUT DA CALCOLARE
	private Double tempoPreparazione ;
	private int cibiPreparati ;
	
	public Simulator(Model model) {
		this.model = model;
	}
	
	public void init(int k, Food f) {
		this.queue = new PriorityQueue<>();
		this.stazioni = new ArrayList<>();
		
		this.cibiPreparati = 0;
		this.tempoPreparazione = 0.0;
		
		setStazioni(k);
		
		List<FoodAndCalories> list = model.elencoConnessi(f);
		for(int i=0; i<stazioni.size() && i<list.size(); i++) {
			Food daPreparare = list.get(i).getF();
			stazioni.get(i).setLibera(false);
			stazioni.get(i).setFood(daPreparare);
			
			Event e = new Event(EventType.FINE, list.get(i).getCalories(), stazioni.get(i), daPreparare);
			this.queue.add(e);
		}
	}

	private void setStazioni(int k) {
		for(int i=0; i<k; i++) {
			Stazione s = new Stazione(true, null);
			this.stazioni.add(s);
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch(e.getType()) {
		case INIZIO:
			List<FoodAndCalories> vicini = model.elencoConnessi(e.getFood());
			for(FoodAndCalories v: vicini) {
				if(v.getF().getPreparazione() == StatoPreparazione.DA_PREPARARE) {
					v.getF().setPreparazione(StatoPreparazione.IN_CORSO);
					e.getStazione().setFood(v.getF());
					e.getStazione().setLibera(false);
					
					Event nuovo = new Event(EventType.FINE, e.getTime()+v.getCalories(), e.getStazione(), v.getF());
					this.queue.add(nuovo);
					break;
				}
			}
			break;
		case FINE:
			this.cibiPreparati++;
			this.tempoPreparazione = e.getTime();
			
			e.getStazione().setLibera(true);
			e.getStazione().getFood().setPreparazione(StatoPreparazione.PREPARATO);
			
			Event ev = new Event(EventType.INIZIO, e.getTime(), e.getStazione(), e.getFood());
			queue.add(ev);
			break;
		}
	}
	
	public int getCibiPreparati() {
		return this.cibiPreparati;
	}

	public Double getTempoPreparazione() {
		return tempoPreparazione;
	}

}
