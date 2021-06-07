package com.example.MessageManager_rest;

import com.google.gson.Gson;

//import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;

@Path("/message")
public class RestApplication {
    @POST
    public String addMessage(String json) {
        String result = "SUCCESS";
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    @DELETE
    @Path("/{id}")
    public String deleteMessage(@PathParam("id")Integer id) {
        return "SUCCESS, " + id;
    }
}
