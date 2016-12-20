<?php
//header("Content-Type: text/html; charset=UTF-8");
$hostname = "127.0.0.1";
$username = "gorapaduk";
$password = "surim1030";
$dbname = "sensordata";

//Connecting to mysql database
$conn = mysql_connect($hostname, $username, $password);
//mysql_query("set names utf8", $con);

//Selecting database
mysql_select_db($dbname,$conn);

mysql_query("set session character_set_connection=utf8;");
mysql_query("set session character_set_results=utf8;");
mysql_query("set session character_set_client=utf8;");

if(!$conn){
echo "1";
exit();
}

if(!mysql_select_db($dbname)){
echo "2";
exit();
}

$xv = $_REQUEST[xv];
$yv = $_REQUEST[yv];
$zv = $_REQUEST[zv];
$time = $_REQUEST[time];
$gx = $_REQUEST[gx];
$gy = $_REQUEST[gy];
$gz = $_REQUEST[gz];

//$sql=stripslashes($sql);
//占쎈쐻占쎈윪�얠쥏�눇�뙼�맪�쐭占쎈쐻�뜝占� /占쎈쐻占쎈윥�떋�궍�쐻占쎈윪甕곤옙 占쎈쐻占쎈윥獄��엺�쐻占쎈윥占쎈묄�뜝�럥�떛嶺뚮ㅎ�뫒占쎄껑�뜝�럥�걫占쎈쐻�뜝占� 占쎈쐻占쎈윪占쎌졆癲꾧퀗�뫅占싼뉖쑏�뜝占� 占쎌뜏占쎌뒩占쎈땻�뜝�럥痢롳옙�눇�뙼�맪�쐭�뜝�럥�꼧占쎈쐻占쎈윥�젆占�. 

$query = "insert into sensordata(AccelXValue,AccelYValue,AccelZValue,timestamp,GyroX,GyroY,GyroZ) values ('$xv','$yv','$zv','$time','$gx','$gy','$gz')";


$q=mysql_query($query) or die("3");

if(!$q) die("4");

echo "5";
mysql_close();
?>
