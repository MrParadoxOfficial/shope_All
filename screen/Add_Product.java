package shope.three3pro.shope_All.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Part;
import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.network.APIService;
import shope.three3pro.shope_All.network.RetroInstance;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Add_Product extends AppCompatActivity {
    private String id,cat_name;
    private  Uri selectedImage;
    private Bitmap bitmap;
    private ImageView ca_image_product;
    private TextInputLayout product_name,product_price,product_point
            ,product_description,trader_name;
    private Button add_btn_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getintent();
        ca_image_product=findViewById(R.id.Image_Product);
        product_name=findViewById(R.id.product_name);
        product_point=findViewById(R.id.product_point);
        product_price=findViewById(R.id.product_price);
        product_description=findViewById(R.id.product_description);
        trader_name=findViewById(R.id.trader_name);
        add_btn_product=findViewById(R.id.add_btn_product);
        ca_image_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(Add_Product.this)
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
                        }).check();;

            }
        });

        add_btn_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String product_name_ss=product_name.getEditText().getText().toString();
                String product_price_ss=product_price.getEditText().getText().toString();
                String product_point_ss=product_point.getEditText().getText().toString();
                String product_description_ss=product_description.getEditText().getText().toString();
                String trader_name_ss=trader_name.getEditText().getText().toString();





                uploadFile(selectedImage
                        ,product_description_ss
                        ,product_name_ss
                        ,product_price_ss
                        ,product_point_ss
                        ,trader_name_ss
                        ,cat_name);




            }
        });




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            //the image URI
            selectedImage = data.getData();
            InputStream inputStream= null;
            try {
                inputStream = this.getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bitmap= BitmapFactory.decodeStream(inputStream);
            ca_image_product.setImageBitmap(bitmap);
         //   Toast.makeText(this, "on "+cat_name, Toast.LENGTH_SHORT).show();

            //calling the upload file method after choosing the file

        }
    }

    private void uploadFile(
            Uri fileUri
            , String desc
            ,String product_name_t
            , String product_price_t
            ,String product_point_t
            ,String trader_name_t
            ,String cat_name) {



        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);
        RequestBody product_name_body = RequestBody.create(MediaType.parse("text/plain"), product_name_t);
        RequestBody product_price_body = RequestBody.create(MediaType.parse("text/plain"), product_price_t);
        RequestBody product_point_body = RequestBody.create(MediaType.parse("text/plain"), product_point_t);
        RequestBody trader_name_body = RequestBody.create(MediaType.parse("text/plain"), trader_name_t);
        RequestBody prouct_size_body = RequestBody.create(MediaType.parse("text/plain"), "size");
        RequestBody cat_name_body = RequestBody.create(MediaType.parse("text/plain"), cat_name);
        String check_fav="false";
        RequestBody check_fav_body = RequestBody.create(MediaType.parse("text/plain"), check_fav);


        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object
        //creating retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetroInstance.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //creating our api
        APIService api = retrofit.create(APIService.class);

        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);


        //creating a call and calling the upload image method
        Call<uploadCatModel> call = api.UploadProduct(
                requestFile
                ,descBody
                ,product_name_body
                ,product_price_body
                ,prouct_size_body
                ,product_point_body
                , check_fav_body
                ,cat_name_body
                ,trader_name_body);

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {
                if (!response.body().error) {
                    Toast.makeText(getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred..."+response.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<uploadCatModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /*
     * This method is fetching the absolute path of the image file
     * if you want to upload other kind of files like .pdf, .docx
     * you need to make changes on this method only
     * Rest part will be the same
     * */
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }



    private void getintent(){
        Intent intent = getIntent();
        id = intent.getStringExtra("catory_id");
        cat_name = intent.getStringExtra("catory_name");

    }
}