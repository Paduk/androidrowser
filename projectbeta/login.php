<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
//include $_SERVER['DOCUMENT_ROOT'].'/header.php';
//session_start();

//$q = "SELECT id FROM userinfo WHERE (id = '$id')";
$q = "SELECT id FROM userinfo WHERE (id = '$id')";
$result = $mysqli->query($q); // 쿼리를 수행 한 것
$returnval = $result->fetch_array(); // 그것의 값들을 returnval array에 저장
// $returnval[0];	-> return 된 id, 없을 시 0 반환
//print $returnval[0];

if($returnval[0] != $id)
	echo 0;
else
{
	$q = "SELECT password FROM userinfo WHERE (id = '$returnval[0]')";
	//if($result = $mysqli->query($q))
	$result = $mysqli->query($q); // 쿼리를 수행 한 것
	$returnval = $result->fetch_array(); // 그것의 값들을 returnval array에 저장
	
	if($returnval[0] != $password){
		echo 1;
	}
	else{ 
			$_SESSION['is_logged'] = 'YES';
       		$_SESSION['login_user'] = $id;

		// TEst
			
			$sql = "SELECT * FROM usercontent WHERE id='$id'";
			$result = $mysqli->query($sql);
			$_SESSION['totalrecord'] = $result->num_rows; // 처음 로그인시 레코드 수 세션 저장
			//echo $_SEESION['totalrecord']; 
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
				$return.="  \"name\" : \"$row->name\",\n";
				$rmvbr = str_replace("\n"," ",$row->text);

				$return.="  \"text\" : \"$rmvbr\"\n";					
				$return.="}\n";
			}

			$return .= "]\n}";

			$filename = "informjson/inform".$id.".json";
			//$filename = "temp.json";
			file_put_contents($filename, $return);
			//echo $_POST[_date];
			//echo $_POST[_departcity];
			//pg_close($con); 			
			$mysqli->close();
		//
		
		echo 2;
	}
}

// 0 반환-> id wrong, 1 -> pw wrong, 2-> complete, others -> error
?>