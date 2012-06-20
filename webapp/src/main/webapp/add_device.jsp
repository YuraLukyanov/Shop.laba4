<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add device</title>
<link href="style.css" rel="stylesheet" type="text/css" />
</head>
<%@ page import="ua.edu.ChaliyLukyanov.laba3.model.EJB.*,java.util.List,ua.edu.ChaliyLukyanov.laba3.model.Application"%>
<body>
	<div id="container">
		<%@ include file="header.jsp"%>
		<div id="main_content">
			<div class="content">
				
				<form action="adddevice" method="post">
					<br/>
					<b>Component: </b>
					<select name="id_component">
						<c:forEach items="${components}" var="component">
							<option value="${component.id}">${component.title}</option>
						</c:forEach>
					</select> <br/><br/>
					<b>Previous device: </b>
					<select name="id_prev_device">
						<option value="-1"> </option>
						<c:forEach items="${devices}" var="device">
							<option value="${device.id}">${device.title}</option>
						</c:forEach>
					</select><br/><br/>
					<b>Title: </b><input type="text" name="title"/><br/>
					<p align="center"><button type="submit">Add</button></p>
				</form>
				<c:if test="${!empty param.error }">
					<h2>${param.error}</h2>
				</c:if>
			</div>

			<%@ include file="menu.jsp"%>

			<div id="clear"></div>

		</div>
		<%@ include file="footer.jsp"%>

	</div>
</body>
</html>