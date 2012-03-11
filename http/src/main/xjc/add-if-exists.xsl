<?xml version="1.0" encoding="UTF-8"?>
<!--
   Copyright 2011-2012 Gregory P. Moyer

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:jxb="http://java.sun.com/xml/ns/jaxb">
	
	<xsl:output omit-xml-declaration="no" indent="yes"/>

	<!-- Copy everything by default. -->
	<xsl:template match="comment()|processing-instruction()|text()">
		<xsl:copy />
	</xsl:template>
	<xsl:template match="*">
		<xsl:copy>
			<xsl:copy-of select="@*" />
			<xsl:apply-templates select="comment()|processing-instruction()|text()|*" />
		</xsl:copy>
	</xsl:template>

	<!-- Add the "if-exists" attribute to the second-level bindings node. -->
	<xsl:template match="//jxb:bindings[@scd]">
	    <xsl:element name="{name()}">
	        <xsl:copy-of select="@*" />
	        <xsl:copy-of select="namespace::*"/>
			<xsl:attribute name="if-exists">true</xsl:attribute>
			<xsl:apply-templates />
		</xsl:element>
	</xsl:template>
	
	<!-- Change the map setting from false to true. Without this step, wsimport refuses to 
	     process any WSDL elements. However, with this step, each service ObjectFactory
	     contains entries duplicated from the common ObjectFactory (yet there are no 
	     duplicate classes generated). Looks like wsimport is broken. -->
    <xsl:template match="//jxb:schemaBindings">
        <xsl:element name="{name()}">
            <xsl:attribute name="map">true</xsl:attribute>
            <xsl:apply-templates />
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>