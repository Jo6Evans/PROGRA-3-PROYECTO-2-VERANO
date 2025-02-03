package SGIF.SGIFCliente.logic;

import SGIF.SGIFCliente.Presentation.Controller.Controller;
import SGIF.SGIFProtocol.IService;
import SGIF.SGIFProtocol.Protocol;
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


///El service tambien implementa el login de IService y maneja el socket porque es la forma
/// en que se comunica el frontend con el backend entonces el hace que el se conecta al server
/// o se desconecte si son problemas de conexion probablemente aqui este el problema



public class ServiceProxy implements IService {
    private static IService theInstance;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Controller controller;
    private boolean continuar = true;

    // Lista de pedidos
    private List<String> pedidos = new ArrayList<>();

    public static IService instance() {
        if (theInstance == null) {
            theInstance = new ServiceProxy();
        }
        return theInstance;
    }

    public ServiceProxy() {
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    private void connect() throws Exception {
        System.out.println("Intentando conectar al servidor...");
        socket = new Socket(Protocol.SERVER, Protocol.PORT);
        System.out.println("Conexión establecida.");
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }

    private void disconnect() throws Exception {
        socket.shutdownOutput();
        socket.close();
    }
    public boolean Login(String nombre, String password) throws Exception {
        connect();
        try {
            out.writeInt(Protocol.LOGIN); // Enviar tipo de solicitud (LOGIN)
            out.writeUTF(nombre); // Enviar nombre de usuario
            out.writeUTF(password); // Enviar contraseña
            out.flush();
            System.out.println("antes del readint");
            int response = in.readInt();
            System.out.println(response);
            if (response == Protocol.NO_ERROR) {
                boolean loginSuccess = in.readBoolean(); // Recibir el booleano desde el servidor
                this.start();
                return loginSuccess;
            } else {
                disconnect();
                throw new Exception("Error en el login: Credenciales incorrectas.");
            }
        } catch (IOException ex) {
            disconnect();
            throw new Exception("Error de conexión con el servidor: " + ex.getMessage());
        }
    }

   /* public Usuario Login(String nombre, String password) throws Exception {
        connect();
        try {
            out.writeInt(Protocol.LOGIN); // Enviar tipo de solicitud (LOGIN)
            out.writeUTF(nombre); // Enviar nombre de usuario
            out.writeUTF(password); // Enviar contraseña
            out.flush();

            int response = in.readInt();
            if (response == Protocol.NO_ERROR) {
                Usuario usuario = (Usuario) in.readObject(); // Recibir el objeto Usuario desde el servidor
                this.start();
                return usuario;
            } else {
                disconnect();
                throw new Exception("Error en el login: Credenciales incorrectas.");
            }
        } catch (IOException | ClassNotFoundException ex) {
            disconnect();
            throw new Exception("Error de conexión con el servidor: " + ex.getMessage());
        }
    }*/

    public void logout(String nombre) throws Exception {
        out.writeInt(Protocol.LOGOUT);
        out.writeUTF(nombre);
        out.flush();
        this.stop();
        this.disconnect();
    }

    // Metodo para agregar pedidos a la lista de pedidos
    public void postPedido(String pedido) {
        try {
            out.writeInt(Protocol.POST);
            out.writeUTF(pedido); // Enviar pedido
            out.flush();
            pedidos.add(pedido); // Guardar el pedido localmente
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // FUNCIONES DE ESCUCHA
    public void start() {
        System.out.println("Escuchando peticiones del servidor...");
        Thread t = new Thread(new Runnable() {
            public void run() {
                listen();
            }
        });
        continuar = true;
        t.start();
    }

    public void stop() {
        continuar = false;
    }

    public void listen() {
        int method;
        while (continuar) {
            try {
                method = in.readInt();
                System.out.println("Recibido del servidor: " + method);
                switch (method) {
                    case Protocol.DELIVER:
                        try {
                            String pedidoRecibido = in.readUTF();
                            deliver(pedidoRecibido);
                        } catch (Exception e) {}
                        break;
                }
                out.flush();
            } catch (IOException ex) {
                continuar = false;
            }
        }
    }

    private void deliver(final String pedido) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                controller.deliverPedido(pedido);  // Pasar el pedido al controlador
            }
        });
    }
}