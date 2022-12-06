package shope.three3pro.shope_All.model;

public class RegisterModel {
    String username,email,password,adress;
            int id,point_have,money_have;

    public RegisterModel() {
    }


    public RegisterModel(
            int id,
            String username,
            String email
           ) {
        this.username = username;
        this.email = email;
        this.id = id;
    }


    public RegisterModel(
            int id
            , String username,
            int point_have,
            int money_have) {
        this.username = username;
        this.point_have = point_have;
        this.id = id;
        this.money_have = money_have;
    }

    public RegisterModel(
            int id,
            String username,
            String email,
            String password,
            String adress,
            int point_have,
            int money_have) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.adress = adress;
        this.id = id;
        this.point_have = point_have;
        this.money_have = money_have;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint_have() {
        return point_have;
    }

    public void setPoint_have(int point_have) {
        this.point_have = point_have;
    }

    public int getMoney_have() {
        return money_have;
    }

    public void setMoney_have(int money_have) {
        this.money_have = money_have;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }


}
