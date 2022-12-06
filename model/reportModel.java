package shope.three3pro.shope_All.model;

public class reportModel {

    int id;

     int user_id;
     String user_name;
     int product_id;
     String product_name;
     String product_trader;
     String product_image;
     int product_price;
     String product_size;
     int product_number;
     String buyier_data;
     String buyer_state;



    public reportModel(int id, int user_id, String user_name, int product_id, String product_name, String product_trader, String product_image, int product_price, String product_size, int product_number, String buyier_data, String buyer_state) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_trader = product_trader;
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_size = product_size;
        this.product_number = product_number;
        this.buyier_data = buyier_data;
        this.buyer_state = buyer_state;
    }

    public reportModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_trader() {
        return product_trader;
    }

    public void setProduct_trader(String product_trader) {
        this.product_trader = product_trader;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_size() {
        return product_size;
    }

    public void setProduct_size(String product_size) {
        this.product_size = product_size;
    }

    public int getProduct_number() {
        return product_number;
    }

    public void setProduct_number(int product_number) {
        this.product_number = product_number;
    }

    public String getBuyier_data() {
        return buyier_data;
    }

    public void setBuyier_data(String buyier_data) {
        this.buyier_data = buyier_data;
    }

    public String getBuyer_state() {
        return buyer_state;
    }

    public void setBuyer_state(String buyer_state) {
        this.buyer_state = buyer_state;
    }
}
