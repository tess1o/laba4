<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Components</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<link href="menu.css" rel="stylesheet" type="text/css" />
<link href="links.css" rel="stylesheet" type="text/css" />
<style type="text/css" title="currentStyle">
@import
	"http://datatables.net/release-datatables/media/css/demo_page.css";

@import
	"http://datatables.net/release-datatables/media/css/demo_table_jui.css"
	;
</style>
<link rel="stylesheet"
	href="http://jquery-ui.googlecode.com/svn/tags/1.8.9/themes/smoothness/jquery-ui.css"
	type="text/css" />

<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js"></script>

<script type="text/javascript" language="javascript"
	src="http://datatables.net/release-datatables/media/js/jquery.js"></script>
<script type="text/javascript" language="javascript"
	src="http://datatables.net/release-datatables/media/js/jquery.dataTables.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
    	$('#submitButton').attr('disabled', 'disabled');
     $('input:checkbox').click(function() {
            var buttonsChecked = $('input:checkbox:checked');
            if (buttonsChecked.length) {
                $('#submitButton').removeAttr('disabled');
                } 
            else {
                $('#submitButton').attr('disabled', 'disabled');
                }
            });
	oTable = $('#show_component').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bPaginate": false,
			"bLengthChange": false
		});
	});
</script>
</head>
<%@ page import="java.util.List"%>
<%@ page errorPage="error.jsp"%>

<body>

	<div id="container">

		<%@ include file="header.jsp"%>

		<div id="main_content">

			<div class="content">

				<h2 align="center">Components:</h2>
				
				<c:if test="${components != null}">
					<form method="post" name="remove_components" action="removesinglecomponent">
						<table border="1" cellpadding="10" align="center"
							id="show_component">
							<thead>
								<tr>
									<td>Title</td>
									<td>Producer</td>
									<td>Weight</td>
									<td>Price</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${components}" var="component">
									<tr>
									 
										<td><input type="checkbox" name="${component.id}" value="${component.id}"/> <a href="showcomponent?id=${component.id}">${component.title}</a></td>
										<td>${component.producer}</td>
										<td>${component.weight}</td>
										<td>${component.price}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<p align="center">
							<input class="ui-state-default ui-corner-all" type="submit" id="submitButton" value="Remove device"  onclick="return confirm('Are you sure?')">
						</p>
					</form>
				</c:if>

			</div>

			<%@ include file="menu.jsp"%>

			<div id="clear"></div>

		</div>

		<%@ include file="footer.jsp"%>

	</div>

</body>

</html>
