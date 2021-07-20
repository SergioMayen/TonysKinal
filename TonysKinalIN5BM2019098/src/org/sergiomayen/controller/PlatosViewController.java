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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.sergiomayen.bd.Conexion;
import org.sergiomayen.bean.Platos;
import org.sergiomayen.bean.TipoPlato;
import org.sergiomayen.system.MainApp;

public class PlatosViewController implements Initializable {
private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
private Operacion tipoOperacion = Operacion.NINGUNO; 
private ObservableList <Platos>listaPlatos;
private ObservableList<TipoPlato>listaTipoPlato;
private MainApp escenarioPrincipal;

    @FXML private TextField txtCodigoPlato;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtNombrePlato;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private TableView tblPlatos;
    @FXML private ComboBox cmbCodigoTipoPlato;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colDescripcion;
    @FXML private TableColumn colPrecio;
    @FXML private TableColumn colCodigoTipoPlato;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    
    public void dasactivarController(){
        txtCodigoPlato.setEditable(false);
        txtCantidad.setEditable(false);
        txtNombrePlato.setEditable(false);
        txtDescripcion.setEditable(false);
        txtPrecio.setEditable(false);
        cmbCodigoTipoPlato.setEditable(false);
    }
    
    public void activarController(){
        txtCodigoPlato.setEditable(false);
        txtCantidad.setEditable(true);
        txtNombrePlato.setEditable(true);
        txtDescripcion.setEditable(true);
        txtPrecio.setEditable(true);
        cmbCodigoTipoPlato.setDisable(false);   
    }
    
    public void limpiarController(){
        txtCodigoPlato.setText(null);
        txtCantidad.setText(null);
        txtNombrePlato.setText(null);
        txtDescripcion.setText(null);
        txtPrecio.setText(null);
        cmbCodigoTipoPlato.getSelectionModel().clearSelection();   
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
    
         public ObservableList<TipoPlato> getTipoPlato(){
        ArrayList<TipoPlato> lista = new ArrayList<TipoPlato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoPlato()}");
            ResultSet result = procedimiento.executeQuery();
            while(result.next()){
                lista.add(new TipoPlato(result.getInt("codigoTipoPlatos"),
                result.getString("descripcionTipo")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    return listaTipoPlato =FXCollections.observableArrayList(lista);
    }
         
    public TipoPlato buscarTipoPlato(int codigoTipoPlatos){
    TipoPlato resultado = null;
    try{
    PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoPlato(?)}");
    sp.setInt(1, codigoTipoPlatos);
    ResultSet registro = sp.executeQuery();
    while (registro.next()){
    resultado = new TipoPlato(registro.getInt("codigoTipoPlatos"),
    registro.getString("descripcionTipo"));      
    }   
    }catch(Exception e){
    e.printStackTrace();
    }
    return resultado;
}
         
    
    public void cargarDatos(){
        tblPlatos.setItems(getPlatos());
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Platos,Integer>("codigoPlatos"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Platos,Integer>("Cantidad"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Platos,String>("nombrePlato"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Platos,String>("DescripcionPlato"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Platos,Double>("precioPlato"));
        colCodigoTipoPlato.setCellValueFactory(new PropertyValueFactory<Platos,Integer>("codigoTipoPlato"));
        cmbCodigoTipoPlato.setItems(getTipoPlato());
        dasactivarController();
        
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

    public void seleccionarElemento(){
        txtCodigoPlato.setText(String.valueOf(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlatos()));
        txtCantidad.setText(String.valueOf(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCantidad()));
        txtNombrePlato.setText(String.valueOf(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getNombrePlato()));
        txtDescripcion.setText(String.valueOf(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getDescripcionPlato()));
        txtPrecio.setText(String.valueOf(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getPrecioPlato()));
        cmbCodigoTipoPlato.getSelectionModel().select(buscarTipoPlato(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
    }
    
    public void guardar(){
        Platos registro = new Platos();
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setNombrePlato(txtNombrePlato.getText());
        registro.setDescripcionPlato(txtDescripcion.getText());
        registro.setPrecioPlato(Double.parseDouble(txtPrecio.getText()));
        registro.setCodigoTipoPlato(((TipoPlato)cmbCodigoTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlatos());
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPlato(?,?,?,?,?)}");
            sp.setInt(1, registro.getCantidad());
            sp.setString(2, registro.getNombrePlato());
            sp.setString(3, registro.getDescripcionPlato());
            sp.setDouble(4, registro.getPrecioPlato());
            sp.setInt(5, registro.getCodigoTipoPlato());
            sp.execute();
            listaPlatos.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        try{
        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPlato(?,?,?,?,?)}");
        Platos registro = (Platos)tblPlatos.getSelectionModel().getSelectedItem();
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setNombrePlato(txtNombrePlato.getText());
        registro.setDescripcionPlato(txtDescripcion.getText());
        registro.setPrecioPlato(Double.parseDouble(txtPrecio.getText()));
        sp.setInt(1, registro.getCodigoPlatos());
        sp.setInt(2, registro.getCantidad());
        sp.setString(3, registro.getNombrePlato());
        sp.setString(4, registro.getDescripcionPlato());
        sp.setDouble(5, registro.getPrecioPlato());
        sp.execute();
        JOptionPane.showMessageDialog(null, "Datos Actualizados");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //Metodos de los botones 
    
public void nuevo(){
        switch(tipoOperacion){
            case NINGUNO:
            limpiarController();
            activarController();
            btnNuevo.setText("Agregar");
            btnEliminar.setText("Cancelar");
            btnEditar.setDisable(true);
            btnReporte.setDisable(true);
            tipoOperacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                limpiarController();
                dasactivarController();
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
    
    
    public void editar(){
    switch(tipoOperacion){
        case NINGUNO:
            activarController();
            if(tblPlatos.getSelectionModel().getSelectedItem()!= null){
            btnEditar.setText("Actulizar");
            btnReporte.setText("Cancelar");
            btnNuevo.setDisable(true);
            btnEliminar.setDisable(true);
            limpiarController();
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
     
     //Metodo de eliminar
 public void eliminar(){
    switch(tipoOperacion){
        case GUARDAR:
            dasactivarController();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoOperacion = Operacion.NINGUNO;
            break;
         default:
    //Verificar que tenga un resultado de la tabla
        if(tblPlatos.getSelectionModel().getSelectedItem()!= null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?","Eliminar el Presupuesto",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == JOptionPane.YES_OPTION){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPresupuesto(?)}");
            sp.setInt(1,((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlatos());
            sp.execute();
            listaPlatos.remove(tblPlatos.getSelectionModel().getFocusedIndex());
            limpiarController();
            JOptionPane.showMessageDialog(null, "Presupuesto eliminada correctamente");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     }else{
    JOptionPane.showMessageDialog(null,"Debe seleccionar un registro de la tabla");
     }}}
 
         public void reporte(){
        switch(tipoOperacion){
            case ACTUALIZAR:
                limpiarController();
                dasactivarController();
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
    
        public void ventanaTipoPlato(){
                escenarioPrincipal.ventanaTipoPlato();

    }
           public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
       }
    
}
