<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
//include $_SERVER['DOCUMENT_ROOT'].'/header.php';
?>

<?php
/*
$xv = $_REQUEST[xv];
$yv = $_REQUEST[yv];
$zv = $_REQUEST[zv];
$time = $_REQUEST[time];
$gx = $_REQUEST[gx];
$gy = $_REQUEST[gy];
$gz = $_REQUEST[gz];
*/

$q = "INSERT into sensordata(AccelXValue,AccelYValue,AccelZValue,timestamp,GyroX,GyroY,GyroZ) VALUES ('$xv','$yv','$zv','$time','$gx','$gy','$gz')";
if($result = $mysqli->query($q))
	print "ok";	
else
	print "fail";
?>