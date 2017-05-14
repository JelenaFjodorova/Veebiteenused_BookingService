/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ttu.idu0075.booking;

import ee.ttu.idu0075._2015.ws.hotell.AddBookingHotellRequest;
import ee.ttu.idu0075._2015.ws.hotell.AddBookingRequest;
import ee.ttu.idu0075._2015.ws.hotell.AddHotellRequest;
import ee.ttu.idu0075._2015.ws.hotell.BookingHotellListType;
import ee.ttu.idu0075._2015.ws.hotell.BookingHotellType;
import ee.ttu.idu0075._2015.ws.hotell.BookingType;
import ee.ttu.idu0075._2015.ws.hotell.GetBookingHotellListRequest;
import ee.ttu.idu0075._2015.ws.hotell.GetBookingListRequest;
import ee.ttu.idu0075._2015.ws.hotell.GetBookingListResponse;
import ee.ttu.idu0075._2015.ws.hotell.GetBookingRequest;
import ee.ttu.idu0075._2015.ws.hotell.GetHotellListRequest;
import ee.ttu.idu0075._2015.ws.hotell.GetHotellListResponse;
import ee.ttu.idu0075._2015.ws.hotell.GetHotellRequest;
import ee.ttu.idu0075._2015.ws.hotell.HotellType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConstants;

/**
 *
 * @author jelena
 */
@WebService(serviceName = "BookingService", portName = "BookingPort", endpointInterface = "ee.ttu.idu0075._2015.ws.hotell.BookingPortType", targetNamespace = "http://www.ttu.ee/idu0075/2015/ws/hotell", wsdlLocation = "WEB-INF/wsdl/BookingWebService/BookingService.wsdl")
public class BookingWebService {

    static int nextHotellId = 1;
    static int nextBookingId = 1;
    static List<HotellType> hotellList = new ArrayList<HotellType>();
    static List<BookingType> bookingList = new ArrayList<BookingType>();
    
    
    public HotellType getHotell(GetHotellRequest parameter) {
        HotellType ht = null;
        if (parameter.getToken().equalsIgnoreCase("salajane")){
            for (int i = 0; i < hotellList.size(); i++) {
                if (hotellList.get(i).getId().equals(parameter.getId())) {
                    ht = hotellList.get(i);
                }
            }
        }
        return ht;
    }

      public HotellType addHotell(AddHotellRequest parameter) {
        HotellType ht = new HotellType();
        if (parameter.getToken().equalsIgnoreCase("salajane")){
            ht.setName(parameter.getName());
            ht.setRating(parameter.getRating());
            ht.setId(BigInteger.valueOf(nextHotellId++));
            hotellList.add(ht);
        }
        return ht;
    }

      public GetHotellListResponse getHotellList(GetHotellListRequest parameter) {
        GetHotellListResponse response = new GetHotellListResponse();
        if (parameter.getToken().equalsIgnoreCase("salajane")){
            for (HotellType hotellType : hotellList) {
                response.getHotell().add(hotellType);
            }
        }
        return response;
    }

    public BookingType getBooking(GetBookingRequest parameter) {
        BookingType bt = null;
        if (parameter.getToken().equalsIgnoreCase("salajane")) {
            for (int i = 0; i < bookingList.size(); i++) {
                if (bookingList.get(i).getId().equals(parameter.getId())) {
                    bt = bookingList.get(i);
                }

            }    
        }
        return bt;
    }

    public BookingType addBooking(AddBookingRequest parameter) {
        BookingType bt = new BookingType();
        if (parameter.getToken().equalsIgnoreCase("salajane")) {
            bt.setId(BigInteger.valueOf(nextBookingId++));
            bt.setCheckin(parameter.getCheckin());
            bt.setDays(parameter.getDays());
            bt.setPrice(parameter.getPrice());
            bt.setCustomerName(parameter.getCustomerName());
            bt.setBookingHotellList(new BookingHotellListType());
            bookingList.add(bt);
        }
        return bt;
    }

      public GetBookingListResponse getBookingList(GetBookingListRequest parameter) {
        GetBookingListResponse response = new GetBookingListResponse();
        if (parameter.getToken().equalsIgnoreCase("salajane")){
            for (BookingType bookingType : bookingList) {
                if (((parameter.getHasRelatedHotell().equalsIgnoreCase("ei" ) 
                        && (bookingType.getBookingHotellList() == null 
                        || bookingType.getBookingHotellList().getBookingHotell().isEmpty()))
                        || (parameter.getHasRelatedHotell().equalsIgnoreCase("jah" ) 
                        && bookingType.getBookingHotellList() != null 
                        && !bookingType.getBookingHotellList().getBookingHotell().isEmpty()) 
                        && (parameter.getStartDate() != null 
                        && parameter.getStartDate().compare(bookingType.getCheckin())== DatatypeConstants.LESSER)
                    && (parameter.getEndDate() != null 
                        && parameter.getEndDate().compare(bookingType.getCheckin())== DatatypeConstants.GREATER)    
                        )){
                response.getBooking().add(bookingType);
            }
            }
        }
        return response;
    }


     public BookingHotellListType getBookingHotellList(GetBookingHotellListRequest parameter) {
        BookingHotellListType bookingHotellList = null;
        if (parameter.getToken().equalsIgnoreCase("salajane")) {
            for (int i = 0; i < bookingList.size(); i++) {
                if (bookingList.get(i).getId().equals(parameter.getBookingId())) {
                    bookingHotellList = bookingList.get(i).getBookingHotellList();
                }

            }    
        }
        return bookingHotellList;
    }

   public BookingHotellType addBookingHotell(AddBookingHotellRequest parameter) {
        BookingHotellType bookingHotell = new BookingHotellType();
        if (parameter.getToken().equalsIgnoreCase("salajane")) {
            bookingHotell.setRoom(parameter.getRoom());
            bookingHotell.setAmountofguests(parameter.getAmountofguests());
            GetHotellRequest hotellRequest = new GetHotellRequest();
            hotellRequest.setId(parameter.getHotellId());
            hotellRequest.setToken(parameter.getToken());
            bookingHotell.setHotell(getHotell(hotellRequest));

        
            for (int i = 0; i < bookingList.size(); i++) {
                if (bookingList.get(i).getId().equals(parameter.getBookingId())) {
                    bookingList.get(i).getBookingHotellList().getBookingHotell().add(bookingHotell);
                    return bookingHotell;
                }

            } 
        }
        return null;
    }
    
}

