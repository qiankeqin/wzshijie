// JavaScript Document

function whenloaded(){
	$(".js_btn_a").click(function(){
		$(".win,.all_bg").fadeOut();
		$(".btn_ysdl").hide();
		$(".login_h").show();
	});
	
	$(".js_btn_b").click(function(){
		$(".btn_ysdl").show();
		$(".login_h").hide();	
		$(this).parent().parent().hide();	
	});
	
	$(".login_h").hover(function(){
		$(this).find("ul").fadeIn("fast");	
	},function(){
		$(this).find("ul").fadeOut("fast");	
	});
	
	winAuto();
	
	$(".js_btn_login").click(function(){
		$(".win:first,.all_bg").fadeIn();
		winAuto();
	});
	
	$(".win_close").click(function(){
		$(".win,.all_bg").fadeOut();
	});
	
	$(".ul_a li a").click(function(){
		$(".win:eq(1),.all_bg").fadeIn();
	});
	
	$(".fbxwz").click(function(){
		$(".win:eq(1),.all_bg").fadeIn();
		winAuto();	
	});
};

function winAuto(){
	$(".win").each(function(){
		var wH = $(this).height();
		$(this).css("margin-top",-wH/2);
	});
}