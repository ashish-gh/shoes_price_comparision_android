package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoespricecomparision.R;
import com.example.shoespricecomparision.ShoesDescriptionActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import model.Shoes;
import url.Url;

public class ShoesAdapter extends RecyclerView.Adapter<ShoesAdapter.ShoesHolder>{

    private List<Shoes> shoesList;
    private Context context;

    public ShoesAdapter(List<Shoes> shoesList, Context context){
        this.shoesList= shoesList;
        this.context = context;
    }


    @NonNull
    @Override
    public ShoesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_shoes, viewGroup,false);
        return new ShoesHolder(view);
    }

    private void strictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    @Override
    public void onBindViewHolder(@NonNull ShoesHolder shoesHolder, int i) {
        final Shoes shoes = shoesList.get(i);
        String imagePath = Url.BASE_URL + "uploads/" + shoes.getShoesImageName();
        strictMode();

        try {
            URL url= new URL(imagePath);
            shoesHolder.imgShoes.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
        }catch (Exception e){
            e.printStackTrace();
        }

//        setting values in recyclerView
        shoesHolder.tvBrand.setText(shoes.getShoesBrand());
        shoesHolder.tvName.setText(shoes.getShoesName());
        shoesHolder.tvDescription.setText(shoes.getShoesDescription());
        shoesHolder.tvPrice.setText(Float.toString(shoes.getShoesPrice()));
        shoesHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShoesDescriptionActivity.class);
                intent.putExtra("shoesBrand",shoes.getShoesBrand());
                intent.putExtra("shoesName",shoes.getShoesName());
                intent.putExtra("shoesPrice",shoes.getShoesPrice());
                intent.putExtra("shoesDescription",shoes.getShoesDescription());
                context.startActivity(intent);

            }
        });

//        shoesHolder.recyclerViewShoes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ShoesDescriptionActivity.class);
//                intent.putExtra("shoesBrand",shoes.getShoesBrand());
//                intent.putExtra("shoesName",shoes.getShoesName());
//                intent.putExtra("shoesPrice",shoes.getShoesPrice());
//                intent.putExtra("shoesDescription",shoes.getShoesDescription());
//                context.startActivity(intent);
//
//            }
//        });



//      add other variables later
    }

    @Override
    public int getItemCount() {
        return shoesList.size();
    }

    public class ShoesHolder extends RecyclerView.ViewHolder
    {

        private TextView tvName,tvBrand,tvDescription;
        private TextView tvPrice;
        private ImageView imgShoes;
        private RecyclerView recyclerViewShoes;
        private CardView cardView;



        public ShoesHolder(View itemView) {
            super(itemView);
            imgShoes = itemView.findViewById(R.id.imgShoes);
            tvBrand= (TextView) itemView.findViewById(R.id.tvShoesBrand);
            tvName = (TextView) itemView.findViewById(R.id.tvShoesName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvShoesPrice);
            tvDescription= (TextView) itemView.findViewById(R.id.tvDescription);
            recyclerViewShoes = (RecyclerView) itemView.findViewById(R.id.recyclerViewListShoes);
            cardView = (CardView) itemView.findViewById(R.id.cardViewShoes);
        }
    }
}
