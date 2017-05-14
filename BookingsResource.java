/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ttu.idu0075.booking;

import ee.ttu.idu0075._2015.ws.hotell.AddBookingHotellRequest;
import ee.ttu.idu0075._2015.ws.hotell.AddBookingRequest;
import ee.ttu.idu0075._2015.ws.hotell.BookingHotellListType;
import ee.ttu.idu0075._2015.ws.hotell.BookingHotellType;
import ee.ttu.idu0075._2015.ws.hotell.BookingType;
import ee.ttu.idu0075._2015.ws.hotell.GetBookingHotellListRequest;
import ee.ttu.idu0075._2015.ws.hotell.GetBookingListRequest;
import ee.ttu.idu0075._2015.ws.hotell.GetBookingListResponse;
import ee.ttu.idu0075._2015.ws.hotell.GetBookingRequest;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * REST Web Service
 *
 * @author jelena
 */
@Path("bookings")
public class BookingsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BookingsResource
     */
    public BookingsResource() {
    }

    /**
     * Retrieves representation of an instance of invoice.InvoicesResource
     * @param token
     * @param hasRelatedHotell
     * @param startDateString
     * @param endDateString
     * @return an instance of java.lang.String
     */
   @GET
    @Produces("application/json")
    public GetBookingListResponse getBookingList(@QueryParam("token") String token,
            @QueryParam("hotells") String hasRelatedHotell,
            @QueryParam("start") String startDateString,
            @QueryParam("end") String endDateString) {
        try {
            BookingWebService ws = new BookingWebService();
            GetBookingListRequest request = new GetBookingListRequest();
            request.setToken(token);
            request.setHasRelatedHotell(hasRelatedHotell);
            
            DateFormat formatter; 
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(startDateString);
            GregorianCalendar gregory = new GregorianCalendar();
            gregory.setTime(date);
            request.setStartDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory));
            date = formatter.parse(endDateString);
            gregory = new GregorianCalendar();
            gregory.setTime(date);
            request.setEndDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory));
            return ws.getBookingList(request);
        } catch (ParseException | DatatypeConfigurationException ex) {
            Logger.getLogger(BookingsResource.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("{id : \\d+}") //support digit only
    @Produces("application/json")
    public BookingType getBooking(@PathParam("id") String id,
            @QueryParam("token") String token) {
        BookingWebService ws = new BookingWebService();
        GetBookingRequest request = new GetBookingRequest();
        request.setId(BigInteger.valueOf(Integer.parseInt(id)));
        request.setToken(token);
        return ws.getBooking(request);
    }
    

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public BookingType addBooking(BookingType content, 
                                @QueryParam("token") String token) {
        BookingWebService ws = new BookingWebService();
        AddBookingRequest request = new AddBookingRequest();
        request.setToken(token);
        request.setCheckin(content.getCheckin());
        request.setDays(content.getDays());
        request.setPrice(content.getPrice());
        request.setCustomerName(content.getCustomerName());
        return ws.addBooking(request);    
    }
    
    @GET
    @Path("{id : \\d+}/hotells") //support digit only
    @Produces("application/json")
    public BookingHotellListType getBookingHotellList(@PathParam("id") String id,
            @QueryParam("token") String token) {
        BookingWebService ws = new BookingWebService();
        GetBookingHotellListRequest request = new GetBookingHotellListRequest();
        request.setBookingId(BigInteger.valueOf(Integer.parseInt(id)));
        request.setToken(token);
        return ws.getBookingHotellList(request);
    }
    

    @POST
    @Path("{id : \\d+}/hotells") //support digit only
    @Consumes("application/json")
    @Produces("application/json")
    public BookingHotellType addBookingHotell(AddBookingHotellRequest content, 
                                @QueryParam("token") String token,
                                @PathParam("id") String id) {
        BookingWebService ws = new BookingWebService();
        AddBookingHotellRequest request = new AddBookingHotellRequest();
        request.setToken(token);
        request.setBookingId(BigInteger.valueOf(Integer.parseInt(id)));
        request.setHotellId(content.getHotellId());
        request.setRoom(content.getRoom());
        request.setAmountofguests(content.getAmountofguests());
        return ws.addBookingHotell(request);    
    }
}


