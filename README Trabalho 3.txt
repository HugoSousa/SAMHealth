O povoamento da ontologia (base lexical, terapeutas, pacientes e transcrições) é realizado com a execução de módulos em JAVA.
Estes módulos encontram-se na pasta ./ontology/OWLWriter/src/main/java/com/dapi.
Como é necessário obter informações do Solr este deve ser previamente executado.

Este povoamento resulta num ficheiro em OWL Functional Syntax (.owl). 
Uma ontologia previamente povoada encontra-se em ./ontology/OWLWriter (SAMH.owl).

Foram criadas diversas queries em Sparql para testar esta ontologia, estas queries encontram-se em ./ontology/OWLWriter (Sparql_queries.rq).