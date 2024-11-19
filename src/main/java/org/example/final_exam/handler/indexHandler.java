package org.example.final_exam.handler;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class indexHandler {
    @GET
    public String index() {
        return "Hello World!";
    }
}
