package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
	
	private List<String> dizionario;
	private String language;
	
	public Dictionary() {
		
	}
	
	public void loadDictionary(String language) {
		dizionario = new ArrayList<String>();
		this.language = language;
		
		try {
			FileReader fr = new FileReader("src/main/resources/"+language+".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			
			while((word = br.readLine()) != null) {
				dizionario.add(word.toLowerCase());
			}
			
			Collections.sort(dizionario);
			br.close();
			System.out.println("Dizionario " + language + " loaded. Found " + dizionario.size() + " words.");
			
		} catch(IOException e) {
			System.out.println("Errore nella lettura del file");
		}
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		List<RichWord> words = new ArrayList<>();
		RichWord parola;
		for(String s : inputTextList) {
			if(dizionario.contains(s))
				parola = new RichWord(s, true);
			else
				parola = new RichWord(s, false);
			words.add(parola);
		}
		return words;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		List<RichWord> words = new ArrayList<>();
		RichWord parola;
		for(String s : inputTextList) {
			for(String word : dizionario) {
				if(word.equalsIgnoreCase(s)) {
					parola = new RichWord(s, true);
					break;
				}
				else
					parola = new RichWord(s, false);
				words.add(parola);
			}
		}
		return words;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		List<RichWord> words = new ArrayList<>();
		RichWord parola;
		
		
		for(String s: inputTextList) {
			int start = 0, end = dizionario.size();
			boolean found = false;
			//vedo a quale metà appartiene -> salvo gli estremi -> ripeto con estremi nuovi ed esco fino a quando start = end
			while(end != start && !found) {
				int half = start + (end - start)/2;
				if(s.compareTo(dizionario.get(half)) == 0) {
					found = true;
				} else if(s.compareTo(dizionario.get(half)) < 0) {
					end = half;
				} else
					start = half + 1;
			}
			if(found) {
				parola = new RichWord(s, true);
			} else {
				parola = new RichWord(s, false);
			}
			words.add(parola);
		}
		
		return words;
	}
}
