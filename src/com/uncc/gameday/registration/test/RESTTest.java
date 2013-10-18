package com.uncc.gameday.registration.test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.uncc.gameday.R;
import com.uncc.gameday.registration.ParkingLot;
import com.uncc.gameday.registration.RegistrationClient;

import android.net.http.AndroidHttpClient;
import android.test.AndroidTestCase;

/* Tests the REST functionality */

public class RESTTest extends AndroidTestCase {
	
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
		if (connection.getResponseCode() != connection.HTTP_OK)
			fail("Could not connect to GameDay! Response code: " + connection.getResponseCode());
	}
	
	public void testFetchLots() {
		RegistrationClient rc = new RegistrationClient(this.mContext);
		List<ParkingLot> lots = rc.listLots();
		assertTrue((lots.size() > 0));
	}

}
