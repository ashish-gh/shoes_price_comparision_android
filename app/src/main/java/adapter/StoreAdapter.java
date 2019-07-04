package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoespricecomparision.R;
import com.example.shoespricecomparision.admin.UpdateShoesActivity;
import com.example.shoespricecomparision.admin.UpdateStoreActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import model.Shoes;
import model.Store;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreHolder>{

    private List<Store> storeList;
    private Context context;

    public StoreAdapter(List<Store> storeList, Context context){
        this.storeList= storeList;
        this.context = context;
    }


    @NonNull
    @Override
    public StoreHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.store_admin, viewGroup,false);
        return new StoreAdapter.StoreHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull StoreHolder storeHolder, int i) {

        final Store store= storeList.get(i);

        //        setting values in recyclerView
        storeHolder.tvStoreName.setText(store.getStoreName());
        storeHolder.tvLatitude.setText(Float.toString(store.getLatitude()));
        storeHolder.tvLongitude.setText(Float.toString(store.getLongitude()));


//        to delete shoes
        storeHolder.imgDeleteStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        alert dialog box in logout
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete ?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(store.getStoreId());
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

//        to update store details
        storeHolder.imgUpdateStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateStoreActivity.class);
                Toast.makeText(context, "value " + store.getStoreId(), Toast.LENGTH_SHORT).show();
                intent.putExtra("storeId", store.getStoreId());
                intent.putExtra("storeName",store.getStoreName());
                intent.putExtra("storeLatitude",store.getLatitude());
                intent.putExtra("storeLongitude",store.getLongitude());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    private void delete(int storeId) {
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<Void> voidCall = shoesAPI.deleteStore(storeId);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context,"Code" + response.code(),Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Toast.makeText(context, "Successfully Deleted",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context,"Error " + t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public class StoreHolder extends RecyclerView.ViewHolder {

        private TextView tvStoreName, tvLatitude, tvLongitude;
        private ImageView  imgDeleteStore, imgUpdateStore;
        private RecyclerView recyclerViewStore;

        public StoreHolder(View itemView) {
            super(itemView);
            tvStoreName= (TextView) itemView.findViewById(R.id.tvStoreNameAdmin);
            tvLongitude = (TextView) itemView.findViewById(R.id.tvStoreLongitudeAdmin);
            tvLatitude= (TextView) itemView.findViewById(R.id.tvStoreLatitudeAdmin);

            imgDeleteStore = (ImageView) itemView.findViewById(R.id.imgDeleteStore);
            imgUpdateStore= (ImageView) itemView.findViewById(R.id.imgUpdateStore);
            recyclerViewStore = (RecyclerView) itemView.findViewById(R.id.recyclerViewListStoreAdmin);

        }
    }
}
