package SGIF.logic;

import java.util.Objects;

public class Unidad {

    private String unidad;

    public Unidad(String unidad) {
        this.unidad = unidad;
    }


    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @Override
    public String toString() {
        return unidad;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Unidad unidad = (Unidad) obj;
        return Objects.equals(this.unidad, unidad.unidad);
    }
}
