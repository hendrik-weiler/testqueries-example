package com.hendrikweiler.testqueries;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    public class Greeting {
        public String greeting;
        public String language;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}")
    public Greeting doGreeting(@PathParam("name") String someValue, @QueryParam("language") String language) {
        var greeting = new Greeting();
        greeting.greeting = "Hello " + someValue;
        greeting.language = language;
        return greeting;
    }
}
