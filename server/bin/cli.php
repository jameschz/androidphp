<?php
/**
 * Ihush Console
 *
 * @author     James.Huang <shagoo@gmail.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */

define('__HUSH_CLI', 1);

require_once '../etc/app.config.php';

require_once 'Hush/Util.php';

////////////////////////////////////////////////////////////////////////////////////////////////////
// Constants definition

define('__MYSQL_HOST', '127.0.0.1');
define('__MYSQL_PORT', '3306');
define('__MYSQL_USER', 'root');
define('__MYSQL_NAME', 'passwd');
define('__MYSQL_IMPORT_TOOL', 'mysql');
define('__MYSQL_DUMPER_TOOL', 'mysqldump');
define('__MYSQL_IMPORT_COMMAND', __MYSQL_IMPORT_TOOL . ' {PARAMS} < {SQLFILE}');
define('__MYSQL_DUMPER_COMMAND', __MYSQL_DUMPER_TOOL . ' {PARAMS} --add-drop-database > {SQLFILE}');

////////////////////////////////////////////////////////////////////////////////////////////////////
// Main process

try {
	require_once 'Demos/Cli.php';
	$cli = new Demos_Cli();
	$cli->run();
	
} catch (Exception $e) {
	Hush_Util::trace($e);
	exit;
}
