<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Components</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="filter.js"></script>
</head>
<%@ page import="ua.edu.ChaliyLukyanov.laba3.model.*,ua.edu.ChaliyLukyanov.laba3.model.DAO.*,java.util.List"%>
<%@ page errorPage="error.jsp"%>

<body onload="init()">

	<div id="container">

		<%@ include file="header.jsp"%>

		<div id="main_content">

			<div class="content">

				<h2 align="center">Components:</h2>
				<c:if test="${components != null}">
					<table border="1" cellpadding="10" align="center" id="table">
						<tr>
							<td>Title</td>
							<td>Producer</td>
							<td>Weight</td>
							<td>Price</td>
						</tr>
						<c:forEach items="${components}" var="component">
							<tr>
								<td><a href="showcomponent?id=${component.id}">${component.title}</a></td>
								<td>${component.producer}</td>
								<td>${component.weight}</td>
								<td>${component.price}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<form action="filtercomponent" method="get">
					<table>
						<tr>
							<td><h4>Sort</h4></td>
							<td><h4>Order</h4></td> 
							<td><h4>Price</h4></td> 
						</tr>
						<tr>
							<td>
								<select size="4" name="sortBy" id="sort" onmouseup="doSort()">
									<option value="none" selected> </option> 
									<option value="title">by title</option>
									<option value="producer">by producer</option>
									<option value="price">by price</option>
								</select> 
							</td>
							<td>
								<select size="2" name="sortOrder" id="order"  onmouseup="doSort()" >
									<option value="asc">Asc</option>
									<option value="desc">Desc</option>
								</select> 
							</td>
							<td>
								<select size="2" name="priceOrder" id="sign"  onmouseup="doSort()" >
									<option value=">=">&gt=</option>
									<option value="<=">&lt=</option>
								</select> <br/> 
								<input type="text" name="priceValue" maxlength="10" size="7"  id="price" onkeyup="doSort()">
							</td> 
						</tr>
					</table>
				</form>
			</div>

			<%@ include file="menu.jsp"%>

			<div id="clear"></div>

		</div>

		<%@ include file="footer.jsp"%>

	</div>

</body>

</html>
