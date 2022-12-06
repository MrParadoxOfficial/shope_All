package shope.three3pro.shope_All.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.model.catogoryModel;
import shope.three3pro.shope_All.screen.cat_all_product;

public class CatogryAdapterSmaller extends RecyclerView.Adapter<CatogryAdapterSmaller.MyViewHolder> {
    private List<catogoryModel> catogoryModels;
    private Context context;

    public CatogryAdapterSmaller(List<catogoryModel> catogoryModels, Context context) {
        this.catogoryModels = catogoryModels;
        this.context=context;
    }
    public void setMovieList(List<catogoryModel> catogoryModels){
        this.catogoryModels=catogoryModels;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.catogry_row_smaller,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.catory_text.setText(this.catogoryModels.get(position).getDes());


        Glide.with(holder.catory_text.getContext()).load(catogoryModels.get(position).getPath()).into(holder.catory_image);
        holder.catory_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, cat_all_product.class);
                intent.putExtra("catory_id",catogoryModels.get(position).getId());
                intent.putExtra("catory_name",catogoryModels.get(position).getDes());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (this.catogoryModels !=null){
            return catogoryModels.size();
        }
        return 0;
    }public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView catory_text;
        ImageView catory_image;
        LinearLayout user_check_layout;
        public MyViewHolder (View itemView){
            super(itemView);
            catory_image=itemView.findViewById(R.id.image_catorgy_row_contian_smaller);
            catory_text=itemView.findViewById(R.id.Catogry_text_row_contian_smaller);



        }
    }
}
