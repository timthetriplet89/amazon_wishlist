/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.wishlist;

/**
 *
 * @author Kam Kam
 */
public class User {
    
    private int id;
    private String name;
    private String username;
    
    public User(String name, String username) {
            this.setName(name);
            this.setUsername(username);
        }
	
	public User(int id, String name, String username) {
            this.setId(id);
            this.setName(name);
            this.setUsername(username);
	}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
