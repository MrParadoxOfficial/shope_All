package shope.three3pro.shope_All.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;
import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.model.RegisterModel;
import shope.three3pro.shope_All.model.sharedPerferance;
import shope.three3pro.shope_All.network.APIService;
import shope.three3pro.shope_All.network.RetroInstance;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

public class AddPaymentCode extends AppCompatActivity {
    private TextInputLayout get_payment_code,money_qun_edx,from_where_edx;
    private String payment_code,money_qun,from_where;
    private Button add_payment_btn,confrim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment_code);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Payment Code");
        money_qun_edx=findViewById(R.id.money_qq_edx);
        from_where_edx=findViewById(R.id.from_where_Edx);
        get_payment_code=findViewById(R.id.payment_code_edx);
        add_payment_btn=findViewById(R.id.add_payment_btn);
        confrim=findViewById(R.id.confrim_payment);
        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPaymentCode.this ,Confrim_payment.class));
            }
        });
        add_payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromEditeText();
                RegisterModel user = sharedPerferance.getInstance(AddPaymentCode.this).getUser();
                String user_name=user.getUsername();
                int user_id=user.getId();
                int mon=Integer.parseInt(money_qun);


                SavePayment(user_id,from_where,payment_code,user_name,mon);
                get_payment_code.getEditText().setText("");
                from_where_edx.getEditText().setText("");
                money_qun_edx.getEditText().setText("");


            }
        });

    }private void getDataFromEditeText(){

        payment_code=get_payment_code.getEditText().getText().toString();
        money_qun=money_qun_edx.getEditText().getText().toString();
        from_where=from_where_edx.getEditText().getText().toString();


    }

    private void SavePayment( int use_id,String from_where,String money_code,String use_name,int money_qq) {

        //creating a file

        //creating request body for file

        RequestBody from_where_c = RequestBody.create(MediaType.parse("text/plain"), from_where);
        RequestBody money_code_c = RequestBody.create(MediaType.parse("text/plain"), money_code);
        RequestBody use_name_c = RequestBody.create(MediaType.parse("text/plain"), use_name);

        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object

        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);


        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.save_payment_code(use_id,from_where_c,money_code_c,use_name_c,money_qq);

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {
                if (!response.body().error) {
                    Toast.makeText(AddPaymentCode.this, "ADD Successfully...", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(AddPaymentCode.this, "Some error occurred...", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<uploadCatModel> call, Throwable t) {
                Toast.makeText(AddPaymentCode.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }




}