<script> 
    $(document).ready(function(){
        <c:if test="${not empty mensagem}">
            swal({
                title: '${mensagem.tipo == "error" ? "Erro" : "Sucesso"}!',
                text: '${mensagem.texto}',
                type: '${mensagem.tipo}',
                confirmButtonText: 'Ok'
            });
            <c:remove var="mensagem" scope="session" />
        </c:if>
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>