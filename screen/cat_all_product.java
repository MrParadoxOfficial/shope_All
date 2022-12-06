package shope.three3pro.shope_All.screen;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.model.productModel;
import shope.three3pro.shope_All.network.DataListViewModel;

public class cat_all_product extends AppCompatActivity {
    private  static  String id,cat_name ;
    private RecyclerView rec;
    private List<productModel> productModel;
    private productAdapter adapter;
    private DataListViewModel viewModel;
    private TextInputLayout cataogry_txt;
    Uri selectedImage;

    Bitmap bitmap;
    String encodeImageString;
    ImageView catogory_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_all_product);
        getintent();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Product");

        Dialog dialog=new Dialog(cat_all_product.this);
        dialog.setContentView(R.layout.add_catogry_dialogue);

        rec=findViewById(R.id.rec_product_in_cato);


        rec.setLayoutManager(new GridLayoutManager(this,3));
        // rec.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


        adapter=new productAdapter(productModel,this);
        rec.setAdapter(adapter);
        Log.e("TAG", "onCreateView: fragment");
        viewModel= ViewModelProviders.of(this).get(DataListViewModel.class);
        Log.e("from on dasdda","tag");

        viewModel.getproductModels().observe(this, new Observer<List<productModel>>() {
            @Override
            public void onChanged(List<productModel> sign) {
                Log.e("from on d","tag");
                if (sign !=null){
                    productModel=sign;
                    adapter.setMovieList(productModel);
                    Toast.makeText(cat_all_product.this, "done from home ", Toast.LENGTH_SHORT).show();
                    Log.e("from on change","tag");

                }else{
                    Log.e("TAG", "onChanged: else " );
                    Toast.makeText(cat_all_product.this, "failed fromm home observer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getProductModel();
        rec.setAdapter(adapter);


        FloatingActionButton add_catory=findViewById(R.id.add_product_flo_btn);
        add_catory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(cat_all_product.this,Add_Product.class);
                intent.putExtra("catory_id",id);
                intent.putExtra("catory_name",cat_name);
                startActivity(intent);
            }

        });

        Button diaButton=dialog.findViewById(R.id.upload_cotry_btn);

        catogory_image=dialog.findViewById(R.id.catory_iamge);
        cataogry_txt=dialog.findViewById(R.id.catory_name_to_add);

        catogory_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(cat_all_product.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, 100);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
                            }
                        }).check();
            }
        });

        Button addDone_pics_and_txt=dialog.findViewById(R.id.upload_cotry_btn);
        addDone_pics_and_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc=cataogry_txt.getEditText().getText().toString();




            }
        });




    }private void getintent(){
        Intent intent = getIntent();
         id = intent.getStringExtra("catory_id");
        cat_name = intent.getStringExtra("catory_name");
        Toast.makeText(this, "on "+cat_name, Toast.LENGTH_SHORT).show();

    }
}
