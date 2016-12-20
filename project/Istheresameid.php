<?php
    require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';

 
    $q = "SELECT id FROM userinfo";
    $result = $mysqli->query($q);
    $issame = "false";

    while($data = $result->fetch_array())
    {
        if($id ==$data[0]){            
            $issame = "true";
            break;
        }
        else
            ;
            
    }

    echo $issame;    

?>