package SGIF.SGIFCliente.Presentation.Controller;

import SGIF.SGIFCliente.Presentation.Model.*;
import SGIF.SGIFCliente.Presentation.View.InventarioView;
import SGIF.SGIFCliente.logic.ServiceProxy;
import SGIF.SGIFProtocol.Articulo;
import SGIF.SGIFProtocol.Categoria;
import SGIF.SGIFProtocol.Presentacion;
import SGIF.SGIFProtocol.SubCategoria;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

///Solo agregue al final la parte de login o handle login para comunicar el view con el serviceProxy

public class Controller {

    private Model model;
    private InventarioView view;
    private ServiceProxy localService;


    public Controller() {
        model = new Model();
        model.cargarArchivo();
        //Service
        localService = (ServiceProxy) ServiceProxy.instance();
    }

    public Model getModel() {
        return model;
    }

    public void startApplication() {
        InventarioView view = new InventarioView();
        view.setController(this);
        view.loadView();
    }

    public AbstractTableModel getModelCategorias() {
        return new CategoriaTableModel(model.getCategorias());
    }

    public AbstractTableModel getModelSubCategorias(Categoria categoriaSeleccionada) {
        return new SubCategoriaTableModel(model.cargarSubCategorias(categoriaSeleccionada));
    }

    public AbstractTableModel getModelArticulos(SubCategoria subCategoriaSeleccionada) {
        return new ArticuloTableModel(model.cargarArticulos(subCategoriaSeleccionada));
    }

    public AbstractTableModel getModelPresentaciones(Articulo articuloSeleccionado) {
        return new PresentacionTableModel(model.cargarPresentaciones(articuloSeleccionado));
    }

    //============================================================================================================
    //GUARDARES
    public Categoria guardarCategoria(String id, String nom, String des) throws Exception {
        return model.guardarCategoria(id, nom, des);
    }
    public Categoria guardarSubCat(Categoria categoria, String idSubcate, String nomSubCate, String descSubcate) throws Exception {
      return model.agregarSubCategoria(categoria, idSubcate, nomSubCate, descSubcate);
    }
    public Categoria agregarArticulo(Categoria categoria, String idSubcate,String id, String marca,String nombre, String descripcion) throws Exception {
        return model.agregarArticulo(categoria,idSubcate,id,marca,nombre,descripcion);
    }
    public Categoria agregarPresentacion(Categoria categoria, String idSubCate, String idArt, String idPres, String unidadPres, String cantidadPres) throws Exception {
        return model.agregarPresentacion(categoria,idSubCate,idArt,idPres,unidadPres,cantidadPres);
    }
    //============================================================================================================
    //============================================================================================================
    //EDITARES
    public Categoria editarCategoria(String idCate, String nuevoNombre, String nuevaDescripcion)throws Exception{
        return model.editarCategoria(idCate,nuevoNombre,nuevaDescripcion);
    }
    public SubCategoria editarSubCategoria(String idCate, String idSubCate, String nuevoNombre, String nuevaDescripcion) throws Exception{
        return model.editarSubCategoria(idCate,idSubCate,nuevoNombre,nuevaDescripcion);
    }
    public Articulo editarArticulo(String idCate, String idSubCate, String idArt, String nuevaMarca, String nuevoNombre, String nuevaDescripcion) throws Exception{
        return model.editarArticulo(idCate,idSubCate,idArt,nuevaMarca,nuevoNombre,nuevaDescripcion);
    }
    public Presentacion editarPresentacion(String idCate, String idSubCate, String idArt, String idPres, String nuevaUnidad, String nuevaCantidad) throws Exception{
        return model.editarPresentacion(idCate,idSubCate,idArt,idPres,nuevaUnidad,nuevaCantidad);
    }
    //============================================================================================================
    //============================================================================================================
    //OBTENER
    public Categoria obtenerCategoriaPorCodigo(String cod){
        return model.obtenerCategoriaPorCodigo(cod);
    }
    public SubCategoria obtenerSubCategoriaPorCodigo(String cod) throws Exception {
        return model.obtenerSubCategoriaPorCodigo(cod);
    }
    public Articulo obtenerArticuloPorCodigo(String codigoArticulo, SubCategoria subCategoria) throws Exception {
        return model.obtenerArticuloPorCodigo(codigoArticulo,subCategoria);
    }
    //============================================================================================================
    //============================================================================================================
    //ACTUALIZAR TABLEMODELS
    public List<Categoria> actualizarTablaCategoria(){
       return model.getCategorias();
    }
    public List<SubCategoria> actualizarSubCategorias(String idC){
        List<SubCategoria> sucatFound = new ArrayList<>();
        for(Categoria c : model.getCategorias()){
            if(c.getID().equals(idC)){
                for(SubCategoria s: c.getSubCategoria()){
                    sucatFound.add(s);
                }
            }
        }
        return sucatFound;
    }
    public List<Articulo> actualizarTablaArticulo(String idc, String idSc){
        List<Articulo> articulosFound =new ArrayList<>();
        for(Categoria c : model.getCategorias()){
            if(c.getID().equals(idc)){
                for(SubCategoria s: c.getSubCategoria()){
                    if(s.getID().equals(idSc)){
                        for(Articulo a : s.getListadoArticulos()){
                            articulosFound.add(a);
                        }
                    }
                }
            }
        }
        return articulosFound;
    }
    public List<Presentacion> actualizarTablaPesentacion(String idC, String idSc, String idArt){
        List<Presentacion> presentacionesFound = new ArrayList<>();
        for(Categoria c : model.getCategorias()){
            if(c.getID().equals(idC)){
                for(SubCategoria s: c.getSubCategoria()){
                    if(s.getID().equals(idSc)){
                        for(Articulo a : s.getListadoArticulos()){
                            if(a.getId().equals(idArt)){
                                for(Presentacion p : a.getPresentacion()){
                                    presentacionesFound.add(p);
                                }
                            }
                        }
                    }
                }
            }
        }
        return presentacionesFound;
    }
    //============================================================================================================
    //============================================================================================================
    //BUSCARES POR ____
    public List<Categoria> categoriasEncontradasConID(String i) {
        return model.categoriasEncontradasConID(i);
    }
    public List<Categoria> categoriasEncontradasConNombre(String n) {
        return model.categoriasEncontradasConNombre(n);
    }
    //--
    public List<SubCategoria> subCategoriasEncontradasConIDICat(String idCat, String i){
        return model.subCategoriasEncontradasConIDICat(idCat, i);
    }
    public List<SubCategoria> subCategoriasEncontradasConNombreIDCat(String nC,String n){
        return model.subCategoriasEncontradasConNombreIDCat(nC,n);
    }
    //--
    public List<Articulo> articulosEncontradosConIDIDCIDSC(String idc, String idSc, String i){
        return model.articulosEncontradosConIDIDCIDSC(idc, idSc, i);
    }
    public List<Articulo> articulosEncontradosConNombreIDCIDSC(String idc, String idSc,String n){
        return model.articulosEncontradosConNombreIDCIDSC(idc, idSc,n);
    }
    //--
    public List<Presentacion> presentacionesEncontradasConIDIDAIDCIDSC(String ida, String idc, String idSc, String i){
        return model.presentacionesEncontradasConIDIDAIDCIDSC(ida, idc, idSc, i);
    }
    public List<Presentacion> presentacionesEncontradasConUnidadIDAIDCIDSC(String ida,String idc, String idSc,String u){
        return model.presentacionesEncontradasConUnidadIDAIDCIDSC(ida,idc, idSc,u);
    }
    //============================================================================================================
    //============================================================================================================

    //UNIDADES COMBOBOX


    public void llenarComboBoxUnidades(JComboBox<String> comboBox) {
        List<String> unidades = model.getUnidades();
        comboBox.removeAllItems();
        for (String unidad : unidades) {
            comboBox.addItem(unidad);
        }
    }
    //============================================================================================================
//USUARIOS

    public void deliverPedido(String pedido) {
        System.out.println( "Lista de pedidos");
    }

    ///eso de SwingWorker es un hilo aparte del original digamos hace que el login se haga aparte asi
    /// no molesta el otro hilo, si le quitamos eso se congela la pagina...


    public boolean handleLogin(String nombre, String password) {
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                if( ServiceProxy.instance().Login(nombre, password)){
                    return true;
                }
                return false;
            }

            @Override
            protected void done() {
                try {
                    boolean resultado = get(); // Obtiene el resultado de doInBackground()
                    if (resultado) {
                        // Si el inicio de sesión fue exitoso, puedes realizar alguna acción
                        System.out.println("Login exitoso para el usuario: " + nombre);
                    } else {
                        // Si las credenciales son incorrectas, puedes manejarlo internamente
                        System.out.println("Login fallido para el usuario: " + nombre);
                    }
                } catch (Exception ex) {
                    // Si ocurre una excepción, puedes manejarla internamente
                    ex.printStackTrace();
                    System.err.println("Error durante el login: " + ex.getMessage());
                }
            }
        }.execute();
        return false;
    }

///Tengo muchos metodos handle porque no se como hacer que funcionen jaja
   /* public boolean handleLogin(String nombre, String password) {
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                // Llama al metodo Login del ServiceProxy en un hilo separado
                return ServiceProxy.instance().Login(nombre, password); // Retorna true si el login es exitoso
            }
        }.execute();
        return false;
    }*/

    /*public boolean handleLogin(String nombre, String password) throws Exception {
        boolean resultado = false;
        if( ServiceProxy.instance().Login(nombre, password)){
            resultado = true;
        }
        return resultado;
    }*/

/*
Sirve para notificar, es una mouseherramienta que usaremos mas tarde
            @Override
            protected void done() {
                try {
                    boolean loginExitoso = get(); // Obtiene el resultado de doInBackground()
                    if (loginExitoso) {
                        // Si el inicio de sesión fue exitoso, puedes realizar alguna acción interna
                        System.out.println("Login exitoso para el usuario: " + nombre);
                    } else {
                        // Si las credenciales son incorrectas, puedes manejarlo internamente
                        System.out.println("Login fallido para el usuario: " + nombre);
                    }
                } catch (Exception ex) {
                    // Si ocurre una excepción, puedes manejarla internamente
                    ex.printStackTrace();
                    System.err.println("Error durante el login: " + ex.getMessage());
                }
            }
   * /

    /*public void handleLogin(String nombre, String password) {
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                // Llama al metodo Login del ServiceProxy en un hilo separado
                Usuario usuario = ServiceProxy.instance().Login(nombre, password);
                return usuario != null; // Retorna true si el usuario no es null
            }

            @Override
            protected void done() {
                try {
                    boolean loginExitoso = get(); // Obtiene el resultado de doInBackground()
                    if (loginExitoso) {
                        // Si el inicio de sesión fue exitoso, notifica a la vista
                        SwingUtilities.invokeLater(() -> view.showTabbedPane());
                    } else {
                        // Si las credenciales son incorrectas, muestra un mensaje de error
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE)
                        );
                    }
                } catch (Exception ex) {
                    // Si ocurre una excepción, muestra el mensaje de error
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() ->
                            JOptionPane.showMessageDialog(null, "Error de conexión con el servidor", "Error", JOptionPane.ERROR_MESSAGE)
                    );
                }
            }
        }.execute();
    }*/

}
