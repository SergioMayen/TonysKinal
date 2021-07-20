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
import org.sergiomayen.bean.TipoPlato;
import org.sergiomayen.system.MainApp;

public class TipoPlatoViewController implements Initializable {
    
    private enum Operacion {NUEVO,ELIMINAR,ACTUALIZAR,GUARDAR,EDITAR,CANCELAR,NINGUNO}
    private Operacion tipoOperacion = Operacion.NINGUNO;
    private ObservableList <TipoPlato>ListaTipoPlato;
    private MainApp escenarioPrincipal;

    @FXML private TextField txtCodigoTipoPlato;
    @FXML private TextField txtDescripcion;
    @FXML private TableView tblTipoPlato;
    @FXML private TableColumn colCódigoTipoPlato;
    @FXML private TableColumn colDescripción;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

public void desactivarController(){
        txtCodigoTipoPlato.setEditable(false);
        txtDescripcion.setEditable(false);
}
    public void activarController(){
        txtCodigoTipoPlato.setEditable(true);
        txtDescripcion.setEditable(true);
    }
    
    public void limpiarController(){
        txtCodigoTipoPlato.setText("");
        txtDescripcion.setText("");
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
    return ListaTipoPlato =FXCollections.observableArrayList(lista);
    }
         
    public void cargarDatos(){
         tblTipoPlato.setItems(getTipoPlato());
         colCódigoTipoPlato.setCellValueFactory(new PropertyValueFactory<TipoPlato,Integer>("codigoTipoPlatos"));
         colDescripción.setCellValueFactory(new PropertyValueFactory<TipoPlato, String>("descripcionTipo"));
         desactivarController();
    }   

    public void seleccionarElementos(){
        txtCodigoTipoPlato.setText(String.valueOf(((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlatos()));
        txtDescripcion.setText(String.valueOf(((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getDescripcionTipo()));
    }
    
 public void guardar(){
        TipoPlato TipoPlatoNuevo = new TipoPlato();
        TipoPlatoNuevo.setDescripcionTipo(txtDescripcion.getText());
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoPlato(?)}");
            sp.setString(1, TipoPlatoNuevo.getDescripcionTipo());
            sp.execute();
            ListaTipoPlato.add(TipoPlatoNuevo);
        }catch(Exception e){
            e.printStackTrace();
    }
    }
 
 public void actualizar(){
     try{
        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoPlato(?,?)}");
        TipoPlato TipoPlatoAct =((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem());
        TipoPlatoAct.setDescripcionTipo(txtDescripcion.getText());
        sp.setInt(1,TipoPlatoAct.getCodigoTipoPlatos());
        sp.setString(2, TipoPlatoAct.getDescripcionTipo());
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
             if(tblTipoPlato.getSelectionModel().getSelectedItem()!=null){
             int respuesta = JOptionPane.showConfirmDialog(null, "¿Está deguro que desea eliminar el registro?","Eliminar Tipo de Empleado",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
             if(respuesta == JOptionPane.YES_OPTION){
         try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
        sp.setInt(1,((TipoPlato)tblTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlatos());
        sp.execute();
        ListaTipoPlato.remove(tblTipoPlato.getSelectionModel().getFocusedIndex());
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
            if(tblTipoPlato.getSelectionModel().getSelectedItem()!= null){
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
    
         public void ventanaPlatos(){
                escenarioPrincipal.ventanaPlatos();

    }
         
           public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
       }
}
