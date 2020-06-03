package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<Food, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	private Map<Integer, Food> idMap;
	private List<Arco> archi;
	private Simulator sim;
	
	public Model() {
		this.dao = new FoodDao();
		this.idMap = new HashMap<Integer, Food>();
		this.sim = new Simulator(this);
	}
	
	public void creaGrafo(int porzioniMax) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<Food> foods = dao.listAllFoods(idMap);
		
		// aggiungo i vertici
		Graphs.addAllVertices(grafo, dao.listFoods(porzioniMax, idMap));
		
		// aggiungo gli archi
		this.archi = dao.getArchi(this.getVertici(), idMap);
		for(Arco a: archi)
			Graphs.addEdgeWithVertices(grafo, a.getF1(), a.getF2(), a.getPeso());
	}

	public Set<Food> getVertici() {
		return this.grafo.vertexSet();
	}
	
	public List<FoodAndCalories> elencoConnessi(Food f){
		List<FoodAndCalories> result = new ArrayList<>();
		List<Food> vicini = Graphs.neighborListOf(grafo, f);
		for(Food v: vicini) {
			Double calorie = grafo.getEdgeWeight(grafo.getEdge(f, v));
			FoodAndCalories fac = new FoodAndCalories(v, calorie);
			result.add(fac);
		}
		Collections.sort(result);
		return result;
	}

	public int getNArchi() {
		return grafo.edgeSet().size();
	}
	
	public void simulazione(int k, Food f) {
		sim.init(k, f);
		sim.run();
	}
	
	public int getCibiPreparati() {
		return sim.getCibiPreparati();
	}

	public Double getTempoPreparazione() {
		return sim.getTempoPreparazione();
	}
	
}
