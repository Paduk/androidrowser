<?php    
    require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
   // $q = "SELECT * from Gpsinfo where idx = (SELECT MAX(idx) FROM Gpsinfo where id='$id')";
    
    //$q = "SELECT * FROM Gpsinfo where id='$id'";
  //  print "$datetime";
 //   print "123";
    //print '$datetime';
    //$q = "INSERT INTO history (datetime, record) VALUES ('$id','1')";
    $q = "SELECT * from history where ( datetime='$id1' OR datetime='$id2' OR datetime='$id3' OR datetime='$id4' OR datetime='$id5' OR datetime='$id6' OR datetime='$id7')";
    $res = $mysqli->query($q);
    //print $res;
   //$data = $res->fetch_array();

   // while($data = $res->fetch_array())
   //{
   //   echo $data[0];
    //  echo $data[1];
     // echo $data[2];   
   //}

 $result = array();    
    while($row = mysqli_fetch_array($res)){  
      array_push($result,  
        array('idx' =>$row[0], 'datetime'=>$row[1],'record'=>$row[2] 
        ));  
    }
    //echo "HelloSurim"; 
    echo json_encode(array("result"=>$result));     
    //while($data = $res->fetch_array())
    
    $mysqli->close(); 
    //echo $id;
?>