package com.example.qingao.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by qingao on 15-11-12.
 */

@Table(name = "Catogeries")
public class Category extends Model {
    @Column(name = "name",index = true)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Category() {
        super();
    }

    public Category(String name) {
        super();
        this.name = name;
    }

    public List<Item> items() {
        return getMany(Item.class, "Category");
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Category{");
        sb.append("id=").append(getId());
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
