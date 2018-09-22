package org.binqua.testing.csd.client.mockito;


import jdk.nashorn.internal.objects.annotations.Getter;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/prefix1")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public interface ACollaboratorResource {

    @GET
    @Path("prefix2")
    ABean getABeanWithNoRequestParameters();

    @GET
    @Path("prefix2/{aRequestArgument0}")
    ABean getABeanWith1RequestParameters(@PathParam("aRequestArgument0") RequestArgument requestArgument0);

    @GET
    @Path("prefix2/{aRequestArgument0}/{aRequestArgument1}")
    ABean getABeanWith2RequestParameters(@PathParam("aRequestArgument0") RequestArgument requestArgument0, @PathParam("aRequestArgument1") RequestArgument requestArgument1);

    @GET
    @Path("prefix2/{aRequestArgument0}")
    ABean getABeanWith1RequestParametersAndAnotherNoPathParamAsFirstParameter(String aString, @PathParam("aRequestArgument0") RequestArgument requestArgument0);

    @GET
    @Path("prefix2/{aRequestArgument0}")
    ABean getABeanWith1RequestParametersAndAnotherNoPathParamAsLastParameter(@PathParam("aRequestArgument0") RequestArgument requestArgument0, String aString);

    @POST
    @Path("prefix2/{aRequestArgument}")
    ABean postABeanWith1RequestParameter(@PathParam("aRequestArgument") RequestArgument requestArgument);

    @GET
    ABean getMethod();

    @DELETE
    ABean deleteMethod();

    @POST
    ABean postMethod();

    @PUT
    ABean putMethod();

    ABean methodWithNoAnnotation();

    @PUT
    @GET
    ABean methodWithMoreTheOneHttpAnnotation();


}
