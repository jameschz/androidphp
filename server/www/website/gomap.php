<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0" />
<title>Simple V3 Map for Android</title>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script>
<script type="text/javascript">
var map;
function initialize() {
	var latitude = 0;
	var longitude = 0;
	if (window.android){
		latitude = window.android.getLatitude();
		longitude = window.android.getLongitude();
	} else {
		latitude = 31.235087;
		longitude = 121.506656;
	}
	var myLatlng = new google.maps.LatLng(latitude,longitude);
	var myOptions = {
		zoom: 13,
		center: myLatlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	}
	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
}
function centerAt(latitude, longitude){
	myLatlng = new google.maps.LatLng(latitude,longitude);
	map.panTo(myLatlng);
}
</script>
</head>
<body style="margin:0px; padding:0px;" onload="initialize()">
  <div id="map_canvas" style="width:100%; height:100%"></div>
</body>
</html>