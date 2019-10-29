package com.example.postergo;

public class Message {

    private String _id;
    private int user_id;
    private String string;
    private int time;
    private int room_number;
    private String user_name;

    public Message(int user_id, String string, int time, int room_number, String user_name) {
        this.user_id = user_id;
        this.string = string;
        this.time = time;
        this.room_number = room_number;
        this.user_name = user_name;
    }

    public Message(String _id,int user_id, String string, int time, int room_number, String user_name) {
        this._id = _id;
        this.user_id = user_id;
        this.string = string;
        this.time = time;
        this.room_number = room_number;
        this.user_name = user_name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
