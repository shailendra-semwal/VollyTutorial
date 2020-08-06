package com.example.myapplication;


import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private static final String HI = "https://uniqueandrocode.000webhostapp.com/hiren/androidtutorial/androidweb.php";
    private List<List_Data>list_data;
    private RecyclerView rv;
    private MyAdapter adapter;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv=(RecyclerView)findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        list_data=new ArrayList<>();

        getImageData();

    }

    private void getImageData() {
        request=new JsonArrayRequest(HI, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject=null;
                for (int i=0; i<response.length(); i++){
                    try {
                        JSONObject ob=response.getJSONObject(i);
                        List_Data listData =new List_Data(ob.getString("imageurl"));
                        list_data.add(listData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                setupData(list_data);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);



    }

    private void setupData(List<List_Data> list_data) {

        adapter=new MyAdapter(list_data,this);
        rv.setAdapter(adapter);

    }
}