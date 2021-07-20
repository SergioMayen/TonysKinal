package org.sergiomayen.bean;

public class ServiciosHasPlatos {
    private int codigoServicios;
    private int codigoPlatos;

    public ServiciosHasPlatos() {
    }

    public ServiciosHasPlatos(int codigoServicios, int codigoPlatos) {
        this.codigoServicios = codigoServicios;
        this.codigoPlatos = codigoPlatos;
    }

    public int getCodigoServicios() {
        return codigoServicios;
    }

    public void setCodigoServicios(int codigoServicios) {
        this.codigoServicios = codigoServicios;
    }

    public int getCodigoPlatos() {
        return codigoPlatos;
    }

    public void setCodigoPlatos(int codigoPlatos) {
        this.codigoPlatos = codigoPlatos;
    }

    @Override
    public String toString() {
        return "ServiciosHasPlatos{" + "codigoServicios=" + codigoServicios + ", codigoPlatos=" + codigoPlatos + '}';
    }
    
    
}
