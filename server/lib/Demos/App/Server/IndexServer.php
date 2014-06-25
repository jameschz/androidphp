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
class IndexServer extends Demos_App_Server
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
	 * > 接口说明：测试接口
	 * <code>
	 * URL地址：/index/index
	 * 提交方式：POST
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 测试接口
	 * @action /index/index
	 * @method get
	 */
	public function indexAction ()
	{
		$this->doAuth();
		
		// get extra customer info
		$customerDao = $this->dao->load('Core_Customer');
		$customerItem = $customerDao->getById($this->customer['id']);
		$this->render('10000', 'Load index ok', array(
			'Customer' => $customerItem
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：用户登录接口
	 * <code>
	 * URL地址：/index/login
	 * 提交方式：POST
	 * 参数#1：name，类型：STRING，必须：YES，示例：admin
	 * 参数#2：pass，类型：STRING，必须：YES，示例：admin
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 用户登录接口
	 * @action /index/login
	 * @params name james STRING
	 * @params pass james STRING
	 * @method post
	 */
	public function loginAction ()
	{
		// return login customer
		$name = $this->param('name');
		$pass = $this->param('pass');
		if ($name && $pass) {
			$customerDao = $this->dao->load('Core_Customer');
			$customer = $customerDao->doAuth($name, $pass);
			if ($customer) {
				$customer['sid'] = session_id();
				$_SESSION['customer'] = $customer;
				$this->render('10000', 'Login ok', array(
					'Customer' => $customer
				));
			}
		}
		// return sid only for client
		$customer = array('sid' => session_id());
		$this->render('14001', 'Login failed', array(
			'Customer' => $customer
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：用户登出接口
	 * <code>
	 * URL地址：/index/logout
	 * 提交方式：POST
	 * 参数#1：sid，类型：STRING，必须：YES，示例：
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 用户登出接口
	 * @action /index/logout
	 * @method post
	 */
	public function logoutAction ()
	{
		$_SESSION['customer'] = null;
		$this->render('10000', 'Logout ok');
	}
}