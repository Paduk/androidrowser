<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
//include $_SERVER['DOCUMENT_ROOT'].'/header.php';
?>

<?php

$q = "INSERT INTO sensor2 (AccelXValue,AccelYValue,AccelZValue,timestamp,GyroX,GyroY,GyroZ) VALUES ('$xv','$yv','$zv','$time', $gx, $gy, $gz)";
if($result = $mysqli->query($q))
	print "ok";	
else
	print "fail";
?>