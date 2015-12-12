/**
 * 在主窗口弹出tab
 */
$.openTab = function(options) {
	if (null == options.url) {
		return null;
	}

	var $mainDiv = parent.$('#tt');
	if ($mainDiv.tabs('exists', options.title)) {
		$mainDiv.tabs('select', options.title);
	} else {
		var height = $mainDiv.height() - parent.$('#tt>.tabs-header').height();
		var content = '<iframe width="100%" height='
				+ height
				+ ' src="'
				+ options.url
				+ '" frameborder="no" border="0" marginwidth="0" marginheight="0" allowtransparency="yes"></iframe>';

		var settings = {
			url : null,
			title : "新tab",
			content : content
		};
		this.opts = $.extend(settings, options);
		$mainDiv.tabs('add', {
			title : this.opts.title,
			content : this.opts.content,
			closable : true
		});
	}
};



function formatFloat(tempData){
	var obj = tempData/100;
	return parseFloat(obj.toFixed(2));
}



function delImg(obj) {
	var self = obj;
	$.messager.confirm('系统提示', '确定要删除这张图片吗?', function(r) {
		if (r) {
			$(self).parent().remove();
		}
	});
}



Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	var week = {
		"0" : "/u65e5",
		"1" : "/u4e00",
		"2" : "/u4e8c",
		"3" : "/u4e09",
		"4" : "/u56db",
		"5" : "/u4e94",
		"6" : "/u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt
				.replace(
						RegExp.$1,
						((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f"
								: "/u5468")
								: "")
								+ week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};