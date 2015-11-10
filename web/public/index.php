<?php

require '../app/vendor/autoload.php';
require '../app/lib/csv.php';

use \Slim\Slim AS Slim;

$app = new Slim(array(
	'templates.path' => './templates',
	'log.enabled' => true,
	'debug' => true));

$app->response()->header('Content-Type', 'text/html;charset=UTF-8');


$app->container->singleton('csv', function (){
    return CSV::get_lexical('../app/data/lexical.csv');
});

//require '../app/routes/session.php';
//require '../app/routes/member.php';
require '../app/routes/routes.php';


$app->run();
?>