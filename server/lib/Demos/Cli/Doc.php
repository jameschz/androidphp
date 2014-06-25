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

/**
 * @package Demos_Cli
 */
class Demos_Cli_Doc extends Demos_Cli
{
	/**
	 * Document cli class instruction
	 * @return void
	 */
	public function helpAction ()
	{		
		echo "\n./cli doc [help|build]\n\n";
	}
	
	/**
	 * Generate lib document to www/doc
	 * @return void
	 */
	public function buildAction ()
	{
		// init phpdoc enviornment
		$phpdocBin = realpath(__COMM_LIB_DIR . '/Phpdoc/phpdoc');
		if (!$phpdocBin) die('Phpdoc exe file can not be found.');
		$phpdocBin = 'php ' . $phpdocBin;
		
		// building api document
		echo "\n\n>>> Start building api document ...\n\n";
		$docThemeApi = 'HTML:frames:service.api';
		$docTitleApi = 'Demos Apis Documentation';
		$targetDirApi = __DOC_DIR . '/doc-api';
		$sourceDirApi = __LIB_PATH_SERVER;
		$command = "$phpdocBin -o $docThemeApi -ti '$docTitleApi' -t $targetDirApi -d $sourceDirApi";
		echo $this->exec($command);
		
		// building lib document
		echo "\n\n>>> Start building lib document ...\n\n";
		$docThemeLib = 'HTML:frames:service.lib';
		$docTitleLib = 'Demos Libs Documentation';
		$targetDirLib = __DOC_DIR . '/doc-lib';
		$sourceDirLib = __LIB_DIR;
		$command = "$phpdocBin -o $docThemeLib -ti '$docTitleLib' -t $targetDirLib -i *Service.php -d $sourceDirLib";
		echo $this->exec($command);
	}
}
