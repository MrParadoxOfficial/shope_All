package shope.three3pro.shope_All.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.model.RegisterModel;
import shope.three3pro.shope_All.model.cart_Model;
import shope.three3pro.shope_All.adpter.OnClickListener;
import shope.three3pro.shope_All.model.sharedPerferance;
import shope.three3pro.shope_All.network.APIService;
import shope.three3pro.shope_All.network.DataListViewModel;
import shope.three3pro.shope_All.network.RetroInstance;


public class cart_activity extends AppCompatActivity {
    private List<cart_Model> cart_models;

    private CartAdapter cartAdapter;
    private RecyclerView rec_cart_product;
    private Button done_buy;
    private OnClickListener onClickListener;
    private int all_point,all_money;
    private TextView total_point_txt,total_price_txt;
    Dialog buy_done;
    private DataListViewModel viewModel;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        buy_done=new Dialog(cart_activity.this);
        buy_done.setContentView(R.layout.buy_done);
        RegisterModel user = sharedPerferance.getInstance(cart_activity.this).getUser();
        String user_name=user.getUsername();
        int user_id=user.getId();




        rec_cart_product=findViewById(R.id.rec_cart_acti);
        done_buy=findViewById(R.id.buy_done);
        done_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterModel user = sharedPerferance.getInstance(cart_activity.this).getUser();
                String user_name=user.getUsername();
                int user_id=user.getId();


                buysomething(user_id,all_money);
            }
        });
        total_point_txt=findViewById(R.id.total_point);
        total_price_txt=findViewById(R.id.total_price);

        cart_models = new ArrayList<>();

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);



        viewModel= ViewModelProviders.of(this).get(DataListViewModel.class);
        Log.e("from on dasdda","tag");

        viewModel.getcartmanger().observe(this, new Observer<List<cart_Model>>() {
            @Override
            public void onChanged(List<cart_Model> sign) {
                Log.e("from on d","tag");
                if (sign !=null){
                    cart_models=sign;
                    cartAdapter.setMovieList(cart_models);
                    Toast.makeText(cart_activity.this, "done from home ", Toast.LENGTH_SHORT).show();
                    Log.e("from on change","tag");

                }else{
                    Log.e("TAG", "onChanged: else " );
                    Toast.makeText(cart_activity.this, "failed fromm home observer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getcartManger();
        rec_cart_product.setAdapter(cartAdapter);


        onClickListener=new OnClickListener() {
            @Override
            public void onClick(int position, int number_of_point, int total_price) {

                all_point=number_of_point;
                all_money=total_price;
               total_point_txt.setText(number_of_point+"");
               total_price_txt.setText(total_price+"");


            }
        };
        cartAdapter = new CartAdapter(cart_models,this,onClickListener,total_price_txt);

        rec_cart_product.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        rec_cart_product.setAdapter(cartAdapter);


    }
    private void buysomething(int id, int money) {
        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //creating retrofit object
        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);
        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.Buy_something(id, money);
        Log.e("String +"+id+"  ","money +"+money+"  ");

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {

                if (!response.body().error) {
                    setcart_pp(cart_models);



                     Toast.makeText(cart_activity.this, "Buy Successfully...", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(cart_activity.this, "Please Charge and try agian...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<uploadCatModel> call, Throwable t) {
                Toast.makeText(cart_activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
                    buy_done.show();

                    Toast.makeText(cart_activity.this, "done remove...", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(cart_activity.this, " try agian...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<uploadCatModel> call, Throwable t) {
                Toast.makeText(cart_activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void saveReport() {
        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //creating retrofit object
        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);
        //creating a call and calling the upload image method
        int id;

        int user_id;
        String user_name;
        int product_id;
        String product_name;
        String product_trader;
        String product_image;
        int product_price;
        int product_size;
        int product_number;
        String buyier_data;
        String buyer_state;
        for( int i=0;i< cart_models.size();i++){

            id=cart_models.get(i).getId();
            user_id=cart_models.get(i).getUser_id();
            user_name=cart_models.get(i).getUser_name();
            product_id=cart_models.get(i).getProduct_id();
            product_name=cart_models.get(i).getProduct_name();
            product_trader=cart_models.get(i).getTrader_name();
            product_image=cart_models.get(i).getImage_url();
            product_price=cart_models.get(i).getProduct_price();
            product_size=cart_models.get(i).getProduct_size();
            product_number=cart_models.get(i).getProduct_price();
            buyier_data=cart_models.get(i).getTrader_name();
            buyer_state=cart_models.get(i).getTrader_name();



            RequestBody user_name_b = RequestBody.create(MediaType.parse("text/plain"), user_name);
            RequestBody product_name_b = RequestBody.create(MediaType.parse("text/plain"), product_name);
            RequestBody product_trader_b = RequestBody.create(MediaType.parse("text/plain"), product_trader);
            RequestBody product_image_b = RequestBody.create(MediaType.parse("text/plain"), product_image);
            RequestBody buyier_data_b = RequestBody.create(MediaType.parse("text/plain"), buyier_data);
            RequestBody buyer_state_b = RequestBody.create(MediaType.parse("text/plain"), buyer_state);

            Call<uploadCatModel> call = apiService.saveReport(
                    user_id,
                    user_name_b,
                    product_id,
                    product_name_b,
                    product_trader_b,
                    product_image_b,
                    product_price
                    ,buyier_data_b
                    ,product_number
                    ,buyier_data_b
                    ,buyer_state_b);

            //finally performing the call


            



            call.enqueue(new Callback<uploadCatModel>() {
                @Override
                public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {

                    if (!response.body().error) {
                        buy_done.show();
                        for( int i=0;i< cart_models.size();i++){

                            removeCart(cart_models.get(i).getId());


                        }

                        Toast.makeText(cart_activity.this, "done ...", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(cart_activity.this, " try agian...", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<uploadCatModel> call, Throwable t) {
                    Toast.makeText(cart_activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        }


    }
    public void setcart_pp(List<cart_Model> model){
        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);
        apiService.createcart(model, new Callback<cart_Model>() {
            @Override
            public void onResponse(Call<cart_Model> call, Response<cart_Model> response) {

                cart_Model responseUser = response.body();
                if (response.isSuccessful() && responseUser != null) {
                    Toast.makeText(cart_activity.this,
                            String.format("User %s with job %s was created at %s with id %s",
                                    responseUser.getUser_name(),
                                    responseUser.getProduct_price(),
                                    responseUser.getProduct_name(),
                                    responseUser.getId()),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(cart_activity.this,
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<cart_Model> call, Throwable t) {

                Toast.makeText(cart_activity.this,
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });




    }








}