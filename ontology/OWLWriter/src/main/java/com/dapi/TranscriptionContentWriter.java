package com.dapi;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hugo on 15/12/2015.
 * This class populates the ontology with the transcriptions content. The Solr server should be running at http://localhost:8983/solr/samh/ .
 *
 */
public class TranscriptionContentWriter {

    private static OWLOntologyManager manager;
    private static OWLOntology ontology;
    private static IRI ontologyIRI;
    private static OWLDataFactory factory;

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
        try {

            URL url = new URL("http://localhost:8983/solr/samh/select?q=*:*&fl=id,content&wt=json&rows=999");
            InputStream is = url.openStream();

            String content = IOUtils.toString(is);

            JSONParser parser = new JSONParser();
            JSONObject contentJSON = (JSONObject)parser.parse(content);

            JSONObject responseObject = (JSONObject)contentJSON.get("response");
            JSONArray docsArray = (JSONArray)responseObject.get("docs");

            for(int i = 0; i < docsArray.size(); i++){
                JSONObject docJSON = (JSONObject)docsArray.get(i);

                String docIdString = (String)docJSON.get("id");
                String contentString = (String)docJSON.get("content");

                OWLIndividual transcriptionIndividual = factory.getOWLNamedIndividual(IRI.create(ontologyIRI + docIdString));
                OWLLiteral contentLiteral = factory.getOWLLiteral(contentString, OWL2Datatype.XSD_STRING);
                OWLDataProperty transcriptionContent = factory.getOWLDataProperty(IRI.create(ontologyIRI + "transcriptionContent"));
                OWLDataPropertyAssertionAxiom transcriptionContentAssertion = factory.getOWLDataPropertyAssertionAxiom(transcriptionContent, transcriptionIndividual, contentLiteral);
                manager.addAxiom(ontology, transcriptionContentAssertion);
            }

            manager.saveOntology(ontology);
            is.close();

            System.out.println("***Populate finished***");
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println("Elapsed Time: " + elapsedTime / 1000.0 + " seconds.");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }
}
