package br.com.realmlivros.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Natan_Felipe on 25/01/2017.
 */

public class Livro extends RealmObject {

    @PrimaryKey
    private int id;
    private String nome, autor;
    private int ano;
    private byte[] foto;


    public Livro() {
    }

    public Livro(int id, String nome, String autor, int ano,byte[] foto) {
        this.id = id;
        this.nome = nome;
        this.autor = autor;
        this.ano = ano;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
