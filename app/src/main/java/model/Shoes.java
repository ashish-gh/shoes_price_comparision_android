package model;

public class Shoes {
    private int id;
    private String shoesBrand;
    private String shoesName;
    private float shoesPrice;
    private String shoesDescription;
    private String shoesImageName;

    public Shoes(String shoesBrand, String shoesName, float shoesPrice, String shoesDescription, String shoesImageName) {
        this.shoesBrand = shoesBrand;
        this.shoesName = shoesName;
        this.shoesPrice = shoesPrice;
        this.shoesDescription = shoesDescription;
        this.shoesImageName = shoesImageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShoesBrand() {
        return shoesBrand;
    }

    public void setShoesBrand(String shoesBrand) {
        this.shoesBrand = shoesBrand;
    }

    public String getShoesName() {
        return shoesName;
    }

    public void setShoesName(String shoesName) {
        this.shoesName = shoesName;
    }

    public float getShoesPrice() {
        return shoesPrice;
    }

    public void setShoesPrice(float shoesPrice) {
        this.shoesPrice = shoesPrice;
    }

    public String getShoesDescription() {
        return shoesDescription;
    }

    public void setShoesDescription(String shoesDescription) {
        this.shoesDescription = shoesDescription;
    }

    public String getShoesImageName() {
        return shoesImageName;
    }

    public void setShoesImageName(String shoesImageName) {
        this.shoesImageName = shoesImageName;
    }
}
