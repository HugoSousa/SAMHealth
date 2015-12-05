# SAMHealth
##### Project Configuration

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

