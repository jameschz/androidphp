<?php
require_once '../../etc/app.config.php';
require_once 'Demos/App.php';

$app = new Demos_App();

$app->setErrorPage('./404.php')
	->addMapFile(__MAP_INI_FILE)
	->addAppDir(__LIB_PATH_SERVER)
	->addAppDir(__LIB_PATH_WEBSITE);

// 将所有的调试信息和错误打印关闭，建议在正式环境关闭
$app->setDebug(true);

// 设置Controller类的类名后缀
$app->run(array(
	'defaultClassSuffix' => 'Server'
));
