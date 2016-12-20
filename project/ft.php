<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
//include $_SERVER['DOCUMENT_ROOT'].'/header.php';
?>

<?php

$q = "INSERT INTO fitness (AccelXValue,AccelYValue,AccelZValue,timestamp) VALUES ('$xv','$yv','$zv','$time')";
if($result = $mysqli->query($q))
	print "ok";	
else
	print "fail";
?>