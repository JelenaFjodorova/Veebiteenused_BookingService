/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ttu.idu0075.booking;

import ee.ttu.idu0075._2015.ws.hotell.AddHotellRequest;
import ee.ttu.idu0075._2015.ws.hotell.GetHotellListRequest;
import ee.ttu.idu0075._2015.ws.hotell.GetHotellListResponse;
import ee.ttu.idu0075._2015.ws.hotell.GetHotellRequest;
import ee.ttu.idu0075._2015.ws.hotell.HotellType;
import java.math.BigInteger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author jelena
 */
@Path("hotells")
public class HotellsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HotellsResource
     */
    public HotellsResource() {
    }

    /**
     * Retrieves representation of an instance of ee.ttu.idu0075.booking.HotellsResource
     * @param token
     * @return an instance of ee.ttu.idu0075._2015.ws.hotell.HotellType
     */
     @GET
    @Produces("application/json")
    public GetHotellListResponse getHotellList(@QueryParam("token") String token){
         BookingWebService ws = new BookingWebService ();
         GetHotellListRequest request = new GetHotellListRequest();
         request.setToken(token);
         return ws.getHotellList(request);
    }
    
    @GET
    @Path("{id : \\d+}")
    @Produces("application/json")
    public HotellType getHotell(@PathParam("id") String id,
            @QueryParam("token") String token) {
        BookingWebService ws = new BookingWebService ();
        GetHotellRequest request = new GetHotellRequest();
        request.setId(BigInteger.valueOf(Integer.parseInt(id)));
        request.setToken(token);
        return ws.getHotell(request);
    }

   @POST
   @Consumes("application/json")
   @Produces("application/json")
   public HotellType addHotell(HotellType content, 
           @QueryParam("token") String token){
       BookingWebService ws = new BookingWebService ();
       AddHotellRequest request = new AddHotellRequest();
       request.setRating(content.getRating());
       request.setName(content.getName());
       request.setToken(token);
       return ws.addHotell(request);
   }
}
