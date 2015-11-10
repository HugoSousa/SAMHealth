<?php

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
		'q' => $query,
		'fl' => 'id,patient,therapist,session_number,session_date,file,score',
		'wt' => 'json',
		'hl' => 'true',
		'hl.snippets' => $highlight_snippets,
		'hl.fl' => 'content',
		'hl.usePhraseHighlighter' => 'true',
		'hl.simple.pre' => '<b>',
		'hl.simple.post' => '</b>',
		'rows' => $results_per_page,
		'start' => ($page - 1) * $results_per_page
		);

	// Create the context for the request
	$context = stream_context_create(
		array(
			'http' => array(
				'method' => 'GET'
				)
			)
		);

	// Send the request
	$response = file_get_contents('http://localhost:8983/solr/samh/select?'.http_build_query($params));

	// Check for errors
	if($response === FALSE){
		die('Error');
	}

	// Decode the response
	$responseData = json_decode($response, TRUE);

	// Print the date from the response
	//var_dump($responseData);

	/////////////////////////
	$app->render('search.php', array('results' => $responseData, 'query' => $query));
});


$app->get('/lexical', function() use ($app) {
	var_dump($app->csv);

});	



?>