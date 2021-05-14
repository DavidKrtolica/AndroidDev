package com.example.snapapp.model;

public class ImageRef {
    
    public String id;
    public String ref;

    public ImageRef() {}

    public ImageRef(String id, String ref) {
        this.id = id;
        this.ref = ref;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }
}
