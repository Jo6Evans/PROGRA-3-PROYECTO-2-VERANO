package SGIF.SGIFCliente.Presentation.View;

import SGIF.SGIFCliente.Presentation.Controller.*;
import SGIF.SGIFCliente.Presentation.Model.ArticuloTableModel;
import SGIF.SGIFCliente.Presentation.Model.CategoriaTableModel;
import SGIF.SGIFCliente.Presentation.Model.PresentacionTableModel;
import SGIF.SGIFCliente.Presentation.Model.SubCategoriaTableModel;
import SGIF.SGIFProtocol.Articulo;
import SGIF.SGIFProtocol.Categoria;
import SGIF.SGIFProtocol.Presentacion;
import SGIF.SGIFProtocol.SubCategoria;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

///Agregue una parte que dice LOGIN y se intenta la misma idea que el proyecto CHAT
/// para hacer aparecer el panel lOGIN y despues el Tabbedpane pero no se logra
/// El problema es que ya en el momento de ingresar o se congela o sale el error de que no se conecto
/// aunque en la consola sale que el server si se conecto alguien pero no funciona


public class InventarioView {

    private JTabbedPane tabbedPane;
    private JPanel mainpanel;
    private JLabel ArticulocategoriaLabel;
    private JLabel ArticulosubCategoriaLabel;
    private JLabel ArticulocodigoLabel;
    private JLabel ArticulonombreLabel;
    private JTextField ArticuloCodigoTxtField;
    private JTextField ArticuloNombreTxtField;
    private JTextField ArticuloDescripcionTxtField;
    private JTextField ArticuloSubCategoriaTxtField;
    private JTextField ArticuloCategoriaTxtField;
    private JButton ArticuloguardarButton;
    private JLabel ArticulodescripcionLabel;
    private JLabel ArticulonombreLabel1;
    private JTextField ArticulonombreBusquedaTxtField;
    private JLabel ArticuloIDLabel;
    private JTextField ArticuloIDBusquedaTxtField;
    private JButton ArticulobuscarButton;
    private JScrollPane ListadoScrollPanel;
    private JTable listadoArticuloPanel;
    private JScrollPane PresentacionesScrollPanel;
    private JTable listadoPresentacionesPanel;
    private JButton presentacionguardarTodoButton;
    private JLabel CategoriacodigoLabel;
    private JTextField CategoriacodigoTxtField;
    private JTextField CategorianombreTxtField;
    private JTextField CategoriadescripcionTxtField;
    private JButton CategoriaguardarButton;
    private JLabel CategorianombreBuscarLabel;
    private JTextField CategorianombreBuscarTxtField;
    private JLabel CategoriaIDBuscarLabel;
    private JTextField CategoriaIDBuscarTxtField;
    private JButton CategoriabuscarButton;
    private JTable listadoCategoria;
    private JLabel CategorianombreLabel;
    private JLabel CategoriadescripcionLabel;
    private JLabel SubCategoriacategoriaLabel;
    private JLabel SubCategoriacodigoLabel;
    private JLabel SubCategorianombreLabel;
    private JLabel SubCategoriadescripcionLabel;
    private JTextField SubCategoriacategoriaTxtField;
    private JTextField SubCategoriacodigoTxtField;
    private JTextField SubCategorianombreTxtField;
    private JTextField SubCategoriadescripcionTxtField;
    private JLabel SubCategorianombreBuscarLabel1;
    private JTextField SubCategorianombreBuscarTxtField;
    private JLabel SubCategoriaIDBuscarLabel;
    private JTextField SubCategoriaIDBuscarTxtField;
    private JButton SubCategoriabuscarButton;
    private JTable listadoSubcategoria;
    private JButton SubCategoriaguardarButton;

    //tab acerca de
    private JPanel AcercaDePanel;
    private JLabel IntegrantesLabel;
    private JLabel LetraAsignadaDeGrupo;
    private JLabel InfoDelPrograma;
    private JLabel PresentacionIDLabel;
    private JLabel capacidadCantidadLabel;
    private JTextField PresentacioncapacidadCantidadTxtField;
    private JTextField PresentacionIDTxtField;
    private JPanel CategoriaJpanel;
    private JPanel articuloPanel;
    private JPanel SubCategoriaPanel;
    private JPanel ArticuloPanel;
    private JLabel PresentacionUnidadLabel;
    private JButton categoriaEliminarButton;
    private JButton categoriaLimpiarButton;
    private JButton subCategoriaElminarButton;
    private JButton subCategoriaLimpiarButton;
    private JButton articuloEliminarButton;
    private JButton articuloLimpiarButton;
    private JButton PresentacionEliminarButton;
    private JButton PresentacionLimpiarButton;
    private JTextField ArticuloMarcaTxtField;
    private JLabel ArticuloMarcaLabel;
    private JButton CategoriaEditarButton;
    private JButton SubCategoriaEditarButton;
    private JButton ArticuloEditarButton;
    private JButton PresentacionEditarButton;
    private JPanel PresentacionesPanel;
    private JTextField PresentacionIDBuscarTxtField;
    private JLabel PresentacionIDBuscarLabel;
    private JButton PresentacionBuscarButton;
    private JLabel PresentacionBuscarUnidadLabel;
    private JTextField PresentacionBuscarUnidadTxtField;
    private JComboBox PresentacionUnidadComboBox;

    //LOGIN
    private JPanel loginPanel;
    private JTextField nombreLogintextField;
    private JButton loginButton;
    private JPasswordField passwordField1;

    private Controller controller;

    public InventarioView() {

        controller = new Controller();
        loginPanel.setVisible(true);
        tabbedPane.setVisible(false);

        SubCategoriacategoriaTxtField.setEnabled(false);
        ArticuloCategoriaTxtField.setEnabled(false);
        ArticuloSubCategoriaTxtField.setEnabled(false);
        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(2, false);
        PresentacionIDTxtField.setEnabled(false);
        PresentacioncapacidadCantidadTxtField.setEnabled(false);
        PresentacionEliminarButton.setEnabled(false);
        PresentacionEditarButton.setEnabled(false);
        PresentacionLimpiarButton.setEnabled(false);
        PresentacionUnidadComboBox.setEnabled(false);
        presentacionguardarTodoButton.setEnabled(false);
        controller.llenarComboBoxUnidades(PresentacionUnidadComboBox);

        PresentacionUnidadComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    PresentacionUnidadComboBox.getSelectedItem();
                    System.out.println("Categoría seleccionada: " + PresentacionUnidadComboBox.getSelectedItem().toString());
                }
            }
        });

        listadoCategoria.setModel(controller.getModelCategorias());
        listadoCategoria.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listadoCategoria.getSelectedRow();
                if (row >= 0) {
                    subCategoriaLimpiarButton.doClick();
                    CategoriaTableModel modeloTabla = (CategoriaTableModel) listadoCategoria.getModel();//
                    Categoria categoria = modeloTabla.getCategoriaAt(row);//
                    CategoriacodigoTxtField.setEnabled(false);
                    SubCategoriaPanel.setEnabled(true);
                    listadoSubcategoria.setModel(controller.getModelSubCategorias(categoria));
                    mostrarDatosCategoria(categoria);
                    tabbedPane.setEnabledAt(1, true);
                } else {
                    System.out.println("c");
                }
            }
        });

        listadoSubcategoria.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listadoSubcategoria.getSelectedRow();
                if (row >= 0) {
                    int selectedCategoryRow = listadoCategoria.getSelectedRow();
                    if (selectedCategoryRow >= 0) {
                        articuloLimpiarButton.doClick();
                        SubCategoriaTableModel modeloTabla = (SubCategoriaTableModel) listadoSubcategoria.getModel();
                        SubCategoria subCategoria = modeloTabla.getSubCategoriaAt(row);
                        SubCategoriacodigoTxtField.setEnabled(false);
                        listadoArticuloPanel.setEnabled(true);
                        listadoArticuloPanel.setModel(controller.getModelArticulos(subCategoria));
                        ArticuloCodigoTxtField.setEnabled(true);
                        mostrarDatosSubCategoria(subCategoria);
                        tabbedPane.setEnabledAt(2, true);
                    } else {
                        System.out.println("s");
                    }
                }
            }
        });


        listadoArticuloPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listadoArticuloPanel.getSelectedRow();
                if (row >= 0) {
                    PresentacionLimpiarButton.doClick();
                    ArticuloTableModel modeloTabla = (ArticuloTableModel) listadoArticuloPanel.getModel();
                    Articulo articulo = modeloTabla.getArticuloAt(row);
                    ArticuloCodigoTxtField.setEnabled(false);
                    listadoPresentacionesPanel.setEnabled(true);
                    listadoPresentacionesPanel.setModel(controller.getModelPresentaciones(articulo));
                    mostrarDatosArticulo(articulo);
                    prenderPresentacionesTodo();
                } else {
                    System.out.println("a");
                }
            }
        });

        listadoPresentacionesPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = listadoPresentacionesPanel.getSelectedRow();
                if (row >= 0) {
                    PresentacionTableModel modeloTabla = (PresentacionTableModel) listadoPresentacionesPanel.getModel();
                    Presentacion presentacion = modeloTabla.getPresentacionAt(row);
                    PresentacionIDTxtField.setEnabled(false);
                    mostrarDatosPresentacion(presentacion);
                } else {
                    System.out.println("p");
                }
            }
        });
        categoriaLimpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoriacodigoTxtField.setEnabled(true);
                CategoriacodigoTxtField.setText("");
                CategorianombreTxtField.setText("");
                CategoriadescripcionTxtField.setText("");
                SubCategoriaPanel.setEnabled(false);
                CategorianombreBuscarTxtField.setText("");
                CategoriaIDBuscarTxtField.setText("");
                CategoriacodigoLabel.setForeground(Color.BLACK);
                CategorianombreLabel.setForeground(Color.BLACK);
                tabbedPane.setEnabledAt(1, false);
                tabbedPane.setEnabledAt(2, false);
            }
        });
        subCategoriaLimpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubCategoriacodigoTxtField.setEnabled(true);
                SubCategoriacodigoTxtField.setText("");
                SubCategorianombreTxtField.setText("");
                SubCategoriadescripcionTxtField.setText("");
                SubCategorianombreBuscarTxtField.setText("");
                SubCategoriaIDBuscarTxtField.setText("");
                tabbedPane.setEnabledAt(2, false);
            }
        });

        articuloLimpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArticuloNombreTxtField.setText("");
                ArticuloNombreTxtField.setText("");
                ArticuloMarcaTxtField.setText("");
                ArticuloCodigoTxtField.setText("");
                ArticuloCodigoTxtField.setEnabled(true);
                ArticuloDescripcionTxtField.setText("");
                ArticulonombreBusquedaTxtField.setText("");
                ArticuloIDBusquedaTxtField.setText("");
                PresentacionLimpiarButton.doClick();
                ArticulocodigoLabel.setForeground(Color.BLACK);
                ArticulonombreLabel.setForeground(Color.BLACK);
                apagarPresentacionesTodo();
            }
        });
        PresentacionLimpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PresentacionIDTxtField.setEnabled(true);
                PresentacionIDTxtField.setText("");
                PresentacioncapacidadCantidadTxtField.setText("");
                PresentacionUnidadComboBox.setSelectedItem(PresentacionUnidadComboBox.getItemAt(0));
                PresentacionIDBuscarTxtField.setText("");
            }
        });
        ///---------------
        CategoriaguardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (CategoriacodigoTxtField.getText().isEmpty() || CategorianombreTxtField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Los campos de código y nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                        CategoriacodigoLabel.setForeground(Color.RED);
                        CategorianombreLabel.setForeground(Color.RED);
                        return;
                    }
                    Categoria categoria = controller.guardarCategoria(CategoriacodigoTxtField.getText(),
                            CategorianombreTxtField.getText(),
                            CategoriadescripcionTxtField.getText());
                    System.out.println("Categoria guardada con exito");
                    CategoriaTableModel modeloTabla = (CategoriaTableModel) listadoCategoria.getModel();
                    modeloTabla.setCategorias(controller.actualizarTablaCategoria()); // Actualiza los datos en el modelo
                    listadoCategoria.clearSelection(); // Limpia la selección previa

                    tabbedPane.setEnabledAt(1, true);
                    System.out.println("ID: " + categoria.getID() + " Nombre: " + categoria.getNombre() + " Descripcion:" + categoria.getDescripcion());
                    ArticuloSubCategoriaTxtField.setText(CategoriacodigoTxtField.getText());
                    JOptionPane.showMessageDialog(null, "Categoria: " + categoria.getNombre() + " creada exitosamente");
                    SubCategoriacategoriaTxtField.setText(CategoriacodigoTxtField.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        SubCategoriaguardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (SubCategoriacodigoTxtField.getText().isEmpty() || CategorianombreTxtField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Los campos de código y nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                        SubCategoriacodigoLabel.setForeground(Color.RED);
                        SubCategorianombreTxtField.setForeground(Color.RED);
                        return;
                    }
                    Categoria encontrada = controller.obtenerCategoriaPorCodigo(CategoriacodigoTxtField.getText());
                    Categoria categoria = controller.guardarSubCat(encontrada, SubCategoriacodigoTxtField.getText(),
                            SubCategorianombreTxtField.getText(), SubCategoriadescripcionTxtField.getText());
                    SubCategoria subcateogoria = categoria.getSubCategoriaById(SubCategoriacodigoTxtField.getText());
                    System.out.println("Categoria guardada con exito");

                    SubCategoriaTableModel modeloTabla = (SubCategoriaTableModel) listadoSubcategoria.getModel();
                    modeloTabla.setSubCategorias(controller.actualizarSubCategorias(CategoriacodigoTxtField.getText())); // actualiza el modelo
                    listadoSubcategoria.clearSelection(); // limpia la seleccion previa

                    tabbedPane.setEnabledAt(2, true);
                    System.out.println("Categoria: " + categoria.getID() + "ID: " + subcateogoria.getID() + " Nombre: " + subcateogoria.getNombre() + " Descripcion:" + subcateogoria.getDescripcion());
                    JOptionPane.showMessageDialog(null, "SubCategoria: " + subcateogoria.getNombre() + " creada exitosamente");
                    SubCategoriacategoriaTxtField.setText(CategoriacodigoTxtField.getText());
                    ArticuloSubCategoriaTxtField.setText(SubCategoriacodigoTxtField.getText());
                    ArticuloCategoriaTxtField.setText(SubCategoriacategoriaTxtField.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ArticuloguardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ArticuloCodigoTxtField.getText().isEmpty() || ArticuloNombreTxtField.getText().isEmpty() ||
                            ArticuloMarcaTxtField.getText().isEmpty()) {

                        JOptionPane.showMessageDialog(null,
                                "Rellene los campos necesarios.", "Error", JOptionPane.ERROR_MESSAGE);
                        ArticulocodigoLabel.setForeground(Color.RED);
                        ArticulonombreLabel.setForeground(Color.RED);
                        ArticuloMarcaLabel.setForeground(Color.RED);
                        return;
                    }
                    String idCategoria = ArticuloCategoriaTxtField.getText();
                    String idSubCategoria = ArticuloSubCategoriaTxtField.getText();
                    String idArticulo = ArticuloCodigoTxtField.getText();
                    String marcaArticulo = ArticuloMarcaTxtField.getText();
                    String nombreArticulo = ArticuloNombreTxtField.getText();
                    String descripcionArticulo = ArticuloDescripcionTxtField.getText();
                    Categoria categoria = controller.obtenerCategoriaPorCodigo(idCategoria);
                    if (categoria == null) {
                        JOptionPane.showMessageDialog(null, "La categoría con el ID " + idCategoria + " no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    categoria = controller.agregarArticulo(categoria, idSubCategoria, idArticulo,
                            marcaArticulo, nombreArticulo, descripcionArticulo
                    );
                    ArticuloTableModel modeloTabla = (ArticuloTableModel) listadoArticuloPanel.getModel();
                    modeloTabla.setArticulos(controller.actualizarTablaArticulo(idCategoria, idSubCategoria)); // Actualiza los datos en el modelo
                    listadoArticuloPanel.clearSelection();

                    JOptionPane.showMessageDialog(null, "Artículo agregado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    prenderPresentacionesTodo();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar el artículo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        presentacionguardarTodoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ArticuloCodigoTxtField.getText().isEmpty() || PresentacionIDTxtField.getText().isEmpty() ||
                             PresentacioncapacidadCantidadTxtField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Ingrese los campos marcados en rojo");
                        ArticulocodigoLabel.setForeground(Color.RED);
                        PresentacionIDLabel.setForeground(Color.RED);
                        PresentacionUnidadLabel.setForeground(Color.RED);
                        capacidadCantidadLabel.setForeground(Color.RED);
                        return;
                    }
                    String idCategoria = ArticuloCategoriaTxtField.getText();
                    String idSubCategoria = ArticuloSubCategoriaTxtField.getText();
                    String idArticulo = ArticuloCodigoTxtField.getText();
                    String idPresentacion = PresentacionIDTxtField.getText();
                    String unidadPresentacion = PresentacionUnidadComboBox.getSelectedItem().toString();
                    String cantidadPresentacion = PresentacioncapacidadCantidadTxtField.getText();

                    Categoria categoria = controller.obtenerCategoriaPorCodigo(idCategoria);
                    if (categoria == null) {
                        JOptionPane.showMessageDialog(null, "La categoría con el ID " + idCategoria + " no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    categoria = controller.agregarPresentacion(categoria, idSubCategoria, idArticulo, idPresentacion,
                            unidadPresentacion, cantidadPresentacion
                    );
                    PresentacionTableModel modeloTabla = (PresentacionTableModel) listadoPresentacionesPanel.getModel();
                    modeloTabla.setPresentaciones(controller.actualizarTablaPesentacion(idCategoria, idSubCategoria, idArticulo)); // Actualiza el modelo
                    listadoPresentacionesPanel.clearSelection();

                    JOptionPane.showMessageDialog(null, "Presentación agregada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar el artículo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        categoriaEliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (CategoriacodigoTxtField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El campo de código es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                        CategoriacodigoLabel.setForeground(Color.RED);
                        return;
                    }
                    String codigoCategoria = CategoriacodigoTxtField.getText();
                    controller.getModel().eliminarCategoria(codigoCategoria);

                    System.out.println("Categoría eliminada con éxito");
                    JOptionPane.showMessageDialog(null, "Categoría eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    //actualizar modelo
                    CategoriaTableModel modeloTabla = (CategoriaTableModel) listadoCategoria.getModel();
                    modeloTabla.setCategorias(controller.actualizarTablaCategoria()); // Actualiza los datos en el modelo
                    listadoCategoria.clearSelection(); // Limpia la selección previa

                    CategoriacodigoTxtField.setText("");
                    CategorianombreTxtField.setText("");
                    CategoriadescripcionTxtField.setText("");
                    ArticuloCategoriaTxtField.setText("");
                    SubCategoriacategoriaTxtField.setText("");
                    CategoriacodigoTxtField.setEnabled(true);
                    tabbedPane.setEnabledAt(2, false);
                    tabbedPane.setEnabledAt(1, false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        subCategoriaElminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (SubCategoriacodigoTxtField.getText().isEmpty() || SubCategorianombreTxtField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El campo de código de subcategoría y categoria es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                        SubCategoriacodigoLabel.setForeground(Color.RED);
                        SubCategoriacategoriaLabel.setForeground(Color.RED);
                        return;
                    }
                    String codigoSubCategoria = SubCategoriacodigoTxtField.getText();
                    String codigoCategoria = SubCategoriacategoriaTxtField.getText();
                    Categoria categoria = controller.getModel().obtenerCategoriaPorCodigo(codigoCategoria);
                    if (categoria == null) {
                        JOptionPane.showMessageDialog(null, "Categoría no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controller.getModel().eliminarSubCategoria(categoria, codigoSubCategoria);
                    JOptionPane.showMessageDialog(null, "Subcategoría eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    //actualizar modelo
                    SubCategoriaTableModel modeloTabla = (SubCategoriaTableModel) listadoSubcategoria.getModel();
                    modeloTabla.setSubCategorias(controller.actualizarSubCategorias(CategoriacodigoTxtField.getText())); // actualiza el modelo
                    listadoSubcategoria.clearSelection(); // limpia la seleccion previa

                    SubCategoriacodigoTxtField.setText("");
                    SubCategoriacategoriaTxtField.setText("");
                    SubCategorianombreTxtField.setText("");
                    SubCategoriadescripcionTxtField.setText("");
                    tabbedPane.setEnabledAt(2, false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        articuloEliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (ArticuloCodigoTxtField.getText().isEmpty() || SubCategoriacodigoTxtField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El campo de código de artículo y subcategoría es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                        ArticuloCodigoTxtField.setForeground(Color.RED);
                        SubCategoriacodigoTxtField.setForeground(Color.RED);
                        return;
                    }
                    String codigoArticulo = ArticuloCodigoTxtField.getText();
                    String codigoSubCategoria = SubCategoriacodigoTxtField.getText();
                    String idCategoria = ArticuloCategoriaTxtField.getText();
                    String idSubCategoria = ArticuloSubCategoriaTxtField.getText();
                    SubCategoria subCategoria = controller.obtenerSubCategoriaPorCodigo(codigoSubCategoria);
                    if (subCategoria == null) {
                        JOptionPane.showMessageDialog(null, "Subcategoria no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controller.getModel().eliminarArticulo(subCategoria, codigoArticulo);

                    JOptionPane.showMessageDialog(null, "Articulo eliminado con exito.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    //actualizar modelo
                    ArticuloTableModel modeloTabla = (ArticuloTableModel) listadoArticuloPanel.getModel();
                    modeloTabla.setArticulos(controller.actualizarTablaArticulo(idCategoria, idSubCategoria)); // Actualiza los datos en el modelo
                    listadoArticuloPanel.clearSelection();

                    ArticuloCodigoTxtField.setText("");
                    ArticuloNombreTxtField.setText("");
                    ArticuloMarcaTxtField.setText("");
                    ArticuloDescripcionTxtField.setText("");
                    apagarPresentacionesTodo();
                    PresentacionLimpiarButton.doClick();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        PresentacionEliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (PresentacionIDTxtField.getText().isEmpty() || ArticuloCodigoTxtField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Los campos de codigo de presentacion Y articulo.", "Error", JOptionPane.ERROR_MESSAGE);
                        PresentacionIDTxtField.setForeground(Color.RED);
                        return;
                    }
                    String idCategoria = ArticuloCategoriaTxtField.getText();
                    String idSubCategoria = ArticuloSubCategoriaTxtField.getText();
                    String idArticulo = ArticuloCodigoTxtField.getText();
                    String codigoPresentacion = PresentacionIDTxtField.getText();
                    String codigoArticulo = ArticuloCodigoTxtField.getText();
                    String codigoSubCategoria = SubCategoriacodigoTxtField.getText();
                    SubCategoria subCategoria = controller.obtenerSubCategoriaPorCodigo(codigoSubCategoria);
                    if (subCategoria == null) {
                        JOptionPane.showMessageDialog(null, "Subcategoria no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Articulo articulo = controller.obtenerArticuloPorCodigo(codigoArticulo, subCategoria);
                    if (articulo == null) {
                        JOptionPane.showMessageDialog(null, "Articulo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    controller.getModel().eliminarPresentacion(articulo, codigoPresentacion);
                    JOptionPane.showMessageDialog(null, "Presentación eliminada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    //actualizar modelo
                    PresentacionTableModel modeloTabla = (PresentacionTableModel) listadoPresentacionesPanel.getModel();
                    modeloTabla.setPresentaciones(controller.actualizarTablaPesentacion(idCategoria, idSubCategoria, idArticulo)); // Actualiza el modelo
                    listadoPresentacionesPanel.clearSelection();

                    PresentacionIDTxtField.setText("");
                    PresentacionUnidadComboBox.setSelectedItem(0);
                    PresentacioncapacidadCantidadTxtField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        CategoriaEditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CategorianombreTxtField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Es necesario ingresar el ID de la categoria a editar");
                    CategoriacodigoLabel.setForeground(Color.RED);
                    return;
                }
                try {
                    String idCate = CategoriacodigoTxtField.getText();
                    String nuevoNombre = CategorianombreTxtField.getText();
                    String nuevaDescripcion = CategoriadescripcionTxtField.getText();

                    Categoria categoria = controller.editarCategoria(idCate, nuevoNombre, nuevaDescripcion);
                    JOptionPane.showMessageDialog(null, "Categoria editada correctamente.");
                    //actualizar modelo
                    CategoriaTableModel modeloTabla = (CategoriaTableModel) listadoCategoria.getModel();
                    modeloTabla.setCategorias(controller.actualizarTablaCategoria()); // Actualiza los datos en el modelo
                    listadoCategoria.clearSelection(); // Limpia la selección previa
                    categoriaLimpiarButton.doClick();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al editar la categoria: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        SubCategoriaEditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SubCategoriacodigoTxtField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Es necesario ingresar el ID de la categoria y la subcategoria a editar");
                    return;
                }
                try {
                    String idCate = CategoriacodigoTxtField.getText();
                    String idSubCate = SubCategoriacodigoTxtField.getText();
                    String nuevoNombre = SubCategorianombreTxtField.getText();
                    String nuevaDescripcion = SubCategoriadescripcionTxtField.getText();

                    SubCategoria subCategoria = controller.editarSubCategoria(idCate, idSubCate, nuevoNombre, nuevaDescripcion);
                    JOptionPane.showMessageDialog(null, "Subcategoria editada correctamente.");
                    //actualizar modelo
                    SubCategoriaTableModel modeloTabla = (SubCategoriaTableModel) listadoSubcategoria.getModel();
                    modeloTabla.setSubCategorias(controller.actualizarSubCategorias(CategoriacodigoTxtField.getText())); // actualiza el modelo
                    listadoSubcategoria.clearSelection(); // limpia la seleccion previa
                    subCategoriaLimpiarButton.doClick();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al editar la subcategoria: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        ArticuloEditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ArticuloCodigoTxtField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Es necesario ingresar el ID del articulo a editar");
                    return;
                }
                try {
                    String idCategoria = ArticuloCategoriaTxtField.getText();
                    String idSubCategoria = ArticuloSubCategoriaTxtField.getText();
                    String idCate = CategoriacodigoTxtField.getText();
                    String idSubCate = SubCategoriacodigoTxtField.getText();
                    String idArt = ArticuloCodigoTxtField.getText();
                    String nuevaMarca = ArticuloMarcaTxtField.getText();
                    String nuevoNombre = ArticuloNombreTxtField.getText();
                    String nuevaDescripcion = ArticuloDescripcionTxtField.getText();

                    Articulo articulo = controller.editarArticulo(idCate, idSubCate, idArt, nuevaMarca, nuevoNombre, nuevaDescripcion);
                    JOptionPane.showMessageDialog(null, "Articulo editado correctamente.");
                    //actualizar modelo
                    ArticuloTableModel modeloTabla = (ArticuloTableModel) listadoArticuloPanel.getModel();
                    modeloTabla.setArticulos(controller.actualizarTablaArticulo(idCategoria, idSubCategoria)); // Actualiza los datos en el modelo
                    listadoArticuloPanel.clearSelection();
                    articuloLimpiarButton.doClick();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al editar el articulo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        PresentacionEditarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ArticuloCodigoTxtField.getText().isEmpty() || PresentacionIDTxtField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Es necesario ingresar el ID del articulo para editar la presentacion");
                    return;
                }
                try {
                    String idCategoria = ArticuloCategoriaTxtField.getText();
                    String idSubCategoria = ArticuloSubCategoriaTxtField.getText();
                    String idArticulo = ArticuloCodigoTxtField.getText();
                    String idCate = CategoriacodigoTxtField.getText();
                    String idSubCate = SubCategoriacodigoTxtField.getText();
                    String idArt = ArticuloCodigoTxtField.getText();
                    String idPres = PresentacionIDTxtField.getText();
                    String nuevaUnidad = PresentacionUnidadComboBox.getSelectedItem().toString();
                    String nuevaCantidad = PresentacioncapacidadCantidadTxtField.getText();

                    Presentacion presentacion = controller.editarPresentacion(idCate, idSubCate, idArt, idPres, nuevaUnidad, nuevaCantidad);
                    JOptionPane.showMessageDialog(null, "Presentacion editada correctamente.");
                    //actualizar modelo
                    PresentacionTableModel modeloTabla = (PresentacionTableModel) listadoPresentacionesPanel.getModel();
                    modeloTabla.setPresentaciones(controller.actualizarTablaPesentacion(idCategoria, idSubCategoria, idArticulo)); // Actualiza el modelo
                    listadoPresentacionesPanel.clearSelection();
                    PresentacionLimpiarButton.doClick();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al editar la presentacion: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        CategoriabuscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CategorianombreBuscarTxtField.getText().isEmpty() && CategoriaIDBuscarTxtField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe Ingresar un codigo o un nombre para buscar");
                    List<Categoria> categorias = controller.actualizarTablaCategoria(); // obtener todas las categorias
                    CategoriaTableModel modeloTabla = (CategoriaTableModel) listadoCategoria.getModel();
                    modeloTabla.setCategorias(categorias);
                    listadoCategoria.clearSelection();
                    return;
                }
                List<Categoria> categorias = new ArrayList<>();
                try {
                    if (!CategoriaIDBuscarTxtField.getText().isEmpty()) {
                        categorias.addAll(controller.categoriasEncontradasConID(CategoriaIDBuscarTxtField.getText()));
                    }

                    if (!CategorianombreBuscarTxtField.getText().isEmpty()) {
                        categorias.addAll(controller.categoriasEncontradasConNombre(CategorianombreBuscarTxtField.getText()));
                    }

                    if (categorias.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se encontraron categorias con los criterios especificados.");
                    } else {
                        System.out.println("categorias encontradas");
                        CategoriaTableModel modeloTabla = (CategoriaTableModel) listadoCategoria.getModel();
                        modeloTabla.setCategorias(categorias); // Actualiza el listado en el modelo
                        listadoCategoria.clearSelection(); // Limpia la selección de la tabla
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al realizar la búsqueda: " + ex.getMessage());
                }
            }
        });
        SubCategoriabuscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SubCategoriaIDBuscarTxtField.getText().isEmpty() && SubCategorianombreBuscarTxtField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe Ingresar un código o un nombre para buscar");
                    List<SubCategoria> subcategorias = controller.actualizarSubCategorias(SubCategoriacategoriaTxtField.getText());
                    SubCategoriaTableModel modeloTabla = (SubCategoriaTableModel) listadoSubcategoria.getModel();
                    modeloTabla.setSubCategorias(subcategorias); // Actualizar el modelo con todas las subcategorias
                    listadoSubcategoria.clearSelection();

                    return;
                }
                List<SubCategoria> subcategorias = new ArrayList<>();
                try {
                    if (!SubCategoriaIDBuscarTxtField.getText().isEmpty()) {
                        subcategorias.addAll(controller.subCategoriasEncontradasConIDICat(SubCategoriacategoriaTxtField.getText(),
                                SubCategoriaIDBuscarTxtField.getText()));
                    }

                    if (!SubCategorianombreBuscarTxtField.getText().isEmpty()) {
                        subcategorias.addAll(controller.subCategoriasEncontradasConNombreIDCat(SubCategoriacategoriaTxtField.getText(),
                                SubCategorianombreBuscarTxtField.getText()));
                    }

                    if (subcategorias.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se encontraron subcategorías con los criterios especificados.");
                    } else {
                        System.out.println("subcategoria encontrada");
                        SubCategoriaTableModel modeloTabla = (SubCategoriaTableModel) listadoSubcategoria.getModel();
                        modeloTabla.setSubCategorias(subcategorias);
                        listadoSubcategoria.clearSelection();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al realizar la búsqueda: " + ex.getMessage());
                }
            }
        });
        ArticulobuscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ArticuloIDBusquedaTxtField.getText().isEmpty() && ArticulonombreBusquedaTxtField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe Ingresar un código o un nombre para buscar");
                    List<Articulo> articulos = controller.actualizarTablaArticulo(ArticuloCategoriaTxtField.getText(), ArticuloSubCategoriaTxtField.getText());
                    ArticuloTableModel modeloTabla = (ArticuloTableModel) listadoArticuloPanel.getModel();
                    modeloTabla.setArticulos(articulos); // Actualizar el modelo con todos los articulos
                    listadoArticuloPanel.clearSelection();

                    return;
                }
                List<Articulo> articulos = new ArrayList<>();
                try {
                    if (!ArticuloIDBusquedaTxtField.getText().isEmpty()) {
                        articulos.addAll(controller.articulosEncontradosConIDIDCIDSC(ArticuloCategoriaTxtField.getText(),
                                ArticuloSubCategoriaTxtField.getText(),
                                ArticuloIDBusquedaTxtField.getText()));
                    }

                    if (!ArticulonombreBusquedaTxtField.getText().isEmpty()) {
                        articulos.addAll(controller.articulosEncontradosConNombreIDCIDSC(ArticuloCategoriaTxtField.getText().trim(),
                                ArticuloSubCategoriaTxtField.getText().trim(),
                                ArticulonombreBusquedaTxtField.getText().trim()));
                    }

                    if (articulos.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se encontraron artículos con los criterios especificados.");
                    } else {
                        ArticuloTableModel modeloTabla = (ArticuloTableModel) listadoArticuloPanel.getModel();
                        modeloTabla.setArticulos(articulos);
                        listadoArticuloPanel.clearSelection();
                        System.out.println("articulos encontrados");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al realizar la búsqueda: " + ex.getMessage());
                }
            }
        });

        PresentacionBuscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUnidad = (String) PresentacionUnidadComboBox.getSelectedItem();
                PresentacionTableModel modeloTabla = (PresentacionTableModel) listadoPresentacionesPanel.getModel();

                modeloTabla.setPresentaciones(Collections.emptyList());
                listadoPresentacionesPanel.clearSelection();

                List<Presentacion> presentaciones = new ArrayList<>();
                try {
                    if (PresentacionIDBuscarTxtField.getText().isEmpty()) {
                        presentaciones = controller.actualizarTablaPesentacion(
                                ArticuloCategoriaTxtField.getText(),
                                ArticuloSubCategoriaTxtField.getText(),
                                ArticuloCodigoTxtField.getText()
                        );
                    } else {
                        if (!PresentacionIDBuscarTxtField.getText().isEmpty()) {
                            presentaciones.addAll(controller.presentacionesEncontradasConIDIDAIDCIDSC(
                                    ArticuloCodigoTxtField.getText(),
                                    ArticuloCategoriaTxtField.getText(),
                                    ArticuloSubCategoriaTxtField.getText(),
                                    PresentacionIDBuscarTxtField.getText()
                            ));
                        }

                        if (presentaciones.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No se encontraron presentaciones con los criterios especificados. Mostrando todos los resultados.");
                            presentaciones = controller.actualizarTablaPesentacion(
                                    ArticuloCategoriaTxtField.getText(),
                                    ArticuloSubCategoriaTxtField.getText(),
                                    ArticuloCodigoTxtField.getText()
                            );
                        }
                    }

                    modeloTabla.setPresentaciones(presentaciones);
                    listadoPresentacionesPanel.clearSelection();

                    if (!presentaciones.isEmpty()) {
                        System.out.println("Presentaciones encontradas: " + presentaciones.size());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al realizar la búsqueda: " + ex.getMessage());
                }
            }
        });


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = nombreLogintextField.getText().trim(); // Obtener el login
                String password = new String(passwordField1.getPassword()); // Obtener la contraseña

                if (nombre.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese su usuario y el password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Llamar al Controller para manejar el login
                try {
                    if ( controller.handleLogin(nombre, password) ) {
                        loginPanel.setVisible(false);
                        tabbedPane.setVisible(true);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

    }
/*
    public void showTabbedPane() {
        // Ocultar el loginPanel y mostrar el tabbedPane
        loginPanel.setVisible(false);
        System.out.println("Cambio de poanel=============");
        tabbedPane.setVisible(true);
    }*/

    //----------------------------------------------------------------
    //para llenar los campos de texto con la info
    private void llenarCamposdeTextoCategoria(Categoria e) {
        CategoriacodigoTxtField.setText(e.getID());
        CategorianombreTxtField.setText(e.getNombre());
        CategoriadescripcionTxtField.setText(e.getDescripcion());
    }

    private void llenarCamposdeTextoSubCategoria(SubCategoria e) {
        SubCategoriacodigoTxtField.setText(e.getID());
        SubCategorianombreTxtField.setText(e.getNombre());
        SubCategoriadescripcionTxtField.setText(e.getDescripcion());
    }

    private void llenarCamposdeTextoArticulo(Articulo e) {
        ArticuloCodigoTxtField.setText(e.getId());
        ArticuloMarcaTxtField.setText(e.getMarca());
        ArticuloNombreTxtField.setText(e.getNombre());
        ArticuloDescripcionTxtField.setText(e.getDescripcion());
    }

    //----------------------------------------------------------------
    //para resetear los campos de presentaciones
    private void apagarPresentacionesTodo() {
        PresentacionIDLabel.setEnabled(false);
        PresentacionUnidadLabel.setEnabled(false);
        capacidadCantidadLabel.setEnabled(false);
        PresentacionIDTxtField.setEnabled(false);
        PresentacioncapacidadCantidadTxtField.setEnabled(false);
        PresentacionEditarButton.setEnabled(false);
        PresentacionLimpiarButton.setEnabled(false);
        presentacionguardarTodoButton.setEnabled(false);
        PresentacionEliminarButton.setEnabled(false);
        PresentacionBuscarButton.setEnabled(false);
        PresentacionIDBuscarTxtField.setEnabled(false);
        PresentacionIDBuscarLabel.setEnabled(false);
        PresentacionUnidadComboBox.setEnabled(false);
        PresentacionIDLabel.setEnabled(false);
        listadoPresentacionesPanel.setModel(new DefaultTableModel());
    }

    private void prenderPresentacionesTodo() {
        PresentacionIDLabel.setEnabled(true);
        PresentacionUnidadLabel.setEnabled(true);
        capacidadCantidadLabel.setEnabled(true);
        PresentacionIDTxtField.setEnabled(true);
        PresentacioncapacidadCantidadTxtField.setEnabled(true);
        PresentacionEditarButton.setEnabled(true);
        PresentacionLimpiarButton.setEnabled(true);
        PresentacionEliminarButton.setEnabled(true);
        PresentacionBuscarButton.setEnabled(true);
        PresentacionIDBuscarTxtField.setEnabled(true);
        PresentacionIDLabel.setEnabled(true);
        PresentacionIDBuscarLabel.setEnabled(true);
        PresentacionUnidadComboBox.setEnabled(true);
        presentacionguardarTodoButton.setEnabled(true);
    }

    //----------------------------------------------------------------
    //para llenar los campos de texto con la informacion del objeto seleccionado del table
    private void mostrarDatosCategoria(Categoria cat) {
        CategoriacodigoTxtField.setText(cat.getID());
        CategorianombreTxtField.setText(cat.getNombre());
        CategoriadescripcionTxtField.setText(cat.getDescripcion());
        SubCategoriacategoriaTxtField.setText(cat.getID());
        ArticuloCategoriaTxtField.setText(cat.getID());
    }

    private void mostrarDatosSubCategoria(SubCategoria cat) {
        SubCategoriacodigoTxtField.setText(cat.getID());
        SubCategorianombreTxtField.setText(cat.getNombre());
        ArticuloSubCategoriaTxtField.setText(cat.getID());
        SubCategoriadescripcionTxtField.setText(cat.getDescripcion());
    }

    private void mostrarDatosArticulo(Articulo art) {
        ArticuloCodigoTxtField.setText(art.getId());
        ArticuloMarcaTxtField.setText(art.getMarca());
        ArticuloNombreTxtField.setText(art.getNombre());
        ArticuloDescripcionTxtField.setText(art.getDescripcion());
    }

    private void mostrarDatosPresentacion(Presentacion p) {
        PresentacionIDTxtField.setText(p.getId());
        PresentacioncapacidadCantidadTxtField.setText(p.getCantidad());
        PresentacionUnidadComboBox.setSelectedItem(p.getUnidad());
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void loadView() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setContentPane(new InventarioView().mainpanel); // Asigna el JPanel al JFrame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al salir

            frame.pack(); // Ajusta el tamaño del JFrame al contenido
            frame.setLocationRelativeTo(null); // Centra la ventana
            frame.setVisible(true); // Muestra la ventana
        });
    }
}
