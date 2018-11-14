<%-- 
    Document   : abrirchamado
    Created on : 05/09/2018, 14:09:30
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt-BR" />  
<c:if test="${sessionScope.logado == null || !sessionScope.logado.administrador}">
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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/DataTables/datatables.min.css">
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
                     <h4 class="mb-3">Lista de usuários</h4>
                    <div class="form-group">
                        <button class="btn btn-success" type="button" data-toggle="modal" data-target="#modalAdicionarUsuario">Novo Usuário</button>
                    </div>
                    <table id="tableUsuarios" class="table">
                        <thead>
                            <tr>
                                <th style="display: none;">ID</th>
                                <th scope="col">Nome</th>
                                <th scope="col">CPF</th>
                                <th scope="col">Admin</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${usuarios}" var="usuario">
                            <tr>
                                <td style="display: none;">${usuario.id}</td>
                                <td scope="row">${usuario.nome}</td>
                                <td class="cpf">${usuario.cpf}</td>
                                <td>${usuario.administrador == true ? 'Sim' : 'Não'}</td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                </div>
            </div>
            </div>

        </main>
                    

        <form id="formEditarUsuario" action="${pageContext.request.contextPath}/Usuario?op=editarUm" method="POST">
        <div class="modal fade" id="modalVisualizarUsuario" tabindex="-1" role="dialog" aria-labelledby="modalVisualizarUsuarioLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <input type="hidden" id="idUsuario" name="idUsuario">
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label for="descricao">Nome</label>
                                    <input type="text" id="nome" name="nome" class="form-control" val="${usuario.nome}"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label for="cpf">CPF</label>
                                    <input type="text" id="cpf" name="cpf" class="form-control cpf" val="${usuario.cpf}"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label for="administrador">Função</label>
                                    <select class="form-control" id="administrador" name="administrador">
                                        <option value="false" ${usuario.administrador == true ? '' : 'selected'}>Técnico</option>
                                        <option value="true" ${usuario.administrador == true ? 'selected' : ''}>Administrador</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-warning" data-dismiss="modal">Cancelar</button>
                        <a id="linkRemover" class="btn btn-danger">Remover</a>
                        <button type="submit" class="btn btn-success">Salvar</button>
                    </div>
                </div>
            </div>
        </div>
        </form>

        <form id="formAdicionarUsuario" action="${pageContext.request.contextPath}/Usuario?op=adicionarUm" method="POST">
        <div class="modal fade" id="modalAdicionarUsuario" tabindex="-1" role="dialog" aria-labelledby="modalAdicionarUsuarioLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label>Nome</label>
                                    <input type="text" name="nome" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label>CPF</label>
                                    <input type="text" name="cpf" class="form-control cpf" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label>Função</label>
                                    <select class="form-control" name="administrador">
                                        <option value="false">Técnico</option>
                                        <option value="true">Administrador</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-warning" data-dismiss="modal">Cancelar</button>
                        <button type="submit" class="btn btn-success">Salvar</button>
                    </div>
                </div>
            </div>
        </div>
        </form>
                    
        <div id="footer"><%@ include file="../public/footer.jsp" %></div>
        
        <script src="${pageContext.request.contextPath}/resources/jquery-3.3.1/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/popper.js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jQuery-Mask-Plugin-master/dist/jquery.mask.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/jquery-validation-1.17.0/dist/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/customValidations.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <script src="${pageContext.request.contextPath}/resources/DataTables/datatables.min.js"></script>
        <%@ include file="../public/initializeJS.jsp" %>
        <script>
            $(document).ready( function () {
                <c:if test="${not empty produto}">
                    $('#modalAdicionarUsuario').modal("show");
                </c:if>

                var table = $('#tableUsuarios').DataTable({
                    "language": {
                        "url": "${pageContext.request.contextPath}/resources/DataTables/Portuguese-Brasil.json"
                    }
                });
                
                $('#tableUsuarios tbody').on('click', 'tr', function () {
                    var tab = table.row( this ).data();
                    var url = "Usuario";
                    var context = '${pageContext.request.contextPath}';
                    $.ajax({
                        url : url, // URL da sua Servlet
                        data : {
                            op: "carregarUmViaAjax",
                            idUsuario : tab[0]
                        }, // Parâmetro passado para a Servlet
                        dataType : 'json',
                        success : function(data) {
                            $('#modalVisualizarUsuario #idUsuario').val(data.id);
                            $('#modalVisualizarUsuario #nome').val(data.nome);
                            $('#modalVisualizarUsuario #cpf').val(data.cpf);
                            $('#cpf').trigger('input');
                            if (data.administrador) $('#modalVisualizarUsuario #administrador').val("true");
                            else $('#modalVisualizarUsuario #administrador').val("false");
                            $('#modalVisualizarUsuario #linkRemover').attr("href", context+"/Usuario?op=removerUm&idUsuario="+data.id);
                            $('#modalVisualizarUsuario').modal("show");
                        },
                        error : function(request, textStatus, errorThrown) {
                            alert(request.status + ', Error: ' + request.statusText);
                             // Erro
                        }
                    });
                } );
                
                $("#formAdicionarUsuario").validate({
                    rules: {
                        nome: {
                            required: true,
                            maxlength: 128
                        },
                        cpf: {
                            required: true,
                            cpfValido: true
                        }
                    },
                    messages: {
                        nome: {
                            required: "Nome é obrigatório !!!",
                            maxlength: "No máximo 128 caracteres no nome !!!"
                        },
                        cpf: {
                            required: "CPF é obrigatório !!!"
                        }
                    },
                    submitHandler: function(form) {
                        form.submit();
                    }
                });
                
                $("#formEditarUsuario").validate({
                    rules: {
                        nome: {
                            required: true,
                            maxlength: 128
                        },
                        cpf: {
                            required: true,
                            cpfValido: true
                        }
                    },
                    messages: {
                        nome: {
                            required: "Nome é obrigatório !!!",
                            maxlength: "No máximo 128 caracteres no nome !!!"
                        },
                        cpf: {
                            required: "CPF é obrigatório !!!"
                        }
                    },
                    submitHandler: function(form) {
                        form.submit();
                    }
                });
            } );
        </script>
    </body>
</html>
