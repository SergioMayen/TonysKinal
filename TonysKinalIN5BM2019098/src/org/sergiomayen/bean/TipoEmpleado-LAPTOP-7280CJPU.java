package org.sergiomayen.bean;

public class TipoEmpleado {
    private int codigoTipoEmpleados;
    private String descripcion;
    
    public TipoEmpleado(){
    }
    
    public TipoEmpleado(int codigoTipoEmpleados, String descripcion){
        this.codigoTipoEmpleados = codigoTipoEmpleados;
        this.descripcion = descripcion;
    }

    public int getCodigoTipoEmpleados() {
        return codigoTipoEmpleados;
    }

    public void setCodigoTipoEmpleados(int codigoTipoEmpleados) {
        this.codigoTipoEmpleados = codigoTipoEmpleados;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getCodigoTipoEmpleados() + " | " + getDescripcion();
    }
    

}
