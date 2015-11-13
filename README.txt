Utilizou-se PHP com a framework Slim(para o routing) para o servidor e Javascript com jQuery para o cliente.

Existem duas routes que estão definidas em ./web/app/routes/routes.php: 
A route /search recebe como parâmetros a query a pesquisar, o número da página e o código do paciente (caso seja feita a filtragem por paciente).
A route /lexical recebe os níveis da base lexical (primário, global, intermédio, especifico) escolhidos para a pesquisa, o número da página e o código do paciente. Através dos níveis da base lexical é feita uma filtragem no csv para obter as palavras pretendidas.

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