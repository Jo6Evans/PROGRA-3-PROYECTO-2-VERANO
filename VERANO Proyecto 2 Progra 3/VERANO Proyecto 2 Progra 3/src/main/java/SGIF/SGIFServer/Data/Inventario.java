package SGIF.SGIFServer.Data;

import SGIF.SGIFProtocol.*;

import java.util.ArrayList;
import java.util.List;

public class Inventario {

    private List<Categoria> categorias;
    private List<String> unidades;
    private List<Usuario> usuarios;

    public Inventario() {
        categorias = new ArrayList<>();
        unidades = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void setUnidades(List<String> unidades) {
        this.unidades = unidades;
    }

    public List<String> getUnidades() {
        System.out.println();
        return unidades;
    }

    public void LoadXML() {
        ReadXMLFile xmlPersistent = new ReadXMLFile();
        categorias = xmlPersistent.cargarCategorias();
        unidades = xmlPersistent.cargarUnidades();
        usuarios = xmlPersistent.cargarUsuarios();
    }

    private boolean categoriaExiste(String codigo) {
        return getCategorias().stream().anyMatch(categoria -> categoria.getID().equals(codigo));
    }

    private Categoria readCategoriaConCodigo(String codigo) {
        for (Categoria categoria : getCategorias()) {
            if (categoria.getID().equals(codigo)) {
                return categoria;
            }
        }
        return null;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        ReadXMLFile xmlPersistent = new ReadXMLFile();
        try {
            xmlPersistent.guardar(this.categorias);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Usuario
    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(List<Usuario> users) {
        this.usuarios = users;
    }

    //=================================================================================================================
    public Categoria crearCategoria(String idCate, String nomCate, String descCate) throws Exception {
        Categoria categoria = buscarCategoriaConID(idCate);
        if (categoria != null) {
            throw new Exception("La categoría con el ID " + idCate + " ya existe.");
        }
        categoria = new Categoria(idCate, nomCate, descCate);
        categorias.add(categoria);
        setCategorias(categorias);
        System.out.println("Categoria creada y guardada con exito");
        return categoria;
    }

    public Categoria agregarSubCategoria(Categoria categoria, String idSubCate, String nomSubCate, String descSubCate) throws Exception {
        SubCategoria subCategoria = categoria.getSubCategoriaById(idSubCate);
        if (subCategoria != null) {
            throw new Exception("La subcategoria con el ID " + idSubCate + " ya existe.");
        }
        subCategoria = new SubCategoria(idSubCate, nomSubCate, descSubCate);
        categoria.getSubCategoria().add(subCategoria);
        setCategorias(categorias);
        System.out.println("Subcategoria agregada y guardada con exito");
        return categoria;
    }

    public Categoria agregarArticulo(Categoria categoria, String idSubCate, String idArt, String marcaArt, String nomArt, String descArt) throws Exception {
        SubCategoria subCategoria = categoria.getSubCategoriaById(idSubCate);
        if (subCategoria == null) {
            throw new Exception("La subcategoria con el ID " + idSubCate + " no existe.");
        }
        Articulo articulo = subCategoria.getArticuloById(idArt);
        if (articulo != null) {
            throw new Exception("El articulo con el ID " + idArt + " ya existe.");
        }
        articulo = new Articulo(idArt, marcaArt, nomArt, descArt);
        subCategoria.getListadoArticulos().add(articulo);
        setCategorias(categorias);
        System.out.println("Articulo agregado y guardado con exito");
        return categoria;
    }

    public Categoria agregarPresentacion(Categoria categoria, String idSubCate, String idArt, String idPres, String unidadPres, String cantidadPres) throws Exception {
        SubCategoria subCategoria = categoria.getSubCategoriaById(idSubCate);
        if (subCategoria == null) {
            throw new Exception("La subcategoria con el ID " + idSubCate + " no existe.");
        }
        Articulo articulo = subCategoria.getArticuloById(idArt);
        if (articulo == null) {
            throw new Exception("El articulo con el ID " + idArt + " no existe.");
        }
        Presentacion presentacion = articulo.getPresentacionById(idPres);
        if (presentacion != null) {
            throw new Exception("La presentacion con el ID " + idPres + " ya existe.");
        }
        presentacion = new Presentacion(idPres, unidadPres, cantidadPres);
        articulo.getPresentacion().add(presentacion);
        setCategorias(categorias);
        System.out.println("Presentacion agregada y guardada con exito");
        return categoria;
    }

    //ELIMINARES
    public void eliminarCategoria(String idCate) throws Exception {
        Categoria categoria = readCategoriaConCodigo(idCate);
        if (categoria == null) {
            throw new Exception("Categoria no encontrada con el ID " + idCate);
        }
        if (!categoria.getSubCategoria().isEmpty()) {
            throw new Exception("La categoria tiene subcategorías asociadas. No se puede eliminar.");
        }
        getCategorias().remove(categoria);
        setCategorias(getCategorias());
        System.out.println("Categoría eliminada con exito.");
    }

    public void eliminarSubCategoria(Categoria categoria, String idSubCate) throws Exception {
        SubCategoria subCategoria = categoria.getSubCategoriaById(idSubCate);
        if (subCategoria == null) {
            throw new Exception("Subcategoría no encontrada con el ID " + idSubCate);
        }
        if (!subCategoria.getListadoArticulos().isEmpty()) {
            throw new Exception("La subcategoría tiene artículos asociados. No se puede eliminar.");
        }
        categoria.getSubCategoria().remove(subCategoria);
        setCategorias(getCategorias());
        System.out.println("Subcategoría eliminada con éxito.");
    }

    public void eliminarArticulo(SubCategoria subCategoria, String idArt) throws Exception {
        Articulo articulo = subCategoria.getArticuloById(idArt);
        if (articulo == null) {
            throw new Exception("Artículo no encontrado con el ID " + idArt);
        }
        if (!articulo.getPresentacion().isEmpty()) {
            throw new Exception("El artículo tiene presentaciones asociadas. No se puede eliminar.");
        }
        subCategoria.getListadoArticulos().remove(articulo);
        setCategorias(getCategorias());
        System.out.println("Artículo eliminado con éxito.");
    }

    public void eliminarPresentacion(Articulo articulo, String idPres) throws Exception {
        Presentacion presentacion = articulo.getPresentacionById(idPres);
        if (presentacion == null) {
            throw new Exception("Presentación no encontrada con el ID " + idPres);
        }
        articulo.getPresentacion().remove(presentacion);
        setCategorias(getCategorias());
        System.out.println("Presentación eliminada con éxito.");
    }

    //EDITARES
    public Categoria editarCategoria(String idCate, String nuevoNombre, String nuevaDescripcion) throws Exception {
        Categoria categoria = buscarCategoriaConID(idCate);
        if (categoria == null) {
            throw new Exception("Categoria no encontrada con el ID " + idCate);
        }
        categoria.setNombre(nuevoNombre);
        categoria.setDescripcion(nuevaDescripcion);
        setCategorias(categorias);
        System.out.println("Categoria editada con éxito.");
        return categoria;
    }

    public SubCategoria editarSubCategoria(String idCate, String idSubCate, String nuevoNombre, String nuevaDescripcion) throws Exception {
        Categoria categoria = buscarCategoriaConID(idCate);
        if (categoria == null) {
            throw new Exception("Categoria no encontrada con el ID " + idCate);
        }
        SubCategoria subCategoria = categoria.getSubCategoriaById(idSubCate);
        if (subCategoria == null) {
            throw new Exception("Subcategoria no encontrada con el ID " + idSubCate);
        }
        subCategoria.setNombre(nuevoNombre);
        subCategoria.setDescripcion(nuevaDescripcion);
        setCategorias(categorias);
        System.out.println("Subcategoria editada con exito.");
        return subCategoria;
    }

    public Articulo editarArticulo(String idCate, String idSubCate, String idArt, String nuevaMarca, String nuevoNombre, String nuevaDescripcion) throws Exception {
        Categoria categoria = buscarCategoriaConID(idCate);
        if (categoria == null) {
            throw new Exception("Categoria no encontrada con el ID " + idCate);
        }
        SubCategoria subCategoria = categoria.getSubCategoriaById(idSubCate);
        if (subCategoria == null) {
            throw new Exception("Subcategoría no encontrada con el ID " + idSubCate);
        }
        Articulo articulo = subCategoria.getArticuloById(idArt);
        if (articulo == null) {
            throw new Exception("Artículo no encontrado con el ID " + idArt);
        }
        articulo.setMarca(nuevaMarca);
        articulo.setNombre(nuevoNombre);
        articulo.setDescripcion(nuevaDescripcion);
        System.out.println("Artículo editado con éxito.");
        setCategorias(categorias);
        return articulo;
    }

    public Presentacion editarPresentacion(String idCate, String idSubCate, String idArt, String idPres, String nuevaUnidad, String nuevaCantidad) throws Exception {
        Categoria categoria = buscarCategoriaConID(idCate);
        if (categoria == null) {
            throw new Exception("Categoria no encontrada con el ID " + idCate);
        }
        SubCategoria subCategoria = categoria.getSubCategoriaById(idSubCate);
        if (subCategoria == null) {
            throw new Exception("Subcategoría no encontrada con el ID " + idSubCate);
        }
        Articulo articulo = subCategoria.getArticuloById(idArt);
        if (articulo == null) {
            throw new Exception("Artículo no encontrado con el ID " + idArt);
        }
        Presentacion presentacion = articulo.getPresentacionById(idPres);
        if (presentacion == null) {
            throw new Exception("Presentación no encontrada con el ID " + idPres);
        }
        presentacion.setUnidad(nuevaUnidad);
        presentacion.setCantidad(nuevaCantidad);
        System.out.println("Presentación editada con éxito.");
        setCategorias(categorias);
        return presentacion;
    }

    //======================================================================================================================
    //GET
    public List<Categoria> getCategorias() {
        return this.categorias;
    }

    //CRUD CATEGORIA
    public void createCategoria(Categoria categoria) throws Exception {
        if (getCategorias().stream().anyMatch(cat -> cat.getID().equals(categoria.getID()))) {
            throw new Exception("El categoria ya existe");
        } else {
            getCategorias().add(categoria); //agrega la categoria a la lista
            setCategorias(getCategorias());// y la actualiza
        }
    }

    public Categoria readCategoria(Categoria categoria) throws Exception {
        Categoria c = getCategorias().stream().filter(cat -> cat.getNombre().equals(categoria.getNombre())).findFirst().orElse(null);
        if (c != null) return c;
        else throw new Exception("Categoria no existe");
    }

    private Categoria readCategoriaConNombre(String nombre) {
        for (Categoria categoria : getCategorias()) {
            if (categoria.getNombre().equals(nombre)) {
                return categoria;
            }
        }
        return null;
    }

    private Categoria buscarCategoriaConID(String id) {
        for (Categoria categoria : getCategorias()) {
            if (categoria.getID().equals(id)) {
                return categoria;
            }
        }
        return null;
    }

    private SubCategoria buscarSubCategoriaConID(String id) {
        for (Categoria categoria : getCategorias()) {
            for (SubCategoria subCategoria : categorias.get(0).getSubCategoria()) {
                if (subCategoria.getID().equals(id)) {
                    return subCategoria;
                }
            }
        }
        return null;
    }

    private Articulo buscarArticuloConID(String id) {
        for (Categoria categoria : getCategorias()) {
            for (SubCategoria subCategoria : categorias.get(0).getSubCategoria()) {
                for (Articulo articulo : subCategoria.getListadoArticulos()) {
                    if (articulo.getId().equals(id)) {
                        return articulo;
                    }
                }
            }
        }
        return null;
    }

    private Presentacion buscarPresentacionConID(String id) {
        for (Categoria categoria : getCategorias()) {
            for (SubCategoria subCategoria : categorias.get(0).getSubCategoria()) {
                for (Articulo articulo : subCategoria.getListadoArticulos()) {
                    for (Presentacion presentacion : articulo.getPresentacion()) {
                        if (presentacion.getId().equals(id)) {
                            return presentacion;
                        }
                    }
                }
            }
        }
        return null;
    }


    public void deleteSubCategoria(Categoria categoria, SubCategoria subCategoria) throws Exception {
        if (!subCategoria.getListadoArticulos().isEmpty()) {
            throw new Exception("La subcategoría tiene artículos asociados");
        } else {
            categoria.getSubCategoria().remove(subCategoria);
            setCategorias(getCategorias());
        }
    }

    public void deleteArticulo(SubCategoria subCategoria, Articulo articulo) throws Exception {
        if (!articulo.getPresentacion().isEmpty()) {
            throw new Exception("El artículo tiene presentaciones asociadas");
        } else {
            subCategoria.getListadoArticulos().remove(articulo);
            setCategorias(getCategorias());
        }
    }

    public void deletePresentacion(Articulo articulo, Presentacion presentacion) {
        articulo.getPresentacion().remove(presentacion);
        setCategorias(getCategorias());
    }

    //BUSCARES
    public List<Categoria> categoriasEncontradasConID(String i) {
        List<Categoria> categoriasFound = new ArrayList<>();
        String normalizedInput = i.trim().replaceAll("\\s+", "").toLowerCase();
        for (Categoria c : getCategorias()) {
            if (c.getID().replaceAll("\\s+", "").toLowerCase().contains(normalizedInput)) {
                categoriasFound.add(c);
            }
        }
        return categoriasFound;
    }
    public List<Categoria> categoriasEncontradasConNombre( String n) {
        List<Categoria> categoriasFound = new ArrayList<>();
        String normalizedInput = n.trim().replaceAll("\\s+", "").toLowerCase();
        for (Categoria c : getCategorias()) {
            if (c.getNombre().replaceAll("\\s+", "").toLowerCase().contains(normalizedInput)) {
                categoriasFound.add(c);
            }
        }
        return categoriasFound;
    }

    //--
    public List<SubCategoria> subCategoriasEncontradasConIDIdCat(String id, String i) {
        List<SubCategoria> subcategoriasFound = new ArrayList<>();
        String normalizedIDCategoria = id.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedIDSubcategoria = i.trim().replaceAll("\\s+", "").toLowerCase();
        for (Categoria c : getCategorias()) {
            if (c.getID().replaceAll("\\s+", "").toLowerCase().equals(normalizedIDCategoria)) {
                for (SubCategoria s : c.getSubCategoria()) {
                    if (s.getID().replaceAll("\\s+", "").toLowerCase().contains(normalizedIDSubcategoria)) {
                        subcategoriasFound.add(s);
                    }
                }
                break;
            }
        }
        return subcategoriasFound;
    }
    public List<SubCategoria> subCategoriasEncontradasConNombreIdCat(String idC,String n){
        List<SubCategoria> subcategoriasFound = new  ArrayList<>();
        String normalizedInput = n.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedIDcategoria = idC.trim().replaceAll("\\s+", "").toLowerCase();
        for (Categoria c : getCategorias()) {
            if (c.getID().replaceAll("\\s+", "").toLowerCase().equals(normalizedIDcategoria)) {
                for (SubCategoria s : c.getSubCategoria()) {
                    if (s.getNombre().replaceAll("\\s+", "").toLowerCase().contains(normalizedInput)) {
                        subcategoriasFound.add(s);
                    }
                }
                break;
            }
        }
        return subcategoriasFound;
    }
    //--
    public List<Articulo> articulosEncontradosConIDIDCIDSC(String idc, String idSc, String i) {
        List<Articulo> articulosFound = new ArrayList<>();
        String normalizedInput = i.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedInputCat = idc.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedInputSC = idSc.trim().replaceAll("\\s+", "").toLowerCase();

        for (Categoria c : getCategorias()) {
            if (c.getID().replaceAll("\\s+", "").toLowerCase().equals(normalizedInputCat)) {
                for (SubCategoria s : c.getSubCategoria()) {
                    if (s.getID().replaceAll("\\s+", "").toLowerCase().equals(normalizedInputSC)) {
                        for (Articulo a : s.getListadoArticulos()) {
                            if (a.getId().replaceAll("\\s+", "").toLowerCase().contains(normalizedInput)) {
                                articulosFound.add(a);
                            }
                        }
                    }
                }
            }
        }
        return articulosFound;
    }
    public List<Articulo> articulosEncontradosConNombreIDCIDSC(String idc, String idSc, String n) {
        List<Articulo> articulosFound = new ArrayList<>();
        String normalizedInput = n.trim().toLowerCase();
        String normalizedInputCat = idc.trim().toLowerCase();
        String normalizedInputSC = idSc.trim().toLowerCase();
        System.out.println("Búsqueda de artículo con nombre: " + normalizedInput);
        System.out.println("ID Categoría: " + normalizedInputCat);
        System.out.println("ID Subcategoría: " + normalizedInputSC);
        for (Categoria c : getCategorias()) {
            System.out.println("Comprobando categoría: " + c.getID().toLowerCase());
            if (c.getID().replaceAll("\\s+", "").toLowerCase().equals(normalizedInputCat)) {
                for (SubCategoria s : c.getSubCategoria()) {
                    System.out.println("Comprobando subcategoría: " + s.getID().toLowerCase());
                    if (s.getID().replaceAll("\\s+", "").toLowerCase().equals(normalizedInputSC)) {
                        for (Articulo a : s.getListadoArticulos()) {
                            System.out.println("Comprobando artículo: " + a.getNombre().toLowerCase());
                            if (a.getNombre().replaceAll("\\s+", "").toLowerCase().contains(normalizedInput)) {
                                articulosFound.add(a);
                            }
                        }
                    }
                }
            }
        }
        return articulosFound;
    }

    //--
    public List<Presentacion> presentacionesEncontradasConIDIDAIDCIDSC(String ida, String idc, String idSc, String i) {
        List<Presentacion> presentacionesFound = new ArrayList<>();
        String normalizedInput = i.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedInputArt = ida.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedInputCat = idc.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedInputSC = idSc.trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("id pres: "+normalizedInput);
        System.out.println("id articulo: "+normalizedInputArt);
        System.out.println("id cat: "+normalizedInputCat);
        System.out.println("id subcat: "+normalizedInputSC);

        for (Categoria c : getCategorias()) {
            if (c.getID().replaceAll("\\s+", "").toLowerCase().equals(normalizedInputCat)) {
                for (SubCategoria s : c.getSubCategoria()) {
                    if (s.getID().replaceAll("\\s+", "").toLowerCase().contains(normalizedInputSC)) {
                        for (Articulo a : s.getListadoArticulos()) {
                            if (a.getId().replaceAll("\\s+", "").toLowerCase().contains(normalizedInputArt)) {
                                for (Presentacion p : a.getPresentacion()) {
                                    if (p.getId().replaceAll("\\s+", "").toLowerCase().contains(normalizedInput)) {
                                        presentacionesFound.add(p);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return presentacionesFound;
    }
    public List<Presentacion> presentacionesEncontradasConUnidadIDAIDCIDSC(String ida,String idc, String idSc,String u){
        List<Presentacion> presentacionesFound = new ArrayList<>();
        String normalizedInput = u.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedInputArt = ida.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedInputCat = idc.trim().replaceAll("\\s+", "").toLowerCase();
        String normalizedInputSC = idSc.trim().replaceAll("\\s+", "").toLowerCase();
        System.out.println("nom pres: "+normalizedInput);
        System.out.println("id articulo: "+normalizedInputArt);
        System.out.println("id cat: "+normalizedInputCat);
        System.out.println("id subcat: "+normalizedInputSC);
        for (Categoria c : getCategorias()) {
            if (c.getID().replaceAll("\\s+", "").toLowerCase().equals(normalizedInputCat)) {
                for (SubCategoria s : c.getSubCategoria()) {
                    if (s.getID().replaceAll("\\s+", "").toLowerCase().contains(normalizedInputSC)) {
                        for (Articulo a : s.getListadoArticulos()) {
                            if (a.getId().replaceAll("\\s+", "").toLowerCase().contains(normalizedInputArt)) {
                                for (Presentacion p : a.getPresentacion()) {
                                    if (p.getUnidad().replaceAll("\\s+", "").toLowerCase().contains(normalizedInput)) {
                                        presentacionesFound.add(p);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return presentacionesFound;
    }

}
