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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.sergiomayen.bd.Conexion;
import org.sergiomayen.bean.Productos;
import org.sergiomayen.system.MainApp;

public class ProductosViewController implements Initializable {
private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
private Operacion tipoOperacion = Operacion.NINGUNO; 
private ObservableList <Productos>listaProductos;
private MainApp escenarioPrincipal;

    @FXML private TextField txtCodigoProductos;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCantidad;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodigoProductos;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colCantidad;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    public void desactivarController(){
        txtCodigoProductos.setEditable(false);
        txtNombre.setEditable(false);
        txtCantidad.setEditable(false);
    }
    
    public void activarController(){
        txtCodigoProductos.setEditable(true);
        txtNombre.setEditable(true);
        txtCantidad.setEditable(true);
    }
    
    public void limpiarController(){
        txtCodigoProductos.setText("");
        txtNombre.setText("");
        txtCantidad.setText("");
    }
    
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
    
    public void cargarDatos(){
        tblProductos.setItems(getProducto());
        colCodigoProductos.setCellValueFactory(new PropertyValueFactory<Productos,Integer>("codigoProductos"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Productos,String>("nombreProducto"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Productos,Integer>("cantidad"));
        desactivarController();
    }
    

    
    public void seleccionarElementos(){
        txtCodigoProductos.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProductos()));
        txtNombre.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getNombreProducto()));
        txtCantidad.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCantidad()));
    }
    
        public void guardar(){
        Productos ProductosN = new Productos();
        ProductosN.setNombreProducto(txtNombre.getText());
        ProductosN.setCantidad(Integer.parseInt(txtCantidad.getText()));
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProducto(?,?);}");
            sp.setString(1, ProductosN.getNombreProducto());
            sp.setInt(2, ProductosN.getCantidad());
            listaProductos.add(ProductosN);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
  public void actualizar(){
      try{
          PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProductos(?,?,?)}");
           Productos ProductosAct =((Productos)tblProductos.getSelectionModel().getSelectedItem());
        ProductosAct.setNombreProducto(txtNombre.getText());
        ProductosAct.setCantidad(Integer.parseInt(txtCantidad.getText()));
        sp.setInt(1,ProductosAct.getCodigoProductos());
        sp.setString(2, ProductosAct.getNombreProducto());
        sp.setInt(3, ProductosAct.getCantidad());
        sp.execute();
        JOptionPane.showMessageDialog(null, "Datos Actualizados");
      }catch(Exception e){
          e.printStackTrace();
      }
  }
    
 public void nuevo(){
        switch(tipoOperacion){
            case NINGUNO:
            activarController();
            limpiarController();
            btnNuevo.setText("Agregar");
            btnEliminar.setText("Cancelar");
            btnEditar.setDisable(true);
            btnReporte.setDisable(true);
            activarController();
            tipoOperacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                limpiarController();
                desactivarController();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoOperacion = Operacion.NINGUNO;
                //Cargar los nuevos datos en la tabla 
                cargarDatos();
                break;
        }
    }    
    
 
 public void eliminar(){
        switch(tipoOperacion){
            case GUARDAR:
            desactivarController();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoOperacion = Operacion.NINGUNO;
            break;
         default:
             if(tblProductos.getSelectionModel().getSelectedItem()!=null){
             int respuesta = JOptionPane.showConfirmDialog(null, "¿Está deguro que desea eliminar el registro?","Eliminar el Tipo de Plato",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
             if(respuesta == JOptionPane.YES_OPTION){
         try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
        sp.setInt(1,((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProductos());
        sp.execute();
        listaProductos.remove(tblProductos.getSelectionModel().getFocusedIndex());
        limpiarController();
        JOptionPane.showMessageDialog(null, "Tipo de Empleado eliminado correctamente");
                 }catch(Exception e){
             e.printStackTrace();
         }
         }else{
                 JOptionPane.showMessageDialog(null, "Debe seleccionar un registro de la tabla");
             }
             }
        }
    }   
 
 
     public void editar(){
    switch(tipoOperacion){
        case NINGUNO:
            activarController();
            if(tblProductos.getSelectionModel().getSelectedItem()!= null){
            btnEditar.setText("Actulizar");
            btnReporte.setText("Cancelar");
            btnNuevo.setDisable(true);
            btnEliminar.setDisable(true);
            tipoOperacion = Operacion.ACTUALIZAR;
            }else{
                JOptionPane.showMessageDialog(null,"Debes seleccionar un registro para editar");
            }break;
            
            case ACTUALIZAR:
            actualizar();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
           tipoOperacion = Operacion.NINGUNO;
           cargarDatos();
           break;
    }
}
    
 public Productos buscarProductos(int codigoProductos){
    Productos resultado = null;
    try{
    PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProducto(?)}");
    sp.setInt(1, codigoProductos);
    ResultSet registro = sp.executeQuery();
    while (registro.next()){
    resultado = new Productos(registro.getInt("codigoProductos"),
    registro.getString("descripion"),
    registro.getInt("cantidad"));      
    }   
    }catch(Exception e){
    e.printStackTrace();
    }
    return resultado;
}
 
   public void reporte(){
        switch(tipoOperacion){
            case ACTUALIZAR:
                limpiarController();
                desactivarController();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoOperacion = Operacion.NINGUNO;
            break;
        }
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
