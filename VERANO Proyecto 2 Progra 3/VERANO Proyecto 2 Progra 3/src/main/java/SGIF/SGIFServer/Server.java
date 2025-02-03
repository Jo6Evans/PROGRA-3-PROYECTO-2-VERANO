package SGIF.SGIFServer;

import java.net.ServerSocket;
import java.util.List;

import SGIF.SGIFProtocol.IService;
import SGIF.SGIFProtocol.Protocol;
import SGIF.SGIFProtocol.Usuario;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
///El server lo que hace es manejar el worker, entonces hace que corra todo,
/// va a manejar el login y el deliver que es donde se manda la lista de pedidos


public class Server {

    private ServerSocket srv;
    private List<Worker> workers;

    public Server() {
        try {
            srv = new ServerSocket(Protocol.PORT);
            workers = Collections.synchronizedList(new ArrayList<>());
            System.out.println("Servidor iniciado en el puerto: " + Protocol.PORT);
        } catch (IOException ex) {
            System.out.println("Error al iniciar el servidor: " + ex.getMessage());
        }
    }

    public void run() {
        IService service = new Service();
        boolean continuar = true;
        while (continuar) {
            try {
                Socket socket = srv.accept();
                System.out.println("Cliente conectado desde " + socket.getInetAddress());

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                //String nombre = in.readUTF();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                //String password = in.readUTF();

                // Verificar el login (ahora devuelve un booleano)
                boolean loginSuccess = this.Login(in, out, service);

                if (loginSuccess) {
                    // Si el login es exitoso, se inicia un Worker para este cliente
                    Worker worker = new Worker(this, in, out, service); // Eliminamos el parámetro "user"
                    workers.add(worker);
                    worker.start();
                } else {
                    // Si el login falla, cerrar la conexión
                    System.out.println("Login fallido. Cerrando conexión con el cliente.");
                    in.close();
                    out.close();
                    socket.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Error en la autenticación: " + e.getMessage());
            }
        }
    }
    /*
        public void run() {
            IService service = new Service();
            boolean continuar = true;
            while (continuar) {
                try {
                    Socket socket = srv.accept();
                    System.out.println("Cliente conectado desde " + socket.getInetAddress());

                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                    // Se obtiene el usuario autenticado
                    Usuario user = this.Login(in, out, service);

                    // Se inicia un Worker para este usuario
                    Worker worker = new Worker(this, in, out, user, service);
                    workers.add(worker);
                    worker.start();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("Error en la autenticación: " + e.getMessage());
                }
            }
        }

        */
    private boolean Login(ObjectInputStream in, ObjectOutputStream out, IService service)
            throws IOException, ClassNotFoundException, Exception {
        int method = in.readInt();
        if (method != Protocol.LOGIN) throw new Exception("Debe iniciar sesión primero");

        String nombre = in.readUTF();  // Leer nombre
        String password = in.readUTF(); // Leer contraseña

        boolean loginSuccess = service.Login(nombre, password);
        out.writeInt(Protocol.NO_ERROR);
        out.writeBoolean(loginSuccess);
        out.flush();
        return loginSuccess;
    }
    /*private Usuario Login(ObjectInputStream in, ObjectOutputStream out, IService service)
            throws IOException, ClassNotFoundException, Exception {
        int method = in.readInt();
        if (method != Protocol.LOGIN) throw new Exception("Debe iniciar sesión primero");

        String nombre = in.readUTF();  // Leer nombre
        String password = in.readUTF(); // Leer contraseña

        Usuario user = service.Login(nombre, password);
        out.writeInt(Protocol.NO_ERROR);
        out.writeObject(user);
        out.flush();
        return user;
    }*/

    public void deliver(List<String> messages) {
        for (Worker wk : workers) {
            wk.deliver(messages);
        }
    }

    public void remove(Usuario user) {
        workers.removeIf(wk -> wk.getUsuario().equals(user));
        System.out.println("Usuario eliminado. Usuarios conectados: " + workers.size());
    }
}
    /*private ServerSocket serverSocket;
    private List<Worker> workers;

    public Server() {
        try {
            serverSocket = new ServerSocket(Protocol.PORT);
            workers = Collections.synchronizedList(new ArrayList<Worker>());
            System.out.println("Servidor iniciado en el puerto " + Protocol.PORT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        boolean continuar = true;
        while (continuar) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + socket.getInetAddress());

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                // Se obtiene el usuario autenticado
                Usuario usuarioAutenticado = login(in, out);

                // Se inicia un Worker para este usuario
                Worker worker = new Worker(socket, in, out, usuarioAutenticado);
                workers.add(worker);
                worker.start();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Error en la autenticación: " + e.getMessage());
            }
        }
    }

    private Usuario login(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException, Exception {
        int method = in.readInt();
        if (method != Protocol.LOGIN) throw new Exception("El cliente debe iniciar sesión primero.");

        // Leer los datos de login
        String nombre = in.readUTF();
        String password = in.readUTF();

        // Buscar usuario en la base de datos (XML o lista en memoria)
        Usuario usuarioEncontrado = buscarUsuario(nombre, password);
        if (usuarioEncontrado == null || !usuarioEncontrado.estaActivo()) {
            out.writeInt(Protocol.ERROR_LOGIN);
            out.flush();
            throw new Exception("Credenciales incorrectas o usuario bloqueado.");
        }

        // Enviar usuario autenticado
        out.writeInt(Protocol.NO_ERROR);
        out.writeObject(usuarioEncontrado);
        out.flush();

        return usuarioEncontrado;
    }

    // Metodo para buscar usuario en XML o lista de usuarios (simulado aquí)
    private Usuario buscarUsuario(String nombre, String password) {
        for (Usuario u : obtenerListaUsuarios()) { // Puedes cambiar esto por lectura desde XML
            if (u.getNombre().equals(nombre) && u.getContrasena().equals(password)) {
                return u;
            }
        }
        return null;
    }

    private List<Usuario> obtenerListaUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("admin", "1234", true));
        usuarios.add(new Usuario("usuario1", "pass1", true));
        usuarios.add(new Usuario("bloqueado", "pass2", false));
        return usuarios;
    }
}*/