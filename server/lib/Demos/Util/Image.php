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
class Demos_Util_Image
{
	/**
	 * 获取头像图片的URL地址
	 * @param int $id
	 */
	public static function getFaceUrl ($id) 
	{
		$facePath = __HOST_WEBSITE . '/faces/default';
		return $facePath . '/face_' . $id . '.png';
	}
	
	/**
	 * 获取头像图片的对象
	 * @param int $id
	 */
	public static function getFaceImage ($id) 
	{
		return array(
			'id' => $id,
			'url' => self::getFaceUrl($id),
			'type' => 'png',
		);
	}
}