package org.sergiomayen.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.sergiomayen.bd.Conexion;
import org.sergiomayen.bean.Empresa;
import org.sergiomayen.bean.Presupuesto;
import org.sergiomayen.system.MainApp;

public class PresupuestoController implements Initializable {
    private MainApp escenarioPrincipal;
   private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
private Operacion tipoOperacion = Operacion.NINGUNO; 
   private ObservableList <Empresa> listaEmpresa;
   private ObservableList<Presupuesto>listaPresupuesto;
   private DatePicker fechaSolicitud;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TableView tblPresupuesto;
    @FXML private TableColumn colCodigoPresupuesto;
    @FXML private TableColumn colFechaSolicitud;
    @FXML private TableColumn colCantidadPresupuesto;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private TextField txtCodigoPresupuesto;
    @FXML private TextField txtCantidadPresupuesto;
    @FXML private ComboBox cmbCodigoEmpresa;
    @FXML private GridPane grpFechaSolicitud;
    
 public void dasactivarController(){
     txtCodigoPresupuesto.setEditable(false);
     txtCantidadPresupuesto.setEditable(false);
     grpFechaSolicitud.setDisable(false);
     cmbCodigoEmpresa.setEditable(false);
     cmbCodigoEmpresa.setDisable(true);
}
 
 
 
 public void activarController(){
     txtCodigoPresupuesto.setEditable(false);
     txtCantidadPresupuesto.setEditable(true);
     grpFechaSolicitud.setDisable(false);
     cmbCodigoEmpresa.setDisable(false);
 }
    
    public void limpiarController(){
     txtCodigoPresupuesto.setText("");
     txtCantidadPresupuesto.setText("");
     cmbCodigoEmpresa.getSelectionModel().clearSelection();   
    }
    
    public void cargarDatos(){
        tblPresupuesto.setItems(getPresupuesto());
        colCodigoPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto,Integer>("codigoPresupuesto"));
        colFechaSolicitud.setCellValueFactory(new PropertyValueFactory<Presupuesto,Date>("fechaSolicitud"));
        colCantidadPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuesto,Double>("cantidadPresupuesto"));
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Presupuesto,Integer>("codigoEmpresa"));
        cmbCodigoEmpresa.setItems(getEmpresa());
        dasactivarController();
        
    }
    
    public ObservableList<Presupuesto> getPresupuesto(){
        ArrayList<Presupuesto> lista = new ArrayList<Presupuesto>();
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPresupuesto }");
            ResultSet resultado = sp.executeQuery();
            while(resultado.next()){
            lista.add(new Presupuesto (resultado.getInt("codigoPresupuesto"),
                    resultado.getDate("fechaSolicitud"),
                    resultado.getDouble("cantidadPresupuesto"),
                    resultado.getInt("codigoEmpresa")
            ));
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaPresupuesto = FXCollections.observableArrayList(lista);
    }
    
     public ObservableList<Empresa> getEmpresa(){
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
         try{
       PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpresa()}");
       ResultSet resultado = procedimiento.executeQuery();
       while(resultado.next()){
       lista.add(new Empresa(resultado.getInt("codigoEmpresas"),
       resultado.getString("nombreEmpresa"),
       resultado.getString("direccion"),
       resultado.getString("telefono")));        
       }
        }catch(Exception e){
        e.printStackTrace();
    }        
        return listaEmpresa =FXCollections.observableArrayList(lista);
    } 
   
     public void seleccionarElemento(){
          txtCodigoPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCodigoPresupuesto()));
          fechaSolicitud.selectedDateProperty().set(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getFechaSolicitud());
          txtCantidadPresupuesto.setText(String.valueOf(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCantidadPresupuesto()));
          cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresa(((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
     }
     
        public Empresa buscarEmpresa(int codigoEmpresas){
    Empresa resultado = null;
    try{
    PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpresa(?)}");
    sp.setInt(1, codigoEmpresas);
    ResultSet registro = sp.executeQuery();
    while (registro.next()){
    resultado = new Empresa(registro.getInt("codigoEmpresas"),
    registro.getString("nombreEmpresa"),
    registro.getString("direccion"),
    registro.getString("telefono"));      
    }   
    }catch(Exception e){
    e.printStackTrace();
    }
    return resultado;
}
        
        
        
public void guardar(){
    Presupuesto registro = new Presupuesto();
    registro.setFechaSolicitud(fechaSolicitud.getSelectedDate());
    registro.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));
    registro.setCodigoEmpresa(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresas());        
try{
    PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarPresupuesto(?,?,?)}");
    sp.setDate(1, new java.sql.Date(registro.getFechaSolicitud().getTime()));
    sp.setDouble(2, registro.getCantidadPresupuesto());
    sp.setInt(3, registro.getCodigoEmpresa());
    sp.execute();
    listaPresupuesto.add(registro);
}catch(Exception e){
    e.printStackTrace();
}

}
   public void actualizar(){
       try{
        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ActualizarPresupuesto(?,?,?)}");
        Presupuesto registro = (Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem();
        registro.setFechaSolicitud(fechaSolicitud.getSelectedDate());
        registro.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));
    sp.setInt(1, registro.getCodigoPresupuesto());
    sp.setDate(2, new java.sql.Date(registro.getFechaSolicitud().getTime()));
    sp.setDouble(3, registro.getCantidadPresupuesto());
    sp.execute();
    JOptionPane.showMessageDialog(null, "Datos Actualizados");
       }catch(Exception w){
           w.printStackTrace();
       }
    }



        
     
         //Metodo para el boton Nuevo 
    public void nuevo(){
        switch(tipoOperacion){
            case NINGUNO:
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
    
    
    public void editarEmpresa(){
    switch(tipoOperacion){
        case NINGUNO:
            activarController();
            if(tblPresupuesto.getSelectionModel().getSelectedItem()!= null){
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
 public void eliminarPresupuesto(){
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
        if(tblPresupuesto.getSelectionModel().getSelectedItem()!= null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?","Eliminar el Presupuesto",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == JOptionPane.YES_OPTION){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPresupuesto(?)}");
            sp.setInt(1,((Presupuesto)tblPresupuesto.getSelectionModel().getSelectedItem()).getCodigoPresupuesto());
            sp.execute();
            listaPresupuesto.remove(tblPresupuesto.getSelectionModel().getFocusedIndex());
            limpiarController();
            JOptionPane.showMessageDialog(null, "Presupuesto eliminada correctamente");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
    }else{
    JOptionPane.showMessageDialog(null,"Debe seleccionar un registro de la tabla");
     }}}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaSolicitud = new DatePicker(Locale.ENGLISH);
        fechaSolicitud.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fechaSolicitud.getCalendarView().todayButtonTextProperty().set("Today");
        fechaSolicitud.getCalendarView().setShowWeeks(false);
        fechaSolicitud.getStylesheets().add("/org/sergiomayen/resource/DatePicker.css");
        grpFechaSolicitud.add(fechaSolicitud, 0, 0);
        
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
