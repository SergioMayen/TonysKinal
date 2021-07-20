package org.sergiomayen.bean;

import java.util.Date;

public class ServiciosHasEmpleados {
    private int codigoServicioHasEmpleado;
    private int codigoServicios;
    private int codigoEmpleado;
    private Date fechaEvento;
    private String horaEvento;
    private String lugarEvento;

    public ServiciosHasEmpleados() {
    }

    public ServiciosHasEmpleados(int codigoServicioHasEmpleado, int codigoServicios, int codigoEmpleado, Date fechaEvento, String horaEvento, String lugarEvento) {
        this.codigoServicioHasEmpleado = codigoServicioHasEmpleado;
        this.codigoServicios = codigoServicios;
        this.codigoEmpleado = codigoEmpleado;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.lugarEvento = lugarEvento;
    }

    public int getCodigoServicioHasEmpleado() {
        return codigoServicioHasEmpleado;
    }

    public void setCodigoServicioHasEmpleado(int codigoServicioHasEmpleado) {
        this.codigoServicioHasEmpleado = codigoServicioHasEmpleado;
    }

    public int getCodigoServicios() {
        return codigoServicios;
    }

    public void setCodigoServicios(int codigoServicios) {
        this.codigoServicios = codigoServicios;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

    @Override
    public String toString() {
        return "ServiciosHasEmpleados{" + "codigoServicioHasEmpleado=" + codigoServicioHasEmpleado + ", codigoServicios=" + codigoServicios + ", codigoEmpleado=" + codigoEmpleado + ", fechaEvento=" + fechaEvento + ", horaEvento=" + horaEvento + ", lugarEvento=" + lugarEvento + '}';
    }    
}
