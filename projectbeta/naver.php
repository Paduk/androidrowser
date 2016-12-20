<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/Naver.php';
		
$naver = new Naver(array(
        "CLIENT_ID" => "Bw3ZzfN9QKVedFZvGiK9",        // (*필수)클라이언트 ID  
        "CLIENT_SECRET" => "6MYc8n6McP",    // (*필수)클라이언트 시크릿
        "RETURN_URL" => "http://gorapaduk.dothome.co.kr/projectbeta/",      // (*필수)콜백 URL
        "AUTO_CLOSE" => true,              // 인증 완료후 팝업 자동으로 닫힘 여부 설정 (추가 정보 기재등 추가행동 필요시 false 설정 후 추가)
        "SHOW_LOGOUT" => true              // 인증 후에 네이버 로그아웃 버튼 표시/ 또는 표시안함
        )
    );
echo '
			<script>
			function loginNaver(){
				var win = window.open(\''.NAVER_OAUTH_URL.'authorize?client_id=Bw3ZzfN9QKVedFZvGiK9&response_type=code&redirect_uri=http://gorapaduk.dothome.co.kr/projectbeta/&state='.$naver->state.'\', \'네이버 아이디로 로그인\',\'width=320, height=480, toolbar=no, location=no\');

				var timer = setInterval(function() {
					if(win.closed) {
						window.location.reload();
						clearInterval(timer);
					}
				}, 500);
			}

			loginNaver();
			</script>
			';

  return $naver->getUserProfile('JSON');
?>