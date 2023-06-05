<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>新礼服推荐</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>
<body class="hold-transition skin-red sidebar-mini">
<!--数据展示头部-->
<div class="box-header with-border">
    <h3 class="box-title">新礼服推荐</h3>
</div>
<!--数据展示头部-->
<!--数据展示内容区-->
<div class="box-body">
    <!-- 数据表格 -->
    <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
        <thead>
        <tr>
            <th class="sorting_asc">礼服名称</th>
            <th class="sorting">创作者</th>
            <th class="sorting">工作室</th>
            <th class="sorting">礼服编号</th>
            <th class="sorting">礼服状态</th>
            <th class="sorting">租赁人</th>
            <th class="sorting">租赁时间</th>
            <th class="sorting">预计归还时间</th>
            <th class="text-center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pageResult.rows}" var="dress">
            <tr>
                <td> ${dress.name}</td>
                <td>${dress.author}</td>
                <td>${dress.press}</td>
                <td>${dress.isbn}</td>
                <td>
                    <c:if test="${dress.status ==0}">可租赁</c:if>
                    <c:if test="${dress.status ==1}">租赁中</c:if>
                    <c:if test="${dress.status ==2}">归还中</c:if>
                </td>
                <td>${dress.borrower}</td>
                <td>${dress.borrowTime}</td>
                <td>${dress.returnTime}</td>
                <td class="text-center">
                    <c:if test="${dress.status ==0}">
                        <button type="button" class="btn bg-olive btn-xs" data-toggle="modal" data-target="#borrowModal"
                                onclick="findDressById(${dress.id},'borrow')"> 租赁
                        </button>
                    </c:if>
                    <c:if test="${dress.status ==1 ||dress.status ==2}">
                        <button type="button" class="btn bg-olive btn-xs" disabled="true">租赁</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- 数据表格 /-->
</div>
<!-- 数据展示内容区/ -->
<%--引入存放模态窗口的页面--%>
<jsp:include page="/admin/dress_modal.jsp"></jsp:include>
</body>
</html>
