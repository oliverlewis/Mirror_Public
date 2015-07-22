package com.parse.loginsample.layoutoverride;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by oliverlewis on 7/4/15.
 */
public class fragment_swipe extends Fragment {

    private List<FriendBO> friendBOList = new ArrayList<>();
    private List<ParseObject> attributes = new ArrayList<>();
    private List<String> question_list = new ArrayList<String>();
    private ParseUser currentUser = ParseUser.getCurrentUser();
    private CardContainer mCardContainer;
    private Button buttonNo, buttonYes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swipe, container, false);
        mCardContainer = (CardContainer) view.findViewById(R.id.layoutview);
        Resources r = getResources();
        Random random = new Random();
        getFriendsforUser();
        getAttributes();

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(getActivity());
        int i;
        final List<ParseObject> attrib = new ArrayList<>();
        //adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1)));
        for(i=0;i<friendBOList.size();i++) {
            attrib.add(attributes.get(random.nextInt(attributes.size())));
            CardModel cardModel = new CardModel(friendBOList.get(i).getFriend_name()+":"+attrib.get(i).get("level_2").toString(), "Description goes here", r.getDrawable(R.drawable.picture1));
            cardModel.setOnClickListener(new CardModel.OnClickListener() {
                @Override
                public void OnClickListener() {
                    Log.i("Swipeable Cards", "I am pressing the card");
                }
            });

            final int finalI = i;
            cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
                @Override
                public void onLike() {
                    saveInDatabase(attrib.get(attrib.size()-1-finalI).get("level_1").toString(), attrib.get(attrib.size()-1-finalI).get("level_2").toString(), friendBOList.get(friendBOList.size()-1-finalI).getFriend_id(), false);
                }

                @Override
                public void onDislike() {
                    saveInDatabase(attrib.get(attrib.size()-1-finalI).get("level_1").toString(), attrib.get(attrib.size()-1-finalI).get("level_2").toString(), friendBOList.get(friendBOList.size()-1-finalI).getFriend_id(), true);
                }
            });

            adapter.add(cardModel);
        }
        mCardContainer.setAdapter(adapter);
        return view;
        /*contentTextView = (TextView) view.findViewById(R.id.textView);
        buttonNo = (Button) view.findViewById(R.id.buttonNo);
        buttonYes = (Button) view.findViewById(R.id.buttonYes);
        currentUser = ParseUser.getCurrentUser();
        getAttributes();
        getFriendsforUser();
        makeRandomSwipes();
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save in database
                Random r = new Random();
                int attributeIndex = r.nextInt(attributes.size());
                saveInDatabase(currentUser.getObjectId().toString(), attributes.get(attributeIndex).get("level_1").toString(), attributes.get(attributeIndex).get("level_2").toString(), 1, friendList.get(r.nextInt(friendList.size())).get("friend2").toString());
                //get next question

                contentTextView.setText(friendList.get(r.nextInt(friendList.size())).get("friend2").toString() + " : " + attributes.get(r.nextInt(attributes.size())).get("level_2"));


            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save in database
                Random r = new Random();
                int attributeIndex = r.nextInt(attributes.size());
                saveInDatabase(currentUser.getObjectId().toString(), attributes.get(attributeIndex).get("level_1").toString(), attributes.get(attributeIndex).get("level_2").toString(), 1, friendList.get(r.nextInt(friendList.size())).get("friend2").toString());
                //get next question

                contentTextView.setText(friendList.get(r.nextInt(friendList.size())).get("friend2").toString() + " : " + attributes.get(r.nextInt(attributes.size())).get("level_2"));

            }
        });

        //
        //contentTextView.setText(ParseUser.getCurrentUser().getUsername());

        return view;
        //return inflater.inflate(R.layout., container, false);*/

    }

    private void saveInDatabase(String level_1, String level_2, String endorsee, boolean endorsed_value) {
        //ParseObject<ParseObject> query = ParseObject.getQuery("User_Attributes");
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("User_Attributes");

        ParseObject endorsee_obj = ParseObject.createWithoutData("_User", endorsee);
        query1.whereEqualTo("user_link", endorsee_obj);
        query1.whereEqualTo("level_1", level_1);
        query1.whereEqualTo("level_2", level_2);
        try {
            List<ParseObject> list = query1.find();
            if (list.size() > 0) {
                ParseObject p = list.get(0);
                p.addUnique("endorsed_by", currentUser.getObjectId());
                p.put("endorsed_value", endorsed_value);
                p.saveInBackground();
                //p.put();
            } else {
                ParseObject object2 = new ParseObject("User_Attributes");
                object2.put("user_link", endorsee_obj);
                object2.put("level_1", level_1);
                object2.put("level_2", level_2);
                //query.addUnique("skills", Arrays.asList(endorsed_by));
                object2.addUnique("endorsed_by", currentUser.getObjectId());
                object2.put("endorsed_value", endorsed_value);
                object2.saveInBackground();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //query.saveInBackground();
    }

    private void makeRandomSwipes() {


    }

    protected void getAttributes() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Attributes");
        //query.whereEqualTo("friend1", currentUser.getCurrentUser().getObjectId());
        try {
            List<ParseObject> list = query.find();
            for (ParseObject object : list) {
                attributes.add(object);
            }
            /*(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        //return objects;
                        for (ParseObject object : objects) {
                            attributes.add(object);
                        }
                        //objectsWereRetrievedSuccessfully(objects);
                    } else {
                        //objectRetrievalFailed();
                    }
                }
            });*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void getFriendsforUser() {
        friendBOList = new ArrayList<>();

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Friends");
        query1.whereEqualTo("friend1", currentUser.getCurrentUser().getObjectId());
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Friends");
        query2.whereEqualTo("friend2", currentUser.getCurrentUser().getObjectId());
        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        try {
            List<ParseObject> list = mainQuery.find();

            for (ParseObject object : list) {
                FriendBO friendBO = new FriendBO();
                if (currentUser.getObjectId().compareToIgnoreCase(object.get("friend1").toString())==0) {
                    friendBO.setFriend_id(object.get("friend2").toString());
                    friendBO.setFriend_name(object.get("friend2_name").toString());
                    friendBOList.add(friendBO);
                }
                else
                {
                    friendBO.setFriend_id(object.get("friend1").toString());
                    friendBO.setFriend_name(object.get("friend1_name").toString());
                    friendBOList.add(friendBO);
                }
            }
             /*InBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        //return objects;
                        for (ParseObject object : objects) {
                            FriendBO friendBO = new FriendBO();
                            if (currentUser.getObjectId().compareToIgnoreCase(object.get("friend1").toString())==0) {
                                friendBO.setFriend_id(object.get("friend2").toString());
                                friendBO.setFriend_name(object.get("friend2_name").toString());
                                friendBOList.add(friendBO);
                            }
                            else
                            {
                                friendBO.setFriend_id(object.get("friend1").toString());
                                friendBO.setFriend_name(object.get("friend1_name").toString());
                                friendBOList.add(friendBO);
                            }
                        }
                        //objectsWereRetrievedSuccessfully(objects);
                    } else {
                        //objectRetrievalFailed();
                    }
                }
            });*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
