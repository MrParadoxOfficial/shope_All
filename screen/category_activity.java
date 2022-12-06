package shope.three3pro.shope_All.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shope.three3pro.shope_All.adpter.CatogryAdapterSmaller;
import shope.three3pro.shope_All.network.APIService;
import shope.three3pro.shope_All.network.DataListViewModel;
import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.model.catogoryModel;

import shope.three3pro.shope_All.network.RetroInstance;

public class category_activity extends AppCompatActivity {
    private RecyclerView rec;
    private List<catogoryModel> catogoryModels;
    private CatogryAdapterSmaller adapter;
    private DataListViewModel viewModel;
    private TextInputLayout cataogry_txt;
    Uri selectedImage;

    Bitmap bitmap;
    String encodeImageString;
    ImageView catogory_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Catory");
        Dialog dialog=new Dialog(category_activity.this);
        dialog.setContentView(R.layout.add_catogry_dialogue);

        rec=findViewById(R.id.rec_catogory);


        rec.setLayoutManager(new GridLayoutManager(this,3));
        // rec.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


        adapter=new CatogryAdapterSmaller(catogoryModels,this);
        rec.setAdapter(adapter);
        Log.e("TAG", "onCreateView: fragment");
        viewModel= ViewModelProviders.of(this).get(DataListViewModel.class);
        Log.e("from on dasdda","tag");

        viewModel.getCatogryModel().observe(this, new Observer<List<catogoryModel>>() {
            @Override
            public void onChanged(List<catogoryModel> sign) {
                Log.e("from on d","tag");
                if (sign !=null){
                    catogoryModels=sign;
                    adapter.setMovieList(catogoryModels);
                    Toast.makeText(category_activity.this, "done from home ", Toast.LENGTH_SHORT).show();
                    Log.e("from on change","tag");

                }else{
                    Log.e("TAG", "onChanged: else " );
                    Toast.makeText(category_activity.this, "failed fromm home observer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getCatagoryModel();
        rec.setAdapter(adapter);


        FloatingActionButton add_catory=findViewById(R.id.add_catory);
        add_catory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }

        });

        Button diaButton=dialog.findViewById(R.id.upload_cotry_btn);

        catogory_image=dialog.findViewById(R.id.catory_iamge);
        cataogry_txt=dialog.findViewById(R.id.catory_name_to_add);

        catogory_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(category_activity.this)
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

        Button addDone_pics_and_txt=dialog.findViewById(R.id.upload_cotry_btn);
        addDone_pics_and_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc=cataogry_txt.getEditText().getText().toString();
                uploadFile(selectedImage, desc);
                viewModel.getCatagoryModel();
                rec.setAdapter(adapter);


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
            catogory_image.setImageBitmap(bitmap);

            //calling the upload file method after choosing the file

        }
    }

    private void uploadFile(Uri fileUri, String desc) {

        //creating a file
        File file = new File(getRealPathFromURI(fileUri));

        //creating request body for file
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);

        //The gson builder
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        //creating retrofit object

        APIService apiService= RetroInstance.getRetrofit().create(APIService.class);


        //creating a call and calling the upload image method
        Call<uploadCatModel> call = apiService.uploadImage(requestFile, descBody);

        //finally performing the call
        call.enqueue(new Callback<uploadCatModel>() {
            @Override
            public void onResponse(Call<uploadCatModel> call, Response<uploadCatModel> response) {
                if (!response.body().error) {
                    Toast.makeText(getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
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

}