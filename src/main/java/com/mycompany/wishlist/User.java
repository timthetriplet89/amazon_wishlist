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
    
    private String id;
    private String name;
    private String username;
	
	public User(String id, String name, String username) {
            this.setId(id);
            this.setName(name);
            this.setUsername(username);
	}
        
    public User(String id, String authname) {
        this.setId(id);
        this.setName(authname);
    }

//    public User(String name, String username) {
//            this.setName(name);
//            this.setUsername(username);
//        }        
        
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
