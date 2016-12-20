<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
//include $_SERVER['DOCUMENT_ROOT'].'/header.php';
?>

<?php
$q = "INSERT INTO userinfo (id,password,regid,date) VALUES ('$id','$password','$regid','$date')";
if($result = $mysqli->query($q))
	print "ok";	
else
	print "fail";
?>