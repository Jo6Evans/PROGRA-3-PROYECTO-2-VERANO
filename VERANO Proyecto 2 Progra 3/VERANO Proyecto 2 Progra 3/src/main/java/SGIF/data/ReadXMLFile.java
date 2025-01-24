package SGIF.data;

import SGIF.logic.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Document builder factory
//Document builder
//Document
public class ReadXMLFile {

    public void guardar(List<Categoria> lists) throws ParserConfigurationException, TransformerException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("inventario");
            doc.appendChild(rootElement);
            Element CategoriaElement;
            String dato;
            for (int i = 0; i < lists.size(); i++) {
                CategoriaElement = doc.createElement("categoria");
                Categoria categoria = (Categoria) lists.get(i);
                CategoriaElement.setAttribute("ID", String.valueOf(categoria.getID()));

                rootElement.appendChild(CategoriaElement);

                dato = categoria.getNombre();
                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode(dato));
                CategoriaElement.appendChild(nombre);

                dato = categoria.getDescripcion();
                Element descripcion = doc.createElement("descripcion");
                descripcion.appendChild(doc.createTextNode(dato));
                CategoriaElement.appendChild(descripcion);


//===========================================SUBCATEGORIAS==============================================================
                Element listaSubCategoriasElement = doc.createElement("listasubcategoria");
                CategoriaElement.appendChild(listaSubCategoriasElement);
                for (SubCategoria subcategoria : categoria.getSubCategoria()) {
                    Element subcategoriaElement = doc.createElement("subcategoria");
                    subcategoriaElement.setAttribute("ID", String.valueOf(subcategoria.getID()));
                    listaSubCategoriasElement.appendChild(subcategoriaElement);

                    Element nombreSubcategoria = doc.createElement("nombre");
                    nombreSubcategoria.appendChild(doc.createTextNode(subcategoria.getNombre()));
                    subcategoriaElement.appendChild(nombreSubcategoria);

                    Element descripcionSubcategoria = doc.createElement("descripcion");
                    descripcionSubcategoria.appendChild(doc.createTextNode(subcategoria.getDescripcion()));
                    subcategoriaElement.appendChild(descripcionSubcategoria);

//=================================================ARTICULOS============================================================
                    Element listaArticulosElement = doc.createElement("listado");
                    subcategoriaElement.appendChild(listaArticulosElement);
                    for (Articulo articulo : subcategoria.getListadoArticulos()) {
                        Element articuloElement = doc.createElement("articulo");
                        articuloElement.setAttribute("ID", String.valueOf(articulo.getId()));

                        Element marcaArticulo = doc.createElement("marca");
                        marcaArticulo.appendChild(doc.createTextNode(articulo.getMarca()));
                        articuloElement.appendChild(marcaArticulo);

                        Element nombreArticulo = doc.createElement("nombre");
                        nombreArticulo.appendChild(doc.createTextNode(articulo.getNombre()));
                        articuloElement.appendChild(nombreArticulo);

                        Element descripcionArticulo = doc.createElement("Descripcion"); //cuidado con este, es el unico con mayuscula
                        descripcionArticulo.appendChild(doc.createTextNode(articulo.getDescripcion()));
                        articuloElement.appendChild(descripcionArticulo);

//===============================================PRESENTACIONES=========================================================
                        Element presentacionesElement = doc.createElement("presentaciones");
                        articuloElement.appendChild(presentacionesElement);
                        for (Presentacion presentacion : articulo.getPresentacion()) {
                            Element presentacionElement = doc.createElement("presentacion");
                            presentacionElement.setAttribute("id", String.valueOf(presentacion.getId())); //estos son los unicos con id en minuscula

                            Element unidad = doc.createElement("unidad");
                            unidad.appendChild(doc.createTextNode(presentacion.getUnidad()));
                            presentacionElement.appendChild(unidad);

                            Element cantidad = doc.createElement("cantidad");
                            cantidad.appendChild(doc.createTextNode(presentacion.getCantidad()));
                            presentacionElement.appendChild(cantidad);

                            presentacionesElement.appendChild(presentacionElement);
                        }
                        listaArticulosElement.appendChild(articuloElement);
                    }
                }
            }
            ///Se escribe el contenido del XML en un archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            // initialize StreamResult with File object to save to file
            StreamResult result = new StreamResult(new File("VERANO Proyecto 2 Progra 3/inventario.xml"));
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List cargarCategorias() {
        List<Categoria> l = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true); //quiero que verifique que el xml solo tenga cosas del xml y que la estructura este sana, un doc correcto
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder(); //documento donde voy a trabajar para el builder, intenta trasnform xml a memoria
            Document doc = db.parse(new File("VERANO Proyecto 2 Progra 3/inventario.xml")); //hace parseo de un doc, se le pasa la ruta del doc, parse para leer yy transformar, devuelve un documento listo
            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize(); //chequea si estructura correcta ttodo estandar de un xml, normalizar y leer correctamente
            // get <staff>
            // this loads all nodes called "categoria" into a list to be processed
//===========================Categoria=========================================================================
            NodeList list = doc.getElementsByTagName("categoria");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
//=====================================SubCategoria============================================================
                    NodeList sublist = element.getElementsByTagName("subcategoria");
                    List<SubCategoria> subcategoriaLista = new ArrayList<>();
                    for (int temp2 = 0; temp2 < sublist.getLength(); temp2++) {
                        Node subNode = sublist.item(temp2);
                        if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element element2 = (Element) subNode;
                            NodeList articuloList = element2.getElementsByTagName("articulo");
                            List<Articulo> articulos = new ArrayList<>();
                            for (int temp3 = 0; temp3 < articuloList.getLength(); temp3++) {
                                Node articuloNode = articuloList.item(temp3);
                                if (articuloNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element element3 = (Element) articuloNode;
                                    NodeList presentacionList = element3.getElementsByTagName("presentacion");
                                    List<Presentacion> presentaciones = new ArrayList<>();
                                    for (int temp4 = 0; temp4 < presentacionList.getLength(); temp4++) {
                                        Node presentacionNode = presentacionList.item(temp4);
                                        if (presentacionNode.getNodeType() == Node.ELEMENT_NODE) {
                                            Element element4 = (Element) presentacionNode;
                                            Presentacion presentacion = new Presentacion(element4.getAttribute("id"),
                                                    element4.getElementsByTagName("unidad").item(0).getTextContent(),
                                                    element4.getElementsByTagName("cantidad").item(0).getTextContent());
                                            presentaciones.add(presentacion);
                                        }
                                    }
                                    Articulo articulo = new Articulo(element3.getAttribute("ID"),
                                            element3.getElementsByTagName("marca").item(0).getTextContent(),
                                            element3.getElementsByTagName("nombre").item(0).getTextContent(),
                                            element3.getElementsByTagName("Descripcion").item(0).getTextContent(), presentaciones);
                                    articulos.add(articulo);
                                }
                            }
                            SubCategoria subCategoria = new SubCategoria(element2.getAttribute("ID"),
                                    element2.getElementsByTagName("nombre").item(0).getTextContent(),
                                    element2.getElementsByTagName("descripcion").item(0).getTextContent(),
                                    articulos);
                            subcategoriaLista.add(subCategoria);
                        }
                    }
                    Categoria categoria = new Categoria(element.getAttribute("ID"),
                            element.getElementsByTagName("nombre").item(0).getTextContent(),
                            element.getElementsByTagName("descripcion").item(0).getTextContent(),
                            subcategoriaLista);
                    l.add(categoria);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return l;
    }

    public List<Unidad> cargarUnidades() {
        List<Unidad> unidades = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File("VERANO Proyecto 2 Progra 3/VERANO Proyecto 2 Progra 3/unidades.xml"));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("unidad");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elementoUnidad = (Element) node;
                    String nombre = elementoUnidad.getTextContent().trim();

                    Unidad unidad = new Unidad(nombre);
                    unidades.add(unidad);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return unidades;
    }

}