package com.uncc.gameday.alerts.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uncc.gameday.alerts.Alert;
import com.uncc.gameday.alerts.AlertDB;

import android.test.AndroidTestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class AlertDBTest.
 */
public class AlertDBTest extends AndroidTestCase {
	
	/** The db handle. */
	private AlertDB dbHandle;
	
	/**
	 * Instantiates a new alert db test.
	 *
	 * @throws Exception the exception
	 */
	public AlertDBTest() throws Exception {
		super.setUp();
		dbHandle = new AlertDB();
	}
	
	/* (non-Javadoc)
	 * @see android.test.AndroidTestCase#setUp()
	 */
	public void setUp() {
		// Drop all alerts
	}
	
	/**
	 * Gets the num alerts.
	 *
	 * @return the num alerts
	 */
	private int getNumAlerts() {
		// Get the number of alerts currently in the database
		
		return -1;
	}
	
	/**
	 * Test persist.
	 */
	public void testPersist() {
		// Create an alert and persist it
		Alert a = new Alert();
		a.setAlarmDate(new Date());
		a.setMessage("testPersist");
		a.setShown(false);
		
		dbHandle.persist(a);
		assertEquals(1, getNumAlerts());
	}
	
	/**
	 * Test persist unique.
	 */
	public void testPersistUnique() {
		// Make sure we can store alerts even when they have the same display date
		Date d = new Date();
		
		Alert a1 = new Alert(d, "testPersistUnique", false);
		Alert a2 = new Alert(d, "testPersistUnique", false);
		
		dbHandle.persist(a1);
		dbHandle.persist(a2);
		assertEquals(2, getNumAlerts());
	}
	
	/**
	 * Test persist multiple.
	 */
	public void testPersistMultiple() {
		Date d = new Date();
		Alert a1 = new Alert(d, "testPersistMultiple", false);
		Alert a2 = new Alert(d, "testPersistMultiple", false);
		List<Alert> l = new ArrayList<Alert>();
		l.add(a1);
		l.add(a2);
		
		dbHandle.persistMultiple(l);
		assertEquals(2, getNumAlerts());
	}
}
