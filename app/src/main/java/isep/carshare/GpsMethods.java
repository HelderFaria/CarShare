package isep.carshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import java.util.Locale;

/**
 * Created by [] on 03-01-2015.
 */
public class GpsMethods {
    private static GpsMethods instance;
    private LocationManager locationManager;
    private String strLastKnowLocation;

    public static GpsMethods getInstance() {
        if (instance == null) {
            instance = new GpsMethods();
        }
        return instance;
    }

    /**
     *
     * @param context Context of application have to be sent
     * @return String with name of street and postal information Format: "Street, 1234 Locality"
     */
    public String getCurrentThoroughfareLocation(final Context context) {

        // Get the location manager
        if (locationManager == null) {
            this.locationManager = (LocationManager) context.getSystemService(android.content.Context.LOCATION_SERVICE);
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingAccuracy(Criteria.ACCURACY_LOW);
                String provider = locationManager.getBestProvider(criteria,true);
                Location location = locationManager.getLastKnownLocation(provider);
                Double lat, lon;
                try {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    Geocoder gcd = new Geocoder(context, Locale.getDefault());
                    strLastKnowLocation = gcd.getFromLocation(lat, lon, 5).get(0).getThoroughfare() + ", ";
                    strLastKnowLocation += gcd.getFromLocation(lat, lon, 5).get(0).getAddressLine(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        return strLastKnowLocation;
    }

    /**
     *
     * @param context Context of application have to be sent
     * This method allow ask user if he want change localizations settings
     */
    private void alertMessageIfGpsIsNoEnabled(final Context context) {
        AlertDialog alert;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS is disabled, do you want to enable it?");
        //builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                dialog.cancel();
            }
        });
        alert = builder.create();
        alert.show();

    }
}
