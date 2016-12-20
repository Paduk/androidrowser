<?php
    require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';

    $id = $_SESSION['login_user'];
 	$sql = "DELETE FROM usercontent WHERE id='$id'";
    $result = $mysqli->query($sql);
        
?>
