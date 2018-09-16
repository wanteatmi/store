<%@page import="com.wut.store.domain.Product"%>
<%@page import="com.wut.store.service.impl.ProductServiceImpl"%>
<%@page import="com.wut.store.service.ProductService"%>
<%@page import="com.wut.store.utils.CookieUtils"%>
<%@page import="com.mchange.v2.coalesce.CoalesceChecker"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
				width: 100%;
			}
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>
		
					<%@ include file="menu.jsp" %>

		<div class="row" style="width:1210px;margin:0 auto;">
			<div class="col-md-12">
				<ol class="breadcrumb">
					<li><a href="#">首页</a></li>
				</ol>
			</div>
			
			<c:forEach var="p" items="${pageBean.list}">
			<div class="col-md-2">
				<a href="${pageContext.request.contextPath }/ProductServlet?method=findByPid&pid=${p.pid}">
					<img src="${pageContext.request.contextPath }/${p.pimage}" width="170" height="170" style="display: inline-block;">
				</a>
				<p><a href="${pageContext.request.contextPath }/ProductServlet?method=findByPid&pid=${p.pid}" style='color:green'> ${fn:substring(p.pname,0,7) }</a></p>
				<p><font color="#FF0000">商城价：&yen;${p.shop_price }</font></p>
			</div>
			</c:forEach>

		
		</div>

		<!--分页 -->
		<div style="width:380px;margin:0 auto;margin-top:50px;">
			<ul class="pagination" style="text-align:center; margin-top:10px;">
				<li <c:if test="${pageBean.currPage==1}">class='disabled'</c:if>>
				 <a href="${pageContext.request.contextPath }/ProductServlet?method=findByCid&currPage=${pageBean.currPage-1}&cid=${param.cid}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
				</li>
				
				<c:forEach var="i" begin="1" end="${pageBean.totalPage }">
					<c:if test="${pageBean.currPage==i}">
						<li class="active"><a href="#">${ i }</a></li>
					</c:if>
					<c:if test="${pageBean.currPage!=i}">
						<li ><a href="${pageContext.request.contextPath }/ProductServlet?method=findByCid&currPage=${i}&cid=${param.cid}">${ i }</a></li>
					</c:if>
				
				</c:forEach>
				
				
				<li <c:if test="${pageBean.currPage==pageBean.totalPage}">class='disabled'</c:if>>
					<a href="${pageContext.request.contextPath }/ProductServlet?method=findByCid&currPage=${pageBean.currPage+1}&cid=${param.cid}" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</ul>
		</div>
		<!-- 分页结束=======================        -->

		<!--
       		商品浏览记录:
        -->
		<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

			<h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
			<div style="width: 50%;float: right;text-align: right;"><a href="">more</a></div>
			<div style="clear: both;"></div>

			<div style="overflow: hidden;">

				<ul style="list-style: none;">
				<%
					Cookie[] cookies=request.getCookies();
					Cookie cookie = CookieUtils.findCookie(cookies, "history");
					ProductService productService=new ProductServiceImpl();
					if(cookie==null){
						out.println("<h3> 您还没有浏览记录</h3>");
					}else{
						String value =cookie.getValue();
						String[] ids=value.split("-");
						for(String id:ids){
							Product p=productService.findByPid(id);
							pageContext.setAttribute("p",p);
				%>
					<li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;"><img src="${pageContext.request.contextPath }/${p.pimage}" width="130px" height="130px" /></li>
				<%
						
						}
					}
				%>
				
					
				</ul>

			</div>
		</div>
		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath }/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
		</div>

	</body>

</html>