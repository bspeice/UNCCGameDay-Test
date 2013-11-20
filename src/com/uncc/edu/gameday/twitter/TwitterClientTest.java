package com.uncc.edu.gameday.twitter;



import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;
import android.test.AndroidTestCase;

import com.uncc.gameday.R;
import com.uncc.gameday.twitter.TwitterClient;

// TODO: Auto-generated Javadoc
/**
 * The Class TwitterClientTest.
 */
public class TwitterClientTest extends AndroidTestCase {
	
	/**
	 * Test twitter.
	 */
	public void testTwitter(){
		// Test the twitter4j logic
		String handle = this.mContext.getString(R.string.gameday_handle);
		System.out.println(handle);
		
		try{
			TwitterClient tc = new TwitterClient();
			List<Status> l = tc.fetchTweets(handle, 5);
			assertEquals(5, l.size());
		} catch (TwitterException e) {
			e.printStackTrace();
			fail();
		}
	}
}
