package com.example.bookreviewapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class BookCustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;

    ArrayList<String> book_name;
    ArrayList<String> author_name;
    ArrayList<String> rating;
    ArrayList<Integer> books;

    public BookCustomAdapter(Context applicationContext, ArrayList<Integer> books, ArrayList<String> book_name, ArrayList<String> author_name, ArrayList<String> rating) {
        this.context = context;
        this.books = books;
        this.book_name = book_name;
        this.author_name = author_name;
        this.rating = rating;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return books.size();
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
        view = inflter.inflate(R.layout.books,null);
        TextView BookName = (TextView)view.findViewById(R.id.BookName);
        TextView BookAuthor = (TextView)view.findViewById(R.id.BookAuthor);
        TextView BookRating = (TextView)view.findViewById(R.id.BookRating);
        ImageView BookImage = (ImageView) view.findViewById(R.id.BookImage);
        BookName.setText(book_name.get(i));
        BookAuthor.setText(author_name.get(i));
        BookRating.setText(rating.get(i));
        BookImage.setImageResource(books.get(i));
        return view;
    }

}
