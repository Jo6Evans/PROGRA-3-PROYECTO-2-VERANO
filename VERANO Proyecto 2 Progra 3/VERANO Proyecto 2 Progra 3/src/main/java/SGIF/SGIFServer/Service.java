package SGIF.SGIFServer;
import SGIF.SGIFProtocol.IService;
import SGIF.SGIFProtocol.Usuario;
import SGIF.SGIFServer.Data.Inventario;

import java.util.List;

///El service solo implementa el metodo de login es bool para asi manejar si entro o no entro

public class Service  implements IService {
    private Inventario data;

    //el data hay que inicializarlo cargando la lista de usuarios por eso tampoco pasaba nada
    public Service() {
        data =  new Inventario();
        data.LoadXML();
    }
    //esto si sirve verificando que ocurre, hay que ocntrolar los falsos y trues en el controller y view para mensajes de info o error y asi
    public boolean Login(String nombre, String password){
        for (Usuario u : data.getUsuarios()) {
            if (u.getNombre().equals(nombre) && u.getContrasena().equals(password)) {
                if (!u.estaActivo()) {
                    System.out.println("El usuario está bloqueado, no tiene acceso.");
                    return false;
                }
                System.out.println("El usuario se autenticó correctamente.");
                return true;
            }
        }
        System.out.println("Usuario no encontrado.");
        return false;
    }

    /*public Usuario Login(String nombre, String password) throws Exception {
        for (Usuario u : data.getUsuarios()) {
            if (u.getNombre().equals(nombre) && u.getContrasena().equals(password)) {
                if (!u.estaActivo()) {
                    throw new Exception("El usuario está bloqueado, no tiene acceso.");
                }
                System.out.println("El usuario se autenticó correctamente.");
                return u;
            }
        }
        throw new Exception("Usuario no encontrado.");
    }*/
}