package org.binqua.testing.csd.client.mockito;


import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/prefix1")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface ACollaboratorWithNoPathAtMethodLevelResource {

    @GET
    ABean getABeanWithNoRequestParameters();


}
