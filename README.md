# SAMHealth
##### Project Configuration

- Dowload Solr
- In Solr root folder:
```sh
$ bin\solr start
$ >bin\solr create -c <CORENAME> -data_driven_schema_configs -p 8983
```
- To post a docx/pdf file:
```sh
$ java -Dparams=literal.id=<UNIQUE_ID> -Durl=http://localhost:8983/solr/<CORENAME>/update/extract -jar example\exampledocs\post.jar <FILE_PATH.docx>
```
