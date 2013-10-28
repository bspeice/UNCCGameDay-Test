package com.uncc.gameday.registration.test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.test.AndroidTestCase;

import com.uncc.gameday.R;
import com.uncc.gameday.parking.ParkingChoice;
import com.uncc.gameday.parking.ParkingLot;
import com.uncc.gameday.parking.ParkingRating;
import com.uncc.gameday.parking.RatingChoices;
import com.uncc.gameday.registration.Attendee;
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
	
	public void testRegistration() {
		// Register a new user, and make sure that they can be retrieved correctly
		Attendee a = new Attendee();
		a.setFirstName("UNCC");
		a.setLastName("Gameday");
		a.setRow(16);
		a.setSection("123");
		
		RegistrationClient rc = new RegistrationClient(this.mContext);
		rc.registerAttendee(a);
		
		Attendee r = rc.listAttendee(a);
		
		assertTrue((r.getId() != 0));
	}
	
	public void testListUsers() {
		// Test listing all users
		RegistrationClient rc = new RegistrationClient(this.mContext);
		List<Attendee> attendees = rc.listAttendees();
		
		assertTrue((attendees.size() > 0));
	}

}
