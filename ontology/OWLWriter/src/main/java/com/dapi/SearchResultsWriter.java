package com.dapi;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

public class SearchResultsWriter {

	private static OWLOntologyManager manager;
    private static OWLOntology ontology;
	private static IRI ontologyIRI;
	private static OWLDataFactory factory;
    
    @SuppressWarnings("rawtypes")
	public static void main(String[] args) {
        System.out.println("***Starting***");
        long startTime = System.currentTimeMillis();

		manager = OWLManager.createOWLOntologyManager();

		File ontologyFile = new File("SAMH2.owl");
		try {
			ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		ontologyIRI = ontology.getOntologyID().getOntologyIRI().get();
		factory = manager.getOWLDataFactory();

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

		try {
			manager.saveOntology(ontology);
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}

		System.out.println("***Populate finished***");
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Elapsed Time: " + elapsedTime / 1000.0 + " seconds.");
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
		
		for (String levelName : list) {

			List<Object> query = lexicalBase.search(levelName);

			levelName = levelName.replaceAll(" ", "_");

			String terms = "";
			for(Object term: query){
				terms += term.toString() + " ";
			}

			terms = terms.substring(0, terms.length()-1);
			try {
				//TODO
				HttpClient httpclient = HttpClients.createDefault();
				HttpPost httppost = new HttpPost("http://localhost:8983/solr/samh/select");

				// Request parameters and other properties.
				List<NameValuePair> params = new ArrayList<NameValuePair>(4);
				params.add(new BasicNameValuePair("q", "text:" + terms));
				params.add(new BasicNameValuePair("fl", "id,score"));
				params.add(new BasicNameValuePair("wt", "json"));
				params.add(new BasicNameValuePair("rows", "999"));

				httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

				//Execute and get the response.
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();

				if (entity != null) {
					InputStream instream = entity.getContent();
					try {
						String content = IOUtils.toString(instream);

						JSONParser parser = new JSONParser();
						JSONObject jsonObject = (JSONObject)parser.parse(content);

						JSONObject responseObject = (JSONObject)jsonObject.get("response");
						JSONArray docsArray = (JSONArray)responseObject.get("docs");

						for(int i = 0; i < docsArray.size(); i++){

							JSONObject docJSON = (JSONObject)docsArray.get(i);
							String docIdString = (String)docJSON.get("id");
							Double scoreDouble = (Double)docJSON.get("score");

							OWLIndividual transcriptionIndividual = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + docIdString));
							OWLIndividual emotionLevelIndividual = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + levelName));

							OWLIndividual searchResultIndividual = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + docIdString + "_" + levelName));
							OWLClass searchResultClass = factory.getOWLClass(IRI.create(ontologyIRI + "SearchResult" ));
							OWLClassAssertionAxiom searchResultClassAssertion = factory.getOWLClassAssertionAxiom(searchResultClass, searchResultIndividual);
							manager.addAxiom(ontology, searchResultClassAssertion);

							OWLObjectProperty hasSearchResult = factory.getOWLObjectProperty(IRI.create(ontologyIRI + "hasSearchResult"));
							OWLObjectPropertyAssertionAxiom hasSearchResultAssertion = factory.getOWLObjectPropertyAssertionAxiom(hasSearchResult, transcriptionIndividual, searchResultIndividual);
							manager.addAxiom(ontology, hasSearchResultAssertion);

							OWLObjectProperty relatesToLevel = factory.getOWLObjectProperty(IRI.create(ontologyIRI + "relatesToLevel"));
							OWLObjectPropertyAssertionAxiom relatesToLevelAssertion = factory.getOWLObjectPropertyAssertionAxiom(relatesToLevel, searchResultIndividual, emotionLevelIndividual);
							manager.addAxiom(ontology, relatesToLevelAssertion);

							OWLLiteral scoreLiteral = factory.getOWLLiteral(scoreDouble);
							OWLDataProperty score = factory.getOWLDataProperty(IRI.create(ontologyIRI + "score"));
							OWLDataPropertyAssertionAxiom scoreAssertion = factory.getOWLDataPropertyAssertionAxiom(score, searchResultIndividual, scoreLiteral);
							manager.addAxiom(ontology, scoreAssertion);
						}



					} catch (ParseException e) {
						e.printStackTrace();
					} finally {
						instream.close();
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	

}
