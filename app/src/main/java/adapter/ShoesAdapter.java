package adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoespricecomparision.R;

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
        Shoes shoes = shoesList.get(i);
        String imagePath = Url.BASE_URL + "uploads/" + shoes.getImage();
        strictMode();

        try {
            URL url= new URL(imagePath);
            shoesHolder.imgShoes.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
        }catch (Exception e){
            e.printStackTrace();
        }

//        setting values in recyclerView
        shoesHolder.tvName.setText(shoes.getName());
        shoesHolder.tvPrice.setText(Float.toString(shoes.getPrice()));

//      add other variables later
    }

    @Override
    public int getItemCount() {
        return shoesList.size();
    }

    public class ShoesHolder extends RecyclerView.ViewHolder
    {

        private TextView tvName;
        private TextView tvPrice;
        private ImageView imgShoes;

        public ShoesHolder(View itemView) {
            super(itemView);
            imgShoes = itemView.findViewById(R.id.imgShoes);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
        }
    }
}
