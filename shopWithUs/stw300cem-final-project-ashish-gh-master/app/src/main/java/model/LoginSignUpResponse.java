package model;

public class LoginSignUpResponse {

    private boolean success;
    private String status;
    private String accessToken;
    private String email;
    private String userType;

    public boolean getSuccess(){
        return success;
    }
    public String getStatus(){
        return status;
    }

    public String getAccessToken(){return accessToken;}

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }
}
