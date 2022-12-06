package shope.three3pro.shope_All.model;

public class catogoryModel {
    private int id;
    private String desc;
    private String path;

    public catogoryModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public catogoryModel( String path, String desc) {
        this.desc = desc;
        this.path = path;
    }

    public String getDes() {
        return desc;
    }

    public void setDes(String des) {
        this.desc = des;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
