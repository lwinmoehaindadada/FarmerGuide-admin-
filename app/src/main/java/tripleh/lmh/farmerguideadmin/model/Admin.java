package tripleh.lmh.farmerguideadmin.model;

public class Admin {
    String id;
    String name;
    String township;
    public Admin(){

    }

    public Admin(String id, String name, String township) {
        this.id = id;
        this.name = name;
        this.township = township;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getTownship() {
        return this.township;
    }
}
