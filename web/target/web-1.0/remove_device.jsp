<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove devices</title>

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://jquery-ui.googlecode.com/svn/branches/labs/selectmenu/ui.selectmenu.js"></script>
<link href="style.css" rel="stylesheet" type="text/css" />
<link href="menu.css" rel="stylesheet" type="text/css" />
<link href="links.css" rel="stylesheet" type="text/css" />
<link href="selectmenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="http://jquery-ui.googlecode.com/svn/tags/1.8.9/themes/smoothness/jquery-ui.css" type="text/css" />

</head>
<%@ page
	import="java.util.Collection,ua.edu.ChaliyLukyanov.laba3.model.device.*,ua.edu.ChaliyLukyanov.laba3.model.Application"%>
<%@ page errorPage="error.jsp"%>
<body>
	<div id="container">

		<%@ include file="header.jsp"%>

		<div id="main_content">

			<div class="content">
				<h2 align="center">Devices:</h2>
				<form method="post" name="remove_devices" action="removedevices">
					<%
						DeviceHome devHome = (DeviceHome) request.getAttribute(Application.DEVICE);
						Collection<Device> devices = devHome.findAll();
						for (Device dev : devices) {
					%>
						<input type="checkbox" name="<%=dev.getId()%>"
							value="<%=dev.getId()%>" /><%=dev.getTitle()%><br />
						<%
							}
						%>
					<p align="center">
						<input class="ui-state-default ui-corner-all" type="submit"
							value="Remove device">
					</p>
				</form>
			</div>
			<%@ include file="menu.jsp"%>

			<div id="clear"></div>

		</div>

		<%@ include file="footer.jsp"%>

	</div>
</body>
</html>