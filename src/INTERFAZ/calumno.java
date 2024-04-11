/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package INTERFAZ;

import CONECCION.conectar;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
//import javafx.scene.control.ComboBox;
//import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ADVANCE
 */
public class calumno {
    int codigo;
    String nombre;
    String apellido;
    String dni;
    String direccion;
    String celular;
    int combo;
    
    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }
    
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    public void Mostrar(JTable paramTablaAlumnos){
        CONECCION.conectar oconeccion = new conectar();
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        paramTablaAlumnos.setRowSorter(OrdenarTabla);
        modelo.addColumn("Id");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("DNI");
        modelo.addColumn("Direccion");
        modelo.addColumn("Celular");
        modelo.addColumn("Cursos");
        paramTablaAlumnos.setModel(modelo);
        String sql = "SELECT es.id,nombres,es.apellidos,es.dni,es.direccion,es.celular,cr.nombre  FROM estudiantes es inner join cursos cr ON es.cursos=cr.id;";
        //String sql = "SELECT * FROM estudiantes";
        String[] datos = new String[7];
        Statement st;
        try {
            st = oconeccion.conexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                modelo.addRow(datos);
            }
            paramTablaAlumnos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se pudo mostrar los registros, "+e);
        }
    }
    
    public void RellenarComboBox(String tabla, String valor, JComboBox combo){
        String sql = "select * from cursos";
        Statement st;
        CONECCION.conectar oconeccion = new conectar();
        try {
            st = oconeccion.conexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){                
                combo.addItem(rs.getString(valor));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se pudo rellenar el ComboBox, "+e);
        }
    }
    
    public void Seleccionar(JTable paramTablaAlumno, JTextField paramId, JTextField paramNombres, JTextField paramApellidos, JTextField paramDni,
            JTextField paramDireccion,JTextField paramCelular,JComboBox paramCombo){
        int fila = paramTablaAlumno.getSelectedRow();
        try{
            if(fila >= 0){
                paramId.setText(paramTablaAlumno.getValueAt(fila, 0).toString());
                paramNombres.setText(paramTablaAlumno.getValueAt(fila, 1).toString());
                paramApellidos.setText(paramTablaAlumno.getValueAt(fila, 2).toString());
                paramDni.setText(paramTablaAlumno.getValueAt(fila, 3).toString());
                paramDireccion.setText(paramTablaAlumno.getValueAt(fila, 4).toString());
                paramCelular.setText(paramTablaAlumno.getValueAt(fila, 5).toString());
                paramCombo.setSelectedItem(paramTablaAlumno.getValueAt(fila, 6).toString());
            }else{
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error de seleccion, "+e);
        }
    }
    
    public void Eliminar(JTextField paramCodigo){
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        CONECCION.conectar oconeccion = new conectar();
        String consulta = "DELETE FROM `estudiantes` WHERE `estudiantes`.`id` = ?;";
        try {
            CallableStatement cs = oconeccion.conexion().prepareCall(consulta);
            cs.setInt(1, getCodigo());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se elimino correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se pudo eliminar, "+e);
        }
    }
    
    public void Limpiar(JTextField paramId, JTextField paramNombres, JTextField paramApellidos, JTextField paramDni,
            JTextField paramDireccion,JTextField paramCelular,JComboBox paramCombo){
        paramId.setText("");
        paramNombres.setText("");
        paramApellidos.setText("");
        paramDni.setText("");
        paramDireccion.setText("");
        paramCelular.setText("");
        paramCombo.getEditor().setItem("");
    }
    
    public void Insertar(JTextField paramNombres, JTextField paramApellidos, JTextField paramDni,
            JTextField paramDireccion,JTextField paramCelular,JComboBox paramCombo){
        
        setNombre(paramNombres.getText());
        setApellido(paramApellidos.getText());
        setDni(paramDni.getText());
        setDireccion(paramDireccion.getText());
        setCelular(paramCelular.getText());
        setCombo(paramCombo.getSelectedIndex() + 1);
        CONECCION.conectar oconeccion = new conectar();
        String consulta = "INSERT INTO `estudiantes` (`nombres`, `apellidos`, `dni`, `direccion`, `celular`, `cursos`) VALUES (?, ?, ?, ?, ?, ?);";
        ImageIcon icono = new ImageIcon("src/IMAGENES/ICONO-GUARDAR.png");
        int rs =JOptionPane.showConfirmDialog(null, "¿Desea guardar este dato?",
                "TALLERES DE VERANO", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, icono);
        if (rs==JOptionPane.YES_OPTION) {
            try {
            CallableStatement cs = oconeccion.conexion().prepareCall(consulta);
            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getDni());
            cs.setString(4, getDireccion());
            cs.setString(5, getCelular());
            cs.setInt(6, getCombo());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se inserto correctamente el alumno");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"No se inserto correctamente el alumno, "+e.toString());
            }
        }else{
            JOptionPane.showMessageDialog(null, "Operación cancelada");
        } 
    }
    
    public void Modificar(JTextField paramId, JTextField paramNombres, JTextField paramApellidos, JTextField paramDni,
            JTextField paramDireccion,JTextField paramCelular,JComboBox paramCombo){
        setCodigo(Integer.parseInt(paramId.getText()));
        setNombre(paramNombres.getText());
        setApellido(paramApellidos.getText());
        setDni(paramDni.getText());
        setDireccion(paramDireccion.getText());
        setCelular(paramCelular.getText());
        setCombo(paramCombo.getSelectedIndex() + 1);
        CONECCION.conectar oconeccion = new conectar();
        String consulta = "UPDATE `estudiantes` SET `nombres` = ?, `apellidos` = ?,"
                + " `dni` = ?, `direccion` = ?, `celular` = ?, `cursos` = ? WHERE `estudiantes`.`id` = ?";
        try {
            CallableStatement cs = oconeccion.conexion().prepareCall(consulta);
            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getDni());
            cs.setString(4, getDireccion());
            cs.setString(5, getCelular());
            cs.setInt(6, getCombo());
            cs.setInt(7, getCodigo());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Modificación exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error de modificacion, "+e);
        }
    }
}
