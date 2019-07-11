package model;

public class Shoes {
    private int itemId;
    private String shoesBrand;
    private String shoesName;
    private float shoesPrice;
    private String shoesDescription;
    private String shoesImageName;
    private int shopId;

    public Shoes(String shoesBrand, String shoesName, float shoesPrice, String shoesDescription, String shoesImageName, int shopId ) {
        this.shoesBrand = shoesBrand;
        this.shoesName = shoesName;
        this.shoesPrice = shoesPrice;
        this.shoesDescription = shoesDescription;
        this.shoesImageName = shoesImageName;
        this.shopId = shopId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
