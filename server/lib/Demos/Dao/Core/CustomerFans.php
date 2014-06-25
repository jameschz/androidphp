<?php
/**
 * Demos Dao
 *
 * @category   Demos
 * @package    Demos_Dao_Core
 * @author     James.Huang <shagoo@gmail.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */

require_once 'Demos/Dao/Core.php';
require_once 'Demos/Util/Image.php';

/**
 * @package Demos_Dao_Core
 */
class Core_CustomerFans extends Demos_Dao_Core
{
	/**
	 * @static
	 */
	const TABLE_NAME = 'customer_fans';
	
	/**
	 * @static
	 */
	const TABLE_PRIM = '';
	
	/**
	 * Initialize
	 */
	public function __init () 
	{
		$this->t1 = self::TABLE_NAME;
		
		$this->_bindTable($this->t1);
	}
	
	/**
	 * Check fans data exists
	 * @param int $customerId
	 * @param int $fansId
	 * @return array
	 */
	public function exist ($customerId, $fansId)
	{
		$sql = $this->select()->from($this->t1, '(1)')
			->where("customerid = ?", $customerId)
			->where("fansid = ?", $fansId);
		
		return $this->dbr()->fetchOne($sql);
	}
	
	/**
	 * Delete fans data
	 * @param int $customerId
	 * @param int $fansId
	 */
	public function delete ($customerId, $fansId) 
	{
		$wheresql = "customerid = $customerId and fansid = $fansId";
		return $this->dbw()->delete($this->t1, $wheresql);
	}
	
	/**
	 * Count fans number
	 * @param int $customerId
	 * @param int $fansId
	 * @return array
	 */
	public function countFans ($customerId)
	{
		$sql = $this->select()->from($this->t1, '(1)')
			->where("customerid = ?", $customerId);
		
		return $this->dbr()->fetchOne($sql);
	}
}
