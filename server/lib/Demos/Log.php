<?php
/**
 * Demos Log
 *
 * @category   Demos
 * @package    Demos_Log
 * @author     James.Huang <huangjuanshi@163.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */
 
require_once 'Hush/Log.php';

/**
 * @package Demos_Log
 */
class Demos_Log
{
	/**
	 * @var string
	 */
	private $_logger = null;
	
	/**
	 * Construct 
	 * Init logger instance
	 * @param string $logger Logger type
	 * @return void
	 */
	public function __construct ($logger = 'sys')
	{
		$this->_logger = Hush_Log::getInstance($logger);
	}
	
	/**
	 * Overload logger log interface
	 * @param string $msg Logging message
	 * @return void
	 */
	public function log ($name, $msg)
	{
		$this->_logger->log($name, $msg);
	}
}
