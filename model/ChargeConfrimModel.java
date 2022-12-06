package shope.three3pro.shope_All.model;

public class ChargeConfrimModel {
    private int id;
    private String use_id;
    private String use_name;
    private int money_qq;
    private String from_where;
    private String money_code;

    public ChargeConfrimModel() {
    }

    public ChargeConfrimModel(int id, String use_id, String use_name, int money_qq, String from_where, String money_code) {
        this.id = id;
        this.use_id = use_id;
        this.use_name = use_name;
        this.money_qq = money_qq;
        this.from_where = from_where;
        this.money_code = money_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUse_id() {
        return use_id;
    }

    public void setUse_id(String use_id) {
        this.use_id = use_id;
    }

    public String getUse_name() {
        return use_name;
    }

    public void setUse_name(String use_name) {
        this.use_name = use_name;
    }

    public int getMoney_qq() {
        return money_qq;
    }

    public void setMoney_qq(int money_qq) {
        this.money_qq = money_qq;
    }

    public String getFrom_where() {
        return from_where;
    }

    public void setFrom_where(String from_where) {
        this.from_where = from_where;
    }

    public String getMoney_code() {
        return money_code;
    }

    public void setMoney_code(String money_code) {
        this.money_code = money_code;
    }
}
