<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Create Customer</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<!-- Navigation Bar-->

<!-- End Navigation Bar-->
<!-- ============================================================== -->
<!-- Start Page Content here -->
<!-- ============================================================== -->
<body data-layout="horizontal">

<!-- Begin page -->
<div id="wrapper">

    <!-- Navigation Bar-->

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
                                    <li class="breadcrumb-item"><a href="javascript: void(0);">Create</a></li>
                                    <li class="breadcrumb-item active">Create Form</li>
                                </ol>
                            </div>
                            <h4 class="page-title">Create Customer</h4>
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
                                <label class="col-md-2 control-label">Name</label>
                                <div class="col-md-10">
                                    <input type="text" readonly class="form-control" name = "name" value="${requestScope.product.getNameProduct()}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label" for="address">Price</label>
                                <div class="col-md-10">
                                    <input type="text" readonly id="address"  name="price" class="form-control"  value="${requestScope.product.getPrice()}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Quantity</label>
                                <div class="col-md-10">
                                    <input type="text" readonly class="form-control" name = "quantity" value="${requestScope.product.getQuantity()}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Color</label>
                                <div class="col-md-10">
                                    <input type="text" readonly class="form-control" name = "color" value="${requestScope.product.getColor()}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label">Description</label>
                                <div class="col-md-10">
                                    <input type="text" readonly class="form-control" name = "description" value="${requestScope.product.getDescription()}">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-2 control-label"></label>
                                <div class="col-md-10">
                                    <select name="idCategory"  id="idCategory" class="form-control" >
                                        <c:forEach items="${applicationScope.categories}" var="category">
                                            <option value="${category.getIdCategory()}">${category.getNameCategory()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-md-10 offset-2">
                                    <button >Delete</button>
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

        <!-- end Footer -->

    </div>

    <!-- ============================================================== -->
    <!-- End Page content -->
    <!-- ============================================================== -->

</div>
<!-- END wrapper -->

<!-- Right Sidebar -->
</body>
</html>

