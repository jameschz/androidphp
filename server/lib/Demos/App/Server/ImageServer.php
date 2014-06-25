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
require_once 'Demos/Util/Image.php';

/**
 * @package Demos_App_Server
 */
class ImageServer extends Demos_App_Server
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
		
		// get image host
		$this->imageHost = __HOST_WEBSITE;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// service api methods
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：查看用户头像接口
	 * <code>
	 * URL地址：/image/faceView
	 * 提交方式：GET
	 * 参数#1：faceId，类型：STRING，必须：YES
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 查看用户头像接口
	 * @action /image/faceView
	 * @params faceId 0 STRING
	 * @method get
	 */
	public function faceViewAction ()
	{
		$faceIdStr = $this->param('faceId');
		$faceIdArr = $faceIdStr ? explode(',', $this->param('faceId')) : array();
		$faceCount = count($faceIdArr);
		if ($faceCount == 1) {
			$faceId = intval($faceIdArr[0]);
			$faceItem = Demos_Util_Image::getFaceImage($faceId);
			$this->render('10000', 'Get face ok', array(
				'Image' => $faceItem
			));
		} elseif ($faceCount > 1) {
			$faceList = array();
			foreach ($faceIdArr as $faceId) {
				$faceList[] = Demos_Util_Image::getFaceImage($faceId);
			}
			$this->render('10000', 'Get face list ok', array(
				'Image.list' => $faceList
			));
		} else {
			$this->render('14012', 'Get face failed');
		}
	}
	
	/**
	 * ---------------------------------------------------------------------------------------------
	 * > 接口说明：头像列表接口
	 * <code>
	 * URL地址：/image/faceList
	 * 提交方式：GET
	 * </code>
	 * ---------------------------------------------------------------------------------------------
	 * @title 头像列表接口
	 * @action /image/faceList
	 * @method get
	 */
	public function faceListAction ()
	{
		// valid face ids
		$faceIdArr = range(0,14);
		// get face images
		$faceList = array();
		foreach ($faceIdArr as $faceId) {
			$faceList[] = Demos_Util_Image::getFaceImage($faceId);
		}
		$this->render('10000', 'Get face list ok', array(
			'Image.list' => $faceList
		));
	}
}