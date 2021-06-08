package rest;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/message")
public class RestApplication {
    InitialContext context;

    {
        try {
            context = new InitialContext();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Resource

    @POST
    public String addMessage(String json) {
        return "SUCCESS";
    }

    @DELETE
    @Path("/{id}")
    public String deleteMessage(@PathParam("id")Integer id) {
        return "SUCCESS, " + id;
    }
}
