package adapter;

import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoespricecomparision.R;

import java.util.List;

import model.Review;
import model.Shoes;
import url.Url;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Review> reviewList;
    private Context context;

    public ReviewAdapter(List<Review> reviewList, Context context){
        this.reviewList= reviewList;
        this.context = context;
    }

    private void strictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_review, viewGroup,false);
        return new ReviewAdapter.ReviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder reviewHolder, int i) {
        Review review = reviewList.get(i);
//        String imagePath = Url.BASE_URL + "uploads/" + shoes.getShoesImageName();
        strictMode();

//        setting values in recyclerview
        reviewHolder.tvReview.setText(review.getReview());
        reviewHolder.tvUserReview.setText(review.getReviewDate());

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder
    {

        private TextView tvReview,tvUserReview;

        public ReviewHolder(View itemView) {
            super(itemView);

            tvReview = itemView.findViewById(R.id.tvReview);
            tvUserReview= itemView.findViewById(R.id.tvUserReview);
        }
    }
}
