package com.feedradius.iter.feedradius;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class sign_up extends AppCompatActivity {
    EditText eid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void submit_sign_up(View view){
        EditText eid_sign_up = (EditText) findViewById(R.id.eid_sign_up);

        if(eid_sign_up.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Fields cannot be empty !!.", Toast.LENGTH_SHORT).show();
        }
        else {
            String user_id = eid_sign_up.getText().toString();
            Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_SHORT).show();

            new sign_up.ExecuteTask().execute(user_id);
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

            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
            if(result.equals("success")) {
                try {
                    Intent intent = new Intent(getApplication(),sign_up_email_verify.class);
                   // String user_id = eid.getText().toString();
                    //intent.putExtra("user_id",user_id);
                    startActivity(intent);
                    eid.setText(null);
                }
               catch (Exception e){
                 // Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
               }

            }
        }
    }

    public String PostData(String[] value)
    {
        String s = "";
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://sss-test.000webhostapp.com/high_radius_feedback/register.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("user_id", value[0]));

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


