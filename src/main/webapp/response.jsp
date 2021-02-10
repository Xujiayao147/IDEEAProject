<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"/>
	<meta content="width=device-width,user-scalable=no" name="viewport"/>
	<title>Xujiayao</title>
	<link href="/css/main.css" rel="stylesheet"/>
	<link href="/css/response.css" rel="stylesheet"/>
	<link href="/avatar.jpg" rel="shortcut icon"/>
</head>
<body>
<div class="header">
	<a href="/"><img class="avatar" height="200" src="/avatar.jpg" width="200" alt="avatar.jpg"/></a>
	<h1>Xujiayao</h1>
	<div>没有个性，如何签名？</div>
	<div class="menu">
		<a class="melec" href="https://github.com/Xujiayao147" target="_blank">GitHub</a><a class="melec"
		                                                                                    href="https://xujiayao147.gitee.io/"
		                                                                                    target="_blank">Blog</a><a
			class="melec" href="mailto:xujiayao147258369@gmail.com" target="_blank">Email</a><a class="melec"
		                                                                                        href="https://space.bilibili.com/270317970"
		                                                                                        target="_blank">bilibili</a>
	</div>
</div>
<%
	String resp;
	String mess;
	String link;
	String linkText;

	switch (request.getParameter("success")) {
		case "true":
			resp = "成功";
			link = "/";
			linkText = "回到主页";
			break;
		case "false":
			resp = "失败";
			link = "javascript:history.back(-1)";
			linkText = "回到上一页";
			break;
		default:
			resp = "无法获取";
			link = "/";
			linkText = "回到主页";
			break;
	}

	switch (request.getParameter("message")) {
		case "1":
			mess = "登录成功";
			break;
		case "2":
			mess = "登录失败：用户名或密码错误";
			break;
		case "3":
			mess = "登录或注册失败：用户名或密码为空";
			break;
		case "4":
			mess = "注册成功";
			break;
		case "5":
			mess = "注册失败：用户名已存在";
			break;
		default:
			mess = "无法获取";
			break;
	}
%>
<div class="respbdy">
	<div class="respt"><%= resp %>
	</div>
	<div class="mess"><%= mess %>
	</div>
	<div class="link"><a href=<%= link %>><%= linkText %>
	</a></div>
</div>
</body>
</html>