package com.example.postergo;

public class Poster {
    private String _id;
    private int poster_id;
    private String filepath;
    private String description;
    private String filename;
    private String movietype;

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
}
