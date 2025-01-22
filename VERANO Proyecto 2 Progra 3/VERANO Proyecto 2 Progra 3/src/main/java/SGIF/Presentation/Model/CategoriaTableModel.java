package SGIF.Presentation.Model;

import SGIF.logic.Articulo;
import SGIF.logic.Categoria;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CategoriaTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Nombre","Descripcion"};
    private List<Categoria> categorias;

    public CategoriaTableModel(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @Override
    public int getRowCount() {
        return categorias.size();
    }
    public void setRowCount(){
        categorias.clear();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    public Categoria getCategoriaAt(int rowIndex){
        return categorias.get(rowIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Categoria categoria = categorias.get(rowIndex);
        switch (columnIndex) {
            case 0: return categoria.getID();
            case 1: return categoria.getNombre();
            case 2: return categoria.getDescripcion();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addCategoria(Categoria categoria) {
        categorias.add(categoria);
        fireTableRowsInserted(categorias.size() - 1, categorias.size() - 1);
    }
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        fireTableDataChanged();
    }
    public void camBiarCategoriaTable(List<Categoria> categorias) {
        new CategoriaTableModel(categorias);
        fireTableDataChanged();
    }
    public void removeCategoria(int rowIndex) {
        categorias.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateCategoria(int rowIndex, Categoria categoria) {
        categorias.set(rowIndex, categoria);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}
