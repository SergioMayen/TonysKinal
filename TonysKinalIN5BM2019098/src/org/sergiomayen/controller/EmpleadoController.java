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
import org.sergiomayen.bean.Empleado;
import org.sergiomayen.bean.TipoEmpleado;
import org.sergiomayen.system.MainApp;

public class EmpleadoController implements Initializable {
        private MainApp escenarioPrincipal;
private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
private Operacion tipoOperacion = Operacion.NINGUNO;
private ObservableList <Empleado>listaEmpleado;
private ObservableList <TipoEmpleado>listaTipoEmpleado;

@FXML private TextField txtCodigoEmpleado;
@FXML private TextField txtNumeroEmpleado;
@FXML private TextField txtApellidos;
@FXML private TextField txtNombres;
@FXML private TextField txtDireccion;
@FXML private TextField txtTelefono;
@FXML private TextField txtGradoCocinero;
@FXML private TableView tblEmpleado;
@FXML private TableColumn colCodigoEmpleado;
@FXML private TableColumn colNumeroEmpleado;
@FXML private TableColumn colApellidos;
@FXML private TableColumn colNombres;
@FXML private TableColumn colDireccion;
@FXML private TableColumn colTelefono;
@FXML private TableColumn colGradoCocinero;
@FXML private TableColumn colCodigoTipoEmpleado;
@FXML private ComboBox cmbCodigoTP;
@FXML private Button btnNuevo;
@FXML private Button btnEliminar;
@FXML private Button btnEditar;
@FXML private Button btnReporte;        

public void dasactivarController(){
    txtCodigoEmpleado.setEditable(false);
    txtNumeroEmpleado.setEditable(false);
    txtApellidos.setEditable(false);
    txtNombres.setEditable(false);
    txtDireccion.setEditable(false);
    txtTelefono.setEditable(false);
    txtGradoCocinero.setEditable(false);
    cmbCodigoTP.setEditable(false);
    cmbCodigoTP.setDisable(true);
    }

public void activarController(){
txtCodigoEmpleado.setEditable(false);
    txtNumeroEmpleado.setEditable(true);
    txtApellidos.setEditable(true);
    txtNombres.setEditable(true);
    txtDireccion.setEditable(true);
    txtTelefono.setEditable(true);
    txtGradoCocinero.setEditable(true);
    cmbCodigoTP.setDisable(false);
}

public void limpiarController(){
    txtCodigoEmpleado.setText("");
    txtNumeroEmpleado.setText("");
    txtApellidos.setText("");
    txtNombres.setText("");
    txtDireccion.setText("");
    txtTelefono.setText("");
    txtGradoCocinero.setText("");
    cmbCodigoTP.getSelectionModel().clearSelection();

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
        tblEmpleado.setItems(getEmpleado());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,Integer>("codigoEmpleado"));
        colNumeroEmpleado.setCellValueFactory(new PropertyValueFactory <Empleado,Integer>("numeroEmpleado"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Empleado,String>("apellidoEmpleado"));
        colNombres.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombreEmpleado"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado,String>("direccionEmpleado"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado,String>("telefonoContacto"));
        colGradoCocinero.setCellValueFactory(new PropertyValueFactory<Empleado,String>("gradoCocinero"));
        colCodigoTipoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado,Integer>("codigoTipoEmpleado"));
        cmbCodigoTP.setItems(getTipoEmpleado());
        dasactivarController();
    }    
        
   public void seleccionarElemento(){
       txtCodigoEmpleado.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
       txtNumeroEmpleado.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getNumeroEmpleado()));
       txtApellidos.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getApellidoEmpleado()));
       txtNombres.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getNombreEmpleado()));
       txtDireccion.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getDireccionEmpleado()));
       txtTelefono.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getTelefonoContacto()));
       txtGradoCocinero.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getGradoCocinero()));
       cmbCodigoTP.getSelectionModel().select(buscarTipoEmpleado(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
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
        
        
public void guardar(){
    Empleado registro = new Empleado();
    registro.setNumeroEmpleado(Integer.getInteger(txtNumeroEmpleado.getText()));
    registro.setApellidoEmpleado(txtApellidos.getText());
    registro.setNombreEmpleado(txtNombres.getText());
    registro.setDireccionEmpleado(txtDireccion.getText());
    registro.setTelefonoContacto(txtTelefono.getText());
    registro.setGradoCocinero(txtGradoCocinero.getText());
    registro.setCodigoTipoEmpleado(((TipoEmpleado)cmbCodigoTP.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleados());
    try{
        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?,?,?)}");
        sp.setInt(1, registro.getNumeroEmpleado());
        sp.setString(2, registro.getApellidoEmpleado());
        sp.setString(3, registro.getNombreEmpleado());
        sp.setString(4, registro.getDireccionEmpleado());
        sp.setString(5, registro.getTelefonoContacto());
        sp.setString(6, registro.getGradoCocinero());
        sp.setInt(7, registro.getCodigoTipoEmpleado());

    }catch(Exception e){
        e.printStackTrace();
    }
    
}        
        
public void actualizar(){
    try{
    PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpleado(?,?,?,?,?,?,?)}");
    Empleado registro = (Empleado)tblEmpleado.getSelectionModel().getSelectedItem();
  registro.setNumeroEmpleado(Integer.getInteger(txtNumeroEmpleado.getText()));
    registro.setApellidoEmpleado(txtApellidos.getText());
    registro.setNombreEmpleado(txtNombres.getText());
    registro.setDireccionEmpleado(txtDireccion.getText());
    registro.setTelefonoContacto(txtTelefono.getText());
    registro.setGradoCocinero(txtGradoCocinero.getText());
    sp.setInt(1, registro.getCodigoEmpleado());
    sp.setInt(2, registro.getNumeroEmpleado());
        sp.setString(3, registro.getApellidoEmpleado());
        sp.setString(4, registro.getNombreEmpleado());
        sp.setString(5, registro.getDireccionEmpleado());
        sp.setString(6, registro.getTelefonoContacto());
        sp.setString(7, registro.getGradoCocinero());
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
    
    public void editarEmpleado(){
    switch(tipoOperacion){
        case NINGUNO:
            activarController();
            if(tblEmpleado.getSelectionModel().getSelectedItem()!= null){
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
            limpiarController();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoOperacion = Operacion.NINGUNO;
            break;
         default:
    //Verificar que tenga un resultado de la tabla
        if(tblEmpleado.getSelectionModel().getSelectedItem()!= null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?","Eliminar el Empleado",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == JOptionPane.YES_OPTION){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
            sp.setInt(1,((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
            sp.execute();
            listaEmpleado.remove(tblEmpleado.getSelectionModel().getFocusedIndex());
            limpiarController();
            JOptionPane.showMessageDialog(null, "Empleado eliminado correctamente");
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
