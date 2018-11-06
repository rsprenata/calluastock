<%-- 
    Document   : login
    Created on : 05/09/2018, 13:52:58
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <title>Callua Stock System</title>
    </head>
    <body>
        <div id="header"><%@ include file="header.jsp" %></div>
        <main role="main">
            <div class="py-5 bg-light">
                <div class="container">
                    <div class="row">
                        <fieldset class="login">
                            <div class="card">
                                <div class="card-body">
                                    <form id="formLogin" action="${pageContext.request.contextPath}/Login?op=logar" method="POST">
                                        <div class="form-group">
                                            <label for="login">CPF:</label>
                                            <input type="text" name="cpf" class="form-control cpf" autofocus>
                                        </div>
                                        <div class="form-group">
                                            <label for="senha">Senha:</label>
                                            <input type="password" name="senha" class="form-control">
                                        </div>
                                        <div class="form-group text-center">
                                            <button type="submit" class="btn btn-success" id="entrar" name="entrar">Entrar</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </main>
        <div id="footer"><%@ include file="footer.jsp" %></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery-Mask-Plugin-master/dist/jquery.mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/customValidations.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <%@ include file="initializeJS.jsp" %>
        <script> 
            $(document).ready(function(){
                $("#formLogin").validate({
                    rules: {
                        cpf: {
                            required: true,
                            cpfValido: true
                        },
                        senha: {
                            required: true,
                            maxlength: 128
                        }
                    },
                    messages: {
                        cpf: {
                            required: "CPF é obrigatório !!!"
                        },
                        senha: {
                            required: "Senha é obrigatória !!!",
                            maxlength: "No máximo 128 caracteres na senha !!!"
                        }
                    },
                    submitHandler: function(form) {
                        form.submit();
                    }
                });
            });
        </script>
    </body>
</html>
