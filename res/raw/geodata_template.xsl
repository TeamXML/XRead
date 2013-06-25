<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="GeocodeResponse">
		<html>
			<head>
				<title>Dynamische Karte</title>
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
				<h3>Deine Karte</h3>
				<table border="0">
	 					<tr>
	   						<td><b>Strasse</b></td>
	   						<td>
	   							<xsl:for-each select="result/address_component">
	   								<xsl:if test="type[text() = 'route']"><xsl:value-of select="short_name"/></xsl:if>
								</xsl:for-each>
							</td>
							<td>
	   							<xsl:for-each select="result/address_component">
	   								<xsl:if test="type[text() = 'street_number']"><xsl:value-of select="short_name"/></xsl:if>
								</xsl:for-each>
							</td>
						</tr>
	 					<tr>
	   						<td><b>PLZ und Ort</b></td>
	   						<td align="right">
	   						<xsl:for-each select="result/address_component">
	   							<xsl:if test="type[text() = 'postal_code']"><xsl:value-of select="short_name"/></xsl:if>
							</xsl:for-each>
							</td>
							<td>
	   						<xsl:for-each select="result/address_component">
	   							<xsl:if test="type[text() = 'locality']"><xsl:value-of select="short_name"/></xsl:if>
							</xsl:for-each>
							</td>
						</tr>
				</table>
				<br/>
				<div id="map_canvas" style="width:100%; height:100%"/>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>