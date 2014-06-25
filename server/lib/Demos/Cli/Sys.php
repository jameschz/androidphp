<?php
/**
 * Demos Cli
 *
 * @category   Demos
 * @package    Demos_Cli
 * @author     James.Huang <huangjuanshi@163.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */

require_once 'Hush/Db/Config.php';

/**
 * @package Demos_Cli
 */
class Demos_Cli_Sys extends Demos_Cli
{
	public function __init ()
	{
		parent::__init();
		
		$this->init_sql = realpath(__ROOT . '/doc/install/mysql.sql');
	}
	
	public function helpAction ()
	{
		// command description
		$this->_printHeader();
		echo "hush sys init\n";
	}
	
	public function initAction () 
	{
		echo 
<<<NOTICE

**********************************************************
* Start to initialize the Hush Framework                 *
**********************************************************

Please pay attention to this action !!!

Because you will do following things :

1. Check or download the related libraries.
2. Import original databases (Please make sure your current databases were already backuped).
3. Check all the runtime environment variables and directories.

Are you sure to do all above things [Y/N] : 
NOTICE;
		
		// check user input
		$input = fgets(fopen("php://stdin", "r"));
		if (strcasecmp(trim($input), 'y')) {
			exit;
		}
		
		// upgrade libraries
		$zendDir = __COMM_LIB_DIR . DIRECTORY_SEPARATOR . 'Zend';
		if (!is_dir($zendDir)) {
			$this->uplibAction();
		}
		
		// import backend and frontend
		$init_cmd = str_replace(
			array('{PARAMS}', '{SQLFILE}'), 
			array($this->_getCmdParams(), $this->init_sql),
			__MYSQL_IMPORT_COMMAND);
		
		echo "\nRun Command : $init_cmd\n";
		system($init_cmd, $res);
		
		if (!$res) {
			echo "Import database ok.\n";
		} else {
			exit;
		}
		
		echo 
<<<NOTICE

**********************************************************
* Initialize successfully                                *
**********************************************************

Thank you for using Hush Framework !!!

NOTICE;
	}
	
	public function uplibAction ()
	{
		// see in etc/global.config.php
		$libDir = __COMM_LIB_DIR;
		if (!is_dir($libDir)) {
			mkdir($libDir, 0777, true);
		}
		
		require_once 'Hush/Util/Download.php';
		$down = new Hush_Util_Download();
		
		// download Zend Framework
		echo "\nInstalling Zend Framework ..\n";
		$downFile = 'http://hush-framework.googlecode.com/files/ZendFramework-1.10.2.zip';
		$saveFile = $libDir . DIRECTORY_SEPARATOR . 'ZendFramework-1.10.2.zip';
		$savePath = $libDir . DIRECTORY_SEPARATOR . '.';
		if ($down->download($downFile, $saveFile)) {
			echo "Extracting.. ";
			$zip = new ZipArchive;
			$zip->open($saveFile);
			$zip->extractTo($savePath);
			$zip->close();
			unset($zip);
			echo "Done!\n";
		}
		
		// download Phpdoc
		echo "\nInstalling Php Documentor ..\n";
		$downFile = 'http://hush-framework.googlecode.com/files/Phpdoc-stable.zip';
		$saveFile = $libDir . DIRECTORY_SEPARATOR . 'Phpdoc-stable.zip';
		$savePath = $libDir . DIRECTORY_SEPARATOR . '.';
		if ($down->download($downFile, $saveFile)) {
			echo "Extracting.. ";
			$zip = new ZipArchive;
			$zip->open($saveFile);
			$zip->extractTo($savePath);
			$zip->close();
			unset($zip);
			echo "Done!\n";
		}
		
		// download Smarty_2
		echo "\nInstalling Smarty 2.x ..\n";
		$downFile = 'http://hush-framework.googlecode.com/files/Smarty-2.6.25.zip';
		$saveFile = $libDir . DIRECTORY_SEPARATOR . 'Smarty-2.6.25.zip';
		$savePath = $libDir . DIRECTORY_SEPARATOR . '.';
		if ($down->download($downFile, $saveFile)) {
			echo "Extracting.. ";
			$zip = new ZipArchive;
			$zip->open($saveFile);
			$zip->extractTo($savePath);
			$zip->close();
			unset($zip);
			echo "Done!\n";
		}
		
		// download Smarty_3
		echo "\nInstalling Smarty 3.x ..\n";
		$downFile = 'http://hush-framework.googlecode.com/files/Smarty-3beta.zip';
		$saveFile = $libDir . DIRECTORY_SEPARATOR . 'Smarty-3beta.zip';
		$savePath = $libDir . DIRECTORY_SEPARATOR . '.';
		if ($down->download($downFile, $saveFile)) {
			echo "Extracting.. ";
			$zip = new ZipArchive;
			$zip->open($saveFile);
			$zip->extractTo($savePath);
			$zip->close();
			unset($zip);
			echo "Done!\n";
		}
		
		unset($down);
		return true;
	}
}
