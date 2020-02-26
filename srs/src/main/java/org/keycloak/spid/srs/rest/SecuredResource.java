package org.keycloak.spid.srs.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/secured")
public class SecuredResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@Context SecurityContext sc) {
        String name = "Unknown";
        try{
            name = sc.getUserPrincipal().getName();
        }catch(Exception e){
            e.printStackTrace();            
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("{ \"result\": \"success\", ");
        buffer.append("{ \"name\": \"").append(name).append("\" ");
        buffer.append("}");
        return buffer.toString();
    }

}