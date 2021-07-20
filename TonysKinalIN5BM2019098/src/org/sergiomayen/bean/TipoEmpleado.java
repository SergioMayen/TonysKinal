package org.sergiomayen.bean;

public class TipoEmpleado {
    private int codigoTipoEmpleados;
    private String descripion;
    
    public TipoEmpleado(){
    }
    
    public TipoEmpleado(int codigoTipoEmpleados, String descripion){
        this.codigoTipoEmpleados = codigoTipoEmpleados;
        this.descripion = descripion;
    }

    public int getCodigoTipoEmpleados() {
        return codigoTipoEmpleados;
    }

    public void setCodigoTipoEmpleados(int codigoTipoEmpleados) {
        this.codigoTipoEmpleados = codigoTipoEmpleados;
    }

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }
    
public String ToString(){
        return getCodigoTipoEmpleados() + "      |        " + getDescripion();
               
    }
    
    
    
}
