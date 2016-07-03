/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wishlist;

/**
 *
 * @author Ben
 */
public class Item {
    private String link;
    private String title;
    
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public Item(String link, String title) {
        this.link = link;
        this.title = title;
    }
    
    
    
}
