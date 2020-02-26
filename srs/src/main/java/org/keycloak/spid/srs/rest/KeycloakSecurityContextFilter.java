package org.keycloak.spid.srs.rest;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.wildfly.swarm.keycloak.deployment.KeycloakSecurityContextAssociation;

/**
 * This filter shows how to register a custom JAX-RS SecurityContext and access KeyCloak tokens  
 */
@Provider
public class KeycloakSecurityContextFilter implements ContainerRequestFilter {

    final static Logger logger = Logger.getLogger("org.keycloak.spid");
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // KeyCloak sets a principal name to the access token subject
        // which is a unique (UUID/etc) identifier. The custom security context
        // sets the principal name to the preferred user name instead.
        
        
        final SecurityContext securityContext = requestContext.getSecurityContext();
        // Simplifying for the demo purposes only
        final Principal kcPrincipal = () -> {
            return KeycloakSecurityContextAssociation.get().getToken().getPreferredUsername();
        };
        
        requestContext.setSecurityContext(new SecurityContext() {

            @Override
            public Principal getUserPrincipal() {
                logger.info("================= [getuserPrincipal]");
                return kcPrincipal;
            }

            @Override
            public boolean isUserInRole(String role) {
                logger.info("================= [isUserInRole]");
                return securityContext.isUserInRole(role);
            }

            @Override
            public boolean isSecure() {
                logger.info("================= [isSecure]");
                return securityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                logger.info("================= [getAuthenticationScheme]");
                return securityContext.getAuthenticationScheme();
            }            
        });
    }

}