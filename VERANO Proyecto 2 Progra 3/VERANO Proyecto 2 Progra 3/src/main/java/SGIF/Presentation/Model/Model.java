package SGIF.Presentation.Model;

import SGIF.data.Inventario;
import SGIF.logic.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Model {
    private Inventario data = new Inventario();
    private List<SubCategoria> subcategorias;
    private List<Articulo> articulos;
    private List<Presentacion> presentaciones;
    private String unidades;
    private ArticuloTableModel articuloTableModel;
    private CategoriaTableModel categoriaTableModel;
    private PresentacionTableModel presentacionTableModel;
    private SubCategoriaTableModel subCategoriaTableModel;

    public void cargarArchivo() {
        data.LoadXML();
    }

    public Model() {
        Inventario data = new Inventario();
    }

    public List<Categoria> getCategorias() {
        return data.getCategorias();
    }

    public List<SubCategoria> cargarSubCategorias(Categoria categoriaSeleccionada) {
        this.subcategorias = categoriaSeleccionada.getSubCategoria();
        return this.subcategorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        data.setCategorias(categorias);
    }

    public void setSubCategorias(List<SubCategoria> subcategoria) {
        this.subcategorias = subcategoria;

    }

    public List<SubCategoria> getSubcategorias() {
        return subcategorias;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;

    }
    public List<Presentacion> getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(List<Presentacion> presentaciones) {
        this.presentaciones = presentaciones;

    }

    public void setUnidades(List<Unidad> unidades) {
        data.setUnidades(unidades);
    }

    public List<Unidad> getUnidades() {
        return data.getUnidades();
    }

    public List<Articulo> cargarArticulos(SubCategoria subcategoriaSeleccionada) {
        this.articulos=subcategoriaSeleccionada.getListadoArticulos();
        return this.articulos;
    }
    public List<Presentacion> cargarPresentaciones(Articulo articuloSeleccionado) {
        this.presentaciones = articuloSeleccionado.getPresentacion();

        return this.presentaciones;
    }

    public Categoria getCategoriaAt(int rowIndex){
        return data.getCategorias().get(rowIndex);
    }
    public SubCategoria getSubCategoriaAt(int rowIndex, Categoria categoriaSeleccionada){
        return categoriaSeleccionada.getSubCategoria().get(rowIndex);
    }
    public Articulo getArticuloAt(int rowIndex, SubCategoria subCategoriaSeleccionada){
        return subCategoriaSeleccionada.getListadoArticulos().get(rowIndex);
    }
    public Presentacion getPresentacionAt(int rowIndex, Articulo articuloSeleccionado){
        return articuloSeleccionado.getPresentacion().get(rowIndex);
    }
    public List<SubCategoria> searchSubCategorias(String id,String nom) {
        List<SubCategoria> subCategoriasEncontradas = new ArrayList<>();
        for(Categoria cat: getCategorias()){
            for(SubCategoria sub : cat.getSubCategoria()){
                if(sub.getNombre().equalsIgnoreCase(id)||sub.getNombre().equalsIgnoreCase(nom)){
                    subCategoriasEncontradas.add(sub);
                }
            }
        }
        return subCategoriasEncontradas;
    }
    public List<Categoria> searchCategorias(String id, String nom) {
        List<Categoria> categoriasEncontradas = new ArrayList<>();
        for(Categoria cat: getCategorias()){
            if(cat.getNombre().equalsIgnoreCase(id)||cat.getNombre().equalsIgnoreCase(nom)){
                categoriasEncontradas.add(cat);
            }
        }
        return categoriasEncontradas;
    }


    //SEARCH
    public List<Categoria> searchC(String id, String nombre){
        return data.getCategorias().stream()
                .filter(i -> (id == null || i.getID().contains(id)) &&
                        (nombre == null || i.getNombre().contains(nombre)))
                .sorted(Comparator.comparing(Categoria::getNombre))
                .collect(Collectors.toList());
    }


    public List<SubCategoria> searchSub(String id, String nombre) {
        List<SubCategoria> subCategoriasEncontradas = new ArrayList<>();
        for (Categoria categoria : data.getCategorias()) {
            for (SubCategoria subCategoria : categoria.getSubCategoria()) {
                if (subCategoria.getNombre().equalsIgnoreCase(id) || subCategoria.getNombre().equalsIgnoreCase(nombre)) {
                    subCategoriasEncontradas.add(subCategoria);
                }
            }
        }
        return subCategoriasEncontradas;
    }

    public List<Articulo> searchArt(String id, String nombre) {
        List<Articulo> articulosEncontradas = new ArrayList<>();
        for (SubCategoria subCategoria : subcategorias) {
            for (Articulo articulo : subCategoria.getListadoArticulos()) {
                if (articulo.getNombre().equalsIgnoreCase(id) || articulo.getNombre().equalsIgnoreCase(nombre)) {
                    articulosEncontradas.add(articulo);
                }
            }
        }
        return articulosEncontradas;
    }
    //===================================================================================================
    //GUARDAR CATEGORIAS
    public Categoria guardarCategoria(String id, String nombre, String descripcion) throws Exception {
        return data.crearCategoria(id, nombre, descripcion);
    }
    public Categoria agregarSubCategoria(Categoria categoria, String idSubcate, String nomSubCate, String descSubcate) throws Exception {
        return data.agregarSubCategoria(categoria, idSubcate, nomSubCate, descSubcate);
    }
    public Categoria agregarArticulo(Categoria categoria, String idSubcate,String id, String marca,String nombre, String descripcion) throws Exception {
        return data.agregarArticulo(categoria,idSubcate,id,marca,nombre,descripcion);
    }
    public Categoria agregarPresentacion(Categoria categoria, String idSubCate, String idArt, String idPres, String unidadPres, String cantidadPres) throws Exception {
        return data.agregarPresentacion(categoria,idSubCate,idArt,idPres,unidadPres,cantidadPres);
    }
    //===================================================================================================
    //===================================================================================================
    //OBTENER POR CODIGO
    public Categoria obtenerCategoriaPorCodigo(String codigo) {
        for (Categoria categoria : getCategorias()) {
            if (categoria.getID().equals(codigo)) {
                return categoria;
            }
        }
        return null;
    }
    public SubCategoria obtenerSubCategoriaPorCodigo(String codigoSubCategoria) throws Exception {
        for (Categoria categoria : getCategorias()) {
            for (SubCategoria subCategoria : categoria.getSubCategoria()) {
                if (subCategoria.getID().equals(codigoSubCategoria)) {
                    return subCategoria;
                }
            }
        }
        throw new Exception("Subcategoría no encontrada con el código " + codigoSubCategoria);
    }

    public Articulo obtenerArticuloPorCodigo(String codigoArticulo, SubCategoria subCategoria) throws Exception {
        for (Articulo articulo : subCategoria.getListadoArticulos()) {
            if (articulo.getId().equals(codigoArticulo)) {
                return articulo;
            }
        }
        throw new Exception("Artículo no encontrado con el código " + codigoArticulo);
    }
    //===================================================================================================
    //===================================================================================================
    //MODIFICAR LAS TABLAS

    public void actualizarDataCategoria(List<Categoria> categorias) {
        categoriaTableModel.setCategorias(categorias);
        categoriaTableModel.fireTableDataChanged();
    }
    //===================================================================================================
    //eliminares
    public void eliminarCategoria(String idCate)throws Exception{
        data.eliminarCategoria(idCate);
    }
    public void eliminarSubCategoria(Categoria categoria, String idSubcate)throws Exception{
        data.eliminarSubCategoria(categoria,idSubcate);
    }
    public void eliminarArticulo(SubCategoria s, String id) throws Exception {
        data.eliminarArticulo(s,id);
    }
    public void eliminarPresentacion(Articulo a, String id) throws Exception {
        data.eliminarPresentacion(a,id);
    }
    //==================================================================================================
    //Actualizares
    public Categoria editarCategoria(String idCate, String nuevoNombre, String nuevaDescripcion)throws Exception{
       return data.editarCategoria(idCate,nuevoNombre,nuevaDescripcion);
    }
    public SubCategoria editarSubCategoria(String idCate, String idSubCate, String nuevoNombre, String nuevaDescripcion) throws Exception{
        return data.editarSubCategoria(idCate,idSubCate,nuevoNombre,nuevaDescripcion);
    }
    public Articulo editarArticulo(String idCate, String idSubCate, String idArt, String nuevaMarca, String nuevoNombre, String nuevaDescripcion) throws Exception{
        return data.editarArticulo(idCate,idSubCate,idArt,nuevaMarca,nuevoNombre,nuevaDescripcion);
    }
    public Presentacion editarPresentacion(String idCate, String idSubCate, String idArt, String idPres, String nuevaUnidad, String nuevaCantidad) throws Exception{
        return data.editarPresentacion(idCate,idSubCate,idArt,idPres,nuevaUnidad,nuevaCantidad);
    }
    //===================================================================================================
    //BUSCARES
    public List<Categoria> categoriasEncontradasConID(String i) {
        return data.categoriasEncontradasConID(i);
    }
    public List<Categoria> categoriasEncontradasConNombre(String n) {
       return data.categoriasEncontradasConNombre(n);
    }
    //--
    public List<SubCategoria> subCategoriasEncontradasConIDICat(String idCat, String i){
       return data.subCategoriasEncontradasConIDIdCat(idCat,i);
    }
    public List<SubCategoria> subCategoriasEncontradasConNombreIDCat(String nC,String n){
      return data.subCategoriasEncontradasConNombreIdCat(nC,n);
    }
    //--
    public List<Articulo> articulosEncontradosConIDIDCIDSC(String idc, String idSc, String i){
        return data.articulosEncontradosConIDIDCIDSC(idc,idSc,i);
    }
    public List<Articulo> articulosEncontradosConNombreIDCIDSC(String idc, String idSc,String n){
        return data.articulosEncontradosConNombreIDCIDSC(idc,idSc,n);
    }
    //--
    public List<Presentacion> presentacionesEncontradasConIDIDAIDCIDSC(String ida, String idc, String idSc, String i){
        return data.presentacionesEncontradasConIDIDAIDCIDSC(ida, idc, idSc, i);
    }
    public List<Presentacion> presentacionesEncontradasConUnidadIDAIDCIDSC(String ida,String idc, String idSc,String u){
        return data.presentacionesEncontradasConUnidadIDAIDCIDSC(ida,idc, idSc,u);
    }
}