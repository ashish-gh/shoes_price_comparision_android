package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoespricecomparision.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import model.Shoes;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shoesAPI.ShoesAPI;
import url.Url;

public class UserAdapterAdmin extends RecyclerView.Adapter<UserAdapterAdmin.UserHolderAdmin>{

    private List<User> userList;
    private Context context;

    public UserAdapterAdmin(List<User> userList, Context context){
        this.userList = userList;
        this.context = context;
    }



    @NonNull
    @Override
    public UserHolderAdmin onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_user, viewGroup,false);
        return new UserAdapterAdmin.UserHolderAdmin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolderAdmin userHolderAdmin, int i) {


        final User user = userList.get(i);
        String imagePath = Url.BASE_URL + "uploads/" + user.getProfileImage();
        strictMode();

        try {
            URL url= new URL(imagePath);
            userHolderAdmin.imgUserAdmin.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
        }catch (Exception e){
            e.printStackTrace();
        }


        //        setting values in recyclerView
        userHolderAdmin.tvUserName.setText(user.getFirstName() + " " + user.getLastName());
        userHolderAdmin.tvUserEmail.setText(user.getEmail());

        //        to delete shoes
        userHolderAdmin.btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        alert dialog box in logout
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete ?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(user.getId());
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
//        shoesHolderAdmin.tvShoesId.setText(shoes.getItemId());
        }

    private void delete(int id) {
        ShoesAPI shoesAPI = Url.getInstance().create(ShoesAPI.class);
        Call<Void> voidCall = shoesAPI.deleteUser(id);
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
        return userList.size();
    }

    private void strictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public class UserHolderAdmin extends RecyclerView.ViewHolder
    {

        private TextView tvUserName, tvUserEmail;
        private ImageView imgUserAdmin;
        private Button btnDeleteUser;

        public UserHolderAdmin(View itemView) {
            super(itemView);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmailAdmin);
            tvUserName = itemView.findViewById(R.id.tvUserNameAdmin);
            imgUserAdmin = (ImageView) itemView.findViewById(R.id.imgUserAdmin);
            btnDeleteUser = (Button) itemView.findViewById(R.id.btnDeleteUserAdmin);

        }
    }
}
