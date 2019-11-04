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

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
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

    public int getRoomNumber() {
        return room_number;
    }

    public void setRoomNumber(int room_number) {
        this.room_number = room_number;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }
}
