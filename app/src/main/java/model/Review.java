package model;

public class Review {
    private int id;
    private String review;
    private String userName;
    private int shoesId;
    private String reviewTime;

    public Review(String review, String userName, int shoesId, String reviewTime) {
        this.review = review;
        this.userName = userName    ;
        this.shoesId = shoesId;
        this.reviewTime = reviewTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getShoesId() {
        return shoesId;
    }

    public void setShoesId(int shoesId) {
        this.shoesId = shoesId;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }
}
