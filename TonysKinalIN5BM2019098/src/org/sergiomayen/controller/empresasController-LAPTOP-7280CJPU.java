package org.sergiomayen.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.sergiomayen.bean.Empresa;
import org.sergiomayen.bd.Conexion;
import org.sergiomayen.report.GenerarReporte;
        

public class empresasController implements Initializable {

private MainApp escenarioPrincipal;
private enum Operacion{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
private Operacion tipoOperacion = Operacion.NINGUNO; 
private ObservableList <Empresa>listaEmpresa;
@FXML
    private TextField txtCodigpEmpresa;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtNombreEmpresa;
    @FXML
    private TableView tblEmpresas;
    @FXML
    private TableColumn colCodigoEmpresa;
    @FXML
    private TableColumn colNombreEmpresa;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    
    public void dasactivarController(){
        txtCodigpEmpresa.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        txtNombreEmpresa.setEditable(false);
    }
    
    public void activarController(){
        //txtCodigpEmpresa.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        txtNombreEmpresa.setEditable(true);
    }
    
    public void limpiarController(){
        txtCodigpEmpresa.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtNombreEmpresa.setText("");
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
            activarController();

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
    
    //Metodo de eliminar
 public void eliminarEmpresa(){
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
        if(tblEmpresas.getSelectionModel().getSelectedItem()!= null){
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro?","Eliminar Empresa",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == JOptionPane.YES_OPTION){
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpresa(?)}");
            sp.setInt(1,((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresas());
            sp.execute();
            listaEmpresa.remove(tblEmpresas.getSelectionModel().getFocusedIndex());
            limpiarController();
            JOptionPane.showMessageDialog(null, "Empresa eliminada correctamente");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    }else{
    JOptionPane.showMessageDialog(null,"Debe seleccionar un registro de la tabla");
     }
            
            
        
    }
}
 
    //Metodo para boton guardar
    public void guardar(){
        Empresa empresaNueva = new Empresa();
        empresaNueva.setNombreEmpresa(txtNombreEmpresa.getText());
        empresaNueva.setDireccion(txtDireccion.getText());
        empresaNueva.setTelefono(txtTelefono.getText());
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpresa(?,?,?)}");
            sp.setString(1,empresaNueva.getNombreEmpresa());
            sp.setString(2,empresaNueva.getDireccion());
            sp.setString(3,empresaNueva.getTelefono());
            sp.execute();
            listaEmpresa.add(empresaNueva);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    

    
    //Metodo para cargar datos a la vista
    public void cargarDatos(){
        tblEmpresas.setItems(getEmpresa());
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa,Integer>("codigoEmpresas"));
        colNombreEmpresa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nombreEmpresa"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empresa, String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empresa, String>("telefono"));
        dasactivarController();
    }
    
    //Metodo para ejecutar el procedimiento almacenado y llenar una lista de resultado 
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
    

    
    //Metodo para seleccionar elementos de la tabla 
public void seleccionarElementos(){
        txtCodigpEmpresa.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresas()));
        txtNombreEmpresa.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getNombreEmpresa()));
        txtDireccion.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getDireccion()));
        txtTelefono.setText(String.valueOf(((Empresa)tblEmpresas.getSelectionModel().getSelectedItem()).getTelefono()));
}

public void editarEmpresa(){
    switch(tipoOperacion){
        case NINGUNO:
            activarController();
            if(tblEmpresas.getSelectionModel().getSelectedItem()!= null){
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

//Metodo para actualizar los datos del modelo y del tableview llamando el sp
public void actualizar(){
    try{
        PreparedStatement sp = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpresa(?,?,?,?)}");
        Empresa empresaActualizada = ((Empresa)tblEmpresas.getSelectionModel().getSelectedItem());
        empresaActualizada.setNombreEmpresa(txtNombreEmpresa.getText());
        empresaActualizada.setDireccion(txtDireccion.getText());
        empresaActualizada.setTelefono(txtTelefono.getText());
        sp.setInt(1,empresaActualizada.getCodigoEmpresas());
        sp.setString(2,empresaActualizada.getNombreEmpresa());
        sp.setString(3, empresaActualizada.getDireccion());
        sp.setString(4, empresaActualizada.getTelefono());
        sp.execute();
        JOptionPane.showMessageDialog(null, "DATOS ACTUALIZADOS");
    }catch(Exception e){
        e.printStackTrace();
    }
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
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoEmpresa", null);
        GenerarReporte.mostrarReporte("ReporteEmpresas.jasper", "Reporte de Empresas", parametros);
    }

    public void generarReporte(){
        switch (tipoOperacion){
            case NINGUNO:
                imprimirReporte();
                break;
        }
    }
    
     public void reporteEmpresa(){
        
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
            case NINGUNO:
                imprimirReporte();
            break;
        }
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
       
    public void ventanaPresupuesto(){
        escenarioPrincipal.ventanaPresupuesto();
    }
           
       
        public void ventanaServicio(){
                escenarioPrincipal.ventanaServicio();

    }
}

   