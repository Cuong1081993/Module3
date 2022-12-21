<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Edit Customer</title>

    <jsp:include page="WEB-INF/layout/headcss.jsp"/>

</head>
<!-- Navigation Bar-->
<jsp:include page="WEB-INF/layout/navbar_custom.jsp"></jsp:include>
<!-- End Navigation Bar-->
<!-- ============================================================== -->
<!-- Start Page Content here -->
<!-- ============================================================== -->
<body data-layout="horizontal">

<!-- Begin page -->
<div id="wrapper">

    <!-- Navigation Bar-->
    <jsp:include page="/WEB-INF/layout/navbar_custom.jsp"></jsp:include>
    <!-- End Navigation Bar-->

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
                                    <li class="breadcrumb-item"><a href="javascript: void(0);">Customer</a></li>
                                    <li class="breadcrumb-item"><a href="javascript: void(0);">Action</a></li>
                                    <li class="breadcrumb-item active">Edit Form</li>
                                </ol>
                            </div>
                            <h4 class="page-title">Edit Customer </h4>
                        </div>
                    </div>
                </div>
                <!-- end page title -->
                <div class="row">
                    <div class="col-sm-12">
                        <form class="form-horizontal" method="post">
                            <c:if test="${requestScope.message != null}">
                                <%--      <h1 style="display: none" class="alert">${requestScope.message}</h1>--%>
                                <script>
                                    let message = '<%= request.getAttribute("message")%>'
                                    window.onload = function (){
                                        Swal.fire({
                                            position: 'top-center',
                                            icon: 'success',
                                            title: message,
                                            showConfirmButton: false,
                                            timer: 1500
                                        })
                                    }
                                </script>
                            </c:if>
                            <c:if test="${requestScope.errors!=null}">
                                <div class="alert alert-danger" role="alert">
                                    <ul>
                                        <c:forEach items="${requestScope.errors}" var="error">
                                            <li>${error}</li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Product Name</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" name = "name" value="${requestScope.product.getName()}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label" for="image">Image</label>
                                <div class="col-md-10">
                                    <input type="text"  id="image"  name="image" class="form-control"  value="${requestScope.product.getImage()}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Price</label>
                                <div class="col-md-10">
                                    <input type="number" class="form-control" name = "price" value="${requestScope.product.getPrice()}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Title</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" name = "title" value="${requestScope.product.getTitle()}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Description</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" name = "description" value="${requestScope.product.getDescription()}">
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-md-10 offset-2">
                                    <button >Edit</button>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
                <!-- end row -->

            </div>
            <!-- end container-fluid -->

        </div>
        <!-- end content -->



        <!-- Footer Start -->
        <jsp:include page="/WEB-INF/layout/footer.jsp"></jsp:include>
        <!-- end Footer -->

    </div>

    <!-- ============================================================== -->
    <!-- End Page content -->
    <!-- ============================================================== -->

</div>
<!-- END wrapper -->

<!-- Right Sidebar -->
<jsp:include page="/WEB-INF/layout/right_bar.jsp"></jsp:include>
</body>

</html>
