
package org.sergiomayen.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.sergiomayen.bd.Conexion;
import org.sergiomayen.bean.Empleado;
import org.sergiomayen.bean.Servicios;
import org.sergiomayen.bean.ServiciosHasEmpleados;
import org.sergiomayen.system.MainApp;

public class ServicioHasEmpleadoViewController implements Initializable {
    private MainApp escenarioPrincipal;
    private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private Operacion tipoOperacion = Operacion.NINGUNO;
    private ObservableList<ServiciosHasEmpleados> listaShE;
    private ObservableList <Servicios> listaServicios;
    private ObservableList<Empleado>listaEmpleado;
    private DatePicker fechaSolicitud;  

    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private ComboBox cmbCodigoEmpleado;
    @FXML private TextField txtHora;
    @FXML private TextField txtLugar;
    @FXML private GridPane grdFecha;
    @FXML private TableView tblServicioHasEmpleado;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colHora;
    @FXML private TableColumn colLugar;
    @FXML private Button btnNuevo;

    public void dasactivarController(){
        cmbCodigoServicio.setEditable(false);
        cmbCodigoServicio.setDisable(true);
        cmbCodigoEmpleado.setEditable(false);
        cmbCodigoEmpleado.setDisable(true);
        grdFecha.setDisable(false);
        txtHora.setEditable(false);
        txtLugar.setEditable(false);
    }
    
       public void activarController(){
        cmbCodigoServicio.setDisable(false);
        cmbCodigoEmpleado.setDisable(false);
        grdFecha.setDisable(false);
        txtHora.setEditable(true);
        txtLugar.setEditable(true);
    }
       
     public void limpiarController(){
     cmbCodigoServicio.getSelectionModel().clearSelection();
     cmbCodigoEmpleado.getSelectionModel().clearSelection();
     fechaSolicitud.selectedDateProperty().set(null);
     txtHora.setText("");
     txtLugar.setText("");
     }
    
 public ObservableList<Empleado>getEmpleado(){
    ArrayList<Empleado>lista = new ArrayList<Empleado>();
    try{
        PreparedStatement sp =Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleado}");
        ResultSet resultado = sp.executeQuery();
        while(resultado.next()){
            lista.add(new Empleado  (resultado.getInt("codigoEmpleado"),
            resultado.getInt("numeroEmpleado"),
            resultado.getString("apellidoEmpleado"),
            resultado.getString("nombreEmpleado"),
            resultado.getString("direccionEmpleado"),  
            resultado.getString("telefonoContacto"),         
            resultado.getString("gradoCocinero"),        
            resultado.getInt("codigoTipoEmpleado")
       ));                    
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    return listaEmpleado = FXCollections.observableArrayList(lista);
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
 
 public ObservableList<ServiciosHasEmpleados>getServiciosHasEmpleados(){
     ArrayList<ServiciosHasEmpleados>lista = new ArrayList<ServiciosHasEmpleados>();
     try{
        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call  sp_ListarServicio_has_Empleados}");
        ResultSet resultado = sp.executeQuery();
        while(resultado.next()){
            lista.add(new ServiciosHasEmpleados(resultado.getInt("codigoServicioHasEmpleado"),
            resultado.getInt("codigoServicios"),
            resultado.getInt("codigoEmpleado"),
            resultado.getDate("fechaEvento"),
            resultado.getString("horaEvento"),
            resultado.getString("lugarEvento")));
        }
     }catch(Exception e){
         e.printStackTrace();
     }
   return listaShE = FXCollections.observableArrayList(lista);
 }              
     
 public void cargarDatos(){
      tblServicioHasEmpleado.setItems(getServiciosHasEmpleados());
    colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,Integer>("codigoServicios"));
    colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,Integer>("codigoEmpleado"));
    cmbCodigoServicio.setItems(getServicios());
    cmbCodigoEmpleado.setItems(getEmpleado());
    colFecha.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,Date>("fechaEvento"));
    colHora.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,String>("horaEvento"));
    colLugar.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,String>("lugarEvento"));
    dasactivarController();
    }
 
public Empleado buscarEmpleado(int getCodigoEmpleado){
    Empleado resultado = null;
    try{
    PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarEmpleado(?)}");
    sp.setInt(1, getCodigoEmpleado);
    ResultSet registro = sp.executeQuery();
    while (registro.next()){
    resultado = new Empleado(registro.getInt("codigoEmpleado"),
    registro.getInt("numeroEmpleado"),
    registro.getString("apellidoEmpleado"),
    registro.getString("nombreEmpleado"),
    registro.getString("direccionEmpleado"),
    registro.getString("telefonoContacto"),
    registro.getString("gradoCocinero"),
    registro.getInt("codigoTipoEmpleado"));      
    }   
    }catch(Exception e){
    e.printStackTrace();
    }
    return resultado;
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
 
 public void seleccionarElementos(){
     cmbCodigoServicio.getSelectionModel().select(buscarServicios(((ServiciosHasEmpleados)tblServicioHasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicios()));
     cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((ServiciosHasEmpleados)tblServicioHasEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
     fechaSolicitud.selectedDateProperty().set(((ServiciosHasEmpleados)tblServicioHasEmpleado.getSelectionModel().getSelectedItem()).getFechaEvento());
     txtHora.setText(String.valueOf(((ServiciosHasEmpleados)tblServicioHasEmpleado.getSelectionModel().getSelectedItem()).getHoraEvento()));
     txtLugar.setText(String.valueOf(((ServiciosHasEmpleados)tblServicioHasEmpleado.getSelectionModel().getSelectedItem()).getLugarEvento()));
 }
 
 public void guardar(){
 ServiciosHasEmpleados registro = new ServiciosHasEmpleados();
 registro.setCodigoServicios(((Servicios)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicios());
 registro.setCodigoEmpleado(((Empleado)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
 registro.setFechaEvento(fechaSolicitud.getSelectedDate());
 registro.setHoraEvento(txtHora.getText());
 registro.setLugarEvento(txtLugar.getText());
 try{
     PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicio_has_Empleados(?,?,?,?,?)}");
     sp.setInt(1, registro.getCodigoServicios());
     sp.setInt(2, registro.getCodigoEmpleado());
     sp.setDate(3, new java.sql.Date(registro.getFechaEvento().getTime()));
     sp.setString(4, registro.getHoraEvento());
     sp.setString(5, registro.getLugarEvento());
     listaShE.add(registro);
 }catch(Exception e){
     e.printStackTrace();
 }
 }
 
 public void actualizar(){
     try{
         PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServicio_has_Empleados(?,?,?,?)}");
         ServiciosHasEmpleados registro = (ServiciosHasEmpleados)tblServicioHasEmpleado.getSelectionModel().getSelectedItem();
         registro.setFechaEvento(fechaSolicitud.getSelectedDate());
         registro.setHoraEvento(txtHora.getText());
         registro.setLugarEvento(txtLugar.getText());
         sp.setInt(1, registro.getCodigoServicioHasEmpleado());
         sp.setDate(2, new java.sql.Date(registro.getFechaEvento().getTime()));
         sp.setString(3, registro.getHoraEvento());
         sp.setString(5, registro.getLugarEvento());
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
            if(tblServicioHasEmpleado.getSelectionModel().getSelectedItem()!= null){
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
        if(tblServicioHasEmpleado.getSelectionModel().getSelectedItem()!= null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?","Eliminar el Servicio Has Empleado",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == JOptionPane.YES_OPTION){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarServicio_has_Empleados(?)}");
            sp.setInt(1,((ServiciosHasEmpleados)tblServicioHasEmpleado.getSelectionModel().getSelectedItem()).getCodigoServicioHasEmpleado());
            sp.execute();
            listaShE.remove(tblServicioHasEmpleado.getSelectionModel().getFocusedIndex());
            limpiarController();
            JOptionPane.showMessageDialog(null, "Servicio Has Empleado eliminado correctamente");
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
               dasactivarController();
                limpiarController();
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
        fechaSolicitud = new DatePicker(Locale.ENGLISH);
        fechaSolicitud.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fechaSolicitud.getCalendarView().todayButtonTextProperty().set("Today");
        fechaSolicitud.getCalendarView().setShowWeeks(false);
        fechaSolicitud.getStylesheets().add("/org/sergiomayen/resource/DatePicker.css");
        grdFecha.add(fechaSolicitud, 0, 0);
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
