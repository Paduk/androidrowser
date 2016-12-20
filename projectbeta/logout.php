<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/preset.php';


session_unset($_SESSION['user_id']);
session_unset($_SESSION['login_user']);
// http://zzaps.tistory.com/42 원코드와 달리 세션을 해제한후에 세션값들을 No,'' 등으로 설정하는걸로 바꾸니까 되는것 같은데?아닌가?
$_SESSION['is_logged'] = 'NO';
$_SESSION['login_user'] = '';

echo "done";
?>