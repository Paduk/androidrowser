<?php
    require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';

 	$id = $_SESSION['login_user'];

    //$q = "SELECT * FROM Gpsinfo where id='$id'";
    $q = "SELECT * from Gpsinfo where idx = (SELECT MAX(idx) FROM Gpsinfo where id='$id')";
    
    //$q = "SELECT * FROM Gpsinfo where id='$id'";
    $result = $mysqli->query($q);
    //$total_record = $result->num_rows;
    $data = $result->fetch_array();
        
    echo $data[2];
    echo ",";
    echo $data[3];    

?>
