<?php

$_Lang['cn'] = array(
	'notice'	=> '你有{0}个新粉丝，请速回查看：）',
);

function L ($lang = 'cn', $key = '')
{
	global $_Lang;
	
	$msg = isset($_Lang[$lang][$key]) ? $_Lang[$lang][$key] : '';
	
	// should do replace logic
	$args = func_get_args();
	if (sizeof($args) > 2) {
		array_shift($args);
		array_shift($args);
		$replace_old = array();
		$replace_new = array();
		foreach ($args as $id => $arg) {
			$replace_old[] = '{' . $id . '}';
			$replace_new[] = $arg;
		}
		$msg = str_replace($replace_old, $replace_new, $msg);
	}
	
	return $msg;
}