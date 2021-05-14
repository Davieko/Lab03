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
				if(word.equals(s))
					parola = new RichWord(s, true);
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
		int start, end;
		String half = dizionario.get(dizionario.size()/2);
		for(String s: inputTextList) {
			if(s.compareTo(half) < 0) {
				start = 0;
				end = dizionario.size()/2;
			} else {
				start = dizionario.size()/2;
				end = dizionario.size();
			}
			for(int i = start; i < end; i++) {
				if(s.equals(dizionario.get(i)))
					parola = new RichWord(s, true);
				else
					parola = new RichWord(s, false);
				words.add(parola);
			}
		}
		
		return words;
	}
}
