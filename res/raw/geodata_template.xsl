<?xml version="1.0"?> 
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#"
	xmlns:plz="http://linkedgeodata.org/ontology/addr:"
	xmlns:addr="http://linkedgeodata.org/ontology/addr%3"
	xmlns:phone="http://linkedgeodata.org/ontology/contact%3">

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
	   							<xsl:for-each select="result/address_component[type[text() = 'route' and position() = 1]]">
	   								<xsl:value-of select="short_name"/>
								</xsl:for-each>&#160;<xsl:for-each select="result/address_component[type[text() = 'street_number' and position() = 1]]">
	   								<xsl:value-of select="short_name"/>
								</xsl:for-each>
							</td>
						</tr>
						<tr>
	   						<td><b>PLZ und Ort&#160;</b></td>
	   						<td>
	   						<xsl:for-each select="result/address_component[type[text() = 'postal_code' and position() = 1]]">
	   							<xsl:value-of select="short_name"/>
							</xsl:for-each>&#160;<xsl:for-each select="result/address_component[type[text() = 'locality' and position() = 1]]">
	   							<xsl:value-of select="short_name"/>
							</xsl:for-each>
							</td>
						</tr>
				</table>
				<br/>
				<div id="map_canvas" style="width:100%; height:100%"/>
			</body>
		</html>
	</xsl:template>
	
	
	<xsl:template match="rdf:RDF">
		<html>
			<head>
				<title>Dynamische Karte</title>
				<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAM8vKFTbW7cNf5N6fHXXWhRYR79TxBXis&amp;sensor=false"></script>
				<script type="text/javascript">
					
					var map;
					function initialize() {
						var mapOptions = {
							zoom: 15,
							center: new google.maps.LatLng(<xsl:value-of select="//rdf:Description/geo:lat" />, <xsl:value-of select="//rdf:Description/geo:long" />),
							mapTypeId: google.maps.MapTypeId.ROADMAP
						};
						map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);
						var pos = new google.maps.LatLng(<xsl:value-of select="//rdf:Description/geo:lat" />, <xsl:value-of select="//rdf:Description/geo:long" />);
						var marker = new google.maps.Marker({
							position:pos,
							map: map,
							title: '<xsl:value-of select="//rdf:Description/addr:Astreet" /> <xsl:value-of select="//rdf:Description/addr:Ahousenumber" />'
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
	   							<xsl:value-of select="//rdf:Description/addr:Astreet" />&#160;<xsl:value-of select="//rdf:Description/addr:Ahousenumber" />
							</td>
						</tr>
	 					<tr>
	   						<td><b>PLZ und Ort&#160;</b></td>
	   						<td>
	   						<xsl:value-of select="rdf:Description/plz:postcode" />&#160;<xsl:value-of select="rdf:Description/addr:Acity" />
							</td>
						</tr>
						<tr>
	   						<td><b>Telefon&#160;</b></td>
	   						<td>
	   						<xsl:value-of select="rdf:Description/phone:Aphone" />
							</td>
						</tr>
				</table>
				<br/>
				<div id="map_canvas" style="width:100%; height:100%"/>
			</body>
		</html>
	</xsl:template>
	
</xsl:stylesheet>