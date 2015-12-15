package com.dapi;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Hugo on 15/12/2015.
 */
public class TranscriptionPatientTherapistWriter {

    private static OWLOntologyManager manager;
    private static OWLOntology ontology;
    private static IRI ontologyIRI;
    private static OWLDataFactory factory;

    private static final String TRANSCRIPTIONS_FOLDER = "transcriptions";

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

        //iterate over transcriptions folders
        try {
            File dir = new File(TRANSCRIPTIONS_FOLDER);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    //read the info.json file
                    File infoFile = new File(child, "/info.json");
                    String contents = Files.toString(infoFile, Charsets.UTF_8);

                    JSONParser parser = new JSONParser();
                    JSONObject infoJSON = (JSONObject)parser.parse(contents);

                    String patientString = (String)infoJSON.get("patient");
                    OWLIndividual patientIndividual = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + patientString));
                    OWLClass patientLevel = factory.getOWLClass(IRI.create(ontologyIRI + "Patient" ));
                    OWLClassAssertionAxiom patientClassAssertion = factory.getOWLClassAssertionAxiom(patientLevel, patientIndividual);
                    manager.addAxiom(ontology, patientClassAssertion);

                    String therapistString = (String)infoJSON.get("therapist");
                    therapistString = therapistString.replaceAll(" ", "_");
                    OWLIndividual therapistIndividual = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + therapistString));
                    OWLClass therapistLevel = factory.getOWLClass(IRI.create(ontologyIRI + "Therapist" ));
                    OWLClassAssertionAxiom therapistClassAssertion = factory.getOWLClassAssertionAxiom(therapistLevel, therapistIndividual);
                    manager.addAxiom(ontology, therapistClassAssertion);

                    JSONArray transcriptionsJSON = (JSONArray)infoJSON.get("sessions");
                    OWLIndividual transcriptionIndividual = null;
                    for(int i = 0; i < transcriptionsJSON.size(); i++) {
                        //get number, date and file
                        JSONObject sessionJSON = (JSONObject) transcriptionsJSON.get(i);

                        String sessionNumberString = (String)sessionJSON.get("number");
                        String sessionDateString = (String)sessionJSON.get("date");
                        String sessionFileString = (String)sessionJSON.get("file");



                        String transcriptionID = patientString + "_" + sessionNumberString;

                        transcriptionIndividual = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + transcriptionID));

                        OWLClass transcriptionLevel = factory.getOWLClass(IRI.create(ontologyIRI + "Transcription" ));
                        OWLClassAssertionAxiom transcriptionClassAssertion = factory.getOWLClassAssertionAxiom(transcriptionLevel, transcriptionIndividual);
                        manager.addAxiom(ontology, transcriptionClassAssertion);

                        OWLLiteral sessionNumberLiteral = factory.getOWLLiteral(sessionNumberString, OWL2Datatype.XSD_INTEGER);
                        OWLDataProperty sessionNumber = factory.getOWLDataProperty(IRI.create(ontologyIRI + "sessionNumber"));
                        OWLDataPropertyAssertionAxiom transcriptionSessionNumberAssertion = factory.getOWLDataPropertyAssertionAxiom(sessionNumber, transcriptionIndividual, sessionNumberLiteral);
                        manager.addAxiom(ontology, transcriptionSessionNumberAssertion);

                        if(sessionDateString != null) {
                            OWLLiteral sessionDateLiteral = factory.getOWLLiteral(sessionDateString, OWL2Datatype.XSD_DATE_TIME);
                            OWLDataProperty sessionDate = factory.getOWLDataProperty(IRI.create(ontologyIRI + "appointmentDate"));
                            OWLDataPropertyAssertionAxiom transcriptionDateAssertion = factory.getOWLDataPropertyAssertionAxiom(sessionDate, transcriptionIndividual, sessionDateLiteral);
                            manager.addAxiom(ontology, transcriptionDateAssertion);
                        }

                        OWLLiteral sessionFileLiteral = factory.getOWLLiteral(sessionFileString, OWL2Datatype.XSD_STRING);
                        OWLDataProperty sessionFile = factory.getOWLDataProperty(IRI.create(ontologyIRI + "transcriptionFile"));
                        OWLDataPropertyAssertionAxiom transcriptionFileAssertion = factory.getOWLDataPropertyAssertionAxiom(sessionFile, transcriptionIndividual, sessionFileLiteral);
                        manager.addAxiom(ontology, transcriptionFileAssertion);

                        //hasTherapist & hasPatient
                        OWLObjectProperty hasTherapist = factory.getOWLObjectProperty(IRI.create(ontologyIRI + "hasTherapist"));
                        OWLObjectPropertyAssertionAxiom hasTherapistAssertion = factory.getOWLObjectPropertyAssertionAxiom(hasTherapist, transcriptionIndividual, therapistIndividual);
                        manager.addAxiom(ontology, hasTherapistAssertion);

                        OWLObjectProperty hasPatient = factory.getOWLObjectProperty(IRI.create(ontologyIRI + "hasPatient"));
                        OWLObjectPropertyAssertionAxiom hasPatientAssertion = factory.getOWLObjectPropertyAssertionAxiom(hasPatient, transcriptionIndividual, patientIndividual);
                        manager.addAxiom(ontology, hasPatientAssertion);
                    }
                }

                manager.saveOntology(ontology);

                System.out.println("***Populate finished***");
                long stopTime = System.currentTimeMillis();
                long elapsedTime = stopTime - startTime;
                System.out.println("Elapsed Time: " + elapsedTime / 1000.0 + " seconds.");

            } else {
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }

    }
}
