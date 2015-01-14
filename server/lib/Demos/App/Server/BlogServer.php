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
class BlogServer extends Demos_App_Server
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
	 * > 接口说明：微博列表接口
	 * <code>
	 * URL地址：/blog/blogList
	 * 提交方式：GET
	 * 参数#1：typeId，类型：INT，必须：YES
	 * 参数#2：pageId，类型：INT，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 微博列表接口
	 * @action /blog/blogList
	 * @params typeId 0 0：全部，1：自己，2：关注
	 * @params pageId 0 INT
	 * @method get
	 */
	public function blogListAction ()
	{
		$this->doAuth();
		
		$typeId = intval($this->param('typeId'));
		$pageId = intval($this->param('pageId'));
		
		$blogList = array();
		switch ($typeId) {
			case 0:
				$blogDao = $this->dao->load('Core_Blog');
				$blogList = $blogDao->getListByPage($pageId);
				break;
			case 1:
				$blogDao = $this->dao->load('Core_Blog');
				$blogList = $blogDao->getListByCustomer($this->customer['id'], $pageId);
				break;
			case 2:
				break;
		}
		if ($blogList) {
			foreach ($blogList as &$row) {
				if (strlen($row['picture']) > 0) {
					$row['picture'] = __PICTURE_URL . $row['picture'];
				}
			}
			$this->render('10000', 'Get blog list ok', array(
				'Blog.list' => $blogList
			));
		}
		$this->render('14008', 'Get blog list failed');
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：查看微博正文接口
	 * <code>
	 * URL地址：/blog/blogView
	 * 提交方式：POST
	 * 参数#1：blogId，类型：INT，必须：YES，示例：1
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 查看微博正文接口
	 * @action /blog/blogView
	 * @params blogId 1 INT
	 * @method post
	 */
	public function blogViewAction ()
	{
		$this->doAuth();
		
		$blogId = intval($this->param('blogId'));
		
		$blogDao = $this->dao->load('Core_Blog');
		$blogItem = $blogDao->read($blogId);
		if ($blogItem) {
			$blogItem['picture'] = __PICTURE_URL . $blogItem['picture'];
		}
		
		$customerDao = $this->dao->load('Core_Customer');
		$customerItem = $customerDao->getById($blogItem['customerid']);
		
		$this->render('10000', 'Get blog ok', array(
			'Customer' => $customerItem,
			'Blog' => $blogItem
		));
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：发表微博接口
	 * <code>
	 * URL地址：/blog/blogCreate
	 * 提交方式：POST
	 * 参数#1：content，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 发表微博接口
	 * @action /blog/blogCreate
	 * @params content '' STRING
	 * @method post
	 */
	public function blogCreateAction ()
	{
		$this->doAuth();
		
		$content = $this->param('content');
		
		if ($content) {
			// upload pic logic
			$upload_file_url = '';
			$upload_err = $_FILES['file0']['error'];
			$upload_file = $_FILES['file0']['tmp_name'];
			$upload_file_name = $_FILES['file0']['name'];
			if ($upload_file_name) {
				$upload_file_ext = pathinfo($upload_file_name, PATHINFO_EXTENSION);
				if ($upload_err == 0) {
					$upload_face_dir = __PICTURE_DIR . '/';
					$upload_file_name = md5(time().rand(123456,999999));
					$upload_file_path = $upload_face_dir . $upload_file_name . '.' . $upload_file_ext;
					if (!move_uploaded_file($upload_file, $upload_file_path)) {
						$this->render('14010', 'Create blog failed');
					} else {
						$upload_file_url = $upload_file_name . '.' . $upload_file_ext;
					}
				} else {
					$this->render('14011', 'Create blog failed');
				}
			}
			// create 
			$blogDao = $this->dao->load('Core_Blog');
			$blogDao->create(array(
				'customerid'	=> $this->customer['id'],
				'desc'			=> '',
				'title'			=> '',
				'content'		=> $content,
				'picture'		=> $upload_file_url,
				'commentcount'	=> 0
			));
			// add customer blogcount
			$customerDao = $this->dao->load('Core_Customer');
			$customerDao->addBlogcount($this->customer['id']);
			$this->render('10000', 'Create blog ok');
		}
		$this->render('14009', 'Create blog failed');
	}
}