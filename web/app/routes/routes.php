<?php

$app->get('/', function () use ($app){
    $app->render('main.php', array());
})->name('home');

$app->get('/search', function () use ($app){
	$query = $app->request()->params('query');
	$page = $app->request()->params('page');
	if (is_null($page)) $page = 1;
	if (is_null($query)) $app->redirect($app->urlFor('home'));
// --
    $app->render('search.php', array());
});

?>