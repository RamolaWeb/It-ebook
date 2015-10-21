package ramola.com.it_ebook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
RecyclerView recyclerView;
    ArrayList<Item> list;
    recycler_adapter adapter;
    EditText search;
    Button btn;
    String data="http://it-ebooks-api.info/v1/search/";
    String font="fonts/GrandHotel-Regular.otf";
    Typeface tf;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        tf=Typeface.createFromAsset(getAssets(),font);
        search= (EditText) findViewById(R.id.search_edit);
        search.setTypeface(tf);
        btn= (Button) findViewById(R.id.search_btn);
        btn.setTypeface(tf);
        changeUI(false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("LOADING.......");
                progressDialog.show();
                fetchData(data + search.getText().toString());
            }
        });
recyclerView.addOnItemTouchListener(new ClickListener(MainActivity.this,new ClickListener.OnItemClickListener() {
    @Override
    public void onItemClick(View view, int position) {
        Item item=list.get(position);
        Intent intent=new Intent("ramola.com.it_ebook.detail");
        intent.putExtra("ID",item.id);
        startActivity(intent);
    }
}));
    }

public void fetchData(String url){
    list=new ArrayList<>();
    changeUI(true);
JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
    @Override
    public void onResponse(JSONObject jsonObject) {
hideProgressDialog();
        try {
            JSONArray jsonArray=new JSONArray(jsonObject.getString("Books"));
            for (int i=0;i<jsonArray.length();i++){
             final   JSONObject data=jsonArray.getJSONObject(i);

                ImageRequest imageRequest=new ImageRequest(data.getString("Image"),new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        try {
                            list.add(new Item(data.getString("Title"),data.getString("isbn"),data.getString("Description"),data.getString("ID"),bitmap));

                        } catch (JSONException e) {
                            e.printStackTrace();
                         Toast.makeText(MainActivity.this,"IMAGE COULD NOT BE LOAD",Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                },0,0,null,new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                        Toast.makeText(MainActivity.this,"Network ERROR",Toast.LENGTH_SHORT).show();
                    }
                });

                MySingleton.getInstance(MainActivity.this).addToRequestQueue(imageRequest);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"NO BOOKS FOUND Hi to all",Toast.LENGTH_SHORT).show();
        }
    }
},new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        volleyError.printStackTrace();
        Toast.makeText(MainActivity.this,"NETWORK ERROR",Toast.LENGTH_SHORT).show();
    }
});
    MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
    adapter=new recycler_adapter(MainActivity.this,list);
    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    recyclerView.setAdapter(adapter);
}

    @Override
    protected void onStop() {
        super.onStop();
        if(progressDialog!=null){
            hideProgressDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           changeUI(false);
        }

        return super.onOptionsItemSelected(item);
    }
    public void changeUI(Boolean result){
        if(result){
            recyclerView.setVisibility(View.VISIBLE);
            search.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);

        }
        else{
            recyclerView.setVisibility(View.GONE);
            search.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
        }
    }
    public  void hideProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }
}
