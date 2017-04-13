package edu.umn.d.restaurantpagingapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by tinar on 4/12/2017.
 */

public class ReservationTest {

    /**
     * Tests the Reservation class's toString method.
     * @throws Exception
     */
    @Test
    public void toString_isCorrect() throws Exception {

        //Expected toString results
        String res1String = "Name: Tina\nParty Size: 3\nPhone Number: (123)456-7890\n1:50";
        String res2String = "Name: Pete\nParty Size: 2\nPhone Number: (555)555-5555\n2:00";
        String res3String = "Name: Santa\nParty Size: 100\nPhone Number: (000)000-0000\n0:00";

        //Testing to see that returned string matches expected string
        assertEquals(res1.toString(), res1String);
        assertEquals(res2.toString(), res2String);
        assertEquals(res3.toString(), res3String);
    }

    /**
     * Tests the Reservation class's getName method.
     * @throws Exception
     */
    @Test
    public void getName_works() throws Exception {

        //Testing to see that getName returns expected name
        assertEquals(res1.getName(), "Tina");
        assertEquals(res2.getName(), "Pete");
        assertEquals(res3.getName(), "Santa");

    }

    /**
     * Tests the Reservation class's setName method.
     * @throws Exception
     */
    @Test
    public void setName_works() throws Exception {

        //Change names using setName and test to see that getName returns expected name
        res1.setName("Tina Larsen");
        assertEquals(res1.getName(), "Tina Larsen");

        res2.setName("Pete Willemsen");
        assertEquals(res2.getName(), "Pete Willemsen");

        res3.setName("Santa Claus");
        assertEquals(res3.getName(), "Santa Claus");
    }

    /**
     * Tests the Reservation class's getPartySize method.
     * @throws Exception
     */
    @Test
    public void getPartySize_works() throws Exception {

        //Testing to see that getPartySize returns expected integer
        assertEquals(res1.getPartySize(), 3);
        assertEquals(res2.getPartySize(), 2);
        assertEquals(res3.getPartySize(), 100);

    }

    /**
     * Tests the Reservation class's setPartySize method.
     * @throws Exception
     */
    @Test
    public void setPartySize_works() throws Exception {

        //Change partySize using setPartySize and test to see that getPartySize returns expected integer
        res1.setPartySize(4);
        assertEquals(res1.getPartySize(), 4);

        res2.setPartySize(3);
        assertEquals(res2.getPartySize(), 3);

        res3.setPartySize(101);
        assertEquals(res3.getPartySize(), 101);
    }

    /**
     * Tests the Reservation class's getPhoneNumber method.
     * @throws Exception
     */
    @Test
    public void getPhoneNumber_works() throws Exception {

        //Testing that getPhoneNumber returns expected string
        assertEquals(res1.getPhoneNumber(), "1234567890");
        assertEquals(res2.getPhoneNumber(), "5555555555");
        assertEquals(res3.getPhoneNumber(), "0000000000");

    }

    /**
     * Tests the Reservation class's setPhoneNumber method.
     * @throws Exception
     */
    @Test
    public void setPhoneNumber_works() throws Exception {

        //Change phone numbers using setPhoneNumber and testing that getPhoneNumber returns the expected string
        res1.setPhoneNumber("2345678901");
        assertEquals(res1.getPhoneNumber(), "2345678901");

        res2.setPhoneNumber("5555555550");
        assertEquals(res2.getPhoneNumber(), "5555555550");

        res3.setPhoneNumber("0000000005");
        assertEquals(res3.getPhoneNumber(), "0000000005");
    }

    /**
     * Tests the Reservation class's getTime method.
     * @throws Exception
     */
    @Test
    public void getTime_works() throws Exception {

        //Testing that getTime returns expected string
        assertEquals(res1.getTime(), "1:50");
        assertEquals(res2.getTime(), "2:00");
        assertEquals(res3.getTime(), "0:00");

    }

    //Reservations to use for testing
    private Reservation res1 = new Reservation("Tina",3,"1234567890","1:50");
    private Reservation res2 = new Reservation("Pete", 2, "5555555555","2:00");
    private Reservation res3 = new Reservation("Santa",100, "0000000000", "0:00");
}
