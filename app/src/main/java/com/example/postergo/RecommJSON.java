package com.example.postergo;

public class RecommJSON {
    private String _id;
    private int poster_id;
    private String filepath;
    private int movietype;

    public RecommJSON(String _id, int poster_id, String filepath, int movietype) {
        this._id = _id;
        this.poster_id = poster_id;
        this.filepath = filepath;
        this.movietype = movietype;
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

    public int getMovietype() {
        return movietype;
    }

    public void setMovietype(int movietype) {
        this.movietype = movietype;
    }
}
