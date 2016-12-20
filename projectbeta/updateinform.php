<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';

$sql = "SELECT * FROM usercontent WHERE id='$id'";
$result = $mysqli->query($sql);
$_SESSION['totalrecord'] = $result->num_rows; // 처음 로그인시 레코드 수 세션 저장			
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
		$return.="  \"name\" : \"$row->name\",\n";
		$rmvbr = str_replace("\n"," ",$row->text);

		$return.="  \"text\" : \"$rmvbr\"\n";					
		$return.="}\n";
	}

$return .= "]\n}";

$filename = "informjson/inform".$id.".json";
			
file_put_contents($filename, $return);			
$mysqli->close();

?>