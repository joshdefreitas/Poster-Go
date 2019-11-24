package com.example.postergo;

import android.net.Uri;

public class Poster {
    private String _id;
    private int poster_id;
    private String filepath;
    private String description;
    private String filename;
    private String movietype;
    private String imdburl;

    public Poster(String _id, int poster_id, String filepath, String description, String filename, String movietype) {
        this._id = _id;
        this.poster_id = poster_id;
        this.filepath = filepath;
        this.description = description;
        this.filename = filename;
        this.movietype = movietype;
    }

    public Poster() {
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public int getPosterId() {
        return poster_id;
    }

    public void setPosterId(int poster_id) {
        this.poster_id = poster_id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMovietype() {
        return movietype;
    }

    public void setMovietype(String movietype) {
        this.movietype = movietype;
    }

    public String getImdburl() {
        return imdburl;
    }

    public void setImdburl(String imdburl) {
        this.imdburl = imdburl;
    }

    public Uri getURI(){
        Uri myUri = Uri.parse("http://13.90.58.142:8081/get/downloadPoster/"+filename);
        return (myUri);
    }
}
