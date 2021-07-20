package org.sergiomayen.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.sergiomayen.controller.DatosPersonalesController;
import org.sergiomayen.controller.EmpleadoController;
import org.sergiomayen.controller.MenuPrincipalViewController;
import org.sergiomayen.controller.PlatosViewController;
import org.sergiomayen.controller.PresupuestoController;
import org.sergiomayen.controller.ProductoHasPlatosController;
import org.sergiomayen.controller.ProductosViewController;
import org.sergiomayen.controller.ServicioController;
import org.sergiomayen.controller.ServicioHasEmpleadoViewController;
import org.sergiomayen.controller.ServicioHasPlatosViewController;
import org.sergiomayen.controller.TipoEmpleadoController;
import org.sergiomayen.controller.TipoPlatoViewController;
import org.sergiomayen.controller.empresasController;


public class MainApp extends Application {
    private final   String PAQUETE_VISTA = "/org/sergiomayen/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
            
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
           this.escenarioPrincipal = escenarioPrincipal;
           this.escenarioPrincipal.setTitle("Tony's Kinal App");
           escenarioPrincipal.getIcons().add(new Image("org/sergiomayen/img/Logo.png"));
    //Parent root = FXMLloader.load(getClass().getResource("org/sergiomayen/view/menuPrincipalView.fxml"));
    //Scene escena = new Scene (root);
    //escenarioPrincipal.setScene(escena);
    menuPrincipal();
    escenarioPrincipal.show();
    
    
    };   
      
public void menuPrincipal(){
    try{
        MenuPrincipalViewController menuPrincipal = (MenuPrincipalViewController)cambiarEscena("menuPrincipalView.fxml",600,337);
        menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
        e.printStackTrace();
    }        
}

public void ventanaProgramador(){
    try{
        DatosPersonalesController datosPersonales = (DatosPersonalesController)cambiarEscena("DatosPersonalesView.fxml",555,616);
        datosPersonales.setEscenarioPrincipal(this);
    }catch (Exception e){
        e.printStackTrace();
    }
}
    public static void main(String[] args) {
        launch(args);        
    }
    
    public void ventanaEmpresas(){
    try{
        empresasController EmpresaController = (empresasController)cambiarEscena("EmpresasView.fxml",692,438);
        EmpresaController.setEscenarioPrincipal(this);
    }catch(Exception e){
        e.printStackTrace();
    }
}
    public void ventanaTipoEmpleado(){
        try{
            TipoEmpleadoController TipoEmpleados = (TipoEmpleadoController)cambiarEscena("TipoEmpleadoView.fxml",536,394);
            TipoEmpleados.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleado(){
        try{
            EmpleadoController Empleado = (EmpleadoController)cambiarEscena("EmpleadoView.fxml",766,555);
            Empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPresupuesto(){
        try{
            PresupuestoController presupuesto = (PresupuestoController)cambiarEscena("PresupuestoView.fxml",600,400);
            presupuesto.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaServicio(){
            try{
                ServicioController servicio = (ServicioController)cambiarEscena("ServiciosView.fxml",759,439);
                servicio.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    
    public void ventanaTipoPlato(){
        try{
            TipoPlatoViewController TipoPlato = (TipoPlatoViewController)cambiarEscena("TipoPlatoView.fxml",568,400);
            TipoPlato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPlatos(){
        try{
            PlatosViewController Platos = (PlatosViewController)cambiarEscena("PlatosView.fxml",649,441);
            Platos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProductos(){
        try{
            ProductosViewController Productos = (ProductosViewController)cambiarEscena("ProductosView.fxml",516,387);
            Productos.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaShE(){
        try{
            ServicioHasEmpleadoViewController ShE = (ServicioHasEmpleadoViewController)cambiarEscena("ServicioHasEmpleadoView.fxml",600,449);
            ShE.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaPhP(){
        try{
        ProductoHasPlatosController php = (ProductoHasPlatosController)cambiarEscena("ProductoHasPlatos.fxml",600,358);
        php.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public void ventanaShP(){
         try{
             ServicioHasPlatosViewController shp = (ServicioHasPlatosViewController)cambiarEscena("ServicioHasPlatosView.fxml",600,335);
             shp.setEscenarioPrincipal(this);
         }catch(Exception e){
             e.printStackTrace();
         }
     }
     
    
    public Initializable cambiarEscena(String fxml,int ancho,int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = MainApp.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(MainApp.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene ((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }

  
    
}
