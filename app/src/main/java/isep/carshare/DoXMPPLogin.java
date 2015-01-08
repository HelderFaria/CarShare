package isep.carshare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * Created by Raul Abreu on 08/01/2015.
 */
class DoXMPPLogin extends AsyncTask<String,Integer,Boolean> {
        private ConnectionConfiguration config = new ConnectionConfiguration(Constants.host, Constants.port, Constants.service);
        private XMPPConnection connection;
        String user ;
        String pass;

        protected ProgressDialog progressDialog;


        Context mContext;
        public DoXMPPLogin (Context context)
        {
            mContext =context;
        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Log.d("Login", "PreExecute");
            progressDialog = ProgressDialog.show(mContext, "Login in", "Login in", true, false);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            user = params[0];
            pass= params[1];

            config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            //XMPPConnection
            connection = new XMPPTCPConnection(config);
            try {
                try {
                    connection.connect();
                    connection.login(user, pass);
                } catch (SmackException.ConnectionException ExpConn) {
                    ExpConn.printStackTrace();
                    return Boolean.FALSE;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }

            return Boolean.TRUE;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result != null  && result == Boolean.TRUE) {
                Toast.makeText(mContext, "Login succeed", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, route_activity.class));
            }else {
                Toast.makeText(mContext, "Login failed", Toast.LENGTH_SHORT).show();
            }

        }
    }

