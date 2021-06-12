package com.ams.ei1027espaciosnaturales.model;

public class Ciudadano {
    private String dni; // Identificador
    private String nombre;
    private String apellidos;
    private int edad;
    private String calle;
    private int numero;
    private int codigoPostal;
    private String poblacion;
    private String telefono;
    private String email;
    private String usuario;
    private String password; // Pin de acceso
    private static final int MIN_RANGE = 1000;
    private static final int MAX_RANGE = 9999;

    public Ciudadano() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni.toUpperCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario.toUpperCase();
    }

    public void generateRandomPin(){
        int nuevoPin = (int) Math.floor(Math.random() * (MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE);
        setPassword(Integer.toString(nuevoPin));
    }

    public void createCodigo(){
        String nuevoCodigo = this.nombre.substring(0, 2)
                .concat(this.apellidos.substring(0, 2))
                .concat(this.dni.substring(6, 9));
        setUsuario(nuevoCodigo);
    }

    @Override
    public String toString() {
        return "Ciudadano{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad=" + edad +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", codigoPostal=" + codigoPostal +
                ", poblacion='" + poblacion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", usuario='" + usuario + '\'' +
                ", password=" + password +
                '}';
    }
}
