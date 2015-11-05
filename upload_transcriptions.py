import os, json, subprocess
from pprint import pprint

for patient_folder in os.listdir(os.getcwd() + "/transcriptions"):
	info_file = "transcriptions/" + patient_folder + "/info.json"

	print patient_folder
	
	with open(info_file) as info_json:
		data = json.load(info_json)
		patient = data["patient"]
		therapist = data["therapist"]
		therapist = therapist.replace(" ", "%20")#replace white spaces with %20
		sessions = data["sessions"]

		for session in sessions:
			session_number = session["number"]
			session_date = session["date"]
			file_name = session["file"]
			file_directory = 'transcriptions/' + patient_folder + '/' + file_name
			doc_id = patient + "_" + session_number

			dparams = '-Dparams=literal.id=' + doc_id + '&literal.patient=' + patient + '&literal.therapist=' + therapist + '&literal.session_number=' + session_number
			if session_date is not None:
				dparams += '&literal.session_date=' + session_date

			durl = '-Durl=http://localhost:8983/solr/samh/update/extract'

			subprocess.call(['java', dparams , durl, '-jar', 'post.jar', file_directory])

			print('Added document for patient/session ' + patient + '/' + session_number)			

print('Done')
    