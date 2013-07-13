<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text/html" />
	<xsl:strip-space elements="*" />

	<xsl:template match="*">
		<xsl:apply-templates select="node()|@*" />
	</xsl:template>

	<xsl:template match="text()">
		<p>
			<xsl:value-of select="concat('&lt;',name(parent::*),'> ',.,'&#xA;')" />
		</p>
	</xsl:template>

	<xsl:template match="@*">
		<p>
			<xsl:value-of select="concat(name(),'=&#x22;',.,'&#x22;&#xA;')" />
		</p>
	</xsl:template>

</xsl:stylesheet>