<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/">
		<html>
			<head>
				<title>Dynamische Karte</title>
				<meta name="viewport" content="width-device-width, initial-sclae-1.0, user-scalable-no" />
				<style type="text/css">
					html {height: 70%}
					body {height: 70%; margin: 0; padding: 0}
					#map_canvas {height: 100%}
				</style>
				<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAM8vKFTbW7cNf5N6fHXXWhRYR79TxBXis&amp;sensor=false"></script>
				<script type="text/javascript">
					
					var map;
					function initialize() {
						var mapOptions = {
							zoom: 15,
							center: new google.maps.LatLng(<xsl:value-of select="//geometry/location/lat"/>, <xsl:value-of select="//geometry/location/lng"/>),
							mapTypeId: google.maps.MapTypeId.ROADMAP
						};
						map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);
						var pos = new google.maps.LatLng(<xsl:value-of select="//geometry/location/lat"/>, <xsl:value-of select="//geometry/location/lng"/>);
						var marker = new google.maps.Marker({
							position:pos,
							map: map,
							title: '<xsl:value-of select="//result/formatted_address" />'
						});
					}
					
					google.maps.event.addDomlistener(window, 'load', initialize);
					
				</script>
			</head>
			<body onload="initialize()">
				<h1>Dynamische Karte</h1>
				<div id="map_canvas" style="width:15%; heigt:100%"/>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>