package fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoespricecomparision.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerViewListShoes;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the shoes_admin for this fragment
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        recyclerViewListShoes = (RecyclerView) view.findViewById(R.id.recyclerViewListShoes);

        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);

        Call<List<Shoes>> listCall = shoesAPI.getShoes();
        listCall.enqueue(new Callback<List<Shoes>>() {
            @Override
            public void onResponse(Call<List<Shoes>> call, Response<List<Shoes>> response) {
                if (!response.isSuccessful()){
//                    use snackbar here
                    Toast.makeText(getActivity(), "Code : "+ response.code() , Toast.LENGTH_SHORT).show();
                }
                List<Shoes> items =  response.body();
                ShoesAdapter shoesAdapter = new ShoesAdapter(items,getActivity());
                recyclerViewListShoes.setAdapter(shoesAdapter);
                recyclerViewListShoes.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<List<Shoes>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

}
