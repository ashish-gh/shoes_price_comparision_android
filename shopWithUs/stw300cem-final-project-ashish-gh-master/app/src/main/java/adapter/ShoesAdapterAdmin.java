package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoespricecomparision.R;
import com.example.shoespricecomparision.ShoesDescriptionActivity;
import com.example.shoespricecomparision.admin.ListShoesActivity;
import com.example.shoespricecomparision.admin.UpdateShoesActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import model.Shoes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class ShoesAdapterAdmin extends RecyclerView.Adapter<ShoesAdapterAdmin.ShoesHolderAdmin>{

    private List<Shoes> shoesList;
    private Context context;

    public ShoesAdapterAdmin(List<Shoes> shoesList, Context context){
        this.shoesList= shoesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShoesHolderAdmin onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shoes_admin, viewGroup,false);
        return new ShoesHolderAdmin(view);
    }

    private void strictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    @Override
    public void onBindViewHolder(@NonNull ShoesHolderAdmin shoesHolderAdmin, int i) {

        final Shoes shoes = shoesList.get(i);
        String imagePath = Url.BASE_URL + "uploads/" + shoes.getShoesImageName();
        strictMode();

        try {
            URL url= new URL(imagePath);
            shoesHolderAdmin.imgShoes.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
        }catch (Exception e){
            e.printStackTrace();
        }

        //        setting values in recyclerView
        shoesHolderAdmin.tvBrand.setText(shoes.getShoesBrand());
        shoesHolderAdmin.tvName.setText(shoes.getShoesName());
        shoesHolderAdmin.tvPrice.setText(Float.toString(shoes.getShoesPrice()));
        shoesHolderAdmin.tvDescription.setText(shoes.getShoesDescription());
//        shoesHolderAdmin.tvShoesId.setText(shoes.getItemId());

//        to delete shoes
        shoesHolderAdmin.imgDeleteShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        alert dialog box in logout
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete ?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(shoes.getItemId());
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

        shoesHolderAdmin.imgUpdateShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateShoesActivity.class);
                Toast.makeText(context, "value " + shoes.getItemId(), Toast.LENGTH_SHORT).show();
                intent.putExtra("shoesId", shoes.getItemId());
                intent.putExtra("shoesBrand",shoes.getShoesBrand());
                intent.putExtra("shoesName",shoes.getShoesName());
                intent.putExtra("shoesPrice",shoes.getShoesPrice());
                intent.putExtra("shoesDescription",shoes.getShoesDescription());
                intent.putExtra("shoesImage",shoes.getShoesImageName());
                intent.putExtra("shopId",shoes.getShopId());
                context.startActivity(intent);
            }
        });

    }


    private void delete(int shoesId) {
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<Void> voidCall = shoesAPI.deleteShoes(shoesId);
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


    @Override
    public int getItemCount() {
        return shoesList.size();
    }

    public class ShoesHolderAdmin extends RecyclerView.ViewHolder
    {

        private TextView tvShoesId, tvBrand, tvName, tvDescription;
        private TextView tvPrice;
        private ImageView imgShoes, imgDeleteShoes, imgUpdateShoes;

        public ShoesHolderAdmin(View itemView) {
            super(itemView);
            tvShoesId = itemView.findViewById(R.id.tvShoesId);
            imgShoes = itemView.findViewById(R.id.imgShoesAdmin);
            tvName = (TextView) itemView.findViewById(R.id.tvShoesNameAdmin);
            tvPrice = (TextView) itemView.findViewById(R.id.tvShoesPriceAdmin);
            tvBrand= (TextView) itemView.findViewById(R.id.tvShoesBrandAdmin);
            tvDescription= (TextView) itemView.findViewById(R.id.tvDescriptionAdmin);
            imgUpdateShoes= itemView.findViewById(R.id.imgUpdateShoes);
            imgDeleteShoes = itemView.findViewById(R.id.imgDeleteShoes);

        }
    }
}
