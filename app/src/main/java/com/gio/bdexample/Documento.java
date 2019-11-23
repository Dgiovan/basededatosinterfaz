package com.gio.bdexample;

public class Documento {
    private String tipo;
    private String folio;
    private String Material;


    public Documento(String tipo, String folio, String material) {
        this.tipo = tipo;
        this.folio = folio;
        Material = material;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }
}
