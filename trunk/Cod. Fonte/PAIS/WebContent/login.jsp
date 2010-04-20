<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="p" uri="http://primefaces.prime.com.tr/ui" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bem Vindo ao PAIS</title>
</head>
<f:view>
<p:layout fullPage="true">

	<p:layoutUnit position="top" height="75" header="Top" resizable="true"
		closable="true" collapsible="true">
		<h:outputText value="North unit content." />
	</p:layoutUnit>

	<p:layoutUnit position="bottom" height="75" header="Bottom"
		resizable="true" closable="true" collapsible="true">
		<h:outputText value="South unit content." />
	</p:layoutUnit>

	<p:layoutUnit position="left" width="200" header="Left"
		resizable="true" closable="true" collapsible="true">
		<h:outputText value="West unit content." />
	</p:layoutUnit>

	<p:layoutUnit position="right" width="200" header="Right"
		resizable="true" closable="true" collapsible="true">
		<h:outputText value="East unit content." />
	</p:layoutUnit>

	<p:layoutUnit position="center">  
         This fullPage layout consists of five different layoutUnits which are resizable and closable by default.  
     </p:layoutUnit>

</p:layout>
</f:view>
</html>