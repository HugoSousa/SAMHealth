<?php
require '../app/lib/lexical_process.php';

$app->get('/', function () use ($app){
	$app->render('main.php', array());
})->name('home');

$app->get('/search', function () use ($app){
	$query = $app->request()->params('query');
	$page = $app->request()->params('page');
	$patient = $app->request()->params('patient');
	if (is_null($page) || $page < 1) $page = 1;
	if (is_null($query)) $app->redirect($app->urlFor('home'));

	$patient_query = "";
	if(!is_null($patient)) {
		$patient_query = 'patient: ' . $patient;
	}
	//////////////////////////

	$results_per_page = 10;
	$highlight_snippets = 3;
	// The data to send to the API
	$params = array(
		'q' => 'text:'.$query,
		'fq' => $patient_query,
		'fl' => 'id,patient,therapist,session_number,session_date,file,score',
		'wt' => 'json',
		'hl' => 'true',
		'hl.snippets' => $highlight_snippets,
		'hl.fl' => 'content',
		'hl.usePhraseHighlighter' => 'true',
		'hl.simple.pre' => '<b>',
		'hl.simple.post' => '</b>',
		'hl.maxAnalyzedChars' => 1000000,
		'hl.q' => 'text:'.$query,
		'rows' => $results_per_page,
		'start' => ($page - 1) * $results_per_page
		);

	set_error_handler(function() {}, E_WARNING); //to catch and ignore error if server is down

	// Send the request
	$response = file_get_contents('http://localhost:8983/solr/samh/select?'.http_build_query($params));
	
	restore_error_handler();

	// Check for errors
	if($response === FALSE){
		$app->render('server_down.php', array());
	}else{
		// Decode the response
		$responseData = json_decode($response, TRUE);

		$app->render('search.php', array('results' => $responseData, 'query' => $query));
	}
});

$app->get('/lexical', function() use ($app) {

	$csv = $app->csv;
	$level1 = $app->request()->params('primary');
	$level2 = $app->request()->params('global');
	$level3 = $app->request()->params('intermediate');
	$level4 = $app->request()->params('specific');
	$patient = $app->request()->params('patient');

	$lexical_params_string = "";
	if (isset($level1)) $lexical_params_string .= "primary=".$level1;
	if (isset($level2)) $lexical_params_string .= "&global=".$level2;
	if (isset($level3)) $lexical_params_string .= "&intermediate=".$level3;
	if (isset($level4)) $lexical_params_string .= "&specific=".$level4;


	$page = $app->request()->params('page');
	if (is_null($page) || $page < 1) $page = 1;
	if (is_null($level1)) {
		$app->render('lexical.php', array('csv' => $app->csv));
		return;
	}

	//////////////////////////
	$words = Lexical_Process::get_words($level1, $level2, $level3, $level4, $app->csv);
	$words_array = explode(" ", $words);

	$words_fl = "";

	//to count the number of times a term appears on the document (a term is a single word! - if multiple terms, they can be calculated separately): 
	//add to fl: <FIELD_ALIAS>:termfreq(text,'<TERM>')

	foreach ($words_array as $w) {
		$words_fl .= 'count_' . $w . ':termfreq(text,\'' . $w . '\'),';
	}

	$patient_query = "";
	if(!is_null($patient)) {
		$patient_query = 'patient:' . $patient;
	}

	//echo $words;
	if (is_null($words)) $app->redirect($app->urlFor('lexical'));
	else {
		$results_per_page = 10;
		$highlight_snippets =5 ;

		$postdata = http_build_query(
			array(
					'q' => 'text:'.$words,
					'fq' => $patient_query,
					'fl' => $words_fl.'id,patient,therapist,session_number,session_date,file,score',
					'wt' => 'json',
					'hl' => 'true',
					'hl.snippets' => $highlight_snippets,
					'hl.fl' => 'content',
					'hl.usePhraseHighlighter' => 'true',
					'hl.simple.pre' => '<b>',
					'hl.simple.post' => '</b>',
					'hl.maxAnalyzedChars' => 1000000,
					'hl.q' => 'text:'.$words,

					'rows' => $results_per_page,
					'start' => ($page - 1) * $results_per_page
					)
		);

		$opts = array('http' =>
		    array(
		        'method'  => 'POST',
		        'header'  => 'Content-type: application/x-www-form-urlencoded',
		        'content' => $postdata
		    )
		);
		$context  = stream_context_create($opts);

		set_error_handler(function() {}, E_WARNING); //to catch and ignore error if server is down
		$response = file_get_contents('http://localhost:8983/solr/samh/select', false, $context);
		restore_error_handler();

		// Check for errors
		if($response === FALSE){
			$app->render('server_down.php', array());
		}else{
			// Decode the response
			$responseData = json_decode($response, TRUE);

			$app->render('lexical_search.php', array('csv' => $app->csv, 'results' => $responseData, 'lexical_params_string' => $lexical_params_string));
			//$app->render('search.php', array('results' => $responseData, 'query' => $words));
		}

	}

})->name('lexical');

?>