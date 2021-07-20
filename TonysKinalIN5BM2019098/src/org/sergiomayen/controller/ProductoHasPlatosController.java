
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.sergiomayen.bd.Conexion;
import org.sergiomayen.bean.Platos;
import org.sergiomayen.bean.Productos;
import org.sergiomayen.bean.ProductosHasPlatos;
import org.sergiomayen.system.MainApp;


public class ProductoHasPlatosController implements Initializable {
    private MainApp escenarioPrincipal;
    private ObservableList <Productos>listaProductos;
    private ObservableList <Platos>listaPlatos;
    private ObservableList <ProductosHasPlatos>listaPhp;


    @FXML private ComboBox cmbCodigoProducto;
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private TableView tblProdutoHasPlatos;
    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colCodigoPlatos;


        public ObservableList<Productos> getProducto(){
        ArrayList<Productos>lista = new ArrayList<Productos>();
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProducto()}");
            ResultSet result = sp.executeQuery();
            while(result.next()){
                lista.add(new Productos(result.getInt("codigoProductos"),
                result.getString("nombreProducto"),
                result.getInt("cantidad")));
            }  
        }catch(Exception e){
            e.printStackTrace();
        }
       return listaProductos = FXCollections.observableArrayList(lista);
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
        
         public ObservableList<ProductosHasPlatos>getProductosHasPlatos(){
     ArrayList<ProductosHasPlatos> lista = new ArrayList<ProductosHasPlatos>();
     try{
         PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProducto_has_Platos() }");
            ResultSet resultado = sp.executeQuery();
            while(resultado.next()){
            lista.add(new ProductosHasPlatos(resultado.getInt("codigoProductos"),
            resultado.getInt("codigoPlatos")));
            }
     }catch(Exception e){
         e.printStackTrace();
         }
     return listaPhp = FXCollections.observableArrayList(lista);
    }

        public void cargarDatos(){
        tblProdutoHasPlatos.setItems(getProductosHasPlatos());
        cmbCodigoProducto.setItems(getProducto());
        cmbCodigoPlato.setItems(getPlatos());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<ProductosHasPlatos,Integer>("codigoProductos"));
        colCodigoPlatos.setCellValueFactory(new PropertyValueFactory<ProductosHasPlatos,Integer>("codigoPlatos"));        
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
 
  public Productos buscarProductos(int codigoProductos){
    Productos resultado = null;
    try{
    PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarProducto(?)}");
    sp.setInt(1, codigoProductos);
    ResultSet registro = sp.executeQuery();
    while (registro.next()){
    resultado = new Productos(registro.getInt("codigoProductos"),
    registro.getString("nombreProducto"),
    registro.getInt("cantidad"));      
    }   
    }catch(Exception e){
    e.printStackTrace();
    }
    return resultado;
}    
  
       public void seleccionElemento(){
    cmbCodigoProducto.getSelectionModel().select(buscarProductos(((ProductosHasPlatos)tblProdutoHasPlatos.getSelectionModel().getSelectedItem()).getCodigoProductos()));
    cmbCodigoPlato.getSelectionModel().select(buscarPlatos(((ProductosHasPlatos)tblProdutoHasPlatos.getSelectionModel().getSelectedItem()).getCodigoPlatos()));
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
