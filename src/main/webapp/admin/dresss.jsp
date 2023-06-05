<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>礼服管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">
<!-- .box-body -->
<div class="box-header with-border">
    <h3 class="box-title">礼服租赁</h3>
</div>
<div class="box-body">
    <%--新增按钮：如果当前登录用户是管理员，页面展示新增按钮--%>
    <c:if test="${Consumer_SESSION.role =='ADMIN'}">
        <div class="pull-left">
            <div class="form-group form-inline">
                <div class="btn-group">
                    <button type="button" class="btn btn-default" title="新建" data-toggle="modal"
                            data-target="#addOrEditModal" onclick="resetFrom()"> 新增
                    </button>
                </div>
            </div>
        </div>
    </c:if>
    <%--新增按钮 /--%>
    <!--工具栏 数据搜索 -->
    <div class="box-tools pull-right">
        <div class="has-feedback">
            <form action="${pageContext.request.contextPath}/dress/search" method="post">
                礼服名称：<input name="name" value="${search.name}">&nbsp&nbsp&nbsp&nbsp
                创作者：<input name="author" value="${search.author}">&nbsp&nbsp&nbsp&nbsp
                工作室：<input name="press" value="${search.press}">&nbsp&nbsp&nbsp&nbsp
                <input class="btn btn-default" type="submit" value="查询">
            </form>
        </div>
    </div>
    <!--工具栏 数据搜索 /-->
    <!-- 数据列表 -->
    <div class="table-box">
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
                    <td>${dress.borrower }</td>
                    <td>${dress.borrowTime}</td>
                    <td>${dress.returnTime}</td>
                    <td class="text-center">
                        <c:if test="${dress.status ==0}">
                            <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                    data-target="#borrowModal" onclick="findDressById(${dress.id},'borrow')"> 租赁
                            </button>
                            <c:if test="${Consumer_SESSION.role =='ADMIN'}">
                                <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                        data-target="#addOrEditModal" onclick="findDressById(${dress.id},'edit')"> 编辑
                                </button>
                            </c:if>
                        </c:if>
                        <c:if test="${dress.status ==1 ||dress.status ==2}">
                            <button type="button" class="btn bg-olive btn-xs" disabled="true">租赁</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!--数据表格/-->
        <%--分页插件--%>
        <div id="pagination" class="pagination"></div>
    </div>
    <!--数据列表/-->
</div>
<!-- /.box-body -->
<%--引入存放模态窗口的页面--%>
<jsp:include page="/admin/dress_modal.jsp"></jsp:include>

<!-- 添加和编辑礼服的模态窗口 -->
<div class="modal fade" id="addOrEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="myModalLabel">礼服信息</h3>
            </div>
            <div class="modal-body">
                <form id="addOrEditDress">
                    <span><input type="hidden" id="ebid" name="id"></span>
                    <table id="addOrEditTab" class="table table-bordered table-striped" width="800px">
                        <%--礼服的id,不展示在页面--%>
                        <tr>
                            <td>礼服名称</td>
                            <td><input class="form-control" placeholder="礼服名称" name="name" id="ebname"></td>
                            <td>礼服编号</td>
                            <td><input class="form-control" placeholder="礼服编号" name="isbn" id="ebisbn"></td>
                        </tr>
                        <tr>
                            <td>工作室</td>
                            <td><input class="form-control" placeholder="工作室" name="press" id="ebpress"></td>
                            <td>创作者</td>
                            <td><input class="form-control" placeholder="创作者" name="author" id="ebauthor"></td>
                        </tr>
                        <tr>
                            <td>礼服数量</td>
                            <td><input class="form-control" placeholder="礼服数量" name="pagination" id="ebpagination"></td>
                            <td>礼服价格<br/></td>
                            <td><input class="form-control" placeholder="礼服价格" name="price" id="ebprice"></td>
                        </tr>
                            <tr>
                                <td>上架状态</td>
                                <td>
                                    <select class="form-control" id="ebstatus" name="status" >
                                        <option value="0">上架</option>
                                        <option value="3">下架</option>
                                    </select>
                                </td>
                            </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" data-dismiss="modal" aria-hidden="true" id="aoe" disabled onclick="addOrEdit()">保存
                </button>
                <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    /*分页插件展示的总页数*/
    pageargs.total = Math.ceil(${pageResult.total}/pageargs.pagesize)
    /*分页插件当前的页码*/
    pageargs.cur = ${pageNum}
    /*分页插件页码变化时将跳转到的服务器端的路径*/
    pageargs.gourl = "${gourl}"
    /*保存搜索框中的搜索条件，页码变化时携带之前的搜索条件*/
    dressVO.name = "${search.name}"
    dressVO.author = "${search.author}"
    dressVO.press = "${search.press}"
    /*分页效果*/
    pagination(pageargs);
</script>
</html>