function openW(id, title, url, width,height) {
	
	if (!height || typeof(height)=="undefined" ){
   		height="600px"; 
	}   
	$WReSmsContent = $('#' + id);
	if ($WReSmsContent.length == 0) {
		$('body')
				.append(
						'<div id="'
								+ id
								+ '" class="easyui-window" title="'
								+ title
								+ '" data-options="modal:true,closed:true" style="width:'
								+ width + ';padding:10px;height:'+height+';">');

		$WReSmsContent = $('#' + id);
		$WReSmsContent.window({
			'top' : 100,
			onBeforeClose : function() {
				if ('undefined' != $('input,select,textarea').tooltip) {
					$('input,select,textarea').tooltip('destroy');
				}
			}
		});
	}
	
	$WReSmsContent.window('open');
	$WReSmsContent.window("refresh", url);
	$(".window").css("z-index","5");
	$(".window-mask").css("z-index","0");
	$(".window-shadow").css("z-index","1");
	
}

function openWBase(id, title, url, width) {
	$WReSmsContent = $('#' + id);
	if ($WReSmsContent.length == 0) {
		$('body').append(
				'<div id="' + id + '" class="easyui-window" title="' + title
						+ '" data-options="closed:true" style="width:' + width
						+ ';padding:10px;">');

		$WReSmsContent = $('#' + id);
		$WReSmsContent.window({
			'top' : 100
		});
	}

	$WReSmsContent.window('open');
	$WReSmsContent.window("refresh", url);
}

function closeW(id) {
	$('#' + id).window('close');
}