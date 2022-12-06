package shope.three3pro.shope_All.screen;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shope.three3pro.shope_All.model.RegisterModel;
import  shope.three3pro.shope_All.model.sharedPerferance;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.network.APIService;
import shope.three3pro.shope_All.network.RequestHandler;
import shope.three3pro.shope_All.network.RetroInstance;

public class menuFragment extends Fragment {
    private CardView add_payment,Log_out_btn,add_dress_name;
    private TextView money;
    Dialog dialog_adress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add_payment=view.findViewById(R.id.add_payment_code);
        money=view.findViewById(R.id.money_txt_have);
        Log_out_btn=view.findViewById(R.id.Log_out_btn);
        add_dress_name=view.findViewById(R.id.add_dress_name);
        dialog_adress=new Dialog(getContext());
        dialog_adress.setContentView(R.layout.add_adress_row);
        add_dress_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_adress.show();
                Button diaButton=dialog_adress.findViewById(R.id.add_adress_btn);
                diaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextInputLayout text=dialog_adress.findViewById(R.id.get_Adress_id_btn);
                        String ee=text.getEditText().getText().toString();
                        RegisterModel user = sharedPerferance.getInstance(getContext()).getUser();

                        int user_id=user.getId();
                        SavePayment(user_id,ee);
                    }
                });






            }
        });

        userLogin();
        Log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPerferance.getInstance(getActivity()).logout();


            }
        });
        add_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),AddPaymentCode.class);
                startActivity(intent);
            }
        });

    }


    private void userLogin() {
        RegisterModel user = sharedPerferance.getInstance(getContext()).getUser();

        //first getting the values
        String username=user.getUsername();


        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
              // login splash
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
               // progressBar.setVisibility(View.GONE);



                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        RegisterModel user = new RegisterModel(
                                userJson.getInt("id"),
                                userJson.getString("username"),
                                userJson.getInt("pointhave"),
                                userJson.getInt("moneyhave")
                        );
                        money.setText(user.getMoney_have()+"");
                        //storing the user in shared preferences

                        //starting the profile activity

                    } else {
                        Toast.makeText(getActivity(), "cann't get money", Toast.LENGTH_SHORT).show();
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
                String url="http://10.1.19.2/test/api.php?apicall=get_money_point";

                //returing the response
                return requestHandler.sendPostRequest(url, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }


    private void SavePayment( int id,String adress) {

        //creating a file

        //creating request body for file

        RequestBody adress_c = RequestBody.create(MediaType.parse("text/plain"), adress);

        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object

        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);


        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.updateAdress(id,adress_c);

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {
                if (!response.body().error) {
                    Toast.makeText(getContext(), "ADD Successfully...", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Some error occurred..."+response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<uploadCatModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



}