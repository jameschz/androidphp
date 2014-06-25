<?php 

$_DataMap = array(
	'Customer' => array(
		'id' => 'id',
		'sid' => 'sid',
		'name' => 'name',
		'sign' => 'sign',
		'face' => 'face',
		'faceurl' => 'faceurl',
		'blogcount' => 'blogcount',
		'fanscount' => 'fanscount',
		'uptime' => 'uptime',
	),
	'Blog' => array(
		'id' => 'id',
		'face' => 'face',
		'author' => 'author',
		'content' => 'content',
		'comment' => 'comment',
		'uptime' => 'uptime',
	),
	'Comment' => array(
		'id' => 'id',
		'content' => 'content',
		'uptime' => 'uptime',
	),
	'Image' => array(
		'id' => 'id',
		'url' => 'url',
		'type' => 'type',
	),
	'Notice' => array(
		'id' => 'id',
		'message' => 'message'
	),
);

function M ($model, $data)
{
	global $_DataMap;
	
	$dataMap = isset($_DataMap[$model]) ? $_DataMap[$model] : null;
	if ($dataMap) {
		$dataRes = array();
		foreach ((array) $data as $k => $v) {
			if (array_key_exists($k, $dataMap)) {
				$mapKey = $dataMap[$k];
				$dataRes[$mapKey] = $v;
			}
		}
		return $dataRes;
	}
	
	return $data;
}