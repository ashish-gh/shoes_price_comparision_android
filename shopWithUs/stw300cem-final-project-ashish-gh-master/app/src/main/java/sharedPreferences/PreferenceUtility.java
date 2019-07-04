package sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtility {

    public static final String TOKEN = "token";
    public static final String EMAIL_ADDRESS = "";
    public static final String USER_TYPE = "";

    private Context context;
    SharedPreferences sharedPreferences = context.getSharedPreferences("User", 0x0000);
    SharedPreferences.Editor editor = sharedPreferences.edit();

//    set preference

    public void setUserType(Context context){
        editor.putString("userType",USER_TYPE);
        editor.commit();
    }
    public void setEmailAddress(Context context){
        editor.putString("email",EMAIL_ADDRESS);
        editor.commit();
    }
    public void setToken(Context context){
        editor.putString("token",TOKEN);
        editor.commit();
    }

//get preference

    public String getToken(){
        return sharedPreferences.getString("token","");
    }
    public String getUserType(){
        return sharedPreferences.getString("userType","");
    }
    public String getEmailAddress(){
        return sharedPreferences.getString("email","");
    }





}
