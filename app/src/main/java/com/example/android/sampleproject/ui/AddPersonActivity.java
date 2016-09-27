package com.example.android.sampleproject.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.sampleproject.R;
import com.example.android.sampleproject.model.Person;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPersonActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = AddPersonActivity.class.getSimpleName();

    public static final String EXTRA_NEW_DATA_POSITION = "new_data_position";

    private DatabaseReference mDatabase;

    private TextInputEditText mFirstName;
    private TextInputEditText mLastName;
    private TextInputEditText mDOB;
    private TextInputEditText mZipCode;
    String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add new person details");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mFirstName = (TextInputEditText) findViewById(R.id.first_name);
        mLastName = (TextInputEditText) findViewById(R.id.last_name);
        mDOB = (TextInputEditText) findViewById(R.id.dob);
        mZipCode = (TextInputEditText) findViewById(R.id.zip_code);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                place = "0";
            } else {
                place= extras.getString(EXTRA_NEW_DATA_POSITION);
            }
        } else {
            place= (String) savedInstanceState.getSerializable(EXTRA_NEW_DATA_POSITION);
        }

        View submit = (Button) findViewById(R.id.submit_button);
        View cancel = (Button) findViewById(R.id.cancel_button);

        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.submit_button:
                if(isValidData()) {
                    savePerson(place, mFirstName.getText().toString(), mLastName.getText().toString(), mDOB.getText().toString(), mZipCode.getText().toString());
                }
                break;
            case R.id.cancel_button:
                this.finish();
                break;
        }
    }

    private boolean isValidData(){
        if(mFirstName.getText().length() == 0) {
            mFirstName.setError("Required Field. Cannot be Empty");
            return false;
        }
        if(mLastName.getText().length() == 0) {
            mLastName.setError("Required Field. Cannot be Empty");
            return false;
        }
        if(mDOB.getText().length() == 0) {
            mDOB.setError("Required Field. Cannot be Empty");
            return false;
        }
        if(mZipCode.getText().length() == 0) {
            mZipCode.setError("Required Field. Cannot be Empty");
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        try{
            Date d = sdf.parse(mDOB.getText().toString());
            Log.d(TAG,"Date of birth is " + d.toString());
        }catch (ParseException exception){

            mDOB.setError("Wrong Format. Should be in MM-DD-YYYY formate");
            return false;
        } catch (IllegalArgumentException e){

            mDOB.setError("Wrong Format. Should be in MM-DD-YYYY formate");
            return false;
        }

        return  true;
    }

    private void savePerson(String userId, String firstName, String lastName, String dob, String zipCode) {
        Person person = new Person(firstName, lastName, dob, zipCode);

        mDatabase.child("persons").child(userId).setValue(person);
        this.finish();
    }
}
