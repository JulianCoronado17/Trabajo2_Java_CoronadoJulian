package com.mycompany.ejercicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaReserva {

    private final List<Reserva> reservas = new ArrayList<>();

    public void realizarReserva(Vuelo vuelo, Asiento asiento, String pasajero) {
        Reserva reserva = new Reserva(vuelo, asiento, pasajero);
        reservas.add(reserva);
        System.out.println("Reserva realizada: " + reserva);
    }

    public void cancelarReserva(String pasajero) {
        reservas.removeIf(r -> r.pasajero.equals(pasajero));
        System.out.println("Reserva cancelada para el pasajero: " + pasajero);
    }

    public void mostrarReservas() {
        reservas.forEach(System.out::println);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaReserva sistema = new SistemaReserva();

        Vuelo vueloNacional = new VueloNacional("VN123", "Lima", "Cusco", 300.00);
        Vuelo vueloInternacional = new VueloInternacional("VI456", "Lima", "Buenos Aires", 500.00);

        int opcion;
        do {
            System.out.println("\\n--- Sistema de Reservas ---");
            System.out.println("1. Realizar reserva");
            System.out.println("2. Cancelar reserva");
            System.out.println("3. Mostrar reservas");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del pasajero: ");
                    String pasajero = scanner.nextLine();
                    System.out.println("Seleccione el vuelo: 1) Nacional 2) Internacional");
                    int tipoVuelo = scanner.nextInt();
                    Vuelo vueloSeleccionado = (tipoVuelo == 1) ? vueloNacional : vueloInternacional;

                    System.out.println("Seleccione clase de asiento: 1) Económica 2) Ejecutiva");
                    int tipoAsiento = scanner.nextInt();
                    Asiento asientoSeleccionado = (tipoAsiento == 1) ? new AsientoEconomico() : new AsientoEjecutivo();

                    sistema.realizarReserva(vueloSeleccionado, asientoSeleccionado, pasajero);
                    break;

                case 2:
                    System.out.print("Nombre del pasajero para cancelar reserva: ");
                    String pasajeroCancelar = scanner.nextLine();
                    sistema.cancelarReserva(pasajeroCancelar);
                    break;

                case 3:
                    sistema.mostrarReservas();
                    break;

                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);

        scanner.close();
    }

}

abstract class Vuelo {

    protected String numeroVuelo;
    protected String origen;
    protected String destino;
    protected double precioBase;

    public Vuelo(String numeroVuelo, String origen, String destino, double precioBase) {
        this.numeroVuelo = numeroVuelo;
        this.origen = origen;
        this.destino = destino;
        this.precioBase = precioBase;
    }

    public abstract double calcularPrecio();

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

}

class VueloNacional extends Vuelo {

    public VueloNacional(String numeroVuelo, String origen, String destino, double precioBase) {
        super(numeroVuelo, origen, destino, precioBase);
    }

    @Override
    public double calcularPrecio() {
        return precioBase;
    }

}

class VueloInternacional extends Vuelo {

    private final double tarifaInternacional = 50.00;

    public VueloInternacional(String numeroVuelo, String origen, String destino, double precioBase) {
        super(numeroVuelo, origen, destino, precioBase);
    }

    @Override
    public double calcularPrecio() {
        return precioBase + tarifaInternacional;
    }

}

abstract class Asiento {

    public abstract double getPrecio();
}

class AsientoEconomico extends Asiento {

    @Override
    public double getPrecio() {
        return 100.00;
    }
}

class AsientoEjecutivo extends Asiento {

    @Override
    public double getPrecio() {
        return 200.00;
    }
}

class Reserva {

    private final Vuelo vuelo;
    private final Asiento asiento;
    final String pasajero;
    private final double costoTotal;

    public Reserva(Vuelo vuelo, Asiento asiento, String pasajero) {
        this.vuelo = vuelo;
        this.asiento = asiento;
        this.pasajero = pasajero;
        this.costoTotal = calcularCostoTotal();
    }

    private double calcularCostoTotal() {
        return vuelo.calcularPrecio() + asiento.getPrecio();
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    @Override
    public String toString() {
        return "Reserva{"
                + "vuelo=" + vuelo.getNumeroVuelo()
                + ", pasajero=" + pasajero + '\\'
                + ", costoTotal=" + costoTotal
                + '}';
    }

}
