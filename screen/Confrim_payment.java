package shope.three3pro.shope_All.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.model.ChargeConfrimModel;
import shope.three3pro.shope_All.network.DataListViewModel;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class Confrim_payment extends AppCompatActivity {
    private RecyclerView confrim_rec;
    private DataListViewModel viewModel;
    private confrimChargeAdapter adapter;
    private List<ChargeConfrimModel> chargeConfrimModels;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confrim_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Confrim Payment");
        confrim_rec=findViewById(R.id.confrim_payment_code);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        confrim_rec.setLayoutManager(layoutManager);
        // rec.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


        adapter=new confrimChargeAdapter(chargeConfrimModels,this);
        confrim_rec.setAdapter(adapter);
        Log.e("TAG", "onCreateView: fragment");
        viewModel= ViewModelProviders.of(this).get(DataListViewModel.class);
        Log.e("from on dasdda","tag");

        viewModel.getChargeModel().observe(this, new Observer<List<ChargeConfrimModel>>() {
            @Override
            public void onChanged(List<ChargeConfrimModel> sign) {
                Log.e("from on d","tag");
                if (sign !=null){
                    chargeConfrimModels=sign;
                    adapter.setMovieList(chargeConfrimModels);
                    Toast.makeText(Confrim_payment.this, "done from home ", Toast.LENGTH_SHORT).show();
                    Log.e("from on change","tag");

                }else{
                    Log.e("TAG", "onChanged: else " );
                    Toast.makeText(Confrim_payment.this, "failed fromm home observer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getCharge_model();
        confrim_rec.setAdapter(adapter);


    }
}