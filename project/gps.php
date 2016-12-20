<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
//include $_SERVER['DOCUMENT_ROOT'].'/header.php';
?>

<?php
$q = "INSERT INTO Gpsinfo (id,latitude,longitude,onoff) VALUES ('$id','$latitude','$longitude','$onoff')";
if($result = $mysqli->query($q))
	print "ok";	
else
	print "fail";
?>