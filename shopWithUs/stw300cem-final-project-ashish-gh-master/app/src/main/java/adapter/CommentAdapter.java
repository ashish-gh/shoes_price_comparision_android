package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoespricecomparision.R;

import java.util.List;

import model.Review;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private List<Review> reviewList;
    private Context context;

    public CommentAdapter(List<Review> reviewList, Context context){
        this.reviewList= reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comment_content, viewGroup,false);
        return new CommentAdapter.CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder commentHolder, int i) {

        final Review review= reviewList.get(i);

        //        setting values in recyclerView
        commentHolder.tvComment.setText(review.getReview());
        commentHolder.btnDeleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        alert dialog box in logout
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete ?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(review.getReviewId());
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

    }

    private void delete(int id) {
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<Void> voidCall = shoesAPI.deleteReview(id);
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
        return reviewList.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        private TextView tvComment;
        private Button btnDeleteComment;

        public CommentHolder(View itemView) {
            super(itemView);
            tvComment = (TextView) itemView.findViewById(R.id.tvCommentAdmin);
            btnDeleteComment= (Button) itemView.findViewById(R.id.btnDeleteCommentAdmin);
        }
    }

}
