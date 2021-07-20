package org.sergiomayen.controller;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.sergiomayen.bd.Conexion;
import org.sergiomayen.bean.Platos;
import org.sergiomayen.bean.Servicios;
import org.sergiomayen.bean.ServiciosHasPlatos;
import org.sergiomayen.system.MainApp;


public class ServicioHasPlatosViewController implements Initializable {
    private MainApp escenarioPrincipal;
    private ObservableList <Platos>listaPlatos;
    private ObservableList<Servicios>listaServicios;
    private ObservableList<ServiciosHasPlatos>listaShp;


    @FXML private ComboBox cmbCodigoServicio;
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private TableView tblServicioHasPlatos;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colCodigoPlato;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    public ObservableList<ServiciosHasPlatos>getServiciosHasPlatos(){
     ArrayList<ServiciosHasPlatos> lista = new ArrayList<ServiciosHasPlatos>();
     try{
         PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios_has_Platos() }");
            ResultSet resultado = sp.executeQuery();
            while(resultado.next()){
            lista.add(new ServiciosHasPlatos(resultado.getInt("codigoServicios"),
            resultado.getInt("codigoPlatos")));
            }
     }catch(Exception e){
         e.printStackTrace();
         }
     return listaShp = FXCollections.observableArrayList(lista);
    }

    public ObservableList<Platos> getPlatos(){
        ArrayList<Platos> lista = new ArrayList<Platos>();
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlato}");
            ResultSet resultado = sp.executeQuery();
            while(resultado.next()){
                lista.add(new Platos (resultado.getInt("codigoPlatos"),
                resultado.getInt("cantidad"),
                resultado.getString("nombrePlato"),
                resultado.getString("descripcionPlato"),
                resultado.getDouble("precioPlato"),
                resultado.getInt("codigoTipoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
      return listaPlatos = FXCollections.observableArrayList(lista);  
    }    
    
     public ObservableList <Servicios>getServicios(){
        ArrayList<Servicios>lista = new ArrayList<Servicios>();
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicio}");
            ResultSet resultado = sp.executeQuery();
            while(resultado.next()){
                lista.add(new Servicios(resultado.getInt("codigoServicios"),
                resultado.getDate("fechaServicio"),
                resultado.getString("tipoServicio"),
                resultado.getString("horaServicio"),
                resultado.getString("lugarServicio"),
                resultado.getString("telefonoContacto"),
                resultado.getInt("codigoEmpresa")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicios = FXCollections.observableArrayList(lista);
    }   
     
    public void cargarDatos(){
        tblServicioHasPlatos.setItems(getServiciosHasPlatos());
        cmbCodigoServicio.setItems(getServicios());
        cmbCodigoPlato.setItems(getPlatos());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServiciosHasPlatos,Integer>("codigoServicios"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<ServiciosHasPlatos,Integer>("codigoPlatos"));
    }
    
 public Servicios buscarServicios(int codigoServicios){
    Servicios resultado = null;
    try{
    PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarServicio(?)}");
    sp.setInt(1, codigoServicios);
    ResultSet registro = sp.executeQuery();
    while (registro.next()){
    resultado = new Servicios(registro.getInt("codigoServicios"),
    registro.getDate("fechaServicio"),
    registro.getString("tipoServicio"),
    registro.getString("horaServicio"),
    registro.getString("lugarServicio"),
    registro.getString("telefonoContacto"),
    registro.getInt("codigoEmpresa"));      
    }   
    }catch(Exception e){
    e.printStackTrace();
    }
    return resultado;
}
 
 
  public Platos buscarPlatos(int codigoPlatos){
    Platos resultado = null;
    try{
    PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarPlato(?)}");
    sp.setInt(1, codigoPlatos);
    ResultSet registro = sp.executeQuery();
    while (registro.next()){
    resultado = new Platos(registro.getInt("codigoPlatos"),
    registro.getInt("cantidad"),
    registro.getString("nombrePlato"),
    registro.getString("descripcionPlato"),
    registro.getDouble("precioPlato"),
    registro.getInt("codigoTipoPlato"));      
    }   
    }catch(Exception e){
    e.printStackTrace();
    }
    return resultado;
}    
     
     public void seleccionElemento(){
    cmbCodigoServicio.getSelectionModel().select(buscarServicios(((ServiciosHasPlatos)tblServicioHasPlatos.getSelectionModel().getSelectedItem()).getCodigoServicios()));
    cmbCodigoPlato.getSelectionModel().select(buscarPlatos(((ServiciosHasPlatos)tblServicioHasPlatos.getSelectionModel().getSelectedItem()).getCodigoPlatos()));
     }
     
     
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
 public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        
     
    }    
       public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
       }     
 
}
