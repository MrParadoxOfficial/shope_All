package shope.three3pro.shope_All.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.model.RegisterModel;
import shope.three3pro.shope_All.network.APIService;
import shope.three3pro.shope_All.network.RetroInstance;

public class sign_up_activity extends AppCompatActivity {
    private Button signUp;
    private TextInputLayout Username_edtx_sign_up,
            Pass_edtx_sign_up,
            email_edtx_sign_up,
            referal_edtx_sign_up;
    private String username,password,referual,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp=findViewById(R.id.sign_up_btn);
        Username_edtx_sign_up=findViewById(R.id.Username_edtx_sign_up);
        Pass_edtx_sign_up=findViewById(R.id.Pass_edtx_sign_up);
        email_edtx_sign_up=findViewById(R.id.email_edtx_sign_up);
        referal_edtx_sign_up=findViewById(R.id.referal_edtx_sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromTextINput();
                SignUP(username,email,password,"",referual,100,0);


            }
        });
    }private void getDataFromTextINput(){
        username=Username_edtx_sign_up.getEditText().getText().toString();
        password=Pass_edtx_sign_up.getEditText().getText().toString();
        referual=referal_edtx_sign_up.getEditText().getText().toString();
        email=email_edtx_sign_up.getEditText().getText().toString();

    }

    private void SignUP( String username_v,String email_c,String password_c,String adress, String referual_c,int point , int money) {

        //creating a file

        //creating request body for file

        RequestBody check_Username = RequestBody.create(MediaType.parse("text/plain"), username_v);
        RequestBody check_email_c = RequestBody.create(MediaType.parse("text/plain"), email_c);
        RequestBody check_password_c = RequestBody.create(MediaType.parse("text/plain"), password_c);

        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object

        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);


        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.sign_up(check_Username,check_email_c,check_password_c,adress,point,money);

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {
                if (!response.body().error) {
                    Toast.makeText(sign_up_activity.this, "Uploaded Successfully...", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(sign_up_activity.this,Log_In.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(sign_up_activity.this, "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<uploadCatModel> call, Throwable t) {
                Toast.makeText(sign_up_activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}