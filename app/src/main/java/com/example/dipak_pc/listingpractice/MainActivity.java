package com.example.dipak_pc.listingpractice;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView lstView;
    private ArrayList<HashMap<String,String>> arrayList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVar();

        getAllWebData();//webcall

/* //comment all dummy data
        HashMap<String,String> hs = new HashMap<>();
        hs.put("title","dipak");
        hs.put("id","0101");
        hs.put("date","06/11/1992");

        HashMap<String,String> hs1 = new HashMap<>();
        hs1.put("title","rohit");
        hs1.put("id","1010");
        hs1.put("date","07/01/1992");

        HashMap<String,String> hs2 = new HashMap<>();
        hs2.put("title","Somath");
        hs2.put("id","1111");
        hs2.put("date","05/03/1991");

        HashMap<String,String> hs3 = new HashMap<>();
        hs3.put("title","vaishali");
        hs3.put("id","0000");
        hs3.put("date","26/01/1991");

        arrayList.add(hs);
        arrayList.add(hs1);
        arrayList.add(hs2);
        arrayList.add(hs3);

        //set array list listView
        lstView.setAdapter(new DataList(MainActivity.this,R.layout.activity_main,arrayList));*/

    }

    public void  initVar(){
        lstView  = (ListView) findViewById(R.id.lstView);
        arrayList = new ArrayList<>();
    }

    public void getAllWebData(){

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url = "http://hidoc.xyz/api/healthtips/healthtips.php";
        System.out.println("Url is : "+url);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                /// We get Response here
                System.out.println("Response is : "+response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("title",jsonObject.getString("title"));
                        hashMap.put("id",jsonObject.getString("id"));
                        hashMap.put("date",jsonObject.getString("date"));
                        hashMap.put("image",jsonObject.getString("image"));
                        arrayList.add(hashMap);
                    }

                    //set data to ListAdapter for show listing
                    lstView.setAdapter(new DataList(MainActivity.this,R.layout.activity_main,arrayList));

                } catch (JSONException e) {
                    System.out.print("Error in JSON PARSE : "+e.getMessage());
                    e.printStackTrace();
                }finally {
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //we get error repsonse here
                Toast.makeText(MainActivity.this,"Something went wrong try again letter",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            protected Map<String,String> getParams(){
                //here we add parameters
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("lastcount","0");
                System.out.println("HashMap : "+hashMap);
                return hashMap;
            }
        };

        //**************************RETRY POLICY VOLLEY ADVANTAGE **********
        int SOCKET_TIME_OUT = 30000;
        request.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIME_OUT,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }


    public void parseData(){

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url = "http://www.johshamgroup.net/jgn/inc/site.inc.web.php?xAction=getWorkType";
        System.out.println("Url is : "+url);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                /// We get Response here
                System.out.println("Response is : "+response);
                //Response is in String format conver it into JSONOBJECT
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //we get JSONObject
                    System.out.print("JSON DATA : "+jsonObject.getString("SUCCESS"));

                    //GET JSONArray from JSONObject and parse it
                    //First check array is present or not
                    if(jsonObject.has("RESULT")){
                        JSONArray jsonArrayResult = jsonObject.getJSONArray("RESULT");
                        //jsonArrayResult is our jSOARray we get one by one record using for loop

                        for(int i=0;i<jsonArrayResult.length();i++){
                            JSONObject jsonObject1 = jsonArrayResult.getJSONObject(i);

                            System.out.print("JSON ARRAY ["+i+"] record is : "+jsonObject1.getString("workTypeID"));
                        }
                    }

                }catch (Exception e){
                    System.out.print("Error in Parsing : "+e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //we get error repsonse here
                Toast.makeText(MainActivity.this,"Something went wrong try again letter",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            protected Map<String,String> getParams(){
                //here we add parameters
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("lastcount","0");
                System.out.println("HashMap : "+hashMap);
                return hashMap;
            }
        };

        //**************************RETRY POLICY VOLLEY ADVANTAGE **********
        int SOCKET_TIME_OUT = 30000;
        request.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIME_OUT,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }



    class DataList extends ArrayAdapter{

        Context cntx;
        ArrayList<HashMap<String,String>> lst;

        DataList(Context cntx, int res, ArrayList<HashMap<String,String>> lst){
            super(cntx,res,lst);
            this.cntx = cntx;
            this.lst = lst;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final HashMap<String,String> hs  = lst.get(position);
            ViewHolder viewHolder;

            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater)cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_item_list_view,null);

                viewHolder = new ViewHolder();
                viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
                viewHolder.tvId = (TextView)convertView.findViewById(R.id.tvID);
                viewHolder.tvDate = (TextView)convertView.findViewById(R.id.tvDate);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ivImage);
                viewHolder.item_parent = (RelativeLayout) convertView.findViewById(R.id.item_parent);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }


            viewHolder.tvTitle.setText(hs.get("title").toString());
            viewHolder.tvId.setText(hs.get("id").toString());
            viewHolder.tvDate.setText(hs.get("date").toString());

            viewHolder.item_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,Details_Screen.class);
                    intent.putExtra("title",hs.get("title").toString());
                    intent.putExtra("id",hs.get("id").toString());
                    intent.putExtra("date",hs.get("date").toString());
                    startActivity(intent);
                    finish();
                }
            });

            return convertView;
        }

        public class ViewHolder{
            ImageView imageView;
            TextView tvTitle,tvDate,tvId;
            RelativeLayout item_parent;
        }
    }
}
