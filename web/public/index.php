<?php
require '../app/vendor/autoload.php';

use \Slim\Slim AS Slim;

$app = new Slim(array(
	'templates.path' => './templates',
	'log.enabled' => true,
	'debug' => true));


//require '../app/routes/session.php';
//require '../app/routes/member.php';
require '../app/routes/routes.php';


$app->run();
?>