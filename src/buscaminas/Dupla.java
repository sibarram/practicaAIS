/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.io.Serializable;

public class Dupla implements Comparable, Serializable {

    int tiempo;
    String nombre;

    public Dupla(int tiempo, String nombre) {
        this.tiempo = tiempo;
        this.nombre = nombre;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Boolean equals(Dupla d) {
        return (this.getTiempo() == d.getTiempo());
    }

    @Override
    public int compareTo(Object o) {
        Dupla d = (Dupla) o;
        return (this.getTiempo() - d.getTiempo());
    }
}
