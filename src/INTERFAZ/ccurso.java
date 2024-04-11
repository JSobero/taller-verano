
package INTERFAZ;

import CONECCION.conectar;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ccurso {

    int codigo;
    String nombreCurso;
    int precio;
    int horas;
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }
    
    public void Insertar(JTextField paramNombre, JTextField paramPrecio, JTextField paramHora){
        setNombreCurso(paramNombre.getText());
        setPrecio(Integer.valueOf(paramPrecio.getText()));
        setHoras(Integer.valueOf(paramHora.getText()));
        CONECCION.conectar oconeccion = new conectar();
        String consulta = "INSERT INTO `cursos` (`nombre`, `precio`, `horas`) VALUES (?, ?, ?);";
        ImageIcon icono = new ImageIcon("src/IMAGENES/ICONO-GUARDAR.png");
        int rs =JOptionPane.showConfirmDialog(null, "¿Desea guardar este dato?",
                "TALLERES DE VERANO", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, icono);
        if (rs==JOptionPane.YES_OPTION) {
            try {
            CallableStatement cs = oconeccion.conexion().prepareCall(consulta);
            cs.setString(1, getNombreCurso());
            cs.setInt(2, getPrecio());
            cs.setInt(3, getHoras());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se inserto correctamente el curso");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"No se inserto correctamente el alumno, "+e.toString());
            }
        }else{
            JOptionPane.showMessageDialog(null, "Operación cancelada");
        }  
    }
    
    public void Mostrar(JTable paramTablaCursos){
        CONECCION.conectar oconeccion = new conectar();
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        paramTablaCursos.setRowSorter(OrdenarTabla);
        modelo.addColumn("Id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Horas");
        paramTablaCursos.setModel(modelo);
        String sql = "select * from cursos";
        String[] datos = new String[4];
        Statement st;
        try {
            st = oconeccion.conexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                modelo.addRow(datos);
            }
            paramTablaCursos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se pudo mostrar los registros, "+e);
        }
    }
    
    public void Seleccionar(JTable paramTablaCursos, JTextField paramId, JTextField paramNombre, JTextField paramPrecio, JTextField paramHora){
        try {
            int fila = paramTablaCursos.getSelectedRow();
            if(fila >= 0){
                paramId.setText(paramTablaCursos.getValueAt(fila, 0).toString());
                paramNombre.setText(paramTablaCursos.getValueAt(fila, 1).toString());
                paramPrecio.setText(paramTablaCursos.getValueAt(fila, 2).toString());
                paramHora.setText(paramTablaCursos.getValueAt(fila, 3).toString());
            }else{
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error de seleccion, "+e);
        }
    }
    
    public void Modificar(JTextField paramCodigo, JTextField paramNombre, JTextField paramPrecio, JTextField paramHora){
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNombreCurso(paramNombre.getText());
        setPrecio(Integer.valueOf(paramPrecio.getText()));
        setHoras(Integer.valueOf(paramHora.getText()));
        CONECCION.conectar oconeccion = new conectar();
        String consulta = "UPDATE `cursos` SET `nombre` = ?, `precio` = ?, `horas` = ? WHERE `cursos`.`id` = ?;";
        try {
            CallableStatement cs = oconeccion.conexion().prepareCall(consulta);
            cs.setString(1, getNombreCurso());
            cs.setInt(2, getPrecio());
            cs.setInt(3, getHoras());
            cs.setInt(4, getCodigo());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Modificación exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error de modificacion, "+e);
        }
    }
    
    public void Eliminar(JTextField paramCodigo){
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        CONECCION.conectar oconeccion = new conectar();
        String consulta = "DELETE FROM `cursos` WHERE `cursos`.`id` = ?;";
        try {
            CallableStatement cs = oconeccion.conexion().prepareCall(consulta);
            cs.setInt(1, getCodigo());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se elimino correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se pudo eliminar, "+e);
        }
    }
    
    public void Limpiar(JTextField paramCodigo, JTextField paramNombre, JTextField paramPrecio, JTextField paramHora){
        paramCodigo.setText("");
        paramNombre.setText("");
        paramPrecio.setText("");
        paramHora.setText("");
    }
}
