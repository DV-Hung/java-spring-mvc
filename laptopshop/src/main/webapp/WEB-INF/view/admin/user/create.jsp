<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Create User - Hỏi Dân IT</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgUrl = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgUrl);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Create User</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item "><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Users</li>
                                    <li class="breadcrumb-item active">Create</li>
                                </ol>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Create User</h3>
                                            <hr>
                                            <form:form method="post" action="/admin/user/create"
                                                modelAttribute="newUser" class="row">
                                                <div class="col-12 col-md-6 mb-3">
                                                    <label class="form-label">Email </label>
                                                    <form:input type="email" class="form-control" path="email" />
                                                </div>
                                                <div class="col-12 col-md-6 mb-3">
                                                    <label class="form-label">Password</label>
                                                    <form:input type="password" class="form-control" path="password" />
                                                </div>
                                                <div class="col-12 col-md-6 mb-3">
                                                    <label for="exampleInputPhone1" class="form-label">Phone</label>
                                                    <form:input type="text" class="form-control" path="phone" />
                                                </div>
                                                <div class="col mb-3">
                                                    <label class="form-label">Fullname</label>
                                                    <form:input type="text" class="form-control" path="fullName" />
                                                </div>

                                                <div class="col-12 mb-3">
                                                    <label class="form-label">Address</label>
                                                    <form:input type="text" class="form-control" path="address" />
                                                </div>

                                                <div class="col-12 col-md-6 mb-3">
                                                    <label class="form-label">Role:</label>
                                                    <select class="form-select">
                                                        <option value="1">ADMIN</option>
                                                        <option value="2">USER</option>

                                                    </select>
                                                </div>
                                                <div class="col-12 col-md-6 mb-3">
                                                    <label for="avatarFile" class="form-label">Avatar:</label>
                                                    <input class="form-control" type="file" id="avatarFile"
                                                        accept=" .png, .jpg, .jpeg">
                                                </div>
                                                <div class="col-12 col-md-6 mb-3">
                                                    <img alt="avatar preview" style="max-height: 250px; display: none;"
                                                        id="avatarPreview" />
                                                </div>
                                                <div class="col-12 mb-5">
                                                    <button type="submit" class="btn btn-primary ">Create</button>
                                                </div>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>

            </html>