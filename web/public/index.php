<?php
require '../app/vendor/autoload.php';

use \Slim\Slim AS Slim;

$app = new Slim();
//require '../app/routes/session.php';
//require '../app/routes/member.php';
//require '../app/routes/admin.php';
/*
$app->get('/', function () {
    echo "Hello world";
});
*/
$app->run();
?>