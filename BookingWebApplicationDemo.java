/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookingwebapplicationdemo;

import ee.ttu.idu0075._2015.ws.hotell.GetHotellRequest;
import ee.ttu.idu0075._2015.ws.hotell.HotellType;
import java.math.BigInteger;

/**
 *
 * @author jelena
 */
public class BookingWebApplicationDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GetHotellRequest request = new GetHotellRequest();

        request.setToken("salajane");
        request.setId(BigInteger.valueOf(1));

        HotellType computer = getHotell(request);

        System.out.println("Hotell ID: " + request.getId()); 
        System.out.println("Hotell Name: " + computer.getName());
        System.out.println("Hotell rating: " + computer.getRating());
    }

    private static HotellType getHotell(ee.ttu.idu0075._2015.ws.hotell.GetHotellRequest parameter) {
        ee.ttu.idu0075._2015.ws.hotell.BookingService service = new ee.ttu.idu0075._2015.ws.hotell.BookingService();
        ee.ttu.idu0075._2015.ws.hotell.BookingPortType port = service.getBookingPort();
        return port.getHotell(parameter);
    }
    
}
