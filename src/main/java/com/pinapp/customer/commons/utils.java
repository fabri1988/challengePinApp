package com.pinapp.customer.commons;

import java.time.LocalDate;
import java.time.Period;

public class utils {

    public static int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula.");
        }

        LocalDate fechaActual = LocalDate.now();

        if (fechaNacimiento.isAfter(fechaActual)) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser en el futuro.");
        }

        return Period.between(fechaNacimiento, fechaActual).getYears();
    }
}
