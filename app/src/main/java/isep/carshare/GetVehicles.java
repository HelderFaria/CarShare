package isep.carshare;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


public class GetVehicles extends AsyncTask<String, Integer, JSONArray> {

    private Spinner VehiclesSpinner;
    private Context mContext;

    public GetVehicles (Spinner spinner, Context context)
    {
        VehiclesSpinner = spinner;
        mContext = context;
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        String URI = Constants.VehicleURI + Constants.ownerQuery + "raul";
        JSONArray jArray = new JSONArray();
        try {
            HTTPConnection http = new HTTPConnection();
            jArray = http.DoHTTPGet(URI);
        }
        catch (Exception e) {
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        VehiclesSpinner.setAdapter(adapter);

    }
}