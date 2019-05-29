package shoesAPI;

import android.content.ClipData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface ShoesAPI {
    //  using object
    @POST("shoes")
    Call<Void> addShoes(@Header("Cookie") String cookie, @Body ClipData.Item item);

    //    for uploading image
//    @Multipart
//    @POST("upload")
//    Call<ImageResponse> uploadImage(@Header("Cookie") String cookie, @Part MultipartBody.Part img);

    // get all items
    @GET("shoes")
    Call<List<ClipData.Item>> getAllItems(@Header("Cookie") String cookie);

    //  for login
//    @FormUrlEncoded
//    @POST("users/login")
//    Call<LoginSignUpResponse> checkUser(@Field("username") String email, @Field("password") String password);

//    for signup

//    @FormUrlEncoded
//    @POST("usres/register")
//    Call<LoginSignUpResponse> registerUser(@Field("username") String email, @Field("password") String password);

    //    for logout
//    @GET("users/logout")
//    Call<Void> logout(@Header("Cookie") String cookie);



}
