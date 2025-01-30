package SGIF.SGIFCliente.Presentation.Model;

import SGIF.SGIFProtocol.Presentacion;
import javax.swing.table.AbstractTableModel;
import java.util.List;
public class PresentacionTableModel extends AbstractTableModel {

    final String[] columnas = {"ID", "Unidad", "Cantidad"};
    private List<Presentacion> presentaciones;

    public PresentacionTableModel(List<Presentacion> presentaciones) {
        this.presentaciones = presentaciones;
    }

    @Override
    public int getRowCount() {
        return presentaciones.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }
    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Presentacion presentacion = presentaciones.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return presentacion.getId();
            case 1:
                return presentacion.getUnidad();
            case 2:
                return presentacion.getCantidad();
            default:
                return null;
        }
    }
    public void addPresentacion(Presentacion presentacion) {
        presentaciones.add(presentacion);
        fireTableRowsInserted(presentaciones.size() - 1, presentaciones.size() - 1);
    }
    public void setPresentaciones(List<Presentacion> presentacion) {
        this.presentaciones = presentacion;
        fireTableDataChanged();
    }
    public void camBiarPresentacionTable(List<Presentacion> pres){
        new PresentacionTableModel(pres);
        fireTableDataChanged();
    }
    public void removePresentacion(int rowIndex) {
        presentaciones.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updatePresentacion(int rowIndex, Presentacion presentacion) {
        this.presentaciones.set(rowIndex, presentacion);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    public Presentacion getPresentacionAt(int rowIndex) {
        return presentaciones.get(rowIndex);
    }
}
