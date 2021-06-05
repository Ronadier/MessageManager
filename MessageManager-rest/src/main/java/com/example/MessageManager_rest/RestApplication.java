package com.example.MessageManager_rest;

import com.google.gson.Gson;

//import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;

@Path("/")
public class RestApplication {
    @POST
//    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/message")
    public String addMessage(String json) {
        String result = "SUCCESS";
        Gson gson = new Gson();
        return gson.toJson(result);
    }
//
}
