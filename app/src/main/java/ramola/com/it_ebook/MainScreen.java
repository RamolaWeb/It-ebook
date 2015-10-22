package ramola.com.it_ebook;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainScreen extends ActionBarActivity {
    RecyclerView recyclerView;
    MainAdapter adapter;
    ArrayList<Item_main> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
       recyclerView= (RecyclerView) findViewById(R.id.recycler_list_main);
        GetJson("http://api.nytimes.com/svc/news/v3/content/all/Technology?api-key=84ced263117a1e6d770f560e9ca6f079:0:73275181");
        adapter=new MainAdapter(MainScreen.this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainScreen.this));
        recyclerView.setAdapter(adapter);
    }
       public void GetJson(String url){
    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
parseJson(jsonObject);
        }
    },new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    });
    MySingleton.getInstance(MainScreen.this).addToRequestQueue(jsonObjectRequest);

}
    public void parseJson(JSONObject jsonObject){
        list=new ArrayList<>();
        String url;
        try {
            JSONArray data=new JSONArray(jsonObject.getString("results"));
            for(int i=0;i<=data.length();i++){
                JSONObject dataItem=data.getJSONObject(i);
                JSONArray urlpic=new JSONArray(dataItem.getString("multimedia"));
                JSONObject urlpicItem=urlpic.getJSONObject(3);
                url=urlpicItem.getString("url");
                list.add(new Item_main(dataItem.getString("section"),dataItem.getString("title"),dataItem.getString("abstract"),url));
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
