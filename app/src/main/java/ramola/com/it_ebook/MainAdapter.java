package ramola.com.it_ebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Context context;
    ArrayList<Item_main> list;
ImageLoader imageLoader;
    public MainAdapter(Context context, ArrayList<Item_main> list) {
        this.context = context;
        this.list = list;
        imageLoader=MySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_main_screen,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.section.setText(list.get(position).Section);
        holder.title.setText(list.get(position).title);
        holder.description.setText(list.get(position).description);
        if(list.get(position).url!=null&&list.get(position).url.length()!=0) {
            imageLoader.get(list.get(position).url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                    holder.image.setImageBitmap(imageContainer.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                }
            });
        }
        else holder.image.setImageResource(R.drawable.ic_launcher);
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).url_more)));
            }
        });
       holder.share.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(Intent.ACTION_SEND);
               i.setType("text/plain");
               i.putExtra(Intent.EXTRA_TEXT,list.get(position).title+" Read more at \n "+list.get(position).url_more);
              context.startActivity(Intent.createChooser(i,"SHARE VIA"));
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView section,title,description;
        ImageView image;
        Button share,more;
        public ViewHolder(View itemView) {
            super(itemView);
            section= (TextView) itemView.findViewById(R.id.section_row_main);
            title= (TextView) itemView.findViewById(R.id.title_row_main);
            description= (TextView) itemView.findViewById(R.id.description_row_main);
            image= (ImageView) itemView.findViewById(R.id.image_row_main);
            share= (Button) itemView.findViewById(R.id.share_row_main);
            more= (Button) itemView.findViewById(R.id.btn_more_row_main);
        }
    }
}
