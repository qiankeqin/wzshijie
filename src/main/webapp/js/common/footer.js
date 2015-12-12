(function(){
	$("#footer li").each(function(){
		var titleH = $("title").html();
		var tH = $(this).find("span").html();
		if(titleH==tH){
			var imgS = $(this).find("img").attr("src").replace("_1.png","");
			$(this).find("img").attr("src",imgS+"_2.png");
		}
	});
	$(".index_ul li:last").find("h5").hide();
})();