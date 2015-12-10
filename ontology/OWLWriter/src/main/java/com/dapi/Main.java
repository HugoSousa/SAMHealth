package com.dapi;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        /*
        try {
            createNewOnto();
        }catch(Exception e){
            System.out.println(e);
        }
        */
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File ontologyFile = new File("SAMH.owl");
        try {
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);// manager.loadOntologyFromPhysicalURI(URI.create("file://C:/Onto.owl";));
            IRI ontologyIRI = ontology.getOntologyID().getOntologyIRI().get();
            System.out.println("ID: " + ontologyIRI);


            OWLDataFactory factory = manager.getOWLDataFactory();
            /*ABARROTADO*/
            OWLIndividual abarrotado = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + "term/abarrotado"));

            OWLClass term = factory.getOWLClass(IRI.create(ontologyIRI + "Term"));

            //abarrotado is a term
            OWLClassAssertionAxiom termClassAssertion = factory.getOWLClassAssertionAxiom(term, abarrotado);


            /*FADIGA*/
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
