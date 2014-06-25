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
class Core_Notice extends Demos_Dao_Core
{
	/**
	 * @static
	 */
	const TABLE_NAME = 'notice';
	
	/**
	 * @static
	 */
	const TABLE_PRIM = 'id';
	
	/**
	 * Initialize
	 */
	public function __init () 
	{
		$this->t1 = self::TABLE_NAME;
		$this->k1 = self::TABLE_PRIM;
		
		$this->_bindTable($this->t1, $this->k1);
	}
	
	/**
	 * Set notice read
	 * @param int $customerId
	 */
	public function setRead ($customerId) {
		$sql = $this->select()->from($this->t1, '*')
			->where("customerid = ?", $customerId)
			->where("status = 0");
		
		$row = $this->dbr()->fetchRow($sql);
		if ($row) {
			$this->update(array(
				'id'		=> intval($row['id']),
				'status'	=> 1
			));
		}
	}
	
	/**
	 * Add fanscount by one
	 * @param int $customerId
	 */
	public function addFanscount ($customerId, $addCount = 1)
	{
		$sql = $this->select()->from($this->t1, '*')
			->where("customerid = ?", $customerId)
			->where("status = 0");
		
		$row = $this->dbr()->fetchRow($sql);
		// update
		if ($row) {
			$fanscount = intval($row['fanscount']) + $addCount;
			$this->update(array(
				'id'		=> intval($row['id']),
				'fanscount'	=> $fanscount
			));
		// insert
		} else {
			$this->create(array(
				'customerid'	=> $customerId,
				'fanscount'		=> 1
			));
		}
	}
	
	/**
	 * Get notification list
	 * @param int $customerId
	 * @return array
	 */
	public function getByCustomer ($customerId)
	{
		$sql = $this->select()->from($this->t1, '*')
			->where("customerid = ?", $customerId)
			->where("status = 0");
		
		$row = $this->dbr()->fetchRow($sql);
		$msg = trim($row['message']);
		// when message is empty 
		if (strlen($msg) > 0) {
			return $row;
		}
		// when has new fans
		$fans = intval($row['fanscount']);
		if ($fans > 0) {
			$row['message'] = L('cn', 'notice', $row['fanscount']);
			return $row;
		}
		// return null
		return null;
	}
}
