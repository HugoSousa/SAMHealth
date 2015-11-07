import subprocess

patient = 'P465'
doc_id = 'P465_S1'
dparams = '-Dparams=literal.id=' + doc_id + '&literal.patient=' + patient
durl = '-Durl=http://localhost:8983/solr/samh/update/extract'
test_file = 'transcriptions/P465/P465 sessao 1.docx'
res = subprocess.call(['java', dparams , durl, '-jar', 'post.jar', test_file])
print res