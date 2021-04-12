package com.firstone.ensiasstudy.models;

public class User {
    public String name, email, password, annee, filiere;

    public User() {
    }

    public User(String name, String email, String password, String annee, String filiere) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.annee = annee;
        this.filiere = filiere;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAnnee() {
        return annee;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }
}
