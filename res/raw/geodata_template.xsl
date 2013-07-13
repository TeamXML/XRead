<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:address="http://linkedgeodata.org/ontology/addr%3" 
	xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#">
	<xsl:output method="html" encoding="UTF-8" />
	<xsl:template match="/">
		<html>
			<head>
				<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>

				<script type="text/javascript"
					src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAM8vKFTbW7cNf5N6fHXXWhRYR79TxBXis&amp;sensor=false"></script>
				<script language="javascript" type="text/javascript">
					var map;
					function initialize() {
					var mapOptions = {
					zoom: 15,
					center: new google.maps.LatLng(
					<xsl:value-of select="//rdf:Description/geo:lat" />, <xsl:value-of select="//rdf:Description/geo:long"/>
					,
					13.2972926
					),
					mapTypeId: google.maps.MapTypeId.ROADMAP
					};
					map = new google.maps.Map(document.getElementById('map_canvas'),mapOptions);
					var pos = new google.maps.LatLng(
					<xsl:value-of select="//rdf:Description/geo:lat" />, <xsl:value-of select="//rdf:Description/geo:long"/>
					);
					var marker = new google.maps.Marker({
					position:pos,
					map: map,
					title: 'Ihre Auswahl'
					});
					}
				</script>
				<style type="text/css">
					body {
					font-size: 12px;
					color: #111111;
					background:
					#c4c4c4;
					font-family: helvetica, arial, sans-serif;
					}

					h1, h2, h3 {
					color: #111111;
					font-weight: normal;
					font-family:
					helvetica, arial,
					sans-serif;
					font-weight: bold;
					}

					a:link,
					a:visited,
					a:hover, a:active {
					color: #111111;
					}

					a {
					-moz-outline:
					none;
					}

					hr {
					background: #dddddd;
					color: #dddddd;
					}

					#header {
					text-align: center;
					padding: 5px 0 5px 0;
					background-color:
					#333333;
					text-shadow: 0 1px 1px rgba(0, 0, 0,
					0.25);
					box-shadow:
					inset 0 -5px 10px rgba(0, 0, 0, 0.25), 0 1px
					1px
					rgba(255, 255,
					255, 0.3);
					-moz-box-shadow: inset 0 -5px 10px
					rgba(0,
					0, 0, 0.25),
					0 1px 1px rgba(255, 255,
					255, 0.3);
					-webkit-box-shadow:
					inset 0
					-5px 10px rgba(0, 0, 0, 0.25), 0 1px
					1px rgba(255, 255,
					255,
					0.3);
					}
					#header h2 {
					font-size: 20px;
					font-style: normal;
					text-transform:
					normal;
					letter-spacing: -1px;
					line-height: 1.2em;
					color: white;
					}
					#header h2 a:link, #header h1 a:active, #header h1
					a:hover,
					#header h2
					a:visited {
					color: white;
					}

					#main .block {
					margin-bottom: 5px;
					padding-top: 1px;
					}

					#main {
					width: 99.5%;
					float: left;
					}
					#main .block
					{
					padding-top: 0px;
					}
					#main .block .content {
					padding-top: 1px;
					height:92%;
					overflow:hidden;
					}
					#main .block .content h2 {
					margin-left: 15px;
					font-size: 22px;
					text-transform:
					normal;
					letter-spacing: -1px;
					line-height: 1.2em;
					}

					.group {
					padding: 3px 3px 3px 3px;
					margin: 3px
					0 3px;
					background-color:
					white;
					text-shadow: 0 1px 0
					white;
					-moz-border-radius: 6px;
					-webkit-border-radius: 6px;
					border-radius:
					6px;
					box-shadow: 0 0 1px
					rgba(0, 0, 0, 0.3), 0 1px
					1px rgba(0, 0, 0,
					0.3);
					-moz-box-shadow: 0 0 1px rgba(0, 0, 0,
					0.3), 0 1px 1px rgba(0,
					0,
					0, 0.3);
					-webkit-box-shadow: 0 0 1px
					rgba(0, 0, 0, 0.3), 0 1px 1px
					rgba(0, 0, 0, 0.3);
					}

					div.left {
					width:100%;
					padding: 3px 3px 3px 3px;
					cursor: pointer;
					}

					div.right
					{
					margin: 0 10px 0 5px;
					padding: 3px 3px
					3px 3px;
					width:100%;
					}
				</style>
			</head>
			<body onload="initialize()">
				<div id="main">
					<xsl:for-each select="rdf:RDF/rdf:Description">
						<div class="block">
							<div class="header" id="header" onclick="$(this).parent().children('.content').toggle();">
								<h2>
									<xsl:value-of select="rdf:label" />
								</h2>
							</div>
							<div class="content">
								<div class="group">
									<div id="map_canvas" style="width:100%; height:100%"/>
								</div>
							</div>
							<div class="content" style="display:none">
								<xsl:if test="address:Astreet">
									<div class="group">
										<div class="left">
											<h3>Straße:</h3>
										</div>
										<div class="right">
											<xsl:value-of select="address:Astreet" />
										</div>
									</div>
								</xsl:if>
								<xsl:if test="address:Ahousenumber">
									<div class="group">
										<div class="left">
											<h3>Hausnummer:</h3>
										</div>
										<div class="right">
											<xsl:value-of select="address:Ahousenumber" />
										</div>
									</div>
								</xsl:if>
								<xsl:if test="address:postcode">
									<div class="group">
										<div class="left">
											<h3>PLZ:</h3>
										</div>
										<div class="right">
											<xsl:value-of select="address:postcode" />
										</div>
									</div>
								</xsl:if>
								<xsl:if test="address:Acity">
									<div class="group">
										<div class="left">
											<h3>Stadt:</h3>
										</div>
										<div class="right">
											<xsl:value-of select="address:Acity" />
										</div>
									</div>
								</xsl:if>
								<xsl:if test="address:Acountry">
									<div class="group">
										<div class="left">
											<h3>Land:</h3>
										</div>
										<div class="right">
											<xsl:value-of select="address:Acountry" />
										</div>
									</div>
								</xsl:if>
								<xsl:if test="geo:lat">
									<div class="group">
										<div class="left">
											<h3>Breitengrad:</h3>
										</div>
										<div class="right">
											<xsl:value-of select="geo:lat" />
										</div>
									</div>
								</xsl:if>
								<xsl:if test="geo:long">
									<div class="group">
										<div class="left">
											<h3>Längengrad:</h3>
										</div>
										<div class="right">
											<xsl:value-of select="geo:long" />
										</div>
									</div>
								</xsl:if>
							</div>
						</div>
					</xsl:for-each>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>