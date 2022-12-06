package shope.three3pro.shope_All.screen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.db.DBManager;
import shope.three3pro.shope_All.db.DB_Cart_Manager;
import shope.three3pro.shope_All.model.RegisterModel;
import shope.three3pro.shope_All.model.productModel;
import shope.three3pro.shope_All.model.sharedPerferance;
import shope.three3pro.shope_All.network.APIService;
import shope.three3pro.shope_All.network.RetroInstance;

public class productAdapter extends RecyclerView.Adapter<productAdapter.MyViewHolder> {
    private List<productModel> productModel;
    private Context context;
    private DBManager dbManager;
    private DB_Cart_Manager dbCartManager;

    public productAdapter(List<productModel> productModels, Context context) {
        this.productModel = productModels;
        this.context=context;
    }
    public void setMovieList(List<productModel> catogoryModels){
        this.productModel=catogoryModels;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_row_smaller,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Product_text_row_contian_smaller.setText(this.productModel.get(position).getProduct_name());
        holder.Product_point_row_contian_smaller.setText(this.productModel.get(position).getProduct_point());
        holder.Product_price_row_contian_smaller.setText(this.productModel.get(position).getProduct_price());


        Glide.with(holder.Product_text_row_contian_smaller.getContext()).load(productModel.get(position).getProduct_image()).into(holder.image_product);
        String getId=String.valueOf(productModel.get(position).getId());
        String getProduct_name=productModel.get(position).getProduct_name();
        String getProduct_price=productModel.get(position).getProduct_price();
        String getProduct_point=productModel.get(position).getProduct_point();
        String getProduct_image=productModel.get(position).getProduct_image();
        String getProduct_description=productModel.get(position).getProduct_description();
        String getTrader_name=productModel.get(position).getTrader_name();
        String getProduct_size=productModel.get(position).getProduct_size();
        int getProduct_check=productModel.get(position).getCheck_fav();
        if (getProduct_check==1){
            holder.love_pbtn.setBackgroundResource(R.drawable.love_on);
        }else {
            holder.love_pbtn.setBackgroundResource(R.drawable.love_off);

        }
        holder.add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegisterModel user = sharedPerferance.getInstance(context).getUser();
                String user_name=user.getUsername();
                int id=user.getId();


               go_to_Cart(id
                       ,user_name
                       ,productModel.get(position).getId()
                       , productModel.get(position).getProduct_name()
                       ,Integer.parseInt(productModel.get(position).getProduct_price())
                       ,productModel.get(position).getProduct_size()
                       ,productModel.get(position).getTrader_name(),
                       productModel.get(position).getProduct_image());

            }
        });
        holder.love_pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getProduct_check==1){

                    updateFav(getId,0);


                    holder.love_pbtn.setBackgroundResource(R.drawable.love_off);


                } else if (getProduct_check==0) {
                    updateFav(getId,1);

//                    dbManager.delete(getId);
                    holder.love_pbtn.setBackgroundResource(R.drawable.love_on);


                }
                else {
                    Toast.makeText(context, "get "+getProduct_check, Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    @Override
    public int getItemCount() {
        if (this.productModel !=null){
            return productModel.size();
        }
        return 0;
    }public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image_product,love_pbtn;
        TextView Product_text_row_contian_smaller,Product_point_row_contian_smaller,Product_price_row_contian_smaller;
        LinearLayout user_check_layout;
        Button add_cart;
        public MyViewHolder (View itemView){
            super(itemView);
            Product_text_row_contian_smaller=itemView.findViewById(R.id.Product_text_row_contian_smaller);
            Product_point_row_contian_smaller=itemView.findViewById(R.id.Product_point_row_contian_smaller);
            Product_price_row_contian_smaller=itemView.findViewById(R.id.Product_price_row_contian_smaller);
            image_product=itemView.findViewById(R.id.image_product_row_contian_smaller);
            love_pbtn=itemView.findViewById(R.id.love_pbtn);
            add_cart=itemView.findViewById(R.id.Buy_btn_get);



        }
    }
    private void updateFav(String id, int check_fav) {

        //creating a file

        //creating request body for file
        RequestBody idBOdy = RequestBody.create(MediaType.parse("text/plain"), id);
        //RequestBody check_favBody = RequestBody.create(MediaType.parse("text/plain"), check_fav);

        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object

        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);


        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.update_check_fav(idBOdy, check_fav);

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {
                if (!response.body().error) {
                    Toast.makeText(context, "Uploaded Successfully...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<uploadCatModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void go_to_Cart(
            int user_id,
            String user_name,
            int product_id,
            String product_name,
            int product_price,
            String product_size,
            String trader_name,
            String image


    ) {

        //creating a file

        //creating request body for file
        RequestBody user_body = RequestBody.create(MediaType.parse("text/plain"), user_name);
        RequestBody productname_body = RequestBody.create(MediaType.parse("text/plain"), product_name);
        RequestBody proudctsize_body = RequestBody.create(MediaType.parse("text/plain"), product_size);
        RequestBody tradername_body = RequestBody.create(MediaType.parse("text/plain"), trader_name);
        RequestBody image_body = RequestBody.create(MediaType.parse("text/plain"), image);
        //RequestBody check_favBody = RequestBody.create(MediaType.parse("text/plain"), check_fav);

        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object

        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);


        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.cart_manger(
                user_id
                ,user_body,
                product_id,
                productname_body,
                product_price,
                proudctsize_body,
                tradername_body,
                image_body
        );

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {
                if (!response.body().error) {
                    Toast.makeText(context, "Uploaded Successfully...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<uploadCatModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
