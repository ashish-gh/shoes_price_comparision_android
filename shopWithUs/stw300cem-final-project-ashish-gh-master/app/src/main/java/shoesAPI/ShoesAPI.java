package shoesAPI;

import android.content.ClipData;

import java.util.List;

import model.ImageResponse;
import model.LoginSignUpResponse;
import model.Review;
import model.ReviewResponse;
import model.Shoes;
import model.ShoesResponse;
import model.ShopResponse;
import model.Store;
import model.User;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ShoesAPI {
    //  using object
//    @POST("shoes")
//    Call<Void> addShoes(@Header("Cookie") String cookie, @Body Shoes shoes);


//------------------------------------------------------------------------------------
    @POST("api/shoes")
    Call<ShoesResponse> addShoes(@Body Shoes shoes);

    @Multipart
    @POST("api/upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("api/shoes")
    Call<List<Shoes>> getShoes();

    @DELETE("api/shoes/{shoesId}")
    Call<Void> deleteShoes(@Path("shoesId") int shoesId);

    @PUT("api/shoes/{shoesId}")
    Call<Void> updateShoes(@Path("shoesId")int shoesId, @Body Shoes shoes);


    @PUT("api/users/{userId}")
    Call<Void> updateUser(@Path("userId")int userId, @Body User user);


    @GET("api/shoes/:shoesId")
    Call<List<Shoes>> getShoe();

    @GET("api/shoes/{shoesName}/shoes")
    Call<List<Shoes>> getShoeByName(@Path("shoesName") String shoesName);

    @GET("api/shoes/{shoesId}")
    Call<List<Shoes>> getShoeById(@Path("shoesId") int shoesId);



//    for user

    @DELETE("api/users/{userId}")
    Call<Void> deleteUser(@Path("userId") int userId);

    @GET("api/users")
    Call<List<User>> getUser();

    @GET("api/users/{userId}")
    Call<List<User>> getUserById(@Path("userId") int userId);

    @GET("api/users/{email}/users")
    Call<List<User>> getUserByEmail(@Path("email") String email);

//to add new user
    @POST("api/register")
    Call<LoginSignUpResponse> registerUser(@Body User user);

// for login
    @POST("/api/auth")
    Call<LoginSignUpResponse>  authenticate(@Body User user);


//----------------------------------------
//    for store

    @POST("api/store")
    Call<ShopResponse> addStores(@Body Store store);

    @GET("api/store")
    Call<List<Store>> getStores();

    @GET("api/store/:storeId")
    Call<List<Store>> getStoreById();

    @DELETE("api/store/{storeId}")
    Call<Void> deleteStore(@Path("storeId") int storeId);

    @PUT("api/store/{storeId}")
    Call<Void> updateStore(@Path("storeId")int storeId, @Body Store store);






//----------------------------------------
//for review

    @GET("api/review/{shoesId}/shoes")
    Call<List<Review>> getReviewsByShoes(@Path("shoesId") int shoesId);

    @POST("api/review")
    Call<ReviewResponse> addReview(@Body Review review);


    @DELETE("api/review/{reviewId}")
    Call<Void> deleteReview(@Path("reviewId") int reviewId);


//
//    @GET("api/review/{shoesId}")
//    Call<List<Review>> getReviewsByShoes(@Path("shoesId") int shoesId);




// -----------------------------------------------------------------------------------


    //    for uploading image
//    @Multipart
//    @POST("upload")
//    Call<ImageResponse> uploadImage(@Header("Cookie") String cookie, @Part MultipartBody.Part img);

    // get all items

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
