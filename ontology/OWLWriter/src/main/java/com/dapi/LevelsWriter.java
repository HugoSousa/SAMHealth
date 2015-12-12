package com.dapi;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.*;

import java.io.*;
import java.util.Iterator;

public class LevelsWriter {

    private static OWLOntologyManager manager;
    private static OWLOntology ontology;
    private static IRI ontologyIRI;

    private static final String PRIMARY_LEVEL = "PrimaryLevel";
    private static final String GLOBAL_LEVEL = "GlobalLevel";
    private static final String INTERMEDIATE_LEVEL = "IntermediateLevel";
    private static final String SPECIFIC_LEVEL = "SpecificLevel";


    public static void main(String[] args) {
        try {
            manager = OWLManager.createOWLOntologyManager();

            File ontologyFile = new File("SAMH.owl");
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
                            }
                        }
                    }
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
            //manager.saveOntology(ontology, new StreamDocumentTarget(System.out));
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }

        /*
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File ontologyFile = new File("SAMH.owl");
        try {
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);// manager.loadOntologyFromPhysicalURI(URI.create("file://C:/Onto.owl";));
            IRI ontologyIRI = ontology.getOntologyID().getOntologyIRI().get();
            System.out.println("ID: " + ontologyIRI);


            OWLDataFactory factory = manager.getOWLDataFactory();
            //ABARROTADO
            OWLIndividual abarrotado = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + "term/abarrotado"));

            OWLClass term = factory.getOWLClass(IRI.create(ontologyIRI + "Term"));

            //abarrotado is a term
            OWLClassAssertionAxiom termClassAssertion = factory.getOWLClassAssertionAxiom(term, abarrotado);


            //FADIGA
            OWLIndividual fadiga = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + "fadiga"));
            OWLClass specificLevel = factory.getOWLClass(IRI.create(ontologyIRI + "SpecificLevel"));

            OWLClassAssertionAxiom specificLevelClassAssertion = factory.getOWLClassAssertionAxiom(specificLevel, fadiga);

            OWLObjectProperty hasTerm = factory.getOWLObjectProperty(IRI.create(ontologyIRI + "hasTerm"));
            OWLObjectPropertyAssertionAxiom hasTerm1 = factory.getOWLObjectPropertyAssertionAxiom(hasTerm, fadiga, abarrotado);

            //add all relations
            manager.addAxiom(ontology, termClassAssertion);
            manager.addAxiom(ontology, specificLevelClassAssertion);
            manager.addAxiom(ontology, hasTerm1);
            System.out.println("RDF/XML: ");
            manager.saveOntology(ontology, new StreamDocumentTarget(System.out));
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
        */

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
}
