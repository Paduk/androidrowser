<?php
    require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';

    $id = $_SESSION['login_user'];
 	$sql = "SELECT * FROM usercontent WHERE id='$id'";
    $result = $mysqli->query($sql);
    //echo $_SESSION['totalrecord']." : ". $result->num_rows;
    /*
        $alarm = "SELECT * FROM usercontent WHERE id='$id'";
        $resultalarm = $mysqli->query($alarm);
        while($rowalarm = mysqli_fetch_object($resultalarm)) 
        {
            $text = "type : $rowalarm->type, name : $rowalarm->name";
        }
        echo $text;
    */
    if($_SESSION['totalrecord'] != $result->num_rows) // 레코드의 변화가 있으면.
    {
            //echo $_SEESION['totalrecord']; 
            //header('Content-Type: Text/json'); // json 형식 명시
            
            
            $_SESSION['totalrecord'] = $result->num_rows;
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

            //$filename = "temp".$id.".json";
            //$filename = "temp.json";
            $filename = "informjson/inform".$id.".json";
            file_put_contents($filename, $return);
            //echo $_POST[_date];
            //echo $_POST[_departcity];
            //pg_close($con);
            // test
            //$alarm = "SELECT * FROM usercontent WHERE id='$id' and idx='select max(idx) from usercontent where id=$id'";
            $alarm = "SELECT * from usercontent where idx = (select max(idx) from usercontent where id='$id')";
            $resultalarm = $mysqli->query($alarm);
            while($rowalarm = mysqli_fetch_object($resultalarm)) 
            {
                $text = "$rowalarm->type , $rowalarm->name";
            }
            echo $text;
            //echo 1;           
            $mysqli->close(); 
    }
    else
    {
        echo 0;
    }
?>
