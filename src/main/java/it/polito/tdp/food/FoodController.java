/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.FoodAndCalories;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...\n");
    	int porzioniMax;
    	try {
    		porzioniMax = Integer.parseInt(txtPorzioni.getText());
    	}
    	catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero intero");
    		return;
    	}
    	model.creaGrafo(porzioniMax);
    	setBox(model.getVertici());
    	txtResult.appendText(String.format("Grafo creato con %d vertici e %d archi\n", model.getVertici().size(), model.getNArchi()));
    }
    
    private void setBox(Set<Food> vertici) {
    	List<Food> list = new ArrayList<>(vertici);
    	Collections.sort(list);
    	this.boxFood.getItems().addAll(list);
	}

	@FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Analisi calorie...\n");
    	Food f = boxFood.getValue();
    	if(f == null) {
    		txtResult.appendText("Devi selezionare un cibo");
    		return;
    	}
    	List<FoodAndCalories> result = model.elencoConnessi(f);
    	if(result.size() == 0)
    		txtResult.appendText("Vertice isolato");
    	else {
    		Collections.sort(result);
    		for(int i=0; i<5; i++)
    			txtResult.appendText(String.format("%s %f\n", result.get(i).getF().getDisplay_name(), result.get(i).getCalories()));
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Simulazione...\n");
    	int k;
    	Food f = boxFood.getValue();
    	if(f == null) {
    		txtResult.appendText("Devi selezionare un cibo");
    		return;
    	}
    	try {
    		k = Integer.parseInt(this.txtK.getText());
    	}
    	catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero intero");
    		return;
    	}
    	if(k<1 || k>10) {
    		txtResult.appendText("Devi inserire un numero compreso tra 1 e 10");
    		return;
    	}
    	model.simulazione(k, f);
    	txtResult.appendText(String.format("Cibi preparati: %d. Tempo di preparazione: %f\n", model.getCibiPreparati(), model.getTempoPreparazione()));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
