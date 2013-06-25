<?xml version="1.0"?>
<xsl:stylesheet version="1.0" exclude-result-prefixes="rdf rss"
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:rss="http://my.netscape.com/rdf/simple/0.9/">
  <xsl:output method="html" indent="yes" />

  <xsl:template match="/">
    <xsl:apply-templates select="rdf:RDF/rss:item" />
  </xsl:template>

  <xsl:template match="rdf:RDF/rss:item">
    <table border="1">
      <tr bgcolor="#9acd32">      
      </tr>
 
      <tr>
      </tr>
    </table>
  <xsl:text>&#013;</xsl:text>
    
  </xsl:template>
</xsl:stylesheet>