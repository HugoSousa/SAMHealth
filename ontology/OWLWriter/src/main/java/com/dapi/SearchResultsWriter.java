package com.dapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class SearchResultsWriter {

	private static OWLOntologyManager manager;
    private static OWLOntology ontology;
    
    @SuppressWarnings("rawtypes")
	public static void main(String[] args) {
        System.out.println("***Starting***");
        System.currentTimeMillis();


            manager = OWLManager.createOWLOntologyManager();

            File ontologyFile = new File("SAMH2.owl");
            try {
                ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);
            } catch (OWLOntologyCreationException e) {
                e.printStackTrace();
            }

            ontology.getOntologyID().getOntologyIRI().get();

            List<String> list = new LinkedList<String>();
            LexicalLevel<String, ArrayList<Object>> lexicalBase  = new LexicalLevel<String, ArrayList<Object>>("Lexical Base", new ArrayList<Object>());
            Reader file;
			try {
				file = new FileReader("lexical.json");
	
	            JSONParser jsonParser = new JSONParser();
	            JSONObject jsonObject = (JSONObject)jsonParser.parse(file);
	           // System.out.println(jsonObject.toJSONString());
	            
	            for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
	                //add level
	                String primaryLevelString = (String) iterator.next();
	                list.add(primaryLevelString);
	                
	                LexicalLevel<String, ArrayList<Object>> primary = new LexicalLevel<String, ArrayList<Object>>(primaryLevelString, new ArrayList<Object>());
	                lexicalBase.addContent(primary);
	                
	                if(primaryLevelString.equals("POSITIVA") || primaryLevelString.equals("NEGATIVA")){
	                    JSONObject globalLevel = (JSONObject)jsonObject.get(primaryLevelString);

	                    for(Iterator globalIterator = globalLevel.keySet().iterator(); globalIterator.hasNext();) {
	                        String globalLevelString = (String) globalIterator.next();
	    	                list.add(globalLevelString);
	    	                
	    	                LexicalLevel<String, ArrayList<Object>> global = new LexicalLevel<String, ArrayList<Object>>(globalLevelString, new ArrayList<Object>());
	    	                primary.addContent(global);
	    	                
	                        JSONObject intermediateLevel = (JSONObject)globalLevel.get(globalLevelString);

	                        for(Iterator intermediateIterator = intermediateLevel.keySet().iterator(); intermediateIterator.hasNext();) {
	                            String intermediateLevelString = (String) intermediateIterator.next();
		    	                list.add(intermediateLevelString);
		    	                
		    	                LexicalLevel<String, ArrayList<Object>> intermediate = new LexicalLevel<String, ArrayList<Object>>(intermediateLevelString, new ArrayList<Object>());
		    	                global.addContent(intermediate);
		    	                
	                            JSONObject specificLevel = (JSONObject)intermediateLevel.get(intermediateLevelString);

	                            for(Iterator specificIterator = specificLevel.keySet().iterator(); specificIterator.hasNext();) {
	                                String specificLevelString = (String) specificIterator.next();
			    	                list.add(specificLevelString);
			    	                
			    	                LexicalLevel<String, ArrayList<Object>> specific = new LexicalLevel<String, ArrayList<Object>>(specificLevelString, new ArrayList<Object>());
			    	                intermediate.addContent(specific);

			    	                addTerms(specific, (JSONArray) specificLevel.get(specificLevelString));
			    	                
	                                
	                            }
	                        }
	                    }
	                }else{
	                    jsonObject.get(primaryLevelString);
    	                addTerms(primary, (JSONArray)jsonObject.get(primaryLevelString));

	                }
	            }
	            
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			processList((LinkedList<String>) list, lexicalBase);
	}
	
	@SuppressWarnings("rawtypes")
	private static void addTerms(LexicalLevel<String, ArrayList<Object>> upper, JSONArray levelTerms) {
        Iterator levelTermsIterator = levelTerms.iterator();
        upper.isEnding = true;
        
        while (levelTermsIterator.hasNext()) {
            String term = (String)levelTermsIterator.next();
            upper.addContent(term);
        }
    }
	
	public static void processList(LinkedList<String> list, LexicalLevel<String, ArrayList<Object>> lexicalBase) {
		System.out.println(list.toString());
		
		for (String name : list) {
			List<Object> query = lexicalBase.search(name);
			System.out.println(name + " " + query.size());
			//TODO
		}
	}
	

	

}
