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
import org.sergiomayen.system.MainApp;
import org.sergiomayen.bd.Conexion;
import org.sergiomayen.bean.TipoEmpleado;



public class TipoEmpleadoController implements Initializable {
    
private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
private Operacion tipoOperacion = Operacion.NINGUNO; 
private ObservableList <TipoEmpleado>listaTipoEmpleado;
private MainApp escenarioPrincipal;

    @FXML
    private TextField txtCodigoTipoEmpleado;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TableView tblTipoEmpleado;
    @FXML
    private TableColumn colCodigoTipoEmpleado;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    
    public void desactivarController(){
        txtCodigoTipoEmpleado.setEditable(false);
        txtDescripcion.setEditable(false);
     }
    
    public void activarController(){
        txtDescripcion.setEditable(true);
    }
    
    public void limpiarController(){
        txtCodigoTipoEmpleado.setText("");
        txtDescripcion.setText("");
    }
       
    public void guardar(){
        TipoEmpleado TipoEmpleadoNuevo = new TipoEmpleado();
        TipoEmpleadoNuevo.setDescripion(txtDescripcion.getText());
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoEmpleado(?)}");
            sp.setString(1,TipoEmpleadoNuevo.getDescripion());
            sp.execute();
            listaTipoEmpleado.add(TipoEmpleadoNuevo);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public ObservableList<TipoEmpleado> getTipoEmpleado(){
        ArrayList<TipoEmpleado> lista = new ArrayList<TipoEmpleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoEmpleado()}");
            ResultSet result = procedimiento.executeQuery();
            while(result.next()){
                lista.add(new TipoEmpleado(result.getInt("codigoTipoEmpleados"),
                result.getString("descripion")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    return listaTipoEmpleado =FXCollections.observableArrayList(lista);
    }
     
    public void cargarDatos(){
         tblTipoEmpleado.setItems(getTipoEmpleado());
         colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<TipoEmpleado,Integer>("codigoTipoEmpleados"));
         colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoEmpleado, String>("descripion"));
         desactivarController();
    }
    
    public void seleccionarElementos(){
         txtCodigoTipoEmpleado.setText(String.valueOf(((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleados()));
         txtDescripcion.setText(String.valueOf(((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getDescripion()));
    }
    
       
public void actualizar(){
    try{
        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoEmpleado(?,?)}");
        TipoEmpleado tipoEmpleadoACT =((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem());
        tipoEmpleadoACT.setDescripion(txtDescripcion.getText());
        sp.setInt(1,tipoEmpleadoACT.getCodigoTipoEmpleados());
        sp.setString(2, tipoEmpleadoACT.getDescripion());
        sp.execute();
        JOptionPane.showMessageDialog(null, "Datos Actualizados");
    }catch(Exception e){
        e.printStackTrace();
    }
}
    
    
    
    public void nuevoTipoEmpleado(){
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
    
    public void eliminarTipoEmpleado(){
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
             if(tblTipoEmpleado.getSelectionModel().getSelectedItem()!=null){
             int respuesta = JOptionPane.showConfirmDialog(null, "¿Está deguro que desea eliminar el registro?","Eliminar Tipo de Empleado",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
             if(respuesta == JOptionPane.YES_OPTION){
         try{
             PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoEmpleado(?)}");
        sp.setInt(1,((TipoEmpleado)tblTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleados());
        sp.execute();
        listaTipoEmpleado.remove(tblTipoEmpleado.getSelectionModel().getFocusedIndex());
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
    
    public void editarTipoEmpleado(){
    switch(tipoOperacion){
        case NINGUNO:
            activarController();
            if(tblTipoEmpleado.getSelectionModel().getSelectedItem()!= null){
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
    
 public TipoEmpleado buscarTipoEmpleado(int codigoTipoEmpleados){
    TipoEmpleado resultado = null;
    try{
    PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoEmpleado(?)}");
    sp.setInt(1, codigoTipoEmpleados);
    ResultSet registro = sp.executeQuery();
    while (registro.next()){
    resultado = new TipoEmpleado(registro.getInt("codigoTipoEmpleados"),
    registro.getString("descripion"));      
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
    
           public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
       }
       
}
