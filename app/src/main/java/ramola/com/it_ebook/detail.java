package ramola.com.it_ebook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class detail extends ActionBarActivity {
TextView title,description,publisher,author;
    ImageView image;
    String url="http://it-ebooks-api.info/v1/book/";
    ImageLoader imageLoader;
    String font="fonts/GrandHotel-Regular.otf";
    Typeface tf;
    String dwnld_url="";
    Button dwnld;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        title= (TextView) findViewById(R.id.title_detail);
        description= (TextView) findViewById(R.id.description_detail);
        publisher= (TextView) findViewById(R.id.publisher_detail);
        author= (TextView) findViewById(R.id.author_detail);
        image= (ImageView) findViewById(R.id.imageView_detail);
        dwnld= (Button) findViewById(R.id.dwnld_btn);
        hideUi(true);
        tf=Typeface.createFromAsset(getAssets(),font);
        title.setTypeface(tf);
        description.setTypeface(tf);
        publisher.setTypeface(tf);
        author.setTypeface(tf);
        Intent intent=getIntent();
        if(null!=intent){
            progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Loading...");
            progressDialog.show();
            fetchdata(url + intent.getStringExtra("ID"));
        }

        dwnld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dwnld_url!=null&&dwnld_url.length()!=0)
                    downloadEbook(dwnld_url);
            }
        });
    }

public void fetchdata(String url) {
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            hideUi(false);
            hideProgressDialog();
            try {
                title.setText("Title :- " + jsonObject.getString("Title"));
                description.setText("Description :- " + jsonObject.getString("Description"));
                publisher.setText("Publisher :- " + jsonObject.getString("Publisher"));
                author.setText("Author :- " + jsonObject.getString("Author"));
                imageLoader = MySingleton.getInstance(detail.this).getImageLoader();
                imageLoader.get(jsonObject.getString("Image"), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                        image.setImageBitmap(imageContainer.getBitmap());
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(detail.this, "LOADING IMAGE ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
                dwnld_url=jsonObject.getString("Download");
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(detail.this, "LOADING ERROR", Toast.LENGTH_SHORT).show();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(detail.this, "Network ERROR", Toast.LENGTH_SHORT).show();
        }
    });
    MySingleton.getInstance(detail.this).addToRequestQueue(jsonObjectRequest);
}
    public void downloadEbook(String url){
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

    }
    public  void hideProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }
    public void hideUi(Boolean ui){
        if(ui){
            title.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
            author.setVisibility(View.GONE);
            publisher.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
            dwnld.setVisibility(View.GONE);
        }
        else{
            title.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            author.setVisibility(View.VISIBLE);
            publisher.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            dwnld.setVisibility(View.VISIBLE);
        }
    }
}
