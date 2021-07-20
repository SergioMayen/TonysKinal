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
import org.sergiomayen.bean.Servicios;
import org.sergiomayen.system.MainApp;


public class ServicioController implements Initializable {
    private MainApp escenarioPrincipal;
 private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
 private Operacion tipoOperacion = Operacion.NINGUNO; 
 private ObservableList <Empresa> listaEmpresa;
 private ObservableList<Servicios>listaServicios;
 private DatePicker fechaServicio;
 
    @FXML private TextField txtCodigoServicio;
    @FXML private TextField txtTipoServicio;
    @FXML private TextField txtHora;
    @FXML private TextField txtLugarServicio;
    @FXML private TextField txtTelefonoContacto;
    @FXML private ComboBox cmbCodigoEmpresa;
    @FXML private GridPane grdFechaServicio;
    @FXML private TableView tblServicios;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colFechaServicio;
    @FXML private TableColumn colTipoServicio;
    @FXML private TableColumn colHoraServicio;
    @FXML private TableColumn colLugarServicio;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    
    
    public void dasactivarController(){
    txtCodigoServicio.setEditable(false);
    grdFechaServicio.setDisable(false);
    txtTipoServicio.setEditable(false);
    txtHora.setEditable(false);
    txtLugarServicio.setEditable(false);
    txtTelefonoContacto.setEditable(false);
    cmbCodigoEmpresa.setEditable(false);
    cmbCodigoEmpresa.setDisable(true);
    }
    
    public void activarController(){
    txtCodigoServicio.setEditable(false);
    grdFechaServicio.setDisable(false);   
    txtTipoServicio.setEditable(true);
    txtHora.setEditable(true);
    txtLugarServicio.setEditable(true);
    txtTelefonoContacto.setEditable(true);
    cmbCodigoEmpresa.setDisable(false);
    }
    
    public void limpiarController(){
    txtCodigoServicio.setText("");
    txtTipoServicio.setText("");
    txtHora.setText("");
    txtLugarServicio.setText("");
    fechaServicio.selectedDateProperty().set(null);
    txtTelefonoContacto.setText("");
   // cmbCodigoEmpresa.getSelectionModel().clearSelection();
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
    
       
    
public void cargarDatos(){
    tblServicios.setItems(getServicios());
    colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("codigoServicios"));
    colFechaServicio.setCellValueFactory(new PropertyValueFactory<Servicios,Date>("fechaServicio"));
    colTipoServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("tipoServicio"));
    colHoraServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("horaServicio"));
    colLugarServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("lugarServicio"));
    colTelefono.setCellValueFactory(new PropertyValueFactory<Servicios, String>("telefonoContacto"));
    colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("codigoEmpresa"));
    cmbCodigoEmpresa.setItems(getEmpresa());
    dasactivarController();
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
    
public void seleccionarElemento(){
    txtCodigoServicio.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicios()));
    fechaServicio.selectedDateProperty().set(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getFechaServicio());
    txtTipoServicio.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTipoServicio()));
    txtHora.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getHoraServicio()));
    txtLugarServicio.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getLugarServicio()));
    txtTelefonoContacto.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTelefonoContacto()));
    cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresa(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));

}    
    
    public void guardar(){
        Servicios registro = new Servicios();
        registro.setFechaServicio(fechaServicio.getSelectedDate());
        registro.setTipoServicio(txtTipoServicio.getText());
        registro.setHoraServicio(txtHora.getText());
        registro.setLugarServicio(txtLugarServicio.getText());
        registro.setTelefonoContacto(txtTelefonoContacto.getText());
        registro.setCodigoEmpresa(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresas());        
        try{
                PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicio(?,?,?,?,?,?)}");
    sp.setDate(1, new java.sql.Date(registro.getFechaServicio().getTime()));
    sp.setString(2, registro.getTipoServicio());
    sp.setString(3, registro.getHoraServicio());
    sp.setString(4, registro.getLugarServicio());
    sp.setString(5, registro.getTelefonoContacto());
    sp.setInt(6, registro.getCodigoEmpresa());
    sp.execute();
    listaServicios.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        try{
     PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServicio(?,?,?,?,?,?)}");
     Servicios registro = (Servicios)tblServicios.getSelectionModel().getSelectedItem();
     registro.setFechaServicio(fechaServicio.getSelectedDate());
        registro.setTipoServicio(txtTipoServicio.getText());
        registro.setHoraServicio(txtHora.getText());
        registro.setLugarServicio(txtLugarServicio.getText());
        registro.setTelefonoContacto(txtTelefonoContacto.getText());
            sp.setInt(1, registro.getCodigoServicios());
           sp.setDate(2, new java.sql.Date(registro.getFechaServicio().getTime()));
            sp.setString(3, registro.getTipoServicio());
            sp.setString(4, registro.getHoraServicio());
            sp.setString(5, registro.getLugarServicio());
            sp.setString(6, registro.getTelefonoContacto());
            sp.execute();
            JOptionPane.showMessageDialog(null, "Datos Actualizados");
        }catch(Exception e){
            e.printStackTrace();
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
    public void editar(){
    switch(tipoOperacion){
        case NINGUNO:
            activarController();
            if(tblServicios.getSelectionModel().getSelectedItem()!= null){
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
        if(tblServicios.getSelectionModel().getSelectedItem()!= null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?","Eliminar el Servicios",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == JOptionPane.YES_OPTION){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarServicio(?)}");
            sp.setInt(1,((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicios());
            sp.execute();
            listaServicios.remove(tblServicios.getSelectionModel().getFocusedIndex());
            limpiarController();
            JOptionPane.showMessageDialog(null, "Servicios eliminada correctamente");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
    }else{
    JOptionPane.showMessageDialog(null,"Debe seleccionar un registro de la tabla");
     }
    }
 }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaServicio = new DatePicker(Locale.ENGLISH);
        fechaServicio = new DatePicker(Locale.ENGLISH);
        fechaServicio.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fechaServicio.getCalendarView().todayButtonTextProperty().set("Today");
        fechaServicio.getCalendarView().setShowWeeks(false);
        fechaServicio.getStylesheets().add("/org/sergiomayen/resource/DatePicker.css");
        grdFechaServicio.add(fechaServicio, 0, 0);
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
       
        public void ventanaEmpresas() {
            escenarioPrincipal.ventanaEmpresas();
        }
}

