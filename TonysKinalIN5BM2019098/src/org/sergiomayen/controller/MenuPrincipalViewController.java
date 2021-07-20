package org.sergiomayen.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.sergiomayen.system.MainApp;

public class MenuPrincipalViewController implements Initializable {
   private MainApp escenarioPrincipal;
            
 @Override
 public void initialize(URL location, ResourceBundle resources) {
    }

    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }



    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    } 

    public void ventanaEmpresas() {
        escenarioPrincipal.ventanaEmpresas();
    }
    
    public void ventanaTipoEmpleado(){
        escenarioPrincipal.ventanaTipoEmpleado();
    }
    
    public void ventanaEmpleado(){
    escenarioPrincipal.ventanaEmpleado();
    }

    public void ventanaPresupuesto(){
        escenarioPrincipal.ventanaPresupuesto();
    }
    
    public void ventanaServicio(){
                escenarioPrincipal.ventanaServicio();

    }
    
    }

