package com.example.weatherapp3;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public void image_set(String summary)
    {
        ImageView im = findViewById(R.id.summary_image);
        RelativeLayout r = findViewById(R.id.main);
        if (summary.equals("Sunny")){
            im.setImageResource(R.drawable.s2);
            r.setBackgroundResource(R.drawable.sunny_final);
        }
        else if (summary.equals("Mostly sunny")){
            im.setImageResource(R.drawable.s3);
            r.setBackgroundResource(R.drawable.sunny_final);
        }
        else if (summary.equals("Partly sunny")){
            im.setImageResource(R.drawable.s4);
            r.setBackgroundResource(R.drawable.sunny_final);
        }
        else if (summary.equals("Mostly cloudy")){
            im.setImageResource(R.drawable.s5);
            r.setBackgroundResource(R.drawable.cloudy_final);
        }
        else if (summary.equals("Cloudy")){
            im.setImageResource(R.drawable.s6);
            r.setBackgroundResource(R.drawable.cloudy_final);
        }
        else if (summary.equals("Overcast")){
            im.setImageResource(R.drawable.s7);
            r.setBackgroundResource(R.drawable.cloudy_final);
        }
        else if (summary.equals("Light rain")){
            im.setImageResource(R.drawable.s10);
            r.setBackgroundResource(R.drawable.rain2);
        }
        else if (summary.equals("Rain")){
            im.setImageResource(R.drawable.s11);
            r.setBackgroundResource(R.drawable.rain2);
        }
        else if (summary.equals("Possible rain")){
            im.setImageResource(R.drawable.s12);
            r.setBackgroundResource(R.drawable.rain2);
        }
        else if (summary.equals("Rain shower")){
            im.setImageResource(R.drawable.s12);
            r.setBackgroundResource(R.drawable.rain2);
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        EditText e1 = findViewById(R.id.search_text);
        ImageButton i1 = findViewById(R.id.imageButton);
        TextView t1 = findViewById(R.id.temp);
        TextView t2 = findViewById(R.id.windspeed);
        TextView t3 = findViewById(R.id.cloud_cover);
        TextView t4 = findViewById(R.id.location);
        TextView t6 = findViewById(R.id.date);
        TextView t7 = findViewById(R.id.hourly);
        TextView t8 = findViewById(R.id.h1);
        TextView t10 = findViewById(R.id.h2);
        TextView t11 = findViewById(R.id.h3);
        TextView t12 = findViewById(R.id.h4);
        TextView t13 = findViewById(R.id.h5);
        TextView t9 = findViewById(R.id.phrase);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inp = e1.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date();
                String url = "https://www.meteosource.com/api/v1/free/point?place_id="+inp+"&sections=all&timezone=UTC&language=en&units=metric&key=e1bq9xu7rvkexhmnpugoiypxuhpiobccwzuo1s18";
                JsonObjectRequest j1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            String current = jsonObject.getString("current");
                            JSONObject json = new JSONObject(current);
                            JSONObject wind_json = new JSONObject(json.getString("wind"));
                            JSONObject precip_json = new JSONObject(json.getString("precipitation"));

                            String temporary = (json.getString("temperature"));
                            String sum = (json.getString("summary"));

                            t1.setText(temporary+" °C");

                            t9.setText(json.getString("summary"));

                            t6.setText("Date: "+formatter.format(date));

                            t4.setText("Location: "+inp.toUpperCase());

                            t2.setText("Wind Speed: "+wind_json.getString("speed"));

                            t3.setText("Cloud Cover: "+json.getString("cloud_cover")+"%");

                            JSONObject jsonHourly = jsonObject.getJSONObject("hourly");
                            JSONArray data = jsonHourly.getJSONArray("data");

                            TextView[] arr1 = new TextView[5];
                            arr1 = new TextView[]{t8,t10,t11,t12,t13};

                            for(int i=0;i<5;i++)
                            {
                                JSONObject obj = data.getJSONObject(i);
                                Double temp_h1 = obj.getDouble("temperature");
                                String time = obj.getString("date");
                                time = time.substring(11,16);
                                arr1[i].setText("\t\t\t\t\t"+time+" AM \t\t\t\t\t\t\t\t\t\t\t"+String.valueOf(temp_h1)+" °C");
                            }

                            image_set(sum);


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                Volley.newRequestQueue(MainActivity.this).add(j1);
            }
        });
    }

}
