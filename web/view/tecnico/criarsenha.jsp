<%-- 
    Document   : criarsenha
    Created on : 05/09/2018, 15:20:53
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <title>Callua Stock</title>
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
                                    <form id="formCadastro" action="${pageContext.request.contextPath}/Usuario?op=criarSenha" method="POST">
                                        <input type="hidden" name="idUsuario" value="${usuario.id}" />
                                        <input type="hidden" name="token" value="${token}" />
                                        <fieldset>
                                            <div class="row">
                                                <div class=" col-md-12">
                                                    <b>Nome: </b><p class="d-inline">${usuario.nome}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class=" col-md-12">
                                                    <b>CPF: </b><p class="d-inline cpfCnpj">${usuario.cpf}</p>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6">
                                                    <label for="senha">Senha</label>
                                                    <input type="password" id="senha" name="senha" class="form-control" placeholder="Senha">
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="senha">Confirmação de senha</label>
                                                    <input type="password" name="confirmacaoSenha" class="form-control" placeholder="Confirmação de senha">
                                                </div>
                                            </div>
                                            <button type="submit" class="btn btn-success float-right" id="cadastrar" name="cadastrar">Cadastrar</button>
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
                    $('header .titulo-header').text('Confirmar cadastro');
                }, 100);
            });
            
            $(document).ready(function(){
                $("#formCadastro").validate({
                    rules: {
                        senha: {
                            required: true,
                            maxlength: 128
                        },
                        confirmacaoSenha: {
                            equalTo: "#senha",
                            required: true,
                            maxlength: 128
                        }
                    },
                    messages: {
                        senha: {
                            required: "Senha é obrigatória !!!",
                            maxlength: "No máximo 128 caracteres na senha !!!"
                        },
                        confirmacaoSenha: {
                            equalTo: "Senha e confirmação diferentes !!!",
                            required: "Confirmação de senha é obrigatória !!!",
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

