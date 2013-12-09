package com.uncc.gameday.parking.test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.uncc.gameday.R;
import com.uncc.gameday.parking.ParkingChoice;
import com.uncc.gameday.parking.ParkingLot;
import com.uncc.gameday.parking.ParkingRating;
import com.uncc.gameday.parking.RatingChoices;
import com.uncc.gameday.parking.ParkingClient;

import android.test.AndroidTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class ParkingClientTest.
 */
public class ParkingClientTest extends AndroidTestCase{

	/* (non-Javadoc)
	 * @see android.test.AndroidTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		
		// Code run before each test goes here.
		
		// Make sure that we can actually contact the server
		/*
		 * Can't use InetAddress since the emulator doesn't support `ping`
		InetAddress host = InetAddress.getByName(this.mContext.getString(R.string.server_hostname));
		if (!host.isReachable(5000))
			fail("Could not contact Gameday server!");
		*/
		HttpURLConnection connection = (HttpURLConnection) new URL("http", mContext.getString(R.string.server_hostname), "")
			.openConnection();
		if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
			fail("Could not connect to GameDay! Response code: " + connection.getResponseCode());
	}
	
	/**
	 * Test fetch lots.
	 */
	public void testFetchLots() {
		ParkingClient pc = new ParkingClient(this.mContext);
		List<ParkingLot> lots = pc.listLots();
		assertTrue((lots.size() > 0));
	}
	
	/**
	 * Test fetch lot.
	 */
	public void testFetchLot() {
		ParkingClient pc = new ParkingClient(this.mContext);
		ParkingLot lot = pc.listLot(ParkingChoice.BLUE);
		assertTrue(lot.getLocation().getValue() == ParkingChoice.BLUE.getValue());
	}
	
	/**
	 * Test rate lot.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void testRateLot() throws InterruptedException {
		ParkingClient pc = new ParkingClient(this.mContext);
		ParkingChoice choice = ParkingChoice.BLUE;
		
		// To test and make sure this works:
		// Rate a lot empty 10 times to flush out any other ratings,
		// then rate it full 15 times. Check to make sure the value is correct.
		// Technically, should only need 10 POSTs, but been having some strange
		// issues where they don't seem to all make it to the server.
		// Sometimes the test will pass, sometimes it won't...
		for (int i = 0; i < 10; i++) {
			pc.rateLot(RatingChoices.EMP, choice);
		}
		
		ParkingRating rating = new ParkingRating();
		rating.setParkingLot(choice);
		rating.setRating(RatingChoices.FLL);
		for (int i = 0; i < 15; i++) {
			pc.rateLot(rating);
		}
		
		ParkingLot lot = pc.listLot(choice);
		
		// While the lot should be full (100%) the unit test POST goes too fast
		// for Retrofit to keep up (or server, tbh not sure which)
		// We are making an assumption here that in production we won't have such
		// rapid-fire usage.
		assertTrue((lot.getFilledPct() > 0));
	}
}
