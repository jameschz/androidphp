<?php
/**
 * Demos Util
 *
 * @category   Demos
 * @package    Demos_Util
 * @author     James.Huang <huangjuanshi@163.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */

/**
 * @package Demos_Util
 */
class Demos_Util_Session
{
	public function __construct() 
	{
		$sid = $_SERVER['HTTP_SID'] ? $_SERVER['HTTP_SID'] : @$_REQUEST['sid'];
		if ($sid) session_id($sid);
		self::autoStart();
	}
	
	static public function autoStart()
	{
		session_start();
		if (!session_id()) {
			Hush_Util::headerRedirect(Demos_Util_Url::format($_SERVER['REQUEST_URI']));
		}
	}
}