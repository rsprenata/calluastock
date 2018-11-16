<%-- 
    Document   : novocliente
    Created on : 05/09/2018, 14:00:30
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.logado == null}">
    <jsp:useBean id="mensagem" class="com.calluastock.util.Mensagem">
        <jsp:setProperty name="mensagem" property="texto" value="Acesso não autorizado"/>
        <jsp:setProperty name="mensagem" property="tipo" value="error"/>
    </jsp:useBean>
    <c:set var="mensagem" value="${mensagem}" scope="session" />
    <jsp:forward page="/Login?op=dashboard" />
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <title>Callua Stock - Dados cadastrais</title>
    </head>
    <body>
        <div id="header"><%@ include file="../public/header.jsp" %></div>
        <main role="main">
            <div class="py-5 bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 order-md-1">
                            <div class="card">
                                <div class="card-body">
                                    <form id="formCadastro" action="${pageContext.request.contextPath}/Usuario?op=editarDados" method="POST">
                                        <fieldset>
                                            <div class="form-group">
                                                <label for="nome">Nome</label>
                                                <input type="text" name="nome" class="form-control" placeholder="Nome" value="${logado.nome}" autofocus>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-4">
                                                    <label for="cpf">CPF</label>
                                                    <input type="text" id="cpf" name="cpf" class="form-control cpf" value="${logado.cpf}" placeholder="CPF">
                                                </div>
                                                <div class="form-group col-md-4">
                                                    <label for="email">Email</label>
                                                    <input type="email" name="email" class="form-control" value="${logado.email}" placeholder="Email">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-4">
                                                    <label for="senha">Senha atual</label>
                                                    <input type="password" id="senhaAtual" name="senhaAtual" class="form-control" placeholder="Senha">
                                                    <small>Deixe esse campo vazio caso não queira editar a senha.</small>
                                                </div>
                                                <div class="form-group col-md-4">
                                                    <label for="senha">Nova senha</label>
                                                    <input type="password" id="senha" name="senha" class="form-control" placeholder="Senha">
                                                </div>
                                                <div class="form-group col-md-4">
                                                    <label for="senha">Confirmação da nova senha</label>
                                                    <input type="password" name="confirmacaoSenha" class="form-control" placeholder="Confirmação de senha">
                                                </div>
                                            </div>
                                            <button type="submit" class="btn btn-primary float-right" id="cadastrar" name="cadastrar">Editar</button>
                                        </fieldset>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <div id="footer"><%@ include file="../public/footer.jsp" %></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery-Mask-Plugin-master/dist/jquery.mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/customValidations.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <%@ include file="../public/initializeJS.jsp" %>
        <script> 
            $(function(){
                setTimeout(() => {
                    $('header .titulo-header').text('Dados cadastrais');
                }, 100);
            });
            
            $(document).ready(function(){
                $("#formCadastro").validate({
                    rules: {
                        nome: {
                            required: true,
                            maxlength: 128
                        },
                        cpf: {
                            required: true,
                            cpfValido: true
                        },
                        email:  {
                            required: true,
                            email: true,
                            maxlength: 128
                        },
                        senhaAtual: {
                            maxlength: 128
                        },
                        senha: {
                            maxlength: 128
                        },
                        confirmacaoSenha: {
                            equalTo: "#senha",
                            maxlength: 128
                        }
                    },
                    messages: {
                        nome: {
                            required: "Nome é obrigatório !!!",
                            maxlength: "No máximo 128 caracteres no nome !!!"
                        },
                        cpf: {
                            required: "CPF é obrigatório !!!"
                        },
                        email : {
                            required: "Email é obrigatório !!!",
                            email: "Email inválido !!!",
                            maxlength: "No máximo 128 caracteres no email !!!"
                        },
                        senhaAtual: {
                            maxlength: "No máximo 128 caracteres na senha !!!"
                        },
                        senha: {
                            maxlength: "No máximo 128 caracteres na senha !!!"
                        },
                        confirmacaoSenha: {
                            equalTo: "Senha e confirmação diferentes !!!",
                            maxlength: "No máximo 128 caracteres na confirmação da senha !!!"
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
