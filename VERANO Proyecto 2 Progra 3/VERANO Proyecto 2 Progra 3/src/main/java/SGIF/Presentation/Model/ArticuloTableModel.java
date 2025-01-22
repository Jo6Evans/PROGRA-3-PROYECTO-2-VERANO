package SGIF.Presentation.Model;

import SGIF.logic.Articulo;
import SGIF.logic.Categoria;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ArticuloTableModel extends AbstractTableModel {
    final String[] columnas = {"ID", "Marca", "Nombre", "Descripcion"};
    private List<Articulo> articulos;

    public ArticuloTableModel(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    @Override
    public int getRowCount() {
        return articulos.size();
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
        Articulo articulo = articulos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return articulo.getId();
            case 1:
                return articulo.getMarca();
            case 2:
                return articulo.getNombre();
            case 3:
                return articulo.getDescripcion();
            default:
                return null;
        }
    }
    public void addArticulo(Articulo as) {
        articulos.add(as);
        fireTableRowsInserted(articulos.size() - 1, articulos.size() - 1);
    }
    public void setArticulos(List<Articulo> as) {
        this.articulos = as;
        fireTableDataChanged();
    }
    public void camBiarArticulosTable(List<Articulo> articulos){
        new ArticuloTableModel(articulos);
        fireTableDataChanged();
    }
    public void removeArticulo(int rowIndex) {
        articulos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateArticulo(int rowIndex, Articulo articulo) {
        this.articulos.set(rowIndex, articulo);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    public Articulo getArticuloAt(int rowIndex) {
        return articulos.get(rowIndex);
    }
}
