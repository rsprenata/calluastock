<%-- 
    Document   : abrirchamado
    Created on : 05/09/2018, 14:09:30
    Author     : renata
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt-BR" />  
<c:if test="${sessionScope.logado == null || (sessionScope.logado == null || !sessionScope.logado.administrador)}">
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
                     <h4 class="mb-3">Lista de produtos</h4>
                    <div class="form-group">
                        <button class="btn btn-success" type="button" data-toggle="modal" data-target="#modalAdicionarProduto">Novo Produto</button>
                    </div>
                    <table id="tableProdutos" class="table">
                        <thead>
                            <tr>
                                <th scope="col">Código</th>
                                <th scope="col">Descrição</th>
                                <th scope="col">Valor</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${produtos}" var="produto">
                            <tr>
                                <th scope="row">${produto.id}</th>
                                <td>${produto.descricao}</td>
                                <td><fmt:formatNumber value="${produto.valor}" type="currency" /></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                </div>
            </div>
            </div>

        </main>
                    

        <form id="formEditarProduto" action="${pageContext.request.contextPath}/Produto?op=editarUm" method="POST">
        <div class="modal fade" id="modalVisualizarProduto" tabindex="-1" role="dialog" aria-labelledby="modalVisualizarProdutoLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <input type="hidden" id="idProduto" name="idProduto">
                        <div class="row">
                            <div class="col-md-12">
                                <b>Código: </b><p id="mpCodigo" class="d-inline"></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label for="descricao">Descrição</label>
                                    <textarea type="text" id="descricao" name="descricao" class="form-control" placeholder="Descrição" rows="3">${produto.descricao}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label for="login">Valor</label>
                                    <input type="text" id="valor" name="valor" class="form-control dinheiro" val="${produto.valor.replace("\\.", ",")}">
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

        <form id="formAdicionarProduto" action="${pageContext.request.contextPath}/Produto?op=adicionarUm" method="POST">
        <div class="modal fade" id="modalAdicionarProduto" tabindex="-1" role="dialog" aria-labelledby="modalAdicionarProdutoLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label>Código: ${proximoCod}</label>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label for="descricao">Descrição</label>
                                    <textarea type="text" name="descricao" class="form-control" placeholder="Descrição" rows="3"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class=" col-md-12">
                                <div class="form-group">
                                    <label for="login">Valor</label>
                                    <input type="text" name="valor" class="form-control dinheiro">
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
                    $('#modalAdicionarProduto').modal("show");
                </c:if>

                var table = $('#tableProdutos').DataTable({
                    "language": {
                        "url": "${pageContext.request.contextPath}/resources/DataTables/Portuguese-Brasil.json"
                    }
                });
                
                $('#tableProdutos tbody').on('click', 'tr', function () {
                    var tab = table.row( this ).data();
                    var url = "Produto";
                    var context = '${pageContext.request.contextPath}';
                    $.ajax({
                        url : url, // URL da sua Servlet
                        data : {
                            op: "carregarUmViaAjax",
                            idProduto : tab[0]
                        }, // Parâmetro passado para a Servlet
                        dataType : 'json',
                        success : function(data) {
                            $('#modalVisualizarProduto #idProduto').val(data.id);
                            $('#modalVisualizarProduto #mpCodigo').html(data.id);
                            $('#modalVisualizarProduto #descricao').html(data.descricao);
                            $('#modalVisualizarProduto #valor').val(data.valor.toLocaleString('pt-br'));
                            $('#modalVisualizarProduto #linkRemover').attr("href", context+"/Produto?op=removerUm&idProduto="+data.id);
                            $('#modalVisualizarProduto').modal("show");
                        },
                        error : function(request, textStatus, errorThrown) {
                            alert(request.status + ', Error: ' + request.statusText);
                             // Erro
                        }
                    });
                } );
                
                $("#formAdicionarProduto").validate({
                    rules: {
                        descricao: {
                            required: true,
                            maxlength: 1024
                        },
                        valor: {
                            required: true
                        }
                    },
                    messages: {
                        descricao: {
                            required: "Descrição é obrigatória !!!",
                            maxlength: "No máximo 1024 caracteres na descrição!!!"
                        },
                        valor: {
                            required: "Valor é obrigatório !!!"
                        }
                    },
                    submitHandler: function(form) {
                        form.submit();
                    }
                });
                
                $("#formEditarProduto").validate({
                    rules: {
                        descricao: {
                            required: true,
                            maxlength: 1024
                        },
                        valor: {
                            required: true
                        }
                    },
                    messages: {
                        descricao: {
                            required: "Descrição é obrigatória !!!",
                            maxlength: "No máximo 1024 caracteres na descrição!!!"
                        },
                        valor: {
                            required: "Valor é obrigatório !!!"
                        }
                    },
                    submitHandler: function(form) {
                        form.submit();
                    }
                });
            } );
            
            function visualizarChamado(idChamado){
                var url = "Chamado";
                $.ajax({
                    url : url, // URL da sua Servlet
                    data : {
                        op: "carregarViaAjax",
                        idChamado : idChamado
                    }, // Parâmetro passado para a Servlet
                    dataType : 'json',
                    success : function(data) {
                        $('#mpChamado').html(data.id);
                        $('#mpCliente').html(data.cliente.nome);
                        $('#mpSituacao').html(data.status);
                        $('#mpTitulo').html(data.titulo);
                        $('#mpEndereco').html(data.endereco.endereco);
                        $('#mpDescricao').html(data.descricao);
                        $('#modalVisualizarChamado').modal("show");
                    },
                    error : function(request, textStatus, errorThrown) {
                        alert(request.status + ', Error: ' + request.statusText);
                         // Erro
                    }
                });
            } 
        </script>
    </body>
</html>
