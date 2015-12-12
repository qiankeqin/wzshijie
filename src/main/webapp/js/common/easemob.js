var appkey ='lagoqu1#comlagoqucrowdfund';
var apiURL = null;
var conn = null;
var talkInputId = "talkInputId";
$(document).ready(function() {
	$(function(){
		conn = new Easemob.im.Connection();
		conn.init({
			https : false,
			//当连接成功时的回调方法
			onOpened : function() {
				handleOpen(conn);
			},
			//当连接关闭时的回调方法
			onClosed : function() {
				handleClosed();
			},
			//收到文本消息时的回调方法
			onTextMessage : function(message) {
				handleTextMessage(message);
			},
			//收到联系人信息的回调方法
			onRoster : function(message) {
				handleRoster(message);
			},
			//收到表情消息时的回调方法
			onEmotionMessage : function(message) {
				handleEmotion(message);
			}
		});
		loginEasemob();
	});
});


//登录系统时的操作方法
var loginEasemob = function(){
	var user =talkMeid.toString();
	var pass =talkMePassword;
	//根据用户名密码登录系统
	conn.open({
		apiUrl : apiURL,
		user : user,
		pwd : pass,
		//连接时提供appkey
		appKey : appkey
	//accessToken : 'YWMt8bfZfFk5EeSiAzsQ0OXu4QAAAUpoZFOMJ66ic5m2LOZRhYUsRKZWINA06HI'
	});
	conn.setPresence();
	return false;
};
		
//发送消息
function send(){
	    //从连接中获取到当前的登录人注册帐号名
	    var UserId = conn.context.userId;
		var messageContent =$("#talkcontent").val();
		var to =membersID;
		var UserType="1";     //发送人
		var state="2";
		var options = {
			to : to,
			msg : messageContent,
			type : "chat"
		};
	    //发送文本消息接口
		conn.sendTextMessage(options);
		appendMsg(UserId,messageContent,UserType,state);
		$("#talkcontent").val("");
}


//收到文本消息的回调方法的实现
function handleTextMessage(message){
	var UserId = message.from;//消息的发送者
	var messageContent = message.data;//文本消息体
	var UserType="2";     //发送人
	var state="1";
	appendMsg(UserId,messageContent,UserType,state);
}

//收到表情消息时的方法
 function handleEmotion(message) {
	var UserId = message.from;//消息的发送者
	var UserType="2";     //发送人
	var state="2";
	var messageContent=message;
	appendMsg(UserId, messageContent, UserType,state);
};
 



function handleOpen(conn){
	//从连接中获取到当前的登录人注册帐号名
	curUserId = conn.context.userId;
	conn.setPresence();//设置用户上线状态，必须调用
}




//表情弹出框
var emotionFlag = false;
function showEmotionDialog(){
	if (emotionFlag==true) {
		$("#wl_faces_box").fadeOut("slow");
		$("#emotionUL").empty();
		emotionFlag = false;
		return;
	}
	if (emotionFlag) {
		$('#wl_faces_box').css({
			"display" : "block"
		});
		return;
	}
	emotionFlag = true;
	// Easemob.im.Helper.EmotionPicData设置表情的json数组
	var sjson = Easemob.im.Helper.EmotionPicData;
	for ( var key in sjson) {
		var emotions = $('<img>').attr({
			"id" : key,
			"src" : sjson[key],
			"style" : "cursor:pointer;"
		}).click(function() {
			selectEmotionImg(this);
		});
		$('<li>').append(emotions).appendTo($('#emotionUL'));
	}
	$('#wl_faces_box').css({
		"display" : "block"
	});
}


function selectEmotionImg(selImg){
	var talkcontent = $("#talkcontent").val();
	talkcontent=talkcontent+selImg.id;
	$("#talkcontent").val(talkcontent);
}

//消息展示窗口
function appendMsg(UserId,messageContent,UserType,state){
	var talk_msg_ul=""; 
	var message="";
	var lineDiv = document.createElement("div");
	if(state==1){
		message=messageContent;
	}if(state==2){
		if (typeof messageContent == 'string') {
			msg = Easemob.im.Helper.parseTextMessage(messageContent);
			msg = msg.body;
		} else {
			msg = messageContent.data;
		} 
		var mc=msg;
		for (var i = 0; i < mc.length; i++) {
			var msg = mc[i];
			var type = msg.type;
			var data = msg.data;
			if (type == "emotion") {
				var eletext = "<p6><img src='" + data + "'/></p6>";
				message+=eletext;
			}else{
				var eletext = "<p3>" + data + "</p3>";
				message+=eletext;
			}
		}
	
	}
	if(UserType==1){
		talk_msg_ul+='<li class="talk_li_me">'
		talk_msg_ul+='<span><a href="###"><img src="'+talkMeImage+'" /></a><s><img src="/images/icon_talk_d_18.jpg" /></s></span>'
		talk_msg_ul+='<strong><font>'+message+'</font></strong>'
		talk_msg_ul+='<div class="clear"></div>'
		talk_msg_ul+='</li>'
	}if(UserType==2){
		talk_msg_ul+='<li>'
			talk_msg_ul+='<span><a href="###"><img src="'+membersImage+'" /></a><s><img src="/images/icon_talk_c_14.png" /></s></span>'
			talk_msg_ul+='<strong><font>'+message+'</font></strong>'
			talk_msg_ul+='<div class="clear"></div>'
			talk_msg_ul+='</li>'
			//保存消息记录
			saveTalkRecord(UserId,messageContent);
	}
	
	$("#talk_msg_ul").append(talk_msg_ul);	
}




//保存消息记录
function saveTalkRecord(membersID,messageContent){
	var storage = window.localStorage;
	var date=new Date();
	var talkTime=date.format("yyyy-MM-dd hh:mm:ss");
	var details = {"membersID":membersID,"membersNickName":membersNickName,"membersImage":membersImage,"talkTime":talkTime,"messageContent":messageContent};
	storage.setItem(membersID,JSON.stringify(details));
}