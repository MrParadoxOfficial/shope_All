package shope.three3pro.shope_All.screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.db.DB_Cart_Manager;
import shope.three3pro.shope_All.model.RegisterModel;
import shope.three3pro.shope_All.model.cart_Model;
import shope.three3pro.shope_All.adpter.OnClickListener;
import shope.three3pro.shope_All.model.sharedPerferance;
import shope.three3pro.shope_All.network.APIService;
import shope.three3pro.shope_All.network.RetroInstance;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private List<cart_Model> productModel;
    private Context context;
    private View.OnClickListener mOnItemClickListener;
    private DB_Cart_Manager dbCartManager;

    private OnClickListener onClickListener;

    TextView rateview;
    public CartAdapter(List<cart_Model> productModels, Context context,OnClickListener onClickListener ,TextView rateview) {
        this.productModel = productModels;
        this.context=context;
        this.onClickListener=onClickListener;
        this.rateview=rateview;

    }
    public void setMovieList(List<cart_Model> catogoryModels){
        this.productModel=catogoryModels;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.cart_row_rec,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.e("Paradox     "+productModel.get(position).getProduct_name()+"","");
        RegisterModel user = sharedPerferance.getInstance(context).getUser();
        String user_name=user.getUsername();
        int user_id=user.getId();
        final int[] qq = {0};

        if (user_id==productModel.get(position).getUser_id()){


            holder.Product_text_row_contian_smaller.setText(this.productModel.get(position).getProduct_name());
            holder.Product_point_row_contian_smaller.setText(this.productModel.get(position).getProduct_id()+"");
            holder.Product_price_row_contian_smaller.setText(this.productModel.get(position).getProduct_price()+"");
            Glide.with(holder.Product_price_row_contian_smaller.getContext()).load(productModel.get(position).getImage_url()).into(holder.image_product);
            holder.Min_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (qq[0] >0){
                        qq[0]--;
                        int totalprice= qq[0] *productModel.get(holder.getAdapterPosition()).getProduct_price();
                        holder.view_qun_txt.setText(qq[0] +"");
                        onClickListener.onClick(holder.getAdapterPosition(),qq[0],totalprice);

                    }else {
                        Toast.makeText(context, "Cann't get under 0", Toast.LENGTH_SHORT).show();

                    }
                }
            });
            holder.PLus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    qq[0] +=1;
                    int totalprice= qq[0] *productModel.get(holder.getAdapterPosition()).getProduct_price();
                    onClickListener.onClick(holder.getAdapterPosition(),qq[0],totalprice);
                    holder.total_price_txt.setText("Total : "+totalprice);

                    holder.view_qun_txt.setText(qq[0] +"");
                }
            });

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id=productModel.get(position).getId();
                    removeCart(id);
                    notifyItemRemoved(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (this.productModel !=null){
            return productModel.size();
        }
        return 0;
    }public class MyViewHolder extends RecyclerView.ViewHolder{
         ImageView image_product,PLus_btn,Min_btn,remove;
        TextView Product_text_row_contian_smaller,Product_point_row_contian_smaller,Product_price_row_contian_smaller,total_price_txt;
        LinearLayout user_check_layout;
        TextView view_qun_txt;
        public MyViewHolder (View itemView){
            super(itemView);
            Product_text_row_contian_smaller=itemView.findViewById(R.id.cart_title_product);
            Product_point_row_contian_smaller=itemView.findViewById(R.id.cart_point_product);
            Product_price_row_contian_smaller=itemView.findViewById(R.id.cart_size_product);
            image_product=itemView.findViewById(R.id.cart_image);
            PLus_btn=itemView.findViewById(R.id.plus_btn_image);
            Min_btn=itemView.findViewById(R.id.min_btn_image);
            view_qun_txt=itemView.findViewById(R.id.view_qun_txt);
            remove=itemView.findViewById(R.id.remove_btn);
            total_price_txt=itemView.findViewById(R.id.total_price_txt);
        }
    }

    public int updateprice(int number,int summtion )
    {
        int sum=0,i;
        for(i=0;i< productModel.size();i++){
            summtion=summtion+number;

        }return summtion;



    }

    private void removeCart(int id) {
        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //creating retrofit object
        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);
        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.removeCart(id);
        Log.e("String +"+id+"  ","id +"+id+"  ");

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {

                if (!response.body().error) {

                    Toast.makeText(context, "done remove...", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, " try agian...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<uploadCatModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
