package SGIF;

import SGIF.Presentation.Controller.Controller;
import SGIF.Presentation.Model.Model;
import SGIF.Presentation.View.InventarioView;
import SGIF.data.ReadXMLFile;
import SGIF.logic.Categoria;
import SGIF.logic.Presentacion;
import SGIF.logic.SubCategoria;
import SGIF.logic.Articulo;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
        InventarioView view = new InventarioView();
        Controller controller = new Controller();
        controller.startApplication();
    }
}