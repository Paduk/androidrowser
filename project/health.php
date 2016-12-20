<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
//include $_SERVER['DOCUMENT_ROOT'].'/header.php';
?>

<?php

$q = "INSERT INTO history (datetime, record) VALUES ('$datetime','$record')";
if($result = $mysqli->query($q))
	print "ok";	
else
	print "fail";
?>