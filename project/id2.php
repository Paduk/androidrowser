<?php    
    require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
   // $q = "SELECT * from Gpsinfo where idx = (SELECT MAX(idx) FROM Gpsinfo where id='$id')";
    
    //$q = "SELECT * FROM Gpsinfo where id='$id'";
  //  print "$datetime";
 //   print "123";
    //print '$datetime';
    //$q = "INSERT INTO history (datetime, record) VALUES ('$id','1')";
    $q = "SELECT * from history where datetime='$id'";
    $res = $mysqli->query($q);
    //print $res;
	//$data = $res->fetch_array();
	while($data = $res->fetch_array())
	{
		echo $data[0];
		echo $data[1];
		echo $data[2];	
	}
	
	
    //while($data = $res->fetch_array())
    
    $mysqli->close(); 
    //echo $id;
?>
