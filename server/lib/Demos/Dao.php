<?php
/**
 * Demos Dao
 *
 * @category   Demos
 * @package    Demos_Dao
 * @author     James.Huang <shagoo@gmail.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */
 
require_once 'Hush/Db/Dao.php';

/**
 * @abstract
 * @package Demos_Dao
 */
class Demos_Dao extends Hush_Db_Dao
{
	/**
	 * Autoload Ihush Daos
	 * 
	 * @param string $dao
	 * @return Demos_Dao
	 */
	public static function load ($class_name)
	{
	    static $_model = array();
	    if(!isset($_model[$class_name])) {
	    	require_once 'Demos/Dao/' . str_replace('_', '/', $class_name) . '.php';
	    	$_model[$class_name] = new $class_name();
	    }
	    return $_model[$class_name];
	}
}