<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
//include $_SERVER['DOCUMENT_ROOT'].'/header.php';
?>

<?php
$q = "INSERT INTO usercontent (id,type,number,name,text) VALUES ('$id','$type','$number','$name','$text')";
if($result = $mysqli->query($q))
	print "ok";	
else
	print "fail";
?>