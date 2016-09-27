package com.example.android.sampleproject.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sampleproject.R;
import com.example.android.sampleproject.model.Person;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Person> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mLastName;
        public TextView mFirstName;
        public TextView mDateOfBirth;
        public TextView mZipCode;

        public ViewHolder(View v) {
            super(v);
            mLastName = (TextView) v.findViewById(R.id.last_name);
            mFirstName = (TextView) v.findViewById(R.id.first_name);
            mDateOfBirth = (TextView) v.findViewById(R.id.date_of_birth);
            mZipCode = (TextView) v.findViewById(R.id.zip_code);
        }
    }

    public MyAdapter() {
        mDataset = new ArrayList<>();
    }

    public void updatePersons(ArrayList<Person> persons){
        mDataset = persons;
        this.notifyDataSetChanged();
    }

    public void addPerson(Person person){
        mDataset.add(person);
        this.notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Person person = mDataset.get(position);

        holder.mFirstName.setText(person.getFirstName());
        holder.mLastName.setText(person.getLastName());
        holder.mDateOfBirth.setText(person.getDateOfBirth());
        holder.mZipCode.setText(person.getZipCode());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}