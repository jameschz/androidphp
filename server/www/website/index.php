<?php 
require_once '../../etc/app.config.php';
require_once 'Hush/App.php';

// redirect to jquery.mobile demos
//Hush_Util::jsRedirect('/demos/index.html');
?>
<a href="javascript:location.reload();">Reload</a><br/>
<hr/>
<a href="javascript:alert('Test Alert');">Test Alert</a><br/>
<a href="javascript:demo.testCallBack('Test Callback');">Test Callback</a><br/>
<a href="./demos/index.html">JQuery Mobile</a><br/>
<a href="./gomap.php">Map Demo</a>