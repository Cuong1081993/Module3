<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Customer Management</title>
    <jsp:include page="/WEB-INF/layout/headcss.jsp"></jsp:include>

</head>

<body>

<!-- Begin page -->
<div id="wrapper">

    <jsp:include page="WEB-INF/layout/navbar_custom.jsp"></jsp:include>


    <jsp:include page="WEB-INF/layout/left_side_menu.jsp"></jsp:include>

    <!-- ============================================================== -->
    <!-- Start Page Content here -->
    <!-- ============================================================== -->

    <div class="content-page">
        <div class="content">

            <!-- Start Content-->
            <div class="container-fluid">

                <!-- start page title -->
                <div class="row">
                    <div class="col-12">
                        <div class="page-title-box">
                            <div class="page-title-right">
                                <ol class="breadcrumb m-0">
                                    <li class="breadcrumb-item"><a href="javascript: void(0);">Minton</a></li>
                                    <li class="breadcrumb-item active">Dashboard</li>
                                </ol>
                            </div>
                            <h4 class="page-title">Welcome !</h4>
                        </div>
                    </div>
                </div>
                <!-- end page title -->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="demo-box p-2">
                            <div class="row row-search-form head-search-table" >
                                <div>
                                    <h1 class="header-title">List Customer</h1>
                                </div>
                                <div>
                                    <form style="margin-bottom: 20px; width: 200px"  action="get">
                                        <button type="button" class="btn btn-warning">
                                            <a href="/product?action=create"><i
                                                    class="fa fa-plus">Add Customer</i></a>
                                        </button>
                                    </form>
                                </div>
                                <div>
                                    <form method="get" action="/customer" class="search-form head-search-table-right">
                                        <button class="mr-1">Search</button>
                                        <input type="text" name="kw" class="mr-1" value="${requestScope.kw}">
                                        <select name="idCountry" id="idCountry" class="form-control" class="mr-1">
                                            <option  value="-1">All</option>
                                            <c:forEach items="${applicationScope.countries}" var="country">
                                                <option value="${country.getId()}" <c:if
                                                        test="${requestScope.idCountry == country.getId()}"> selected</c:if>>${country.getName()}
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
                                            <th>Full Name</th>
                                            <th>Address</th>
                                            <th>Country</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${requestScope.customers}" var="customer">
                                            <tr>
                                                <th scope="row">${customer.getId()}</th>
                                                <td>${customer.getName()}</td>
                                                <td>${customer.getAddress()}</td>

                                                <c:forEach items="${requestScope.countries}" var="country">
                                                    <c:if test="${country.getId() == customer.getIdCountry()}">
                                                        <td>${country.getName()}</td>
                                                    </c:if>
                                                </c:forEach>
                                                <td>
                                                    <a href="/customer?action=edit&id=${customer.getId()}"><i class="fa fa-edit"></i> </a>
                                                    <a href="/customer?action=delete&id=${customer.getId()}"><i class="fa fa-remove"></i></a>
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
                                                            <a href="/customer?page=${i}&q=${requestScope.kw}&idCountry=${requestScope.idCountry}"
                                                               class="page-link">${i}</a>
                                                        </li>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <c:if test="${currentPage lt noOfPages}">
                                                <li class="page-item">
                                                    <a href="/customer?page=${currentPage + 1}&kw=${requestScope.kw}&idCountry=${requestScope.idCountry}"
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

                    <jsp:include page="WEB-INF/layout/footer.jsp"/>

                </div>

                <!-- ============================================================== -->
                <!-- End Page content -->
                <!-- ============================================================== -->


            </div>
            <!-- END wrapper -->

            <!-- Right Sidebar -->
            <jsp:include page="WEB-INF/layout/right_bar.jsp"></jsp:include>
            <!-- /Right-bar -->

            <!-- Right bar overlay-->
            <div class="rightbar-overlay"></div>

            <!-- Vendor js -->
            <script src="/assets\js\vendor.min.js"></script>

            <script src="/assets\libs\jquery-knob\jquery.knob.min.js"></script>
            <script src="/assets\libs\peity\jquery.peity.min.js"></script>

            <!-- Sparkline charts -->
            <script src="/assets\libs\jquery-sparkline\jquery.sparkline.min.js"></script>

            <!-- init js -->
            <script src="/assets\js\pages\dashboard-1.init.js"></script>

            <!-- App js -->
            <script src="/assets\js\app.min.js"></script>

</body>
</html>
