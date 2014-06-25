<?php
/**
 * Demos App
 *
 * @category   Demos
 * @package    Demos_App_Server
 * @author     James.Huang <huangjuanshi@163.com>
 * @license    http://www.apache.org/licenses/LICENSE-2.0
 * @version    $Id$
 */

require_once 'Demos/App/Server.php';

/**
 * @package Demos_App_Server
 */
class CustomerServer extends Demos_App_Server
{
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 全局设置：
	 * <code>
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 */
	public function __init ()
	{
		parent::__init();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// service api methods
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：用户列表接口
	 * <code>
	 * URL地址：/customer/customerList
	 * 提交方式：GET
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 用户列表接口
	 * @action /customer/customerList
	 * @method get
	 */
	public function customerListAction ()
	{
		$this->doAuth();
		
		$customerDao = $this->dao->load('Core_Customer');
		$customerList = $customerDao->getListByPage();
		$this->render('10000', 'Get customer list ok', array(
			'Customer.list' => $customerList
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：查看用户信息接口
	 * <code>
	 * URL地址：/customer/customerView
	 * 提交方式：POST
	 * 参数#1：customerId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 查看用户信息接口
	 * @action /customer/customerView
	 * @params customerId 1 INT
	 * @method post
	 */
	public function customerViewAction ()
	{
		$this->doAuth();
		
		$customerId = $this->param('customerId');
		
		// get extra customer info
		$customerDao = $this->dao->load('Core_Customer');
		if ($customerDao->exist($customerId)) {
			$customerItem = $customerDao->getById($customerId);
			$this->render('10000', 'View customer ok', array(
				'Customer' => $customerItem
			));
		}
		$this->render('14002', 'View customer failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：更新用户信息接口
	 * <code>
	 * URL地址：/customer/customerEdit
	 * 提交方式：POST
	 * 参数#1：key，类型：STRING，必须：YES
	 * 参数#2：val，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 更新用户信息接口
	 * @action /customer/customerEdit
	 * @params key '' STRING
	 * @params val '' STRING
	 * @method post
	 */
	public function customerEditAction ()
	{
		$this->doAuth();
		
		$key = $this->param('key');
		$val = $this->param('val');
		if ($key) {
			$customerDao = $this->dao->load('Core_Customer');
			try {
				$customerDao->update(array(
					'id'	=> $this->customer['id'],
					$key	=> $val,
				));
			} catch (Exception $e) {
				$this->render('14003', 'Update customer failed');
			}
			$this->render('10000', 'Update customer ok');
		}
		$this->render('14004', 'Update customer failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：新建用户接口
	 * <code>
	 * URL地址：/customer/customerCreate
	 * 提交方式：POST
	 * 参数#1：name，类型：STRING，必须：YES
	 * 参数#2：pass，类型：STRING，必须：YES
	 * 参数#3：sign，类型：STRING，必须：YES
	 * 参数#4：face，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 新建用户接口
	 * @action /customer/customerCreate
	 * @params name '' STRING
	 * @params pass '' STRING
	 * @params sign '' STRING
	 * @params face '0' STRING
	 * @method post
	 */
	public function customerCreateAction ()
	{
//		$this->doAuth();
		
		$name = $this->param('name');
		$pass = $this->param('pass');
		$sign = $this->param('sign');
		$face = $this->param('face');
		if ($name && $pass && $sign && $face) {
			$customerDao = $this->dao->load('Core_Customer');
			$customerDao->create(array(
				'name'	=> $name,
				'pass'	=> $pass,
				'sign'	=> $sign,
				'face'	=> $face
			));
			$this->render('10000', 'Create customer ok');
		}
		$this->render('14005', 'Create customer failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：添加粉丝接口
	 * <code>
	 * URL地址：/customer/fansAdd
	 * 提交方式：POST
	 * 参数#1：customerId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 添加粉丝接口
	 * @action /customer/fansAdd
	 * @params customerId '' INT
	 * @method post
	 */
	public function fansAddAction ()
	{
		$this->doAuth();
		
		$customerId = $this->param('customerId');
		if ($customerId) {
			$fansDao = $this->dao->load('Core_CustomerFans');
			if (!$fansDao->exist($customerId, $this->customer['id'])) {
				$fansDao->create(array(
					'customerid'	=> $customerId,
					'fansid'		=> $this->customer['id']
				));
				// add customer blogcount
				$customerDao = $this->dao->load('Core_Customer');
				$customerDao->addFanscount($customerId);
				// add into notice
				$noticeDao = $this->dao->load('Core_Notice');
				$noticeDao->addFanscount($customerId);
				$this->render('10000', 'Add fans ok');
			}
		}
		$this->render('14006', 'Add fans failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：删除粉丝接口
	 * <code>
	 * URL地址：/customer/fansDel
	 * 提交方式：POST
	 * 参数#1：customerId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 删除粉丝接口
	 * @action /customer/fansDel
	 * @params customerId '' INT
	 * @method post
	 */
	public function fansDelAction ()
	{
		$this->doAuth();
		
		$customerId = $this->param('customerId');
		if ($customerId) {
			$fansDao = $this->dao->load('Core_CustomerFans');
			if ($fansDao->exist($customerId, $this->customer['id'])) {
				$fansDao->delete($customerId, $this->customer['id']);
				$this->render('10000', 'Delete fans ok');
			}
		}
		$this->render('14007', 'Delete fans failed');
	}
}