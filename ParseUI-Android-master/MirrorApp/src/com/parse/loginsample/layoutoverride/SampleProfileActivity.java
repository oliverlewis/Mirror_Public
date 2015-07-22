/*
 *  Copyright (c) 2014, Parse, LLC. All rights reserved.
 *
 *  You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 *  copy, modify, and distribute this software in source code or binary form for use
 *  in connection with the web services and APIs provided by Parse.
 *
 *  As with any software that integrates with the Parse platform, your use of
 *  this software is subject to the Parse Terms of Service
 *  [https://www.parse.com/about/terms]. This copyright notice shall be
 *  included in all copies or substantial portions of the software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.parse.loginsample.layoutoverride;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.AccessToken;
import com.facebook.FacebookBroadcastReceiver;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shows the user profile. This simple activity can function regardless of whether the user
 * is currently logged in.
 */
public class SampleProfileActivity extends FragmentActivity {
  private static final int LOGIN_REQUEST = 0;

  //private TextView titleTextView;
  //private TextView emailTextView;
  //private TextView nameTextView;
  private ProfilePictureView userProfilePictureView;

    //private ActionBar actionBar;

  private ParseUser currentUser;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_profile);

    //titleTextView = (TextView) findViewById(R.id.profile_title);
    //emailTextView = (TextView) findViewById(R.id.profile_email);
    //nameTextView = (TextView) findViewById(R.id.profile_name);
    userProfilePictureView = (ProfilePictureView) findViewById(R.id.profile_picture);



    ParseUser currentUser = ParseUser.getCurrentUser();
    if ((currentUser != null) && currentUser.isAuthenticated()) {
      makeMeRequest();
    }
      //setContentView(R.layout.activity_profile);
      //k2oMfrGIR+N4VQ6AAy8iOA|UIDw
      //k2oMfrGIR+N4VQ6AAy8i0AlUIDw=
      //XlnE2PbYPysS2sTYxJfimYcGMVI=
      // Get the ViewPager and set it's PagerAdapter so that it can display items

      // Give the TabLayout the ViewPager

      TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
      tabLayout.addTab(tabLayout.newTab().setText("Home"));
      tabLayout.addTab(tabLayout.newTab().setText("Profile"));
      tabLayout.addTab(tabLayout.newTab().setText("Swipe"));
      tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

      final CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.viewpager);
      viewPager.setPagingEnabled(false);
      viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), SampleProfileActivity.this));
      viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
      tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

          @Override
          public void onTabSelected(TabLayout.Tab tab) {
              viewPager.setCurrentItem(tab.getPosition());
          }
          @Override
          public void onTabUnselected(TabLayout.Tab tab) {

          }

          @Override
          public void onTabReselected(TabLayout.Tab tab) {

          }
      });
    //loginOrLogoutButton = (Button) findViewById(R.id.login_or_logout_button);
    //titleTextView.setText(R.string.profile_title_logged_in);

    /*loginOrLogoutButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentUser != null) {
          // User clicked to log out.
          ParseUser.logOut();
          currentUser = null;
          showProfileLoggedOut();
        } else {
          // User clicked to log in.

        }
      }
    });*/
  }

  @Override
  protected void onStart() {
    super.onStart();

    currentUser = ParseUser.getCurrentUser();
    if (currentUser != null) {
      showProfileLoggedIn();
    } else {
      ParseLoginBuilder loginBuilder = new ParseLoginBuilder(
              SampleProfileActivity.this);
      startActivityForResult(loginBuilder.build(), LOGIN_REQUEST);

    }
  }

  /**
   * Shows the profile of the given user.
   */
  private void showProfileLoggedIn() {
    //titleTextView.setText(R.string.profile_title_logged_in);
    //emailTextView.setText(currentUser.getEmail());
    String fullName = currentUser.getString("name");

    if (fullName != null) {
      //nameTextView.setText(fullName);
    }
    //loginOrLogoutButton.setText(R.string.profile_logout_button_label);
  }

  /**
   * Show a message asking the user to log in, toggle login/logout button text.
   */
  private void showProfileLoggedOut() {
    //titleTextView.setText(R.string.profile_title_logged_out);
    //emailTextView.setText("");
    //nameTextView.setText("");
    //loginOrLogoutButton.setText(R.string.profile_login_button_label);
  }

  private void updateViewsWithProfileInfo() {
    ParseUser currentUser = ParseUser.getCurrentUser();
    if (currentUser.has("profile")) {
      JSONObject userProfile = currentUser.getJSONObject("profile");
      try {

        if (userProfile.has("facebookId")) {
          userProfilePictureView.setProfileId("10153999845374167");
        } else {
          // Show the default, blank user profile picture
          userProfilePictureView.setProfileId(null);
        }

        if (userProfile.has("name")) {
          //nameTextView.setText(userProfile.getString("name"));
        } else {
          //nameTextView.setText("");
        }

      } catch (Exception e) {
        //Log.d(IntegratingFacebookTutorialApplication.TAG, "Error parsing saved user data.");
      }
    }
  }

  private void makeMeRequest() {
    GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
            new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                    if (jsonObject != null) {
                        JSONObject userProfile = new JSONObject();

                        try {
                            userProfile.put("facebookId", jsonObject.getLong("id"));
                            userProfile.put("name", jsonObject.getString("name"));

                            if (jsonObject.getString("gender") != null)
                                userProfile.put("gender", jsonObject.getString("gender"));

                            //if (jsonObject.getString("email") != null)
                            //  userProfile.put("email", jsonObject.getString("email"));

                            // Save the user profile info in a user property
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            currentUser.put("profile", userProfile);
                            currentUser.saveInBackground();

                            // Show the user info
                            //updateViewsWithProfileInfo();
                            //pull the swiping feature
                            //startSwipingFeature();
                        } catch (JSONException e) {
                            //Log.d(IntegratingFacebookTutorialApplication.TAG,"Error parsing returned user data. " + e);
                        }
                    } else if (graphResponse.getError() != null) {
                        switch (graphResponse.getError().getCategory()) {
                            case LOGIN_RECOVERABLE:
                                //Log.d(IntegratingFacebookTutorialApplication.TAG,"Authentication error: " + graphResponse.getError());
                                break;

                            case TRANSIENT:
                                Log.d(SampleApplication.TAG, "Transient error. Try again. " + graphResponse.getError());
                                break;

                            case OTHER:
                                //Log.d(IntegratingFacebookTutorialApplication.TAG,"Some other error: " + graphResponse.getError());
                                break;
                        }
                    }
                }
            });

    request.executeAsync();
  }

  private void startSwipingFeature() {
      fragment_swipe f_swipe = new fragment_swipe();

    // get friends for current user
    f_swipe.getFriendsforUser();
    // get attributes from table
    f_swipe.getAttributes();
    // selection of attributes

    // save to parse database
      /*List<ParseQuery<ParseObject>> queries = new ArrayList<>();
    ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Test");
    query1.whereEqualTo("name1", "oliver");
      ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Test");
      query2.whereEqualTo("name2", "oliver");
      queries.add(query1);
      queries.add(query2);

      ParseQuery.or(queries).findInBackground(new FindCallback<ParseObject>() {
          public void done(List<ParseObject> objects, ParseException e) {
              if (e == null) {
                  objectsWereRetrievedSuccessfully(objects);
              } else {
                  //objectRetrievalFailed();
              }
          }
      });*/
  }



  private void objectsWereRetrievedSuccessfully(List<ParseObject> objects) {
    for(ParseObject object : objects){
      System.out.println(object.get("friend2"));
    }
  }
}
