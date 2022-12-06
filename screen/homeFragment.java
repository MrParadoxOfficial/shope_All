package shope.three3pro.shope_All.screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.adpter.CatogryAdapter;
import shope.three3pro.shope_All.model.catogoryModel;
import shope.three3pro.shope_All.model.productModel;
import shope.three3pro.shope_All.network.DataListViewModel;


public class homeFragment extends Fragment {
    private TextView show_all_catogory;
    private ImageView cart_mine;
    private RecyclerView rec,product_rec;
    private List<catogoryModel> catogoryModels;
    private List<productModel> productModel;
    private CatogryAdapter adapter;
    private productAdapter productAdapter;
    private DataListViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        show_all_catogory=view.findViewById(R.id.showAll_text_unused);
        cart_mine=view.findViewById(R.id.cart_btn);
        cart_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),cart_activity.class);
                startActivity(intent);
            }
        });
        show_all_catogory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), category_activity.class);
                startActivity(intent);
            }
        });
        rec=view.findViewById(R.id.home_frag_cat_rec);
        product_rec=view.findViewById(R.id.home_frag_product_rec);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager ProductManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);



        rec.setLayoutManager(layoutManager);

        product_rec.setLayoutManager(new GridLayoutManager(getActivity(),3));
        // rec.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));


        adapter=new CatogryAdapter(catogoryModels,getActivity());
        productAdapter=new productAdapter(productModel,getActivity());
        rec.setAdapter(adapter);
        product_rec.setAdapter(productAdapter);
        Log.e("TAG", "onCreateView: fragment");
        viewModel= ViewModelProviders.of(getActivity()).get(DataListViewModel.class);
        Log.e("from on dasdda","tag");

        viewModel.getCatogryModel().observe(getActivity(), new Observer<List<catogoryModel>>() {
            @Override
            public void onChanged(List<catogoryModel> sign) {
                Log.e("from on d","tag");
                if (sign !=null){
                    catogoryModels=sign;
                    adapter.setMovieList(catogoryModels);
                    Toast.makeText(getContext(), "done from home ", Toast.LENGTH_SHORT).show();
                    Log.e("from on change","tag");

                }else{
                    Log.e("TAG", "onChanged: else " );
                    Toast.makeText(getContext(), "failed fromm home observer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getCatagoryModel();


        //intialize 2reclerview
        Log.e("TAG", "onCreateView: fragment");
        viewModel= ViewModelProviders.of(getActivity()).get(DataListViewModel.class);
        Log.e("from on dasdda","tag");

        viewModel.getproductModels().observe(getActivity(), new Observer<List<productModel>>() {
            @Override
            public void onChanged(List<productModel> sign) {
                Log.e("from on d","tag");
                if (sign !=null){
                    productModel=sign;
                    productAdapter.setMovieList(productModel);
                    Toast.makeText(getContext(), "done from home ", Toast.LENGTH_SHORT).show();
                    Log.e("from on change","tag");

                }else{
                    Log.e("TAG", "onChanged: else " );
                    Toast.makeText(getContext(), "failed fromm home observer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getProductModel();
        product_rec.setAdapter(productAdapter);
    }


}