<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    pageContext.setAttribute("WEB_PATH", request.getContextPath());
%>
<!-- web路径：
    不以/开始的相对路径，找资源，以当前资源的路径为基准，经常容易出问题。
    以/开始的相对路径，找资源，以服务器的路径为标准(http://localhost:3306)；需要加上项目名
    http://localhost:3306/SSM
-->
<html>
<head>
    <title>员工列表</title>
    <link rel="stylesheet" href="${WEB_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.css">
    <script type="text/javascript" src="${WEB_PATH}/static/bootstrap-3.3.7-dist/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="${WEB_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
</head>
<body>
    <!--搭建显示页面-->
    <div class="container">
        <div class="jumbotron">
            <!--标题-->
            <div class="row">
                <div class="col-md-12"><h1>SSM-CRUD</h1></div>
            </div>
        </div>
        <!--按钮-->
        <div class="row">
            <!--占用2列，偏移10列-->
            <div class="col-md-2 col-md-offset-10">
                <strong>操作:</strong>
                <button class="btn btn-primary">新增</button>
                <button class="btn btn-danger">删除</button>
            </div>
        </div>
        <!--表格-->
        <div class="row">
            <table class="table table-hover col-md-12">
                <tr>
                    <th>员工id</th>
                    <th>员工姓名</th>
                    <th>员工性别</th>
                    <th>员工邮箱</th>
                    <th>员工部门</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${pageInfo.list}" var="emp">
                    <tr>
                        <td>${emp.empId}</td>
                        <td>${emp.empName}</td>
                        <td>${emp.gender=="M"?"男":"女"}</td>
                        <td>${emp.email}</td>
                        <td>${emp.dept.deptName}</td>
                        <td>
                            <button class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                编辑
                            </button>
                            <button class="btn btn-danger btn-sm">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                删除
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <!--分页条-->
        <div class="row" style="text-align: center">
            <nav aria-label="Page navigation">
                <ul class="pagination pagination-lg">
                    <c:if test="${pageInfo.hasPreviousPage}">
                        <li>
                            <a href="${WEB_PATH}/emps?pn=1">首页</a>
                        </li>
                        <li>
                            <a href="${WEB_PATH}/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
                                <span aria-hidden="true">上一页</span>
                            </a>
                        </li>
                    </c:if>
                    <c:forEach items="${pageInfo.navigatepageNums}" var="page">
                        <c:if test="${page == pageInfo.pageNum}">
                            <li class="active"><a href="#">${page}</a></li>
                        </c:if>
                        <c:if test="${page != pageInfo.pageNum}">
                            <li><a href="${WEB_PATH}/emps?pn=${page}">${page}</a></li>
                        </c:if>
                    </c:forEach>
                    <c:if test="${pageInfo.hasNextPage}">
                        <li>
                            <a href="${WEB_PATH}/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
                                <span aria-hidden="true">下一页</span>
                            </a>
                        </li>
                        <li>
                            <a href="${WEB_PATH}/emps?pn=${pageInfo.pages}">末页</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
        <!--页面说明-->
        <div class="row" style="text-align: center">
            -当前在第 <strong>${pageInfo.pageNum}</strong> 页，总计 <strong>${pageInfo.pages}</strong> 页，共 <strong>${pageInfo.total}</strong> 条记录-
        </div>
    </div>
</body>
</html>
