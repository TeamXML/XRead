<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:owl="http://www.w3.org/2002/07/owl#"
	xmlns:dcterms="http://purl.org/dc/terms/" xmlns:dbpedia-owl="http://dbpedia.org/ontology/"
	xmlns:foaf="http://xmlns.com/foaf/0.1/" xmlns:dbpprop="http://dbpedia.org/property/"
	xmlns:ns7="http://www.w3.org/ns/prov#">
	<xsl:output method="text/html" encoding="UTF-8" />

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when test="rdf:RDF/*[2]">
				<html>
					<head>
						<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
						<script language="javascript" type="text/javascript">

							function toggleGroupOnClick(element){
							event.stopPropagation();
							$(element).children(".right").toggle();
							}
							function toggleGroupsOnClick(element){
							event.stopPropagation();
							$(element).children(".hidden").toggle();
							}							
						</script>
						<style type="text/css">
							body {
							font-size: 12px;
							color: #111111;
							background: #c4c4c4;
							font-family: helvetica, arial, sans-serif;
							}

							h1, h2, h3 {
							color: #111111;
							font-weight: normal;
							font-family:
							helvetica, arial, sans-serif;
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
							1px rgba(255, 255,
							255, 0.3);
							-moz-box-shadow: inset 0 -5px 10px
							rgba(0, 0, 0, 0.25),
							0 1px 1px rgba(255, 255,
							255, 0.3);
							-webkit-box-shadow: inset 0
							-5px 10px rgba(0, 0, 0, 0.25), 0 1px
							1px rgba(255, 255,
							255, 0.3);
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
							#header h2 a:link, #header h1 a:active, #header h1 a:hover,
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
							#main .block {
							padding-top: 0px;
							}
							#main .block
							.content {
							padding-top: 1px;
							}
							#main
							.block .content .inner {
							padding: 0 15px 15px;
							}
							#main .block
							.content h2 {
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
							0 3px;
							background-color:
							white;
							text-shadow: 0 1px 0
							white;
							-moz-border-radius: 6px;
							-webkit-border-radius: 6px;
							border-radius: 6px;
							box-shadow: 0 0 1px
							rgba(0, 0, 0, 0.3), 0 1px
							1px rgba(0, 0, 0, 0.3);
							-moz-box-shadow: 0 0 1px rgba(0, 0, 0,
							0.3), 0 1px 1px rgba(0, 0,
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
							padding: 3px 3px 3px 3px;
							width:100%;
							display:none;
							}

							.group.hidden{
							display:none;
							}
						</style>
					</head>
					<body>
						<div id="main">
							<xsl:for-each select="rdf:RDF/rdf:Description">
								<xsl:if test="*[2]">
									<div class="block">
										<div class="header" id="header" onclick="$(this).parent().children('.content').toggle();">
											<h2>
												<xsl:choose>
													<xsl:when test="dbpprop:name">
														<xsl:value-of select="dbpprop:name" />
													</xsl:when>
													<xsl:otherwise>
														<xsl:choose>
															<xsl:when test="rdfs:label">
																<xsl:value-of select="rdfs:label" />
															</xsl:when>
															<xsl:otherwise>
																<a href="{@rdf:about}">
																	<xsl:value-of select="@rdf:about" />
																</a>
															</xsl:otherwise>
														</xsl:choose>
													</xsl:otherwise>
												</xsl:choose>
											</h2>
										</div>
										<div class="content">
											<xsl:if test="dbpprop:author">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Autoren:</h3>
													</div>
													<xsl:for-each select="dbpprop:author">

														<div class="right">
															<a href="{@rdf:resource}">
																<xsl:value-of select="@rdf:resource" />
															</a>
														</div>

													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="dbpprop:publisher">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Publisher:</h3>
													</div>
													<xsl:for-each select="dbpprop:publisher">

														<div class="right">
															<a href="{@rdf:resource}">
																<xsl:value-of select="@rdf:resource" />
															</a>
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="dbpprop:books">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Bücher:</h3>
													</div>
													<xsl:for-each select="dbpprop:books">
														<xsl:if test="@rdf:resource">
															<div class="right">
																<a href="{@rdf:resource}">
																	<xsl:value-of select="@rdf:resource" />
																</a>
															</div>
														</xsl:if>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="dbpprop:genre">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Genre:</h3>
													</div>
													<xsl:for-each select="dbpprop:genre">
														<div class="right">
															<a href="{@rdf:resource}">
																<xsl:value-of select="@rdf:resource" />
															</a>
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="dbpprop:pages">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Seiten:</h3>
													</div>
													<xsl:for-each select="dbpprop:pages">
														<div class="right">
															<xsl:value-of select="current()" />
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="dbpprop:country">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Länder:</h3>
													</div>
													<xsl:for-each select="dbpprop:country">
														<div class="right">
															<xsl:value-of select="current()" />
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="rdf:type">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Typ:</h3>
													</div>
													<xsl:for-each select="rdf:type">
														<div class="right">
															<a href="{@rdf:resource}">
																<xsl:value-of select="@rdf:resource" />
															</a>
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="dcterms:subject">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Kategorien:</h3>
													</div>
													<xsl:for-each select="dcterms:subject">
														<div class="right">
															<a href="{@rdf:resource}">
																<xsl:value-of select="@rdf:resource" />
															</a>
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="foaf:depiction">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Abbildungen:</h3>
													</div>
													<xsl:for-each select="foaf:depiction">
														<div class="right">
															<a href="{@rdf:resource}">
																<xsl:value-of select="@rdf:resource" />
															</a>
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="dbpprop:hasPhotoCollection">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Externe Bildersammlungen:</h3>
													</div>
													<xsl:for-each select="dbpprop:hasPhotoCollection">
														<div class="right">
															<a href="{@rdf:resource}">
																<xsl:value-of select="@rdf:resource" />
															</a>
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="dbpedia-owl:abstract">
												<div class="group" onclick="$(this).children('.hidden').toggle();">
													<div class="left">
														<h3>Zusammenfassungen:</h3>
													</div>
													<xsl:for-each select="dbpedia-owl:abstract">
														<div class="group hidden" onclick="event.stopPropagation();$(this).children('.right').toggle();">
															<div class="left">
																<h4>
																	Sprache:
																	<xsl:value-of select="@xml:lang" />
																</h4>
															</div>
															<div class="right">
																<xsl:value-of select="current()" />
															</div>
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="rdfs:comment">
												<div class="group" onclick="$(this).children('.hidden').toggle();">
													<div class="left">
														<h3>Kommentare:</h3>
													</div>
													<xsl:for-each select="rdfs:comment">
														<div class="group hidden" onclick="event.stopPropagation();$(this).children('.right').toggle();">
															<div class="left">
																<h4>
																	Sprache:
																	<xsl:value-of select="@xml:lang" />
																</h4>
															</div>
															<div class="right">
																<xsl:value-of select="current()" />
															</div>
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="owl:sameAs">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Ähnliche Seiten:</h3>
													</div>
													<xsl:for-each select="owl:sameAs">
														<div class="right">
															<a href="{@rdf:resource}">
																<xsl:value-of select="@rdf:resource" />
															</a>
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
											<xsl:if test="dbpedia-owl:wikiPageExternalLink">
												<div class="group" onclick="$(this).children('.right').toggle();">
													<div class="left">
														<h3>Externe Links:</h3>
													</div>
													<xsl:for-each select="dbpedia-owl:wikiPageExternalLink">
														<div class="right">
															<a href="{@rdf:resource}">
																<xsl:value-of select="@rdf:resource" />
															</a>
														</div>
													</xsl:for-each>
												</div>
											</xsl:if>
										</div>
									</div>
								</xsl:if>
							</xsl:for-each>
						</div>
					</body>
				</html>
			</xsl:when>
			<xsl:otherwise>
				<xsl:for-each
					select="rdf:RDF/rdf:Description/dbpedia-owl:wikiPageRedirects">
					<meta http-equiv="refresh" content="0; url={@rdf:resource}" />
				</xsl:for-each>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>