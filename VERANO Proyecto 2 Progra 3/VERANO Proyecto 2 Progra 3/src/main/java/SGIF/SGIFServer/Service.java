package SGIF.SGIFServer;
import SGIF.SGIFProtocol.IService;
import SGIF.SGIFProtocol.Usuario;
import SGIF.SGIFServer.Data.Inventario;

import java.util.List;

///El service solo implementa el metodo de login es bool para asi manejar si entro o no entro

public class Service  implements IService {
    private Inventario data;
    private List<Usuario> usuario;


    public Service() {
        data =  new Inventario();
    }
    public boolean Login(String nombre, String password) throws Exception {
        for (Usuario u : data.getUsuarios()) {
            if (u.getNombre().equals(nombre) && u.getContrasena().equals(password)) {
                if (!u.estaActivo()) {
                    throw new Exception("El usuario está bloqueado, no tiene acceso.");
                }
                System.out.println("El usuario se autenticó correctamente.");
                return true;
            }
        }
        throw new Exception("Usuario no encontrado.");
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