package com.fbse.recommentmobilesystem.XZHL0220;

import java.util.List;

public class XZHL0220_Choice {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String key;
    private String title;
    private List<String> options;

    public List<String> getOptions() {
        return options;
    }

    public String getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
