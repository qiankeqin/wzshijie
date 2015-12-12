var minute = 1000 * 60;
var hour = minute * 60;
var day = hour * 24;
var halfamonth = day * 15;
var month = day * 30;
function getDateDiff(dateTimeStamp){
	var now = new Date().getTime();
	var  ddate = new Date((dateTimeStamp).replace(new RegExp("-","gm"),"/")).getTime();
	var diffValue = now - ddate;
	 
	if(diffValue < 0){
	 //若日期不符则弹出窗口告之
	 //alert("结束日期不能小于开始日期！");
	 }
	var monthC =diffValue/month;
	var weekC =diffValue/(7*day);
	var dayC =diffValue/day;
	var hourC =diffValue/hour;
	var minC =diffValue/minute;
	if(monthC>=1){
	 result=  parseInt(monthC) + "月前";
	 }
	 else if(weekC>=1){
	 result=parseInt(weekC) + "周前";
	 }
	 else if(dayC>=1){
	 result=parseInt(dayC) +"天前";
	 }
	 else if(hourC>=1){
	 result=parseInt(hourC) +"小时前";
	 }
	 else if(minC>=1){
	 result=parseInt(minC) +"分钟前";
	 }else
	 result="刚刚";
	return result;
}

function getDate(dateTimeStamp){
	var now = new Date().getTime();
	var  ddate = new Date((dateTimeStamp).replace(new RegExp("-","gm"),"/")).getTime();
	var diffValue = ddate-now;
	if(diffValue < 0){
		return 0;
	 }
	var dayC =diffValue/day;
	if(dayC>=1){
		return parseInt(dayC)+1;
	}else{
		return 1;
	}
}

function formatTimeStamp(fmtCode,dateTimeStamp){
	var  date = new Date((dateTimeStamp).replace(new RegExp("-","gm"),"/"));
    arr_d=splitDate(date);  
    result=arr_d.yyyy+"年"+arr_d.MM+"月"+arr_d.dd+"日";  
	return result;
}


function formatTimeSecond(dateTimeStamp){
	var  date = new Date((dateTimeStamp).replace(new RegExp("-","gm"),"/"));
    arr_d=splitDate(date);  
    result=arr_d.MM+"月"+arr_d.dd+"日"+"  "+arr_d.hh+":"+arr_d.mm;  
	return result;
}

function formatTimeThird(dateTimeStamp){
	var  date = new Date((dateTimeStamp).replace(new RegExp("-","gm"),"/"));
    arr_d=splitDate(date);  
    result=arr_d.yyyy+"."+arr_d.MM+"."+arr_d.dd;  
	return result;
}
function formatTimeFour(dateTimeStamp){
	var  date = new Date((dateTimeStamp).replace(new RegExp("-","gm"),"/"));
    arr_d=splitDate(date);  
    result=arr_d.yyyy+"-"+arr_d.MM+"-"+arr_d.dd;  
	return result;
}
function formatTimeFive(dateTimeStamp){
	var  date = new Date((dateTimeStamp).replace(new RegExp("-","gm"),"/"));
    arr_d=splitDate(date);  
    result=arr_d.yyyy+"年"+arr_d.MM+"月"+arr_d.dd+"日";  
	return result;
}
function formatTimeLine(date){
	var now = new Date;
    var  that = new Date(date);
    arr_d=splitDate(that);  
    if (that.getFullYear () == now.getFullYear () && that.getMonth () == now.getMonth ()&& that.getDate () == now.getDate () - 1)
    {
        return '昨天';
    }else if(that.getFullYear () == now.getFullYear () && that.getMonth () == now.getMonth ()&& that.getDate () == now.getDate ()){
    	return '今天';
    }else{
        var s = arr_d.yyyy+"-"+arr_d.MM+"-"+arr_d.dd;
        return s;
    }
}
function splitDate(d){
	var yyyy,MM,dd,hh,mm,ss;
	yyyy=d.getFullYear();
	MM=(d.getMonth()+1)<10?"0"+(d.getMonth()+1):d.getMonth()+1;

	dd=d.getDate()<10?"0"+d.getDate():d.getDate();

	hh=d.getHours()<10?"0"+d.getHours():d.getHours();

	mm=d.getMinutes()<10?"0"+d.getMinutes():d.getMinutes();

	ss=d.getSeconds()<10?"0"+d.getSeconds():d.getSeconds();
	return {"yyyy":yyyy,"MM":MM,"dd":dd,"hh":hh,"mm":mm,"ss":ss}; 
}
//时间格式化 
//var dt = new Date();
//var nowDate = dt.format("yyyy-MM-dd hh:mm:ss");
//2013-12-02 14:02:11
Date.prototype.format =function(format)
{
    var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(), //day
            "h+" : this.getHours(), //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3), //quarter
            "S" : this.getMilliseconds() //millisecond
    };
    if(/(y+)/.test(format))
    {   
        format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4- RegExp.$1.length));
    }
    for(var k in o)
    {
        if(new RegExp("("+ k +")").test(format))
        {
            format = format.replace(RegExp.$1,RegExp.$1.length==1? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
        }
    }
    return format;
};


//聊天记录时间展示
function getMessageTime(messageTime){
	var nowDate = new Date().getTime();
	var  messageTime = new Date((messageTime).replace(new RegExp("-","gm"),"/")).getTime();
	var diffValue = nowDate - messageTime;
	var dayC =diffValue/day;
	var hourC =diffValue/hour;
	var minC =diffValue/minute;
	if(dayC>=1){
		 result=parseInt(dayC) +"天前";
	}else if(hourC>=1){
		 result=parseInt(hourC) +"个小时前";
	 }else if(minC>=1){
	     result=parseInt(minC) +"分钟前";
	 }else{
		result="刚刚";
	}
	return result;
}


function formatFloat(tempData){
	var obj = tempData/100;
	return parseFloat(obj.toFixed(2));
}