package sv.cv181237.parcial02_cv181237;

public class Persona {
    private String nombre;
    private String apellido;
    private String carnet;
    private String telefono;
    private String edad;
    private String proveedor;

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }



    public Persona(String nombre, String apellido, String carnet, String telefono, String edad, String proveedor) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.carnet = carnet;
        this.telefono = telefono;
        this.edad = edad;
        this.proveedor = proveedor;
    }

    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }
}
