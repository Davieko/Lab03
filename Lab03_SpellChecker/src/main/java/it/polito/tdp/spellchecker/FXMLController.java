/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Dictionary dizionario;
	

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxLingua"
	private ComboBox<String> boxLingua; // Value injected by FXMLLoader

	@FXML // fx:id="txtDaCorreggere"
	private TextArea txtDaCorreggere; // Value injected by FXMLLoader

	@FXML // fx:id="spellCheckButton"
	private Button spellCheckButton; // Value injected by FXMLLoader

	@FXML // fx:id="txtCorretto"
	private TextArea txtCorretto; // Value injected by FXMLLoader

	@FXML // fx:id="lblErrori"
	private Label lblErrori; // Value injected by FXMLLoader

	@FXML // fx:id="clearTextButton"
	private Button clearTextButton; // Value injected by FXMLLoader

	@FXML // fx:id="lblStato"
	private Label lblStato; // Value injected by FXMLLoader

	@FXML
	void doActivation(ActionEvent event) {
		
		if (boxLingua.getValue() !=null) {
			dizionario.loadDictionary(boxLingua.getValue());
    		txtDaCorreggere.setDisable(false);
			txtCorretto.setDisable(false);
			spellCheckButton.setDisable(false);
			clearTextButton.setDisable(false);
			txtDaCorreggere.clear();
			txtCorretto.clear();
    		
    	}else {
    		
			txtDaCorreggere.setDisable(true);
			txtCorretto.setDisable(true);
			spellCheckButton.setDisable(true);
			clearTextButton.setDisable(true);
			txtDaCorreggere.setText("Seleziona una lingua!");
    		
    	}
		
	}

	@FXML
	void doClearText(ActionEvent event) {
		txtCorretto.clear();
		txtDaCorreggere.clear();
		lblErrori.setText("Number of Errors:");
		lblStato.setText("Spell Check Status:");
	}

	@FXML
	void doSpellCheck(ActionEvent event) {
		
		StringBuilder sb = new StringBuilder();
		Integer count = 0;
		String testo = txtDaCorreggere.getText();
		String[] parole = testo.split(" ");
		
		List<String> inputTextList = new ArrayList<String>();
		for (String s : parole) {
			//s.replaceAll("“[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
			s = s.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]]", "");
			if(!s.equals(""))
				inputTextList.add(s);
		}
		long tic = System.nanoTime();
		//List<RichWord> paroleSupervisionate = dizionario.spellCheckText(inputTextList);
		//List<RichWord> paroleSupervisionate = dizionario.spellCheckTextLinear(inputTextList);
		List<RichWord> paroleSupervisionate = dizionario.spellCheckTextDichotomic(inputTextList);
		long toc = System.nanoTime();
		Long d = toc - tic;
		
		for(RichWord s : paroleSupervisionate) {
			if(!s.isCorrect()) {
				sb.append(s.getWord()+ "\n");
				count++;
			}
		}
		
		lblErrori.setText("Gli errori sono: " + count);
		txtCorretto.setText(sb.toString());
		lblStato.setText(lblStato.getText() + " " +(toc - tic) / 1E9 +" seconds");
		
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxLingua != null : "fx:id=\"boxLingua\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtDaCorreggere != null
				: "fx:id=\"txtDaCorreggere\" was not injected: check your FXML file 'Scene.fxml'.";
		assert spellCheckButton != null
				: "fx:id=\"spellCheckButton\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtCorretto != null : "fx:id=\"txtCorretto\" was not injected: check your FXML file 'Scene.fxml'.";
		assert lblErrori != null : "fx:id=\"lblErrori\" was not injected: check your FXML file 'Scene.fxml'.";
		assert clearTextButton != null
				: "fx:id=\"clearTextButton\" was not injected: check your FXML file 'Scene.fxml'.";
		assert lblStato != null : "fx:id=\"lblStato\" was not injected: check your FXML file 'Scene.fxml'.";
		

	}
	public void setModel(Dictionary model) {
		txtDaCorreggere.setDisable(true);
    	txtDaCorreggere.setText("Selezionare una lingua");
    	
    	txtCorretto.setDisable(true);
    	boxLingua.getItems().addAll("English","Italian");
    	
    	spellCheckButton.setDisable(true);
    	clearTextButton.setDisable(true);
 	
    	
    	this.dizionario = model;
	}
}
