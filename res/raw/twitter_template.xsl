<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:twitter="https://api.twitter.com/">

	<xsl:template match="/">
		<html>
			<head>
				<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
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
					margin-bottom:
					20px;
					padding-top: 1px;
					}

					#main .block
					.content .inner {
					padding: 0
					15px 15px;
					}
					#main {
					width: 99.5%;
					float: left;
					}
					#main .block
					{
					padding-top: 0px;
					}
					#main .block
					.content {
					padding-top: 1px;
					height:92%
					}
					#main
					.block .content .inner {
					padding: 0 15px 15px;
					}
					#main .block
					.content
					h2 {
					margin-left: 15px;
					font-size: 22px;
					text-transform:
					normal;
					letter-spacing: -1px;
					line-height: 1.2em;
					}
					#main .block
					.content p {
					font-size: 13px;
					font-style: normal;
					font-weight:
					normal;
					text-transform: normal;
					letter-spacing: normal;
					line-height: 1.45em;
					}

					.group {
					padding: 3px 3px 3px 3px;
					margin: 3px
					0
					3px;
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
			<body>
				<div id="main">
					<div class="block">
						<xsl:for-each select="rdf:RDF/rdf:Description">
							<xsl:if test="twitter:screen_name">
								<div class="header" id="header"
									onclick="$(this).parent().children('.content').toggle();">
									<h2>
										<xsl:value-of select="twitter:screen_name" />
									</h2>
									<img>
										<xsl:attribute name="src">
        										<xsl:value-of select="twitter:profile_image_url" />
    									</xsl:attribute>
									</img>
								</div>
							</xsl:if>
						</xsl:for-each>
						<div class="content">
							<xsl:for-each select="rdf:RDF/rdf:Description">
								<xsl:if test="twitter:name">
									<div class="group" onclick="$(this).children('.group').toggle();">
										<div class="left">
											<h3>Benutzer</h3>
										</div>
										<div class="group" style="display:none">
											<div class="left">
												<h3>Name:</h3>
											</div>
											<div class="right">
												<xsl:value-of select="twitter:name" />
											</div>
										</div>
										<div class="group" style="display:none">
											<div class="left">
												<h3>Beschreibung:</h3>
											</div>
											<div class="right">
												<xsl:value-of select="twitter:description" />
											</div>
										</div>
										<div class="group" style="display:none">
											<div class="left">
												<h3>Ort:</h3>
											</div>
											<div class="right">
												<xsl:value-of select="twitter:location" />
											</div>
										</div>
										<div class="group" style="display:none">
											<div class="left">
												<h3>Follower:</h3>
											</div>
											<div class="right">
												<xsl:value-of select="twitter:followers_count" />
											</div>
										</div>
										<div class="group" style="display:none">
											<div class="left">
												<h3>Freunde:</h3>
											</div>
											<div class="right">
												<xsl:value-of select="twitter:friends_count" />
											</div>
										</div>
										<div class="group" style="display:none">
											<a href="{twitter:url}">
												<h3>Homepage</h3>
											</a>
										</div>
									</div>
								</xsl:if>
							</xsl:for-each>
							<xsl:for-each select="rdf:RDF/rdf:Description">
							<xsl:if test="twitter:text">
								<div class="group" onclick="$(this).children('.group').toggle();">
									<div class="left">
										<h3>
											<xsl:value-of select="twitter:text" />
										</h3>
									</div>
									<div class="group" style="display:none">
										<div class="left">
											<h3>Quelle:</h3>
										</div>
										<div class="right">
											<xsl:value-of select="twitter:source" />
										</div>
										<div class="left">
											<h3>Retweets:</h3>
										</div>
										<div class="right">
											<xsl:value-of select="twitter:retweet_count" />
										</div>
										<div class="left">
											<h3>Favorites:</h3>
										</div>
										<div class="right">
											<xsl:value-of select="twitter:favorite_count" />
										</div>
									</div>
								</div>
								</xsl:if>
							</xsl:for-each>
						</div>
					</div>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>