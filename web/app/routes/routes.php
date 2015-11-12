<?php
require '../app/lib/lexical_process.php';

$app->get('/', function () use ($app){
	$app->render('main.php', array());
})->name('home');

$app->get('/search', function () use ($app){
	$query = $app->request()->params('query');
	$page = $app->request()->params('page');
	if (is_null($page) || $page < 1) $page = 1;
	if (is_null($query)) $app->redirect($app->urlFor('home'));
	//////////////////////////

	$results_per_page = 10;
	$highlight_snippets = 3;
	// The data to send to the API
	$params = array(
		'q' => 'text:'.$query,
		'fl' => 'id,patient,therapist,session_number,session_date,file,score',
		'wt' => 'json',
		'hl' => 'true',
		'hl.snippets' => $highlight_snippets,
		'hl.fl' => 'content',
		'hl.usePhraseHighlighter' => 'true',
		'hl.simple.pre' => '<b>',
		'hl.simple.post' => '</b>',
		'hl.maxAnalyzedChars' => 1000000,
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

	//echo $words_fl;
	if (is_null($words)) $app->redirect($app->urlFor('lexical'));
	else {
		$results_per_page = 10;
		$highlight_snippets =5 ;

		$postdata = http_build_query(
			array(
					'q' => 'text:'.$words,
					'fl' => $words_fl.'id,patient,therapist,session_number,session_date,file,score',
					'wt' => 'json',
					'hl' => 'true',
					'hl.snippets' => $highlight_snippets,
					'hl.fl' => 'content',
					'hl.usePhraseHighlighter' => 'true',
					'hl.simple.pre' => '<b>',
					'hl.simple.post' => '</b>',
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

			$app->render('lexical_search.php', array('csv' => $app->csv, 'results' => $responseData, 'query' => ""));
			//$app->render('search.php', array('results' => $responseData, 'query' => $words));
		}

	}

})->name('lexical');

?>