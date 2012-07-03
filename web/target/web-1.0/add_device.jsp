<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add device</title>

<link rel="stylesheet"
	href="http://jquery-ui.googlecode.com/svn/tags/1.8.9/themes/smoothness/jquery-ui.css"
	type="text/css" />
<link href="selectmenu.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="http://jquery-ui.googlecode.com/svn/branches/labs/selectmenu/ui.selectmenu.js"></script>
<link href="style.css" rel="stylesheet" type="text/css" />
<link href="menu.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function() {

		$('select').selectmenu({
			style : 'dropdown'
		});
	});
</script>
</head>
<%@ page
	import="java.util.Collection,ua.edu.ChaliyLukyanov.laba3.model.component.*,ua.edu.ChaliyLukyanov.laba3.model.device.*,ua.edu.ChaliyLukyanov.laba3.model.Application"%>
<body>
	<div id="container">
		<%@ include file="header.jsp"%>
		<div id="main_content">
			<div class="content">

				<form action="adddevice" method="post">
					<br /> <b>Component: </b> <select name="id_component">
						<%
							ComponentHome compHome = (ComponentHome) request
									.getAttribute(Application.COMPONENT);
							Collection<Component> components = compHome.findAll();
							for (Component c : components) {
						%>
						<option value="<%=c.getId()%>"><%=c.getTitle()%></option>
						<%
							}
						%>
					</select> <br /> <br /> <b>Previous device: </b> <select
						name="id_prev_device">
						<option value="-1"></option>
						<%
							DeviceHome deviceHome = (DeviceHome) request
									.getAttribute(Application.DEVICE);
							Collection<Device> devices = deviceHome.findAll();
							for (Device d : devices) {
						%>
						<option value="<%=d.getId()%>"><%=d.getTitle()%></option>
						<%
							}
						%>
					</select><br /> <br /> <b>Title: </b><input type="text" name="title" /><br />
					<p align="center">
						<input class="ui-state-default ui-corner-all" type="submit"
							value="Add device">
					</p>
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