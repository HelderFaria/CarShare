package isep.carshare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        radioGroupRoute = (RadioGroup) findViewById(R.id.radioGroupRoute);
        VehiclesSpinner = (Spinner) findViewById(R.id.spinnerVehicle);

        radioGroupRoute.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroupRoute, int checkedID) {

                // find which radio button is selected
                if(checkedID == R.id.radioButtonDriver) {
                    VehiclesSpinner.setVisibility(View.VISIBLE);
                } else if(checkedID == R.id.radioButtonRider) {
                    VehiclesSpinner.setVisibility(View.GONE);
                } else {
                    VehiclesSpinner.setVisibility(View.GONE);
                }
            }
        });
        if (isNetworkAvailable()) {
            GetVehicles GetVehicle = new GetVehicles(VehiclesSpinner);
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


class GetVehicles extends AsyncTask<String, Integer, JSONArray> {

    private Spinner VehiclesSpinner;
    public GetVehicles (Spinner spinner)
    {
        VehiclesSpinner =spinner;
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        JSONObject jObject = new JSONObject();
        JSONArray jArray = new JSONArray();
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(Constants.VehicleURI + Constants.ownerQuery + "raul");
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.i("log_tag", "No OK status received");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            jObject = new JSONObject(builder.toString());
            jArray = jObject.getJSONArray("list");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jArray;
    };

    @Override
    protected void onPostExecute(JSONArray result) {
        List<String> spinnerArray =  new ArrayList<String>();
        super.onPostExecute(result);
        try {
            for(int i=0; i<result.length(); i++){
                JSONObject vehicle = result.getJSONObject(i);
                spinnerArray.add(vehicle.getString("brand") + " " + vehicle.getString("model"));
                Log.i("log_tag", spinnerArray.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


//        Log.i("log_tag","passei aqui");

        //        if (result != null  && result == Boolean.TRUE) {
//            Toast.makeText(mContext, "Login succeed", Toast.LENGTH_SHORT).show();
//            mContext.startActivity(new Intent(mContext, route_activity.class));
//        }else {
//            Toast.makeText(mContext, "Login failed", Toast.LENGTH_SHORT).show();
//        }

    }
}