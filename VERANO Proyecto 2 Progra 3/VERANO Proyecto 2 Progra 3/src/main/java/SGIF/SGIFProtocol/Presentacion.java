package SGIF.SGIFProtocol;

public class Presentacion {
    String id;
    String unidad;
    String cantidad;

    public Presentacion(String id, String unidad, String cantidad) {
        this.id = id;
        this.unidad = unidad;
        this.cantidad = cantidad;
    }
    public String getId() {
        return id;
    }

    public String getUnidad() {
        return unidad;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
    @Override
    public String toString() {
        return "Presentacion: " + id + " Unidad: " + unidad + " Cantidad: " + cantidad;
    }
}
