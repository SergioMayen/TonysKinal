package org.sergiomayen.bean;

import java.util.Date;

public class Servicios {
    
    private int codigoServicios;
    private Date fechaServicio;
    private String tipoServicio;
    private String horaServicio;
    private String lugarServicio;
    private String telefonoContacto;
    private int codigoEmpresa;

    public Servicios() {
    }

    public Servicios(int codigoServicios, Date fechaServicio, String tipoServicio, String horaServicio, String lugarServicio, String telefonoContacto, int codigoEmpresa) {
        this.codigoServicios = codigoServicios;
        this.fechaServicio = fechaServicio;
        this.tipoServicio = tipoServicio;
        this.horaServicio = horaServicio;
        this.lugarServicio = lugarServicio;
        this.telefonoContacto = telefonoContacto;
        this.codigoEmpresa = codigoEmpresa;
    }

    public int getCodigoServicios() {
        return codigoServicios;
    }

    public void setCodigoServicios(int codigoServicios) {
        this.codigoServicios = codigoServicios;
    }

    public Date getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(String horaServicio) {
        this.horaServicio = horaServicio;
    }

    public String getLugarServicio() {
        return lugarServicio;
    }

    public void setLugarServicio(String lugarServicio) {
        this.lugarServicio = lugarServicio;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    @Override
    public String toString() {
        return "Servicios{" + "codigoServicios=" + codigoServicios + ", fechaServicio=" + fechaServicio + ", tipoServicio=" + tipoServicio + ", horaServicio=" + horaServicio + ", lugarServicio=" + lugarServicio + ", telefonoContacto=" + telefonoContacto + ", codigoEmpresa=" + codigoEmpresa + '}';
    }

    
}
