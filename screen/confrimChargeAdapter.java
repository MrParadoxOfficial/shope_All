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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.db.DB_Cart_Manager;
import shope.three3pro.shope_All.model.ChargeConfrimModel;
import shope.three3pro.shope_All.network.APIService;
import shope.three3pro.shope_All.network.RetroInstance;

public class confrimChargeAdapter extends RecyclerView.Adapter<confrimChargeAdapter.MyViewHolder> {
    private List<ChargeConfrimModel> chargeConfrimModels;
    private Context context;

    private DB_Cart_Manager dbCartManager;
    private int qq=0;

    public confrimChargeAdapter(List<ChargeConfrimModel> productModels, Context context) {
        this.chargeConfrimModels = productModels;
        this.context=context;
    }
    public void setMovieList(List<ChargeConfrimModel> chargeConfrimModels){
        this.chargeConfrimModels=chargeConfrimModels;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.confrim_payment_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.user_name_confr.setText(this.chargeConfrimModels.get(position).getUse_name());
        holder.money_name_confr.setText(this.chargeConfrimModels.get(position).getMoney_qq()+"");
        holder.fromwhere_confrom.setText(this.chargeConfrimModels.get(position).getFrom_where());
        holder.entCOde_confr.setText(this.chargeConfrimModels.get(position).getMoney_code());
        holder.true_png.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 int id=Integer.parseInt(chargeConfrimModels.get(position).getUse_id());
                 int money=chargeConfrimModels.get(position).getMoney_qq();
                 int id_post=chargeConfrimModels.get(position).getId();
                updatemoney(id,money,id_post,holder.getAdapterPosition());

            }
        });
        holder.flase_png.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_post=chargeConfrimModels.get(position).getId();

                deletemoney(id_post,holder.getAdapterPosition());

            }
        });





    }

    @Override
    public int getItemCount() {
        if (this.chargeConfrimModels !=null){
            return chargeConfrimModels.size();
        }
        return 0;
    }public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView user_name_confr
                ,money_name_confr
                ,fromwhere_confrom
                ,entCOde_confr;
        LinearLayout user_check_layout;
        ImageView true_png,flase_png;
        public MyViewHolder (View itemView){
            super(itemView);
            user_name_confr=itemView.findViewById(R.id.user_name_confr);
            money_name_confr=itemView.findViewById(R.id.money_name_confr);
            fromwhere_confrom=itemView.findViewById(R.id.fromwhere_confrom);
            entCOde_confr=itemView.findViewById(R.id.paymentCOde_confr);
            true_png=itemView.findViewById(R.id.true_png);
            flase_png=itemView.findViewById(R.id.no_png);
     ;



        }}

    private void updatemoney(int id, int money,int id_post,int postion) {





        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object

        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);


        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.updateMoney(id, money);
        Log.e("String +"+id+"  ","money +"+money+"  ");

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {
                if (!response.body().error) {
                   // Toast.makeText(context, "Updated Successfully...", Toast.LENGTH_LONG).show();
                    deletemoney(id_post,postion);
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
    private void deletemoney(int id,int position) {





        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object

        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);


        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.delete_charge(id);
        Log.e("String +"+id+"  ","money +");

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {
                if (!response.body().error) {
                    Toast.makeText(context, "All Successfully...", Toast.LENGTH_LONG).show();
                    removeItem(position);

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

    public void removeItem(int position) {
        chargeConfrimModels.remove(position);
        notifyItemRemoved(position);
    }




}
