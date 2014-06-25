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
class DebugServer extends Demos_App_Server
{
	public function __init ()
	{
		parent::__init();
		
		// init memu url list
		$this->index = '/debug/';
		$this->apiAuth = '/debug/auth';
		$this->apiQuit = '/debug/quit'; 
		$this->apiHome = '/debug/apiHome';
		$this->apiList = '/debug/apiList';
		$this->apiStat = '/debug/apiStat';
		
		// Get service action's annotation list
		$this->serviceConfigList = $this->_getServiceConfigList();
		
		$this->_printHead();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// public methods
	
	public function authAction ()
	{
		$username = $this->param('username');
		$password = $this->param('password');
		if ($username && $password) {
			if ($username == 'admin' && $password == 'admin') {
				$this->admin['sid'] = session_id();
				$this->admin['name'] = $username;
				$_SESSION['admin'] = $this->admin;
				$this->forward($this->apiHome);
			}
		}
		
		echo "<form method='post'>";
		echo "<table class='tbmin' cellpadding=0 cellspacing=0>\n";
		echo "<tr><td>Username</td><td>:</td><td><input name='username' type='text' /></td></tr>";
		echo "<tr><td>Password</td><td>:</td><td><input name='password' type='password' /></td></tr>";
		echo "<tr><td colspan=3><input type='submit' value='登录' /></td></tr>";
		echo "</table>\n";
		echo "</form>\n";
	}
	
	public function quitAction ()
	{
		$_SESSION['admin'] = null;
		$this->forward($this->index);
	}
	
	public function indexAction () 
	{
		$this->_printHome();
		
		echo "&gt; <a href='/debug/apiHome'>Debug Console</a><br/>\n";
		echo "&gt; <a href='/doc/api/index.html'>Api Document</a><br/>\n";
		echo "&gt; <a href='/doc/lib/index.html'>Lib Document</a><br/>\n";
	}
	
	public function apiHomeAction () 
	{
		$this->doAuthAdmin();
		$this->_printMenu();
		
		echo "<table class='tbcom' cellpadding=0 cellspacing=0>\n";
		echo "<tr><td style='width:80px;'>&gt; Api Test</td><td style='width:10px;'>:</td><td>实时接口测试</td></tr>";
		echo "<tr><td style='width:80px;'>&gt; Api Stat</td><td style='width:10px;'>:</td><td>接口访问统计</td></tr>";
		echo "</table>\n";
		echo "<hr/>\n";
		
		echo "<b>Welcome <font color=red>{$this->admin['name']}</font></b>";
	}
	
	public function apiStatAction ()
	{
		$this->doAuthAdmin();
		$this->_printMenu();
		
		$html = "<table class='tbfix' cellpadding=1 cellspacing=1>\n";
		foreach ((array) $this->serviceConfigList as $serviceName => $actionList) {
			$html .= "<tr><td class='title' colspan=4>{$serviceName}</td></tr>\n";
			foreach ((array) $actionList as $actionName => $actionConfig) {
				$actionKey = "$serviceName::$actionName";
				$visit = 0; // count visit count
				$runtime = 0; // count average visit runtime
				$html .= "<tr><td>{$actionName}</td><td>接口地址：{$actionConfig['action']}</td><td>访问次数：{$visit}</td><td>平均响应时间：{$runtime}</td></tr>\n";
			}
		}
		$html .= "</table>\n";
		echo $html;
	}
	
	public function apiListAction ()
	{
		$this->doAuthAdmin();
		$this->_printMenu();
		
		$html = "<table class='tbfix' cellpadding=1 cellspacing=1>\n";
		foreach ((array) $this->serviceConfigList as $serviceName => $actionList) {
			$html .= "<tr><td class='title' colspan=4>{$serviceName}</td></tr>\n";
			foreach ((array) $actionList as $actionName => $actionConfig) {
				$html .= "<tr><td>{$actionName}</td><td>{$actionConfig['title']}</td><td>{$actionConfig['action']}</td><td><a href='apiTest?serviceName={$serviceName}&actionName={$actionName}'>测试</a></td></tr>\n";
			}
		}
		$html .= "</table>\n";
		echo $html;
	}
	
	public function apiTestAction ()
	{
		$this->doAuthAdmin();
		$this->_printMenu();
		
		echo "<script type='text/javascript' src='/js/debug/apiTest.js'></script>\n";
		echo "<script type='text/javascript'>\n";
		echo "$(document).ready(function(){";
		echo "var header={};";
		echo "$('.doTest').click(function(){apiTest(header)});";
		echo "});\n";
		echo "</script>\n";
		
		$serviceName = $this->param('serviceName');
		$actionName = $this->param('actionName');
		$configList = $this->serviceConfigList[$serviceName][$actionName];
		if (!$configList) {
			echo "Error : can not found '$serviceName::$actionName'.\n";
			exit;
		}
		
		// append sid
		$configList['action'] = $this->url->format($configList['action']);
		
		$action = $configList['action'];
		$method = $configList['method'];
		$html = "<input type='hidden' id='action' value='{$action}'/>\n";
		$html .= "<input type='hidden' id='method' value='{$method}'/>\n";
		$html .= "<table class='tbcom' cellpadding=1 cellspacing=1>\n";
		$html .= "<tr><td class='title' colspan=2>{$serviceName} > {$actionName}</td></tr>\n";
		foreach ((array) $configList as $configKey => $configVal) {
			// action params
			if (is_array($configVal)) {
				$html .= "<tr><td>Test Data</td><td><table>\n";
				foreach ((array) $configVal as $paramName => $paramData) {
					$paramDval = $paramData['dval']; // default value
					$paramDesc = $paramData['desc']; // description
					$html .= "  <tr><td>KEY : <input type='text' name='paramKey' value='{$paramName}'/> VALUE : <input type='text' name='paramVal' style='width:300px' value='$paramDval'/> ({$paramDesc}) </td></tr>\n";
				}
				$html .= "</table></td></tr>\n";
			// action attr
			} else {
				$html .= "<tr><td class='left'>{$configKey}</td><td>{$configVal}</td></tr>\n";
			}
		}
		$html .= "<tr><td class='left'>Test Submit</td><td><input type='button' class='doTest' value='提交测试'/></td></tr>\n";
		$html .= "<tr><td class='left'>Test Result</td><td><textarea id='result'></textarea></td></tr>\n";
		$html .= "</table>\n";
		echo $html;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// protected methods
	
	protected function _printHome ()
	{
		echo "<table class='tbmin' cellpadding=0 cellspacing=0>\n";
		echo "<tr><td>VISITOR IP</td><td>:</td><td>" . $_SERVER['REMOTE_ADDR'] . "</td></tr>\n";
		echo "</table>\n";
		echo "<hr/>\n";
	}
	
	protected function _printMenu ()
	{
		echo "<a href='{$this->apiHome}'>Home</a>\n";
		echo "| <a href='{$this->apiList}'>Api Test</a>\n";
		echo "| <a href='{$this->apiStat}'>Api Stat</a>\n";
		echo "| <a href='{$this->apiQuit}'>Logout</a>\n";
		echo "<hr/>\n";
	}
	
	protected function _printHead ()
	{
		header("Content-type: text/html; charset=utf-8");
		
		echo "<style>\n";
		echo "table {margin:0px; padding:0px;}\n";
		echo "table.tbfix {width:100%; table-layout:fixed; background:#bbb}\n";
		echo "table.tbcom {width:100%; background:#bbb}\n";
		echo "td {padding:3px; background:#fff}\n";
		echo "td.title {background:#eee}\n";
		echo "td.left {width:200px}\n";
		echo "input.button {width:100px}\n";
		echo "textarea#result {width:100%; height:300px; background:#ffffe0}";
		echo "</style>\n";
		
		echo "<script type='text/javascript' src='/js/jquery.js'></script>\n";
		echo "<script type='text/javascript' src='/js/app.util.js'></script>\n";
		
		echo "<h2>" . ucfirst(__APP_NAME) . " Debug Server - v" . __APP_VERSION . "</h2>\n";
		echo "<hr/>\n";
	}
	
	protected function _getServiceConfigList ()
	{
		require_once 'Hush/Document.php';
		$serviceConfigList = array();
		foreach (glob(__LIB_PATH_SERVER . '/*.php') as $classFile) {
			$className = basename($classFile, '.php');
			if ($classFile && $className) {
				require_once $classFile;
				$rClass = new ReflectionClass($className);
				$methodList = $rClass->getMethods();
				$doc = new Hush_Document($classFile);
				foreach ($methodList as $method) {
					$config = $doc->getAnnotation($className, $method->name);
					if ($config && preg_match('/Action$/', $method->name)) {
						$serviceConfigList[$className][$method->name] = $config;
					}
				}
			}
		}
//		Hush_Util::dump($serviceConfigList);exit;
		return $serviceConfigList;
	}

}