 $().ready(function(){
           // demo.initGoogleMaps(35.0857914,129.0714132); 
            //var id = <?php ?>
            $("#loginModal").keypress(function(e) { 
                if (e.keyCode == 13){
                    loginwithAjax();
                }                
            });
            $("#PhoneModal").keypress(function(e) { 
                if (e.keyCode == 13){
                    Getaddr();
                }                
            });
            if(getLocalstorage('newalarm') == undefined){            
                //setCookie('newalarm',1,'7');                         
                ;  
              }
              else{
                //setCookie('newalarm',parseInt(getCookie('newalarm'))+1,'7'); 
                $("#newalram").html("<span class='notification'>"+getLocalstorage('newalarm')+"</span>");
              }

            for(var i = 0 ; i < 100 ; i++)
            {
              if(getLocalstorage('alarmcontent'+i) == undefined){
              document.getElementById('alarmcontent').innerHTML+='<li><a href="#" onclick="deletenotifi();">Delete Notification</a></li>';                                
                break;            
              }
              else{
                //$("#alarmcontent").html("<li><a href='#''>"+getCookie('alarmcontent'+i)+"</a></li>");
                document.getElementById('alarmcontent').innerHTML+='<li><a href="#">'+getLocalstorage('alarmcontent'+i)+'</a></li>';
              }
            }              
        });
function loginwithAjax()
            {

                var id = $("#id").val();
                var pw = $("#password").val();
                var form_data = {
                    id : id,
                    password : pw
                }
                $.ajax({
                    type: "POST",
                    url: "login.php",
                    data: form_data,
                    success: function(response){                      
                        if(response == 0)
                            alert("Wrong id");
                        
                        else if(response == 1)                        
                            alert("Wrong pw");                        
                        else if(response == 2){
                            alert("Login complete");
                            location.reload();
                        }
                        else
                            alert(response);
                    }
                });
                               
            }
function logout()
            {
                $.ajax({
                type: "POST",
                url: "logout.php",                
                success: function(response) {
                    alert("logout done");
                    location.reload();
                }
               });
            }
function Getgps()
{
    $.ajax({
        type: "POST",
        url: "gcmgps.php",                
            success: function(response) {
                alert("GPS done");                    
                alert("Wait 2seconds");                    
                $(location).attr('href',"maps.html");
            }
        });
}
function Getaddr()
{    
    var name_ = document.getElementById("num").value;    
    var form_data = {
        name : name_
    }
    $.ajax({
        type: "POST",
        data: form_data,
        url: "gcmaddr.php",                
            success: function(response) {                
                alert("Addr done");                    
                alert("Wait 2seconds");                    
               $(location).attr('href',"address.html");
            }
        });
}
function refresh()
{
    console.log("done");
    $.ajax({
        type: "POST",
        url: "getcontent.php",                
            success: function(response) {
                if(response!=0)
                {                    
                    //alert("New alarm!");
                    demo.showNotification('top','center',"New Alarm",response);
                    //location.reload();
                }
            }
        });
}

var func;
function start() {
    func = setInterval(refresh, 7000);    
}
function stop() {
    clearInterval(func);
}
function Delete() {
    $.ajax({
        type: "POST",        
        url: "deletedata.php",                
            success: function(response) {                                                            
               demo.showNotification('top','center',"Delete Done!");
               //location.reload();
            }
        });    
}

 /* Cookie 관련 함수 js 16_02_13작성 */
    function setLocalstorage(name,value){    
        window.localStorage[name] = value;
    }
    function getLocalstorage(name){    
        return window.localStorage[name];
    }
    function removestorage(name){    
        localStorage.removeItem(name);
    }
    
        function setCookie(name, value, day){
            var date = new Date();
            date.setDate(date.getDate() + day);

            var willCookie = '';
            willCookie +=name+'=' + encodeURIComponent(value) + ';';
            willCookie +='expires=' + date.toUTCString() +'';

            document.cookie = willCookie;
        }

        function getCookie(name){
            var cookies = document.cookie.split(';');

            for (var i in cookies){
                if(cookies[i].search(name) != -1){
                    return decodeURIComponent(cookies[i].replace(name + '=', ''));
                }
            }
        }

        function removeCookie(name){
            var date = new Date();
            date.setDate(date.getDate()-1);

            var willCookie = '';
            willCookie += name + '=remove;';
            willCookie += 'expires=' + date.toUTCString();

            document.cookie = willCookie;
        }
function removealarm()
{    
    removestorage('newalarm');
    document.getElementById('newalram').innerHTML='';    
}
function deletenotifi()
{
    for(var i = 0 ; i < 100 ; i++)
        {
          if(getLocalstorage('alarmcontent'+i) == undefined){            
            document.getElementById('alarmcontent').innerHTML='<li><a href="#" onclick="deletenotifi();">Delete Notification</a></li>';
            break;
          }
          else
            removestorage('alarmcontent'+i);
        }
}