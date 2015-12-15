package com.dapi;

import java.io.*;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class LevelsWriter {

    private static OWLOntologyManager manager;
    private static OWLOntology ontology;
    private static IRI ontologyIRI;

    private static final String PRIMARY_LEVEL = "PrimaryLevel";
    private static final String GLOBAL_LEVEL = "GlobalLevel";
    private static final String INTERMEDIATE_LEVEL = "IntermediateLevel";
    private static final String SPECIFIC_LEVEL = "SpecificLevel";


    public static void main(String[] args) {

        System.out.println("***Starting***");
        long startTime = System.currentTimeMillis();

        try {
            manager = OWLManager.createOWLOntologyManager();

            File ontologyFile = new File("SAMH2.owl");
            try {
                ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);
            } catch (OWLOntologyCreationException e) {
                e.printStackTrace();
            }

            ontologyIRI = ontology.getOntologyID().getOntologyIRI().get();

            Reader file = new FileReader("lexical.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(file);
            //System.out.println(jsonObject);

            for(Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
                //add level
                String primaryLevelString = (String) iterator.next();
                addLevel(primaryLevelString, PRIMARY_LEVEL, null);
                if(primaryLevelString.equals("POSITIVA") || primaryLevelString.equals("NEGATIVA")){
                    JSONObject globalLevel = (JSONObject)jsonObject.get(primaryLevelString);

                    for(Iterator globalIterator = globalLevel.keySet().iterator(); globalIterator.hasNext();) {
                        String globalLevelString = (String) globalIterator.next();
                        addLevel(globalLevelString, GLOBAL_LEVEL, primaryLevelString);
                        JSONObject intermediateLevel = (JSONObject)globalLevel.get(globalLevelString);

                        for(Iterator intermediateIterator = intermediateLevel.keySet().iterator(); intermediateIterator.hasNext();) {
                            String intermediateLevelString = (String) intermediateIterator.next();
                            addLevel(intermediateLevelString, INTERMEDIATE_LEVEL, globalLevelString);
                            JSONObject specificLevel = (JSONObject)intermediateLevel.get(intermediateLevelString);

                            for(Iterator specificIterator = specificLevel.keySet().iterator(); specificIterator.hasNext();) {
                                String specificLevelString = (String) specificIterator.next();
                                addLevel(specificLevelString, SPECIFIC_LEVEL, intermediateLevelString);

                                JSONArray specificLevelTerms = (JSONArray)specificLevel.get(specificLevelString);
                                addTerms(specificLevelString, specificLevelTerms);
                            }
                        }
                    }
                }else{
                    JSONArray exceptionalPrimaryLevelTerms = (JSONArray)jsonObject.get(primaryLevelString);
                    addTerms(primaryLevelString, exceptionalPrimaryLevelTerms);

                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }


        try {
            File file = new File("result.owl");
            manager.saveOntology(ontology, IRI.create(file.toURI()));

            System.out.println("***Populate finished***");
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Elapsed Time: " + elapsedTime / 1000.0 + " seconds.");

        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    private static void addTerms(String levelString, JSONArray levelTerms) throws OWLOntologyStorageException {
        Iterator levelTermsIterator = levelTerms.iterator();

        while (levelTermsIterator.hasNext()) {
            String term = (String)levelTermsIterator.next();
            addTerm(term, levelString);
        }
    }

    public static void addLevel(String levelIndividual, String emotionLevel, String parentLevelIndividual) throws OWLOntologyCreationException, OWLOntologyStorageException {

        OWLDataFactory factory = manager.getOWLDataFactory();

        levelIndividual = levelIndividual.replaceAll(" ", "_");
        if(parentLevelIndividual != null)
            parentLevelIndividual = parentLevelIndividual.replaceAll(" ", "_");

        OWLIndividual individual = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + levelIndividual));

        OWLClass level = factory.getOWLClass(IRI.create(ontologyIRI + emotionLevel ));

        OWLClassAssertionAxiom levelClassAssertion = factory.getOWLClassAssertionAxiom(level, individual);
        manager.addAxiom(ontology, levelClassAssertion);

        String upperProperty = null;
        if(emotionLevel.equals(GLOBAL_LEVEL)){
            upperProperty = "isUpperLevelGlobal";
        }else if(emotionLevel.equals(INTERMEDIATE_LEVEL)){
            upperProperty = "isUpperLevelIntermediate";
        }else if(emotionLevel.equals(SPECIFIC_LEVEL)){
            upperProperty = "isUpperLevelSpecific";
        }

        if(upperProperty != null) {
            OWLObjectProperty isUpperLevel = factory.getOWLObjectProperty(IRI.create(ontologyIRI + upperProperty));
            OWLIndividual parentIndividual = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + parentLevelIndividual));
            OWLObjectPropertyAssertionAxiom isUpperLevelAssertion = factory.getOWLObjectPropertyAssertionAxiom(isUpperLevel, parentIndividual, individual);
            manager.addAxiom(ontology, isUpperLevelAssertion);
        }

        manager.saveOntology(ontology);
    }

    public static void addTerm(String term, String level) throws OWLOntologyStorageException {
        OWLDataFactory factory = manager.getOWLDataFactory();

        term = term.replaceAll(" ", "_");
        level = level.replaceAll(" ", "_");

        OWLIndividual individual = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + "term/" + term));
        OWLClass termClass = factory.getOWLClass(IRI.create(ontologyIRI + "Term" ));

        OWLClassAssertionAxiom termClassAssertion = factory.getOWLClassAssertionAxiom(termClass, individual);
        manager.addAxiom(ontology, termClassAssertion);

        OWLObjectProperty hasTerm = factory.getOWLObjectProperty(IRI.create(ontologyIRI + "hasTerm"));
        OWLIndividual parentLevel = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + level));
        OWLObjectPropertyAssertionAxiom isUpperLevelAssertion = factory.getOWLObjectPropertyAssertionAxiom(hasTerm, parentLevel, individual);
        manager.addAxiom(ontology, isUpperLevelAssertion);

        manager.saveOntology(ontology);

    }

    /*
    public static void createNewOnto() throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        IRI ontologyIRI = IRI.create("http://example.com/owlapi/families");
        OWLOntology ont = manager.createOntology(ontologyIRI);
        OWLDataFactory factory = manager.getOWLDataFactory();

        OWLIndividual john = factory.getOWLNamedIndividual(IRI
                .create(ontologyIRI + "#John"));
        OWLIndividual mary = factory.getOWLNamedIndividual(IRI
                .create(ontologyIRI + "#Mary"));
        OWLIndividual susan = factory.getOWLNamedIndividual(IRI
                .create(ontologyIRI + "#Susan"));
        OWLIndividual bill = factory.getOWLNamedIndividual(IRI
                .create(ontologyIRI + "#Bill"));

        OWLObjectProperty hasWife = factory.getOWLObjectProperty(IRI
                .create(ontologyIRI + "#hasWife"));

        OWLObjectPropertyAssertionAxiom axiom1 = factory
                .getOWLObjectPropertyAssertionAxiom(hasWife, john, mary);

        AddAxiom addAxiom1 = new AddAxiom(ont, axiom1);
        // Now we apply the change using the manager.
        manager.applyChange(addAxiom1);

        System.out.println("RDF/XML: ");
        manager.saveOntology(ont, new StreamDocumentTarget(System.out));
    }
    */
}
