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
 
require_once 'Demos/Dao.php';

/**
 * @package Demos_Dao
 */
class Demos_Dao_Core extends Demos_Dao
{
	/**
	 * @static
	 */
	const DB_NAME = 'demos_core';
	
	/**
	 * Construct
	 */
	public function __construct ()
	{
		// initialize dao
		parent::__construct(MysqlConfig::getInstance());
		
		// set default dao settings
		$this->_bindDb(self::DB_NAME);
	}
}