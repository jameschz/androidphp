<?php
/**
 * Global php settings
 */
error_reporting(E_ERROR | E_WARNING | E_PARSE);

/**
 * Global timezone
 */
date_default_timezone_set('PRC');

/**
 * Common Directories
 */
define('__ETC', dirname(__FILE__));
define('__ROOT', realpath(__ETC . '/../'));
define('__LIB_DIR', realpath(__ROOT . '/lib'));
define('__BIN_DIR', realpath(__ROOT . '/bin'));
define('__WWW_DIR', realpath(__ROOT . '/www'));
define('__DAT_DIR', realpath(__ROOT . '/dat'));
define('__DOC_DIR', realpath(__ROOT . '/doc'));
define('__CACHE_DIR', realpath(__DAT_DIR . '/cache'));

/**
 * Common libraries paths
 * TODO : Copy Zend Framework and Hush libraries to following paths !!!
 */
define('__COMM_LIB_DIR', realpath(__ROOT . '/../../phplibs'));
define('__HUSH_LIB_DIR', realpath(__ROOT . '/../../hush-framework/hush-lib'));

/**
 * Check libraries
 */
$zendDir = __COMM_LIB_DIR . DIRECTORY_SEPARATOR . 'Zend';
$hushDir = __HUSH_LIB_DIR . DIRECTORY_SEPARATOR . 'Hush';
if (!is_dir($zendDir) || !is_dir($hushDir)) {
	if (!defined('__HUSH_CLI')) {
		echo "Please enter 'server/bin' and use 'cli sys init' command to complete the installation.";
	} else {
		echo "Please check 'Zend' and 'Hush' libraries are existed in the __COMM_LIB_DIR and __HUSH_LIB_DIR dirs.";
	}
	exit(1);
}

/**
 * Set system's include path
 */
set_include_path('.' . PATH_SEPARATOR . __LIB_DIR . PATH_SEPARATOR . __COMM_LIB_DIR . PATH_SEPARATOR . __HUSH_LIB_DIR . PATH_SEPARATOR . get_include_path());

/**
 * Enviornment settings
 * Include 'dev', 'test', 'www'
 * Impact some variables and debug infomation
 * TODO : should be changed by enviornment change !!!
 */
define('__ENV', 'dev');
