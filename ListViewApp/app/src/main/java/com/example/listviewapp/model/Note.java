package com.example.listviewapp.model;

public class Note {

    public String id;
    public String text;

    public Note() {}

    public Note(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

}
