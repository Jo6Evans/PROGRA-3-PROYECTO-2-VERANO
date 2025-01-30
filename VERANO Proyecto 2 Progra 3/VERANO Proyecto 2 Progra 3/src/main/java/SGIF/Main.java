package SGIF;

import SGIF.SGIFCliente.Presentation.Controller.Controller;
import SGIF.SGIFCliente.Presentation.View.InventarioView;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
        InventarioView view = new InventarioView();
        Controller controller = new Controller();
        controller.startApplication();
    }
}