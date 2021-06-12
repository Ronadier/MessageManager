package rest;

import jms.JMSproducer;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/message")
public class RestApplication {

    @POST
    public String addMessage(String message) {
        return JMSproducer.addMessage(message);
    }

    @DELETE
    @Path("/{id}")
    public String deleteMessage(@PathParam("id") Integer id) {
        return JMSproducer.deleteMessage(id);
    }
}
