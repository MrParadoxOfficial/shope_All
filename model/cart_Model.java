package shope.three3pro.shope_All.model;

public class cart_Model {
    int id,user_id,product_id,product_price,product_size;
    String user_name,product_name,trader_name,image_url;



    public cart_Model() {
    }
    public cart_Model(
            int id,
            int user_id,
            String user_name,
            int product_id,
            String product_name,
            int product_price,
            int product_size,
            String trader_name,
            String image_url) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.product_price = product_price;
        this.product_size = product_size;
        this.user_name = user_name;
        this.product_name = product_name;
        this.trader_name = trader_name;
        this.image_url = image_url;
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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_size() {
        return product_size;
    }

    public void setProduct_size(int product_size) {
        this.product_size = product_size;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getTrader_name() {
        return trader_name;
    }

    public void setTrader_name(String trader_name) {
        this.trader_name = trader_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
