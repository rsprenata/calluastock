<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" errorPage="../public/erro.jsp"%>
<c:if test="${sessionScope.logado == null || sessionScope.logado == null || !sessionScope.logado.administrador}">
    <jsp:useBean id="mensagem" class="com.calluastock.util.Mensagem">
        <jsp:setProperty name="mensagem" property="texto" value="Acesso não autorizado"/>
        <jsp:setProperty name="mensagem" property="tipo" value="error"/>
    </jsp:useBean>
    <c:set var="mensagem" value="${mensagem}" scope="session" />
    <jsp:forward page="/Login?op=dashboard" />
</c:if>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-4.1.3-dist/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/fontawesome-free-5.4.1-web/css/all.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/sweetalert2-7.28.8/dist/sweetalert2.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/bootstrap-datepicker-1.6.4-dist/css/bootstrap-datepicker.min.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
        <title>Callua Stock</title>
    </head>
    <body>
        <div id="header"><%@ include file="../public/header.jsp" %></div>

        <main role="main">
            <div class="py-5 bg-light">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <select class="form-control" id="selectRelatorio">
                            <option value="">Selecione o relatório</option>
                            <option value="1">Produtos disponíveis</option>
                        </select>
                    </div>
                </div>
                <div id="parametrosRelatorio1" class="row col-md-12 parametrizacao" style="display: none;">
                    <form action="${pageContext.request.contextPath}/Relatorio?op=gerar" method="POST" target="_blank">
                        <input type="hidden" value="produtosDisponiveis" name="relatorio" />
                        <br/>
                        <div class="row col-md-12 text-center">
                            <button class="btn btn-lg btn-success" type="submit">
                                 Gerar <i class="fa fa-file-pdf pull-right"></i>
                            </button>
                        </div>
                    </form>
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
        <script src="${pageContext.request.contextPath}/resources/list.js/list.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/masks.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-datepicker-1.6.4-dist/js/bootstrap-datepicker.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/bootstrap-datepicker-1.6.4-dist/locales/bootstrap-datepicker.pt-BR.min.js"></script>
        <%@ include file="../public/initializeJS.jsp" %>
        <script>
            $(function(){
                setTimeout(() => {
                    $('header .titulo-header').text('Relatórios');
                }, 100);
                
                $(".datepicker").datepicker({
                    format: 'dd/mm/yyyy',
                    language: 'pt-BR'
                });
            });
            
            $('#selectRelatorio').change(function (e) {
                var relatorio_id = this.value;

                if (relatorio_id) {
                    $('.parametrizacao').hide(255);
                    $('#parametrosRelatorio' + relatorio_id).show(510);
                }
            });
            
        </script>
    </body>
</html>
