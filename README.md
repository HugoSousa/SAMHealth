# SAMHealth
Academic project aiming to take advantage of Information Retrieval techniques to explorea **S**entimental **A**nalysis for **M**ental **H**ealth (**SAMH**).

**Note:** Sensitive patient data files are not available in the transcription folder and will not be published in this repository.

### Information Retriveal
##### Project description (pt)

Utilizou-se PHP com a framework Slim(para o routing) para o servidor e Javascript com jQuery para o cliente.

Existem duas routes que estão definidas em ./web/app/routes/routes.php: 
A route /search recebe como parâmetros a query a pesquisar, o número da página e o código do paciente (caso seja feita a filtragem por paciente).
A route /lexical recebe os níveis da base lexical (primário, global, intermédio, especifico) escolhidos para a pesquisa, o número da página e o código do paciente.
Através dos níveis da base lexical é feita uma filtragem no csv para obter as palavras pretendidas. Esta transformação é implementada nos scripts contidos na pasta ./web/app/lib/ .

Os templates usados estão definidos em ./web/public/templates.	

Os ficheiros das transcrições devem-se encontrar no diretório ./web/public/transcritions/<ID Paciente>.
Exemplo:

/transcriptions
	/P003
		P003_S1.docx
		P003_S2.docx
		...
	/P010
		P010_S1.docx
		...
	...

O start.cmd é um script que inicia o servidor Solr no core samh que inclui os dados e configurações da plataforma.

O script upload_transcriptions.py serve para indexar as transcrições para o Solr. Estas transcrições deverão encontrar-se na pasta ./transcritions/<ID Paciente>.

Os ficheiros (.doc/.docx) das transcrições não foram incluídos mas encontram-se indexados no core samh no Solr.

##### How to configure a new Solr Core

- Dowload Solr
- In Solr root folder:
```sh
$ bin\solr start
$ >bin\solr create -c <CORENAME> -data_driven_schema_configs -p 8983
```
- Copy the solrconfig.xml and schema.xml files into `server/solr/<CORENAME>/conf`. This configuration saves the content as a field, which allows it to be queried later.
- Restart the server:
```sh
$ bin\solr restart -p 8983
```

Note: Changes only apply to new files uploaded. If you already have posted any documents, you won't see the changes applied, they will have the same fields.

- To post a docx/pdf file:
```sh
$ java -Dparams=literal.id=<UNIQUE_ID> -Durl=http://localhost:8983/solr/<CORENAME>/update/extract -jar example\exampledocs\post.jar <FILE_PATH.docx>
```

- To delete the documents on Solr:

1. Go to 'Documents'
2. Select 'Document Type' XML
3. Add on 'Document' the following XML

  ```sh
  <delete><query>*:*</query></delete>
  <commit/>
  ```
4. Submit

### Semantic Web (pt)

O povoamento da ontologia (base lexical, terapeutas, pacientes e transcrições) é realizado com a execução de módulos em JAVA.
Estes módulos encontram-se na pasta ./ontology/OWLWriter/src/main/java/com/dapi.
Como é necessário obter informações do Solr este deve ser previamente executado.

Este povoamento resulta num ficheiro em OWL Functional Syntax (.owl). 
O povoamento cria um ficheiro em ./ontology/OWLWriter (SAMH.owl).

Foram criadas diversas queries em Sparql para testar esta ontologia, estas queries encontram-se em ./ontology/OWLWriter (Sparql_queries.rq).
