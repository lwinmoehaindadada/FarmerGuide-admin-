package tripleh.lmh.farmerguideadmin.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String email;
    private String gender;
    private String id;
    private String name;
    private List<String> palntedCrops;
    private String profile_url;
    private String township;
    private String work;

    public User(){}

    public String getTownship() {
        return this.township;
    }

    public User(String id, String name, String profile_url, String email, String work, String gender, String township) {
        this.id = id;
        this.name = name;
        this.profile_url = profile_url;
        this.email = email;
        this.work = work;
        this.gender = gender;
        this.palntedCrops = this.palntedCrops;
        this.township = township;
    }

    public String getWork() {
        return this.work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getPalntedCrops() {
        return this.palntedCrops;
    }

    public void setPalntedCrops(List<String> palntedCrops) {
        this.palntedCrops = palntedCrops;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public User(String id, String name, String profile_url, String email, String township) {
        this.township = township;
        this.id = id;
        this.name = name;
        this.profile_url = profile_url;
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_url() {
        return this.profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
