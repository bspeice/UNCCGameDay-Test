package com.uncc.gameday.registration.test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.uncc.gameday.R;
import com.uncc.gameday.registration.ParkingChoices;
import com.uncc.gameday.registration.ParkingLot;
import com.uncc.gameday.registration.ParkingRating;
import com.uncc.gameday.registration.RatingChoices;
import com.uncc.gameday.registration.RegistrationClient;

/* Tests the REST functionality */

public class RegistrationClientTest extends AndroidTestCase {
	
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
	
	public void testFetchLots() {
		RegistrationClient rc = new RegistrationClient(this.mContext);
		List<ParkingLot> lots = rc.listLots();
		assertTrue((lots.size() > 0));
	}
	
	public void testFetchLot() {
		RegistrationClient rc = new RegistrationClient(this.mContext);
		ParkingLot lot = rc.listLot(ParkingChoices.BLUE);
		assertTrue(lot.getLocation().getValue() == ParkingChoices.BLUE.getValue());
	}
	
	public void testRateLot() throws InterruptedException {
		RegistrationClient rc = new RegistrationClient(this.mContext);
		ParkingChoices choice = ParkingChoices.BLUE;
		
		// To test and make sure this works:
		// Rate a lot empty 10 times to flush out any other ratings,
		// then rate it full 15 times. Check to make sure the value is correct.
		// Technically, should only need 10 POSTs, but been having some strange
		// issues where they don't seem to all make it to the server.
		// Sometimes the test will pass, sometimes it won't...
		for (int i = 0; i < 10; i++) {
			rc.rateLot(RatingChoices.EMP, choice);
		}
		
		ParkingRating rating = new ParkingRating();
		rating.setParkingLot(choice);
		rating.setRating(RatingChoices.FLL);
		for (int i = 0; i < 15; i++) {
			rc.rateLot(rating);
		}
		
		ParkingLot lot = rc.listLot(choice);
		assertEquals(100, lot.getFilledPct()); // FULL -> 100%
	}

}
