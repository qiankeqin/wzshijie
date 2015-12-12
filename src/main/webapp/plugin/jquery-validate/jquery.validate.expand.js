/**
 * jquery.validate相关扩展验证
 * 
 * @author Administrator
 */
jQuery.extend(jQuery.validator.messages, {
	required : "<s>必选字段</s>",
	remote : "<s>请修正该字段</s>",
	email : "<s>请输入正确格式的电子邮件</s>",
	url : "<s>请输入合法的网址</s>",
	date : "<s>请输入合法的日期</s>",
	dateISO : "<s>请输入合法的日期 (ISO).</s>",
	number : "<s>请输入合法的数字</s>",
	digits : "<s>只能输入整数</s>",
	creditcard : "<s>请输入合法的信用卡号</s>",
	equalTo : "<s>请再次输入相同的值</s>",
	accept : "<s>请输入拥有合法后缀名的字符串</s>",
	maxlength : jQuery.format("<s>请输入一个 长度最多是 {0} 的字符串</s>"),
	minlength : jQuery.format("<s>请输入一个 长度最少是 {0} 的字符串</s>"),
	rangelength : jQuery.format("<s>请输入 一个长度介于 {0} 和 {1} 之间的字符串</s>"),
	range : jQuery.format("<s>请输入一个介于 {0} 和 {1} 之间的值</s>"),
	max : jQuery.format("<s>请输入一个最大为 {0} 的值</s>"),
	min : jQuery.format("<s>请输入一个最小为 {0} 的值</s>")
});

// 验证用户名
jQuery.validator.addMethod("userNameCheck", function(value, element) {
	return this.optional(element) || /^[a-zA-Z]\w{4,20}$/.test(value);
}, "<s>请输入4-20位字母开头的字母或数字和下划线</s>");

// 字符验证
jQuery.validator.addMethod("stringCheck", function(value, element) {
	return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
}, "<s>只能包括中文字、英文字母、数字和下划线</s>");

// 邮箱验证
jQuery.validator
		.addMethod(
				"isEmail",
				function(value, element) {
					return this.optional(element)
							|| /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/
									.test(value);
				}, "<s>请正确填写邮箱格式</s>");

// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
	var length = value.length;
	var mobile = /(^(11|12|13|14|15|16|17|18|19)\d{9}$)/;
	return this.optional(element) || (length == 11 && mobile.test(value));
}, "<s>请正确填写您的手机号码</s>");

// 电话号码验证
jQuery.validator.addMethod("isTel", function(value, element) {
	var tel = /^\d{3,4}-?\d{7,9}$/; // 电话号码格式010-12345678
	return this.optional(element) || (tel.test(value));
}, "<s>请正确填写您的电话号码</s>");

// 联系电话(手机/电话皆可)验证
jQuery.validator.addMethod("isPhone", function(value, element) {
	var mobile = /(^(11|12|13|14|15|16|17|18|19)\d{9}$)|(^0(([1,2]\d)|([3-9]\d{2}))\d{7,8}$)/;
	var tel = /^\d{3,4}-?\d{7,9}$/;
	return this.optional(element) || (tel.test(value) || mobile.test(value));

}, "<s>请正确填写您的联系电话</s>");

//日期时间验证
jQuery.validator.addMethod("datetime", function(value, element) {
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	return this.optional(element) || (reg.test(value));

}, "<s>请输入正确的日期时间格式</s>");
//时间验证
jQuery.validator.addMethod("time", function(value, element) {
	var reg=/^((20|21|22|23|[0-1]\d)\:[0-5][0-9])(\:[0-5][0-9])?$/;
	return this.optional(element) || (reg.test(value));

}, "<s>请输入正确的时间格式</s>");

//是否手机号
String.prototype.isMobile = function(tag) {
	var length = this.length;
	var mobile = /(^(11|12|13|14|15|16|17|18|19)\d{9}$)/;
	return length == 11 && mobile.test(this);
};