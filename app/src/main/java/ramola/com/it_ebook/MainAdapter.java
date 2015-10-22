package ramola.com.it_ebook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.section.setText(list.get(position).Section);
        holder.title.setText(list.get(position).title);
        holder.description.setText(list.get(position).description);
        imageLoader.get(list.get(position).url,new ImageLoader.ImageListener() {
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView section,title,description;
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            section= (TextView) itemView.findViewById(R.id.section_row_main);
            title= (TextView) itemView.findViewById(R.id.title_row_main);
            description= (TextView) itemView.findViewById(R.id.description_row_main);
            image= (ImageView) itemView.findViewById(R.id.image_row_main);
        }
    }
}
