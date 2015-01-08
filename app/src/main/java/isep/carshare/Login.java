package isep.carshare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.util.Log;


import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Raul Abreu on 16/12/2014.
 */
public class Login extends Activity {

    Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isNetworkAvailable()) {

                    EditText Editusername = (EditText) findViewById(R.id.EditUser);
                    EditText Editpassword = (EditText) findViewById(R.id.EditPass);
                    String password = Editpassword.getText().toString();
                    String username = Editusername.getText().toString();

                    Boolean res = Boolean.FALSE;

                    DoXMPPLogin login = new DoXMPPLogin(Login.this);
                    login.execute(username, password);

                }
                else {
                    showMessage("No internet connection");
                }
            }
        });

    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}




