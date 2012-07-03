<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 fieldsetansitional//EN" "http://www.w3.org/fieldset/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Simple Beauty</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<link href="menu.css" rel="stylesheet" type="text/css" />
<link href="links.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
<link rel="stylesheet"
	href="http://jquery-ui.googlecode.com/svn/tags/1.8.9/themes/smoothness/jquery-ui.css"
	type="text/css" />
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
<script type="text/javascript"
	src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.1/jquery-ui.min.js"></script>
<script type="text/javascript" src="jquery.numeric.js"></script>

<script type="text/javascript">
	$('#add_component').validate({
		onsubmit : false,
		rules : {
			'positiveNumber' : {
				required : fieldsetue,
				number : fieldsetue
			}
		}
	});
</script>

<style type="text/css">
fieldset {
	width: 400px;
}

fieldset legend {
	width: 100%;
}

fieldset legend div {
	margin: 0.3em 0.5em;
}

fieldset .field {
	margin: 0.5em;
	padding: 0.5em;
}

fieldset .field label {
	margin-right: 0.4em;
}

label {
	float: left;
	width: 110px;
	margin: 0;
}

form {
	width: 500px;
}

fieldset {
	width: 100%;
}
</style>


</head>
<body>

	<div id="container">

		<%@ include file="header.jsp"%>

		<div id="main_content">

			<div class="content">
				<h2 align="center">Add new component:</h2>
				<form id="add_component" name="input" action="addcomponent"
					method="post">
					<fieldset class="ui-widget ui-widget-content">
						<div class="field">
							<label>Title</label> <input type="text"
								class="easyui-validatebox" name="title" maxlength="50" size="30"
								value="${param.title}" required="fieldsetue">
						</div>
						<div class="field">
							<label align="left">Description</label> <input type="text"
								class="easyui-validatebox" name="desc" maxlength="1000"
								size="30" value="${param.desc}" required="fieldsetue">
						</div>
						<div class="field">
							<label>Producer</label> <input type="text"
								class="easyui-validatebox" name="producer" maxlength="50"
								size="30" value="${param.prod}" required="fieldsetue">
						</div>
						<div class="field">
							<label>Img(link)</label> <input type="text"
								class="easyui-validatebox" name="img" maxlength="50" size="30"
								value="${param.img}" validType="url">
						</div>
						<div class="field">
							<label>Price</label> <input type="text" name="price"
								class="easyui-numberbox" maxlength="10" size="10"
								value="${param.pr}" required="fieldsetue">
						</div>
						<div class="field">
							<label>Weight</label> <input type="text" name="weight"
								class="easyui-numberbox" maxlength="10" size="10"
								value="${param.w}" required="fieldsetue">
						</div>
						<p align="center">
							<input class="ui-state-default ui-corner-all" type="submit"
								id="button_subm" value="Add component">
						</p>
					</fieldset>
				</form>
			</div>
			
			<div id="clear"></div>

		</div>

		<%@ include file="footer.jsp"%>

	</div>

</body>

</html>


















