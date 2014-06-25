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
class Demos_Util_Url
{
	static public function format ($url)
	{
		$url = parse_url($url);
		$url = $url['path'] . '?sid=' . session_id() . '&' . $url['query'];
		return $url;
	}
}