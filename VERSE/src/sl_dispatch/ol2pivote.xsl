<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="OpenLabyrinthservice">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="OpenLabyrinthmap">
        <xsl:value-of select="OpenLabyrinthmapid"/>
        <xsl:value-of select="OpenLabyrinthmapname"/>
        <xsl:value-of select="OpenLabyrinthmaproot"/>
    </xsl:template>
    
    
    <xsl:template match="nodes">
        <xsl:for-each select="node">
            <xsl:value-of select="mnodeid"/>
            <xsl:value-of select="mnodename"/>
        </xsl:for-each>
    </xsl:template>
    
    
    <xsl:template match="labyrinth">
    <pages>
	    <page type="controller">
	    <pivote>
		<ss>sl<xsl:value-of select="mysession"/></ss>
		<xsl:value-of select="message" disable-output-escaping="yes" />

		<hr/>

		<an>
		    <id><xsl:value-of select="mnodetitle"/></id>
		    <lbl><xsl:value-of select="mnodetitle"/></lbl>
		    <dam>
			<type>VPDText</type>
			<v></v>
			<d><xsl:value-of select="message" disable-output-escaping="yes" /></d>
		    </dam>
		    <links>

		       <xsl:for-each select="linker">
			<o>
			    <l><xsl:value-of select="linker" disable-output-escaping="yes" /></l>
			</o>
			</xsl:for-each>
		    </links>
		</an>


		<others>
		    <mapname><xsl:value-of select="mapname"/></mapname>
		    <mapid><xsl:value-of select="mapid"/></mapid>
		    <mnodeid><xsl:value-of select="mnodeid"/></mnodeid>
		    <mapscore><xsl:value-of select="mapscore"/></mapscore>
		    <timestring><xsl:value-of select="timestring"/></timestring>
		    <linker><xsl:value-of select="linker"/></linker>
		    <tracestring><xsl:value-of select="tracestring" disable-output-escaping="yes" /></tracestring>
		    <rootnode><xsl:value-of select="rootnode"/></rootnode>       
		    <counterstring><xsl:value-of select="counterstring" disable-output-escaping="yes" /></counterstring>
		</others>
	    </pivote>
	    </page>
    
	    <page type="options">

	    <html>
	    <head>
		<title>Node: <xsl:value-of select="mnodetitle"/></title>
	    </head>
	    <body>
		    <font size="7" face="arial">
				<xsl:for-each select="linker">
					<xsl:value-of select="linker" disable-output-escaping="yes" />
					<br/>
				</xsl:for-each>
		   </font>
	    </body>
	    </html>
	    </page>
    
	    <page type="title">
	    <html>
	    <head>
		<title>Node: <xsl:value-of select="mnodetitle"/></title>
	    </head>
	    <body>
		    <font size="7" face="arial">
		    <h4><xsl:value-of select="mnodetitle"/></h4>
				<p>
				<xsl:value-of select="message" disable-output-escaping="yes" />
				</p>
				<ul>
				<xsl:for-each select="linker">
					<li>
						<xsl:value-of select="linker" disable-output-escaping="yes" />
					</li>
				</xsl:for-each>
				</ul>
		   </font>
	  </body></html>
	 </page>
    </pages>
    </xsl:template>
    
    
    <xsl:template match="sid"> 
    <sid>
    	<dtg><xsl:value-of select="dtg-hh:mm:ss"/></dtg>
    	<case><xsl:value-of select=""/></case>
    	<av><xsl:value-of select="avatarname"/></av>
    	<controller><xsl:value-of select="???"/></controller>
    	<tutor><xsl:value-of select="???"/></tutor>
    	<nodehistory><xsl:for-each select="sessionmap/node">|<xsl:value-of select="name" />,<xsl:value-of select="dtg-hh:mm:ss" /></xsl:for-each></nodehistory>
    	<curnode><xsl:value-of select="nodename"/></curnode>
    	<resources><xsl:value-of select="???"/></resources>

</sid>
	<properties>
	<counters>
		<xsl:for-each select="counter">
			<counter>
				<xsl:attribute name="id">
					<xsl:value-of select="counter/id" />
				</xsl:attribute>
				<xsl:attribute name="isVisible">
					<xsl:value-of select="counter/visibility" />
				</xsl:attribute>
				<xsl:value-of select="counter" />
			</counter> 
		</xsl:for-each>
	</counters>
</properties>
  </xsl:template>
    
</xsl:stylesheet>