package com.example.sqlitetest;

import org.litepal.crud.DataSupport;

public class Category extends DataSupport {
    private int id;
    private int Categoryname;
    private int CategoryCode;

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryname(int categoryname) {
        Categoryname = categoryname;
    }

    public void setCategoryCode(int categoryCode) {
        CategoryCode = categoryCode;
    }
}
