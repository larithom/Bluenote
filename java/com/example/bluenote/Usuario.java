package com.example.bluenote;

public class Usuario {
    String senha;
    String email;
    String usuario;

    public Usuario(String senha, String email, String usuario) {
        this.senha = senha;
        this.email = email;
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
