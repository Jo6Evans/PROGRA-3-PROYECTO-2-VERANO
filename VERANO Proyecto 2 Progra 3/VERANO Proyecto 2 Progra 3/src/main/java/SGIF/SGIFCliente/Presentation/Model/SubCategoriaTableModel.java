package SGIF.SGIFCliente.Presentation.Model;

import SGIF.SGIFProtocol.SubCategoria;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SubCategoriaTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Nombre", "Descripcion"};
    private List<SubCategoria> subcategorias;

    public SubCategoriaTableModel(List<SubCategoria> subcategorias) {
        this.subcategorias = subcategorias;
    }

    @Override
    public int getRowCount() {
        return subcategorias.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SubCategoria subCategoria = subcategorias.get(rowIndex);
        switch (columnIndex) {
            case 0: return subCategoria.getID();
            case 1: return subCategoria.getNombre();
            case 2: return subCategoria.getDescripcion();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];

    }
    public void addSubCategoria(SubCategoria presentacion) {
        subcategorias.add(presentacion);
        fireTableRowsInserted(subcategorias.size() - 1, subcategorias.size() - 1);
    }
    public void setSubCategorias(List<SubCategoria> subcategorias) {
        this.subcategorias = subcategorias;
        fireTableDataChanged();
    }
    public void camBiarSubCategoriaTable(List<SubCategoria> s){
        new SubCategoriaTableModel(s);
        fireTableDataChanged();
    }
    public void removeSubCategoria(int rowIndex) {
        subcategorias.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateSubCategoria(int rowIndex, SubCategoria subcategoria) {
        this.subcategorias.set(rowIndex, subcategoria);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public SubCategoria getSubCategoriaAt(int row) {
        return subcategorias.get(row);
    }
}