<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Customer Management</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<body>

<!-- Begin page -->
<div id="wrapper">
    <!-- ============================================================== -->
    <!-- Start Page Content here -->
    <!-- ============================================================== -->
    <div class="content-page">
        <div class="content">
            <!-- Start Content-->
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="demo-box p-2">
                            <div class="row row-search-form head-search-table" >
                                <div>
                                    <h1 class="header-title">List Product</h1>
                                </div>
                                <div>
                                    <form style="margin-bottom: 20px; width: 200px"  action="get">
                                        <button type="button" class="btn btn-warning">
                                            <a href="/product?action=create"><i
                                                    class="fa fa-plus">Add Product</i></a>
                                        </button>
                                    </form>
                                </div>
                                <div>
                                    <form method="get" action="/product" class="search-form head-search-table-right">
                                        <button class="mr-1">Search</button>
                                        <input type="text" name="search" class="mr-1" value="${requestScope.search}">
                                        <select name="idCategory" id="idCategory" class="form-control" class="mr-1">
                                            <option  value="-1">All</option>
                                            <c:forEach items="${applicationScope.categories}" var="category">
                                                <option value="${category.getIdCategory()}"
                                                        <c:if
                                                        test="${requestScope.idCategory == category.getIdCategory()}"> selected</c:if>>${category.getNameCategory()}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </form>
                                </div>
                            </div>

                            <div class="row">
                                <div class="table-responsive">
                                    <table class="table mb-0">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Product Name</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th>Color</th>
                                            <th>Description</th>
                                            <th>Category</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.products}" var="product">
                                            <tr>
                                                <th scope="row">${product.getIdProduct()}</th>
                                                <td>${product.getNameProduct()}</td>
                                                <td>${product.getPrice()}</td>
                                                <td>${product.getQuantity()}</td>
                                                <td>${product.getColor()}</td>
                                                <td>${product.getDescription()}</td>
                                                <c:forEach items="${requestScope.categories}" var="category">
                                                    <c:if test="${category.getIdCategory() == product.getIdCategory()}">
                                                        <td>${category.getNameCategory()}</td>
                                                    </c:if>
                                                </c:forEach>
                                                <td>
                                                    <a href="/product?action=edit&id=${product.getIdProduct()}"><i class="fa fa-edit"></i> </a>
                                                    <a href="/product?action=delete&id=${product.getIdProduct()}"><i class="fa fa-remove"></i></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <%--                            </c:forEach>--%>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="container">
                                    <div class="flex-row-reverse d-flex">
                                        <ul class="pagination pagination-split float-right mb-0">
                                            <c:if test="${currentPage != 1}">
                                                <li class="page-item">
                                                    <a href="/product?page=${currentPage - 1}&kw=${requestScope.kw}&idProduct=${requestScope.idProduct}"
                                                       class="page-link"><i class="fa fa-angle-left"></i></a>
                                                </li>
                                            </c:if>
                                            <c:forEach begin="1" end="${noOfPages}" var="i">
                                                <c:choose>
                                                    <c:when test="${currentPage eq i}">
                                                        <li class="page-item active">
                                                            <a href="#" class="page-link">${i}</a>
                                                        </li>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <li class="page-item">
                                                            <a href="/product?page=${i}&q=${requestScope.kw}&idCategory=${requestScope.idCategory}"
                                                               class="page-link">${i}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <c:if test="${currentPage lt noOfPages}">
                                                <li class="page-item">
                                                    <a href="/product?page=${currentPage + 1}&kw=${requestScope.kw}&idCategory=${requestScope.idCategory}"
                                                       class="page-link"><i class="fa fa-angle-right"></i></a>
                                                </li>
                                            </c:if>

                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <!-- end row -->

                        </div> <!-- container -->

                    </div> <!-- content -->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>