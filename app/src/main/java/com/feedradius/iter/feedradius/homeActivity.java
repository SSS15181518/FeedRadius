package com.feedradius.iter.feedradius;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class homeActivity extends AppCompatActivity {
EditText eid,pass;
public static String name, post, email, dp, nof, phno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        eid = (EditText) findViewById(R.id.eid);
        pass = (EditText) findViewById(R.id.pass);

    }

    public void register(View view)
    {
        Intent intent = new Intent(getApplication(),sign_up.class);
        startActivity(intent);
    }
    public void forgotPassword(View view)
    {
        Intent intent = new Intent(getApplication(),sign_up.class);
        startActivity(intent);
    }


    public void sign_in(View V)
    {
        if(eid.getText().toString().isEmpty() || pass.getText().toString().isEmpty())
        {
            Snackbar.make(V, "Fields Cannot be Empty !!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }

        else {
            String user_id = eid.getText().toString();
            String password = pass.getText().toString();
            new homeActivity.ExecuteTask().execute(user_id, password);
        }
    }

    class ExecuteTask extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String res;
            res = PostData(params);
            return res;
        }
        @Override
        protected void onPostExecute(String result)
        {

            if(result.equals("success")) {


                loadProfile process = new loadProfile();
                process.execute();
                SharedPreferences sharedPreferences = getSharedPreferences("MyData",MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("eid",eid.getText().toString());
                editor.putString("pass",pass.getText().toString());
                editor.putString("name",name);
                editor.putString("email",email);
                editor.putString("post",post);
                editor.putString("phno",phno);
                editor.putString("nof",nof);

                editor.commit();

                Toast.makeText(getApplicationContext(),"Log In Success !!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplication(),feedback.class);
               // String user_id = eid.getText().toString();
               // intent.putExtra("name",user_id);
                startActivity(intent);
                eid.setText(null);
                pass.setText(null);
            }else{
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }
    }

    public String PostData(String[] value)
    {
        String s = "";
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://sss-test.000webhostapp.com/high_radius_feedback/login.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("user_id", value[0]));
            list.add(new BasicNameValuePair("password", value[1]));
            //end new data
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            s = readResponse(httpResponse);
        }
        catch (Exception exception)
        {

        }
        return s;
    }
    public String readResponse(HttpResponse res)
    {
        InputStream is = null;
        String return_text = "";
        try
        {
            is = res.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while((line = bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
            return_text = sb.toString();
        }catch(Exception e)
        {

        }
        return return_text;
    }

}




