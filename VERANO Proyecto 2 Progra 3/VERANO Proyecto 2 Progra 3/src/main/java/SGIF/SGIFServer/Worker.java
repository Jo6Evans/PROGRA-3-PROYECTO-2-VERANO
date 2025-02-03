package SGIF.SGIFServer;

import SGIF.SGIFProtocol.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.List;

///El worker es lo que hace que se transporte los obj digamos, en este caso maneja obj usuarios
/// entonces va a manejar los login, logout y deliver que es mandar o enviar la lista de pedidos


public class Worker {
    private Server srv;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private IService service;
    private Usuario usuario; //esto hay problema porque no se inicializa entonces nunca va a pasar de la ventana del login
    private boolean continuar;

    /* public Worker(Server srv, ObjectInputStream in, ObjectOutputStream out, Usuario Usuario, IService service) {
         this.srv = srv;
         this.in = in;
         this.out = out;
         this.Usuario = Usuario;
         this.service = service;
     }*/
    public Worker(Server server, ObjectInputStream in, ObjectOutputStream out, IService service) {
        this.srv = server;
        this.in = in;
        this.out = out;
        this.service = service;
    }
    public void start() { //esto y todos los que digan "usuario." da error porque nunca se inicializa el usuario y se cae
        System.out.println("Worker iniciado para " + usuario.getNombre());
        continuar = true;
        new Thread(this::listen).start();
    }

    public void stop() {
        continuar = false;
        System.out.println("Conexión cerrada para " + usuario.getNombre());
    }

    public void listen() {
        while (continuar) {
            try {
                int method = in.readInt();
                System.out.println("Operación recibida: " + method);

                switch (method) {
                    case Protocol.LOGIN:
                        handleLogin();
                        break;
                    case Protocol.LOGOUT:
                        handleLogout();
                        break;
                    case Protocol.POST:
                        handlePost();
                        break;
                }
                out.flush();
            } catch (IOException ex) {
                System.out.println("Error en la conexión: " + ex.getMessage());
                continuar = false;
            }
        }
    }
    private void handleLogin() {
        try {
            String nombre = in.readUTF();
            String password = in.readUTF();
            boolean loginSuccess = service.Login(nombre, password);

            out.writeInt(Protocol.NO_ERROR);
            out.writeBoolean(loginSuccess);
        } catch (Exception e) {
            System.out.println("Error de autenticación: " + e.getMessage());
            try {
                out.writeInt(Protocol.ERROR_LOGIN);
            } catch (IOException ignored) {}
        }
    }
    /*private void handleLogin() {
        try {
            String nombre = in.readUTF();
            String password = in.readUTF();
            Usuario = service.Login(nombre, password);

            out.writeInt(Protocol.NO_ERROR);
            out.writeObject(Usuario);
        } catch (Exception e) {
            System.out.println("Error de autenticación: " + e.getMessage());
            try {
                out.writeInt(Protocol.ERROR_LOGIN);
            } catch (IOException ignored) {}
        }
    }*/

    private void handleLogout() {
        srv.remove(usuario);
        stop();
    }

    private void handlePost() {
        try {
            List<String> messages = (List<String>) in.readObject();
            System.out.println(usuario.getNombre() + " envió mensajes: " + messages);
            srv.deliver(messages);
        } catch (Exception e) {
            System.out.println("Error al manejar POST: " + e.getMessage());
        }
    }

    public void deliver(List<String> messages) {
        try {
            out.writeInt(Protocol.DELIVER);
            out.writeObject(messages);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error al entregar mensajes: " + e.getMessage());
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
    /*private Server srv;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private IService service;
    private Usuario Usuario;
    private boolean continuar;

    public Worker(Server srv, ObjectInputStream in, ObjectOutputStream out, Usuario Usuario, IService service) {
        this.srv = srv;
        this.in = in;
        this.out = out;
        this.Usuario = Usuario;
        this.service = service;
    }

    public void start() {
        try {
            System.out.println("Worker atendiendo peticiones...");
            Thread t = new Thread(this::listen);
            continuar = true;
            t.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        continuar = false;
        System.out.println("Conexión cerrada...");
    }

    public void listen() {
        int method;
        while (continuar) {
            try {
                method = in.readInt();
                System.out.println("Operación recibida: " + method);
                switch (method) {
                    case Protocol.LOGIN:
                        handleLogin();
                        break;
                    case Protocol.LOGOUT:
                        handleLogout();
                        break;
                    case Protocol.POST:
                        handlePost();
                        break;
                }
                out.flush();
            } catch (IOException ex) {
                System.out.println("Error en la conexión: " + ex.getMessage());
                continuar = false;
            }
        }
    }

    private void handleLogin() {
        try {
            String nombre = in.readUTF();  // Leer nombre enviado por el cliente
            String password = in.readUTF(); // Leer contraseña enviada por el cliente

            Usuario usuarioAutenticado = service.Login(nombre, password); // Validar credenciales

            out.writeInt(Protocol.NO_ERROR); // Enviar código de éxito
            out.writeObject(usuarioAutenticado); // Enviar usuario autenticado
        } catch (Exception e) {
            System.out.println("Error de autenticación: " + e.getMessage());
            try {
                out.writeInt(Protocol.ERROR_LOGIN); // Enviar código de error
            } catch (IOException ignored) {}
        }
    }

    private void handleLogout() {
        try {
            srv.remove(Usuario);
            stop();
        } catch (Exception e) {
            System.out.println("Error al cerrar sesión: " + e.getMessage());
        }
    }

    private void handlePost() {
        try {
            Message message = (Message) in.readObject();
            message.setSender(Usuario);
            srv.deliver(message);
            System.out.println(Usuario.getNombre() + ": " + message.getMessage());
        } catch (Exception e) {
            System.out.println("Error al manejar mensaje POST: " + e.getMessage());
        }
    }

    public void deliver(Message message) {
        try {
            out.writeInt(Protocol.DELIVER);
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error al entregar mensaje: " + e.getMessage());
        }
    }
}*/