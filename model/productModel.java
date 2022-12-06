package shope.three3pro.shope_All.model;

public class productModel {
    private int id;
    private String php_id;
    private String product_name;
    private String product_price;
    private String product_size;
    private String product_point;
    private String product_description;
    private String trader_name;
    private String product_image;
    private String cat_name;


    private int check_fav;

    public int getCheck_fav() {
        return check_fav;
    }

    public void setCheck_fav(int check_fav) {
        this.check_fav = check_fav;
    }

    public String getPhp_id() {
        return php_id;
    }

    public void setPhp_id(String php_id) {
        this.php_id = php_id;
    }

    public productModel() {
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public productModel(
            int id
            ,String php_id
            , String product_name
            , String product_price
            , String product_size
            , String product_point
            , String product_description
            , String trader_name
            , String product_image
            , int check_fav
            , String cat_name

            ) {
        this.id = id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_size = product_size;
        this.product_point = product_point;
        this.product_description = product_description;
        this.trader_name = trader_name;
        this.product_image = product_image;
        this.php_id = php_id;
        this.check_fav = check_fav;
        this.cat_name = cat_name;
    }
    public productModel(
            int id
            ,String php_id
            , String product_name
            , String product_price
            , String product_size
            , String product_point
            , String product_description
            , String trader_name
            , String product_image

    ) {
        this.id = id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_size = product_size;
        this.product_point = product_point;
        this.product_description = product_description;
        this.trader_name = trader_name;
        this.product_image = product_image;
        this.php_id = php_id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_size() {
        return product_size;
    }

    public void setProduct_size(String product_size) {
        this.product_size = product_size;
    }

    public String getProduct_point() {
        return product_point;
    }

    public void setProduct_point(String product_point) {
        this.product_point = product_point;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getTrader_name() {
        return trader_name;
    }

    public void setTrader_name(String trader_name) {
        this.trader_name = trader_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
