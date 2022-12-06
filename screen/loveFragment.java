package shope.three3pro.shope_All.screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import shope.three3pro.shope_All.R;
import shope.three3pro.shope_All.db.DBManager;
import shope.three3pro.shope_All.model.productModel;



public class loveFragment extends Fragment {
    private ArrayList<productModel> product_loved;
    private DBManager dbHandler;
    private productAdapter productAdapter;
    private RecyclerView rec_loved_product;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_love, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rec_loved_product=view.findViewById(R.id.rec_loved_product);
        product_loved = new ArrayList<>();
        dbHandler = new DBManager(getActivity());
        dbHandler.open();
        product_loved = dbHandler.readCourses();
        productAdapter = new productAdapter(product_loved, getActivity());
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        rec_loved_product.setLayoutManager(new GridLayoutManager(getContext(),3));

        // setting our adapter to recycler view.
        rec_loved_product.setAdapter(productAdapter);



    }
}