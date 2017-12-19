package co.com.millennialapps.utils.models;

import com.google.firebase.database.Exclude;

/**
 * Created by erick on 1/7/2017.
 */

public class User {

    private String id;
    private String email;
    @DataFieldNull
    private String username;
    @DataFieldNull
    private String password;
    private String name;
    @DataFieldNull
    private String urlPhoto;
    @DataFieldNull
    private String urlBanner;
    @Exclude
    private String token;

    public User() {
        id = "";
        email = "";
        username = "";
        password = "";
        name = "";
        urlPhoto = "";
        urlBanner = "";
        token = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getUrlBanner() {
        return urlBanner;
    }

    public void setUrlBanner(String urlBanner) {
        this.urlBanner = urlBanner;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
