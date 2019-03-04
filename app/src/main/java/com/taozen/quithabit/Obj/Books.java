package com.taozen.quithabit.Obj;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Books {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("name")
    @Expose
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
