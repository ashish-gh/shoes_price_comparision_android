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

public class ShoesAdapterAdmin extends  RecyclerView.Adapter<ShoesAdapterAdmin.ShoesHolderAdmin>{

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



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ShoesHolderAdmin extends RecyclerView.ViewHolder
    {

        private TextView tvBrand, tvName, tvDescription;
        private TextView tvPrice;
        private ImageView imgShoes;

        public ShoesHolderAdmin(View itemView) {
            super(itemView);
            imgShoes = itemView.findViewById(R.id.imgShoesAdmin);
            tvName = (TextView) itemView.findViewById(R.id.tvShoesNameAdmin);
            tvPrice = (TextView) itemView.findViewById(R.id.tvShoesPriceAdmin);
            tvBrand= (TextView) itemView.findViewById(R.id.tvShoesBrandAdmin);
            tvDescription= (TextView) itemView.findViewById(R.id.tvDescriptionAdmin);
        }
    }
}
