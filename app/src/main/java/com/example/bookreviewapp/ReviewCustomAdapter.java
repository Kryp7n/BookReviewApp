package com.example.bookreviewapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ReviewCustomAdapter extends BaseAdapter{
    Context context;
    LayoutInflater inflter;
    ArrayList<String> user;
    ArrayList<String> user_review;

    public ReviewCustomAdapter (Context applicationContext, ArrayList<String> user, ArrayList<String> user_review) {
        this.context = context;
        this.user = user;
        this.user_review = user_review;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return user.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.reviews,null);
        TextView User = (TextView)view.findViewById(R.id.User);
        TextView UserReview = (TextView)view.findViewById(R.id.UserReview);
        User.setText(user.get(i));
        UserReview.setText(user_review.get(i));
        return view;
    }
}
