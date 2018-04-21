package com.current.android.current;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public class EventAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String name;
    private ArrayList<EventPost> mEventPostArrayList;



    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            if (dataSnapshot == null) return;

            EventPost event = dataSnapshot.getValue(EventPost.class);
            if (!mEventPostArrayList.contains(event)){
                mEventPostArrayList.add(event);
                notifyDataSetChanged();
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public EventAdapter(Activity activity, DatabaseReference ref, ArrayList<EventPost> list){
        mActivity=activity;
        mDatabaseReference =ref.child("events");
        mDatabaseReference.addChildEventListener(listener);
        mEventPostArrayList = list;
    }


    private static class ViewHolder{
        TextView event_name,event_des;
        ImageView color;
        RelativeLayout.LayoutParams params;

    }


    @Override
    public int getCount() {
        return mEventPostArrayList.size();
    }

    @Override
    public EventPost getItem(int position) {
        return mEventPostArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_event_layout, parent, false);

            final ViewHolder holder = new ViewHolder();
            holder.event_name = (TextView) convertView.findViewById(R.id.textView_name);
            holder.event_des = (TextView) convertView.findViewById(R.id.textView_description);
            holder.params = (RelativeLayout.LayoutParams) holder.event_name.getLayoutParams();
            convertView.setTag(holder);

        }

        final EventPost event = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();



        holder.event_name.setText(event.getEventName());

        holder.event_des.setText(event.getEventDescription());


        return convertView;
    }

    void cleanup(){
        mDatabaseReference.removeEventListener(listener);
    }


}
