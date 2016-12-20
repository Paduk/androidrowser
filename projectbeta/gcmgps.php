<?php
    require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';
/*
    $q = "SELECT regidid FROM Individualkey where idx = 3";
    $result = $mysqli->query($q);
    $data = $result->fetch_array();
*/
 // curl 함수가 정의 안되었을땐 php.ini에 가서 ;extension=php_curl.dll 세미콜론 지워주면 됨
    
    
//    echo $data[0];



    //$regid = "APA91bHjVbU5yZT6QF_5FotmLOZyOgj5aecKMOiquExk6tiEtWcZ07-sWoc9Hq1dTyTc8MSqpQNt2S1RTDTyv1Kngag8T8wuc9psZNuA8TsKKcSd1udOxCG44FCJkR3Jf3iz-G-gMufabx6T1YBF9Hl9HpdKreBwDQ";
   // $regid = "APA91bG9rj4pvEAUvUcFy8N19DFRgA2oRKJ3cQPHMEmQIgCKtkpxqYHhtiK4W21ZbdjXWTcV5ioZjfpX3kXz_0-coUbz2qeXBZhjCvPmLBX5Pa5NY-q8wrJ0Cl0tW-1EeImR8aO3Mh5ZHdyLzM4n5GGiScDuIuRnZQ";
    // 헤더 부분
    $id = $_SESSION["login_user"];
    $helloidx = 0;
    $result1 = "SELECT regid FROM userinfo WHERE id='$id'";
    $result = $mysqli->query($result1);
    $data = $result->fetch_array();

    $headers = array(
            'Content-Type:application/json',
            'Authorization:key=AIzaSyD9GMBc6WkIpooY-XaoDF5VvTOpr7HlTYc'
            );
 
    // 푸시 내용, data 부분을 자유롭게 사용해 클라이언트에서 분기할 수 있음.
    $arr = array();
    $arr['data'] = array();    
    $arr['data']['data'] = "map";
    $arr['registration_ids'] = array();
    $arr['registration_ids'][0] = $data[0];

    //$arr['registration_ids'][0] = $data1[0];
    //$arr['registration_ids'][1] = $data[0];
 
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, 'https://android.googleapis.com/gcm/send');
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS,json_encode($arr));
    $response = curl_exec($ch);
    curl_close($ch);
 
    // 푸시 전송 결과 반환.
    $obj = json_decode($response);
 
    // 푸시 전송시 성공 수량 반환.
    $cnt = $obj->{"success"};
 
    print $id;
?>