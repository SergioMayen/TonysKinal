
package org.sergiomayen.bean;

public class ProductosHasPlatos {
    private int codigoProductos;
    private int codigoPlatos;

    public ProductosHasPlatos() {
    }

    public ProductosHasPlatos(int codigoProductos, int codigoPlatos) {
        this.codigoProductos = codigoProductos;
        this.codigoPlatos = codigoPlatos;
    }

    public int getCodigoProductos() {
        return codigoProductos;
    }

    public void setCodigoProductos(int codigoProductos) {
        this.codigoProductos = codigoProductos;
    }

    public int getCodigoPlatos() {
        return codigoPlatos;
    }

    public void setCodigoPlatos(int codigoPlatos) {
        this.codigoPlatos = codigoPlatos;
    }

    @Override
    public String toString() {
        return "ProductosHasPlatos{" + "codigoProductos=" + codigoProductos + ", codigoPlatos=" + codigoPlatos + '}';
    }
    
    
    
    

}