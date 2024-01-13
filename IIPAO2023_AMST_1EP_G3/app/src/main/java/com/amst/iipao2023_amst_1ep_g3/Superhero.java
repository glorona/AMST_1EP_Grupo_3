package com.amst.iipao2023_amst_1ep_g3;

public class Superhero {
    private String name;
    private String imageUrl;

    private String id;

    public Superhero(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getId(){ return id; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
}