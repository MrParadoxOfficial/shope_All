package shope.three3pro.shope_All.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.model.RegisterModel;
import shope.three3pro.shope_All.network.APIService;
import shope.three3pro.shope_All.model.sharedPerferance;
import shope.three3pro.shope_All.network.RequestHandler;
import shope.three3pro.shope_All.network.RetroInstance;


public class Log_In extends AppCompatActivity {
    private TextView sign_in_textview;
    private TextInputLayout password_edtx_sign_In,Username_edtx_sign_in;
    Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        sign_in_textview=findViewById(R.id.sign_in_textview);
        password_edtx_sign_In=findViewById(R.id.password_edtx_sign_In);
        Username_edtx_sign_in=findViewById(R.id.Username_edtx_sign_in);
        sign_in=findViewById(R.id.sign_in_btn);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=Username_edtx_sign_in.getEditText().getText().toString();
                String password=password_edtx_sign_In.getEditText().getText().toString();
                userLogin();
            }
        });


    }


    private void sginIn( String username_v,String password_c) {

        //creating a file
        //creating request body for file






        RequestBody check_Username = RequestBody.create(MediaType.parse("text/plain"), username_v);
        RequestBody check_password_c = RequestBody.create(MediaType.parse("text/plain"), password_c);

        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object

        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);


        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.log_in(check_Username,check_password_c);

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {
                if (!response.body().error) {
                    Toast.makeText(Log_In.this, "Uploaded Successfully...", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Log_In.this,Log_In.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Log_In.this, "Some error occurred..."+response.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<uploadCatModel> call, Throwable t) {
                Toast.makeText(Log_In.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }




    private void userLogin() {
        //first getting the values
        String username=Username_edtx_sign_in.getEditText().getText().toString();
        String password=password_edtx_sign_In.getEditText().getText().toString();

        //if everything is fine
        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        RegisterModel user = new RegisterModel(
                                userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getString("email")
                        );

                        //storing the user in shared preferences
                        sharedPerferance.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), home_main.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                String url="http://10.1.19.2/test/api.php?apicall=login";


                //returing the response
                return requestHandler.sendPostRequest(url, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }
    }






