package manjinder.fragmentproject;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "DATA";
    ProgressDialog dialog;
    Fragment firstFragment, secondFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ArrayList<String> myPosts;
    String dataFromServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPosts = new ArrayList<>();
        dialog = new ProgressDialog(this);
        new DownloadPosts().execute();
    }

    public void replaceFragment(View view) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Toast.makeText(MainActivity.this, "CLICKED MAIN ACTIVITY " + FragmentTransaction.TRANSIT_FRAGMENT_OPEN, Toast.LENGTH_LONG).show();
        firstFragment = new FirstFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Bundle extras = new Bundle();
        extras.putString("jsonData", dataFromServer);
        firstFragment.setArguments(extras);
        fragmentTransaction.replace(R.id.parentView, firstFragment);
        fragmentTransaction.commit();
    }
    public void removeFragment(View view){
        fragmentTransaction = fragmentManager.beginTransaction();
        if(secondFragment != null) {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fragmentTransaction.remove(secondFragment).commit();
        } else {
            fragmentTransaction.remove(firstFragment).commit();
            Toast.makeText(MainActivity.this, "FRAGMENT NOT INITIALIZED", Toast.LENGTH_LONG).show();
        }
    }

    public class DownloadPosts extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            dialog.setMessage("GETTING YOUR DATA");
            dialog.show();
            Log.d(TAG, "onPreExecute: " );
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String aVoid) {
            dialog.dismiss();
            firstFragment = new FirstFragment();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Bundle extras = new Bundle();
            extras.putString("jsonData", aVoid);
            firstFragment.setArguments(extras);
            fragmentTransaction.add(R.id.parentView, firstFragment, "ABC");
            fragmentTransaction.commit();
            super.onPostExecute(aVoid);
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection connection;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/posts");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String data;
                while((data = bufferedReader.readLine()) != null) {
                    stringBuilder.append(data);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataFromServer = stringBuilder.toString();
            return stringBuilder.toString();
        }
    }
}
