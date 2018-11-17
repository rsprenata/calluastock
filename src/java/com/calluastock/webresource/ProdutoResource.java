/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calluastock.webresource;

import com.calluastock.bean.Produto;
import com.calluastock.facade.ProdutoFacade;
import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author renata
 */
@Path("produtos")
public class ProdutoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProdutoResource
     */
    public ProdutoResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProdutos() {
        List<Produto> produtos = ProdutoFacade.buscarDisponiveis();
        GenericEntity<List<Produto>> lista = new GenericEntity<List<Produto>>(produtos){};
        return Response.ok().entity(lista).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produto getProduto(@PathParam("id") Integer id) {
        Produto produto = ProdutoFacade.buscarUm(id);
        
        return produto;
    }
    
    @PUT
    @Path("/utilizar/{codigo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response utilizar(Produto p, @PathParam("codigo") int codigo) {
        
        Produto produto = ProdutoFacade.buscarUm(codigo);
        if (produto.getQuantidade() - p.getQuantidade() < 0) {
            return Response.serverError().build();
        } else {
            ProdutoFacade.utilizar(produto, p.getQuantidade());
            return Response.ok().build();
        }
    }
}
