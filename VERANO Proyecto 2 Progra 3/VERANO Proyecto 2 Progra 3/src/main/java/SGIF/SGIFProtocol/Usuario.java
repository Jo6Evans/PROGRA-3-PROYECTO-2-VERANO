package SGIF.SGIFProtocol;

import java.io.Serializable;

public class Usuario implements Serializable { //preguntar si puede ser quemado sin tener que hacer un xml exclusivo para
    private String nombre;
    private String contrasena;
    private boolean estaActivo; //bloqueado o activo
    public Usuario(String nombre, String contrasena, boolean estaActivo) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.estaActivo = estaActivo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public boolean estaActivo() {
        return estaActivo;
    }
    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }
}
