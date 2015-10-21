package ramola.com.it_ebook;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class recycler_adapter extends RecyclerView.Adapter<recycler_adapter.ViewHolder> {
    ArrayList<Item> list;
    Context context;
    String font="fonts/GrandHotel-Regular.otf";
    Typeface tf;
    public recycler_adapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
        tf=Typeface.createFromAsset(context.getAssets(),font);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText("Title :- "+list.get(position).Title);
        holder.description.setText("Description :- "+list.get(position).Description);
        holder.author.setText("ISBN :- "+list.get(position).Author);
        holder.imageView.setImageBitmap(list.get(position).bm);
        holder.id.setText("ID :- "+list.get(position).id);
        holder.title.setTypeface(tf);
        holder.description.setTypeface(tf);
        holder.author.setTypeface(tf);
        holder.id.setTypeface(tf);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,description,author,id;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title_row);
            description= (TextView) itemView.findViewById(R.id.description_row);
            author= (TextView) itemView.findViewById(R.id.author_row);
            imageView= (ImageView) itemView.findViewById(R.id.image_row);
            id= (TextView) itemView.findViewById(R.id.text_ID);
        }
    }
}
