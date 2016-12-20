<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
//메인메뉴
$sql = "SELECT * FROM Maintable";
$result = $mysqli->query($sql);
//header('Content-Type: Text/json'); // json 형식 명시
$return = "{\n\"records\": [\n"; 

$jsonidx = 0;

while($row = mysqli_fetch_object($result)) 
{	
	if($jsonidx != 0) $return.=",";
	$jsonidx++;

	$return.="{\n";
	$return.="  \"idx\" : \"$jsonidx\",\n";	
	$return.="  \"type\" : \"$row->type\",\n";	
	$return.="  \"number\" : \"$row->number\",\n";	
	$rmvbr = str_replace("\n"," ",$row->text);

	$return.="  \"text\" : \"$rmvbr\"\n";					
	$return.="}\n";
}

$return .= "]\n}";

$filename = "temp.json";
file_put_contents($filename, $return);
//echo $_POST[_date];
//echo $_POST[_departcity];
//pg_close($con); 
echo $return;
$mysqli->close();
exit;
?>