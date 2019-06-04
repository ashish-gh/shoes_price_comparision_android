package shoesAPI;

import android.content.ClipData;

import java.util.List;

import model.ImageResponse;
import model.Review;
import model.Shoes;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ShoesAPI {
    //  using object
//    @POST("shoes")
//    Call<Void> addShoes(@Header("Cookie") String cookie, @Body Shoes shoes);


//------------------------------------------------------------------------------------
    @POST("api/addShoe")
    Call<Void> addShoes(@Body Shoes shoes);

    @Multipart
    @POST("api/upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("api/shoes")
    Call<List<Shoes>> getShoes();

//----------------------------------------
//for review

    @GET("api/review")
    Call<List<Review>> getReviews();

    @POST("api/addReview")
    Call<Void> addReview(@Body Review review);



// -----------------------------------------------------------------------------------


    //    for uploading image
//    @Multipart
//    @POST("upload")
//    Call<ImageResponse> uploadImage(@Header("Cookie") String cookie, @Part MultipartBody.Part img);

    // get all items
    @GET("shoes")
    Call<List<ClipData.Item>> getAllShoes(@Header("Cookie") String cookie);

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
