package fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoespricecomparision.LoginActivity;
import com.example.shoespricecomparision.R;
import com.example.shoespricecomparision.ScanQR;
import com.example.shoespricecomparision.admin.ListShoesActivity;

import java.util.List;

import adapter.ShoesAdapter;
import adapter.ShoesAdapterAdmin;
import model.Shoes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

import static android.support.constraint.Constraints.TAG;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerViewListShoes;
    public static EditText etSearchShoes;
    private Button btnSearchShoes;
    ImageView imgQr;

    public SearchFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the shoes_admin for this fragment
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        recyclerViewListShoes = (RecyclerView) view.findViewById(R.id.recyclerViewListShoes);

        etSearchShoes = view.findViewById(R.id.etSearchShoes);

        btnSearchShoes = view.findViewById(R.id.btnSearchShoes);

        btnSearchShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: i clicked search button");   
            }
        });

        imgQr = view.findViewById(R.id.imgQr);

//        click listener on qr code
        imgQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), ScanQR.class));
            }
        });


//      load shoes
        loadShoes();
        return view;

    }


    private void loadShoes() {
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<List<Shoes>> listCall = shoesAPI.getShoes();
        listCall.enqueue(new Callback<List<Shoes>>() {
            @Override
            public void onResponse(Call<List<Shoes>> call, Response<List<Shoes>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Code : "+ response.code() , Toast.LENGTH_SHORT).show();
                }
                List<Shoes> items =  response.body();
                if (items.size() > 0){
                    ShoesAdapter shoesAdapter = new ShoesAdapter(items,getActivity());
                    recyclerViewListShoes.setAdapter(shoesAdapter);
                    recyclerViewListShoes.setLayoutManager(new LinearLayoutManager(getActivity()));
                }else {
                    Toast.makeText(getActivity(), "No item found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Shoes>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error", t.getLocalizedMessage());
            }
        });

    }

    private void search() {
        String shoesName = etSearchShoes.getText().toString();

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<List<Shoes>> listCall = shoesAPI.getShoeByName(shoesName);

        listCall.enqueue(new Callback<List<Shoes>>() {
            @Override
            public void onResponse(Call<List<Shoes>> call, Response<List<Shoes>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Code : "+ response.code() , Toast.LENGTH_SHORT).show();
                }
                List<Shoes> items =  response.body();
                Log.d(TAG, "onResponse: list size below " + items.size());
                if (items.size() > 0){
                    ShoesAdapter shoesAdapter = new ShoesAdapter(items,getActivity());
                    recyclerViewListShoes.setAdapter(shoesAdapter);
                    recyclerViewListShoes.setLayoutManager(new LinearLayoutManager(getActivity()));
                }else {
                    Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Shoes>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure:  "  + t.getLocalizedMessage());
            }
        });
    }

    private boolean textvalidation() {
        boolean validate = true;
        if (TextUtils.isEmpty(etSearchShoes.getText().toString())) {
            etSearchShoes.requestFocus();
            Toast.makeText(getActivity(), "Please Enter Shoes Name", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.btnSearchShoes){
////            to check if search text is empty or not
//            Toast.makeText(getActivity(), "This is ", Toast.LENGTH_SHORT).show();
////            if (textvalidation()){
////                search();
////            }
//        }
    }


}


