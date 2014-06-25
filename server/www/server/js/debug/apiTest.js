function apiTest () {
	var headers = arguments[0] || {};
	var action = $('#action').val();
	var method = $('#method').val();
	var keys = new Array();
	var vals = new Array();
	var data = '';
	$('input[name=paramKey]').each(function(){
		var key = $.trim($(this).val());
		keys.push(encodeURIComponent(key));
	});
	$('input[name=paramVal]').each(function(){
		var val = $.trim($(this).val());
		vals.push(encodeURIComponent(val));
	});
	for(var i=0; i<keys.length; i++){
		data += keys[i] + '=' + vals[i] + '&';
	}
	$.ajax({
		'headers' : headers,
		'type' : method,
		'url' : action,
		'data' : data,
		'success' : function(msg){
			$('#result').val(formatJson(msg));
		}	
	});
}