<%-- 
    Document   : header
    Created on : Oct 22, 2018, 4:36:27 PM
    Author     : renata
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div class="navbar navbar-expand-sm navbar-dark bg-info shadow-sm">
    <div class="container d-flex justify-content-between">
        <a href="${sessionScope.logado == null ? pageContext.request.contextPath.concat('/view/public/index.jsp') : 'Login?op=dashboard'}" class="navbar-brand d-flex align-items-center">
            <strong>Callua Stock</strong>
        </a>
        <span class="navbar-text titulo-header"></span>
        <c:choose>
            <c:when test="${sessionScope.logado != null && !sessionScope.logado.administrador}">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="logadoDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${logado.nome}</a>
                        <div class="dropdown-menu" aria-labelledby="logadoDropdown">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Produto?op=listar">Produtos</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Usuario?op=dadosForm">Dados cadastrais</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Login?op=logout">Sair</a>
                        </div>
                    </li>
                </ul>
            </c:when>
            <c:when test="${sessionScope.logado != null && sessionScope.logado.administrador}">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="logadoDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${logado.nome}</a>
                        <div class="dropdown-menu" aria-labelledby="logadoDropdown">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Produto?op=listar">Produtos</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Usuario?op=listar">Usuarios</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Usuario?op=dadosForm">Dados cadastrais</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/Login?op=logout">Sair</a>
                        </div>
                    </li>
                </ul>
            </c:when>
        </c:choose>
    </div>
    </div>
    
</header>