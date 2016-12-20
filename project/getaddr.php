<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
//include $_SERVER['DOCUMENT_ROOT'].'/header.php';
?>

<?php
$q = "INSERT INTO PhoneAddr (id,name,number) VALUES ('$id','$name','$number')";
if($result = $mysqli->query($q))
	print "ok";	
else
	print "fail";
?>

<?php
	$sql = "SELECT * FROM PhoneAddr WHERE id='$id'";
	$result = $mysqli->query($sql);
	//$_SESSION['totalrecord'] = $result->num_rows; // 처음 로그인시 레코드 수 세션 저장
	
	$return = "{\n\"records\": [\n"; 
	$jsonidx = 0;
	while($row = mysqli_fetch_object($result)) 
		{	
			if($jsonidx != 0) $return.=",";
			$jsonidx++;

			$return.="{\n";
			$return.="  \"idx\" : \"$jsonidx\",\n";	
			$return.="  \"name\" : \"$row->name\",\n";
			$return.="  \"number\" : \"$row->number\"\n";												
			$return.="}\n";
		}
	$return .= "]\n}";
	//$filename = "addr".$id.".json";
	$filename = "../projectbeta/addrjson/address".$id.".json";
	file_put_contents($filename, $return);
			//echo $_POST[_date];
			//echo $_POST[_departcity];
			//pg_close($con); 			
	$mysqli->close();
?>