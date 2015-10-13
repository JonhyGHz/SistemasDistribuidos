package envioinformacionsockets;

import java.io.Serializable;

public class Alumno implements Serializable{
    
    //Declaracion de variables
    private String nombre;
    private String apellidoMaterno;
    private String apellidoPaterno;
    private String numeroControl;
    private String materia;

    //Constructor con parametros
    public Alumno(String nombre, String apellidoMaterno, String apellidoPaterno, String numeroControl, String materia) {
        this.nombre = nombre;
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.numeroControl = numeroControl;
        this.materia = materia;
    }

    //METODOS GET Y SET
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }


    @Override
    public String toString() {
        return "Alumno{" + "nombre=" + nombre + ", apellidoMaterno=" + apellidoMaterno + ", apellidoPaterno=" + apellidoPaterno + ", numeroControl=" + numeroControl + ", materia=" + materia + '}';
    }
}
