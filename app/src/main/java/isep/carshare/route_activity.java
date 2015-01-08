package isep.carshare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class route_activity extends ActionBarActivity {

    private RadioGroup radioGroupRoute;
    private RadioButton radioButton;
    private Spinner VehiclesSpinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        EditText editTextFromDate = (EditText) findViewById(R.id.editDate );
        editTextFromDate.setInputType(InputType.TYPE_NULL);
        setDate fromDate = new setDate(editTextFromDate, this);
        EditText editTextFromTime = (EditText) findViewById(R.id.editTime );
        editTextFromTime.setInputType(InputType.TYPE_NULL);
        setTime fromTime = new setTime(editTextFromTime, this);

        radioGroupRoute = (RadioGroup) findViewById(R.id.radioGroupRoute);
        VehiclesSpinner = (Spinner) findViewById(R.id.spinnerVehicle);


        radioGroupRoute.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroupRoute, int checkedID) {

                // find which radio button is selected
                if(checkedID == R.id.radioButtonDriver) {
                    findViewById(R.id.textView3).setVisibility(View.VISIBLE);
                    findViewById(R.id.rangeEdit).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView4).setVisibility(View.VISIBLE);
                    findViewById(R.id.textView6).setVisibility(View.VISIBLE);
                    VehiclesSpinner.setVisibility(View.VISIBLE);
                } else if(checkedID == R.id.radioButtonRider) {
                    findViewById(R.id.textView3).setVisibility(View.GONE);
                    findViewById(R.id.rangeEdit).setVisibility(View.GONE);
                    findViewById(R.id.textView4).setVisibility(View.GONE);
                    findViewById(R.id.textView6).setVisibility(View.GONE);
                    VehiclesSpinner.setVisibility(View.GONE);
                } else {
                    findViewById(R.id.textView3).setVisibility(View.GONE);
                    findViewById(R.id.rangeEdit).setVisibility(View.GONE);
                    findViewById(R.id.textView4).setVisibility(View.GONE);
                    findViewById(R.id.textView6).setVisibility(View.GONE);
                    VehiclesSpinner.setVisibility(View.GONE);
                }
            }
        });
        if (isNetworkAvailable()) {
            GetVehicles GetVehicle = new GetVehicles(VehiclesSpinner, route_activity.this);
            GetVehicle.execute();
        }
        else {
            showMessage("No internet connection");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_car:
//                this.startActivity(new Intent(this, car_activity.class));
            case R.id.action_settings:
//                this.startActivity(new Intent(this, setting_activity.class));
            case R.id.action_map:
//                this.startActivity(new Intent(this, route_activity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}


