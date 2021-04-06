package com.example.fanarver3;

public class Exercise {
    private String Name;
    private String Diffcaluty;
    private Integer Image;
    private String URL;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Exercise(String name, String diffcaluty, Integer image,String url) {
        Name = name;
        Diffcaluty = diffcaluty;
        Image = image;
        URL=url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDiffcaluty() {
        return Diffcaluty;
    }

    public void setDiffcaluty(String diffcaluty) {
        Diffcaluty = diffcaluty;
    }

    public Integer getImage() {
        return Image;
    }

    public void setImage(Integer image) {
        Image = image;
    }
}
