import subprocess

patient = 'P003'
doc_id = 'P003_S4'
dparams = '-Dparams=literal.id=' + doc_id + '&literal.patient=' + patient
durl = '-Durl=http://localhost:8983/solr/samh/update/extract'
test_file = 'transcriptions/P003/P003_S4.docx'
res = subprocess.call(['java', dparams , durl, '-jar', 'post.jar', test_file])
print res