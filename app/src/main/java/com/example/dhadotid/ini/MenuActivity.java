package com.example.dhadotid.ini;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.LayoutInflater;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id, txt_userID, txt_judul, txt_isi;
    String id, judul, isi, userID, userIDFIX;

    private static final String TAG = MenuActivity.class.getSimpleName();

    private static final String url_select = AppConfig.URL_CRUD + "select.php";
    private static String url_insert = AppConfig.URL_CRUD + "insert.php";
    private static final String url_edit = AppConfig.URL_CRUD + "edit.php";
    private static final String url_update = AppConfig.URL_CRUD + "update.php";
    private static final String url_delete = AppConfig.URL_CRUD + "delete.php";

    public static final String TAG_ID = "id";
    public static final String TAG_USERID = "userID";
    public static final String TAG_JUDUL = "judul_curhat";
    public static final String TAG_ISI = "isi_curhat";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_MESSAGE = "message";

    String tag_json_obj;

    private Toolbar mToolbar;
    private static final String ID = "id";

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //txtName = (TextView)findViewById(R.id.name);
        //txtEmail = (TextView)findViewById(R.id.email);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);

        //mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //join variable in layout and java
        fab = (FloatingActionButton)findViewById(R.id.fab_add);
        swipe = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        list = (ListView)findViewById(R.id.list);

        //for parsing data from json to adapter
        adapter = new Adapter(MenuActivity.this, itemList);
        list.setAdapter(adapter);

        //show widged refresh
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                itemList.clear();
                adapter.notifyDataSetChanged();
                callVolley(userIDFIX);
            }
        });

        //function floating action button for call form curhat
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm("", "", "", "", "Save");
            }
        });

        // listview ditekan lama akan menampilkan dua pilihan edit atau delete data
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view,
                                           final int position, long id) {
                // TODO Auto-generated method stub
                final String idx = itemList.get(position).getId();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(MenuActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        switch (which) {
                            case 0:
                                edit(idx);
                                break;
                            case 1:
                                delete(idx);
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });

        //SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        //Session manager
        session = new SessionManager(getApplicationContext());

        if(!session.isLoggedIn()){
            logoutUser();
        }

        //fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
        userIDFIX = user.get("id");
        //display the user details
        //txtName.setText(id);
        //txtEmail.setText(email);
    }

    public void onRefresh(){
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley(userIDFIX);
    }

    //buat ngosongin edittext
    private void kosong(){
        txt_id.setText(null);
        txt_userID.setText(null);
        txt_judul.setText(null);
        txt_isi.setText(null);
    }
    //for show dialog form curhat
    private void DialogForm(String idx, final String userIDx, String judulx, String isix, String button){
        dialog = new AlertDialog.Builder(MenuActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_curhat, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Curhat");

        txt_id = (EditText) dialogView.findViewById(R.id.txt_id);
        txt_userID = (EditText) dialogView.findViewById(R.id.txt_userID);
        txt_judul = (EditText) dialogView.findViewById(R.id.txt_judul_curhat);
        txt_isi = (EditText) dialogView.findViewById(R.id.txt_isi_curhat);

        if(!idx.isEmpty()){
            txt_id.setText(idx);
            txt_userID.setText(userIDx);
            txt_judul.setText(judulx);
            txt_isi.setText(isix);
        }else {
            kosong();
        }
        dialog.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                id = txt_id.getText().toString();
                userID = userIDFIX;
                judul = txt_judul.getText().toString();
                isi = txt_isi.getText().toString();

                simpan_update();
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                kosong();
            }
        });
        dialog.show();
    }

    //untuk menampilkan data pada listview
    private void callVolley(final String userIDFIX){
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);
/*
        StringRequest strReq = new StringRequest(Request.Method.POST, url_select, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject obj = new JSONObject(response);
                    success = obj.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        for (int i = 0; i < response.length(); i++) {
                            Data item = new Data();
                            item.setId(obj.getString(TAG_ID));
                            item.setUserID(obj.getString(TAG_USERID));
                            item.setJudul(obj.getString(TAG_JUDUL));
                            item.setIsi(obj.getString(TAG_ISI));
                            //menambah item ke array
                            itemList.add(item);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,"Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        })
            */

        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                //pasring json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        //adapter.notifyDataSetChanged();
                        Data item = new Data();
                        item.setId(obj.getString(TAG_ID));
                        item.setUserID(obj.getString(TAG_USERID));
                        item.setJudul(obj.getString(TAG_JUDUL));
                        item.setIsi(obj.getString(TAG_ISI));
                        //menambah item ke array
                        itemList.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //notif perubahan data pada adapter
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,"Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to select url
                Map<String, String> params = new HashMap<String, String>();
                params.put("userID", userIDFIX);

                return params;
            }
        };
        //menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }
    //fungsi untuk menyimpan atau update
    private void simpan_update(){
        String url;
        //if id kosong maka simpan, if id ada maka update
        if(id.isEmpty()){
            url = url_insert;
        }else {
            url = url_update;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                //JSONObject jObj = null;
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    //cek error node pada json
                    if (success == 1) {
                        Log.d("Add/Update", jObj.toString());

                        callVolley(userIDFIX);
                        kosong();

                        Toast.makeText(MenuActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MenuActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MenuActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                //jike id kosong maka simpan, jika id ada nilainya maka update
                if(id.isEmpty()){
                    params.put("userID", userIDFIX);
                    params.put("judul_curhat", judul);
                    params.put("isi_curhat", isi);
                }else{
                    params.put("id", id);
                    params.put("userID", userIDFIX);
                    params.put("judul_curhat", judul);
                    params.put("isi_curhat", isi);
                }
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
    //fungsi untuk get edit data
    private void edit(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    //cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String idx = jObj.getString(TAG_ID);
                        String userIDx = jObj.getString(TAG_USERID);
                        String judulx = jObj.getString(TAG_JUDUL);
                        String isix = jObj.getString(TAG_ISI);

                        DialogForm(idx, userIDx, judulx, isix, "UPDATE");
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MenuActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MenuActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                //posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
    //fungsi delete
    private void delete(final String idx){
        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Delete", jObj.toString());

                        callVolley(userIDFIX);

                        Toast.makeText(MenuActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(MenuActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MenuActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser(){
        session.setLogin(false);
        db.deleteUsers();

        //Launching the login activity
        Intent in = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
