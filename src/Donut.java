public class Donut {
    public static void main(String[] args) throws InterruptedException{
        // Constantes:
        final int RADIO_SUPERIOR = 2;
        final int RADIO_INFERIOR = 1;

        final double PASO_THETA = 0.02;
        final double PASO_PSI = 0.02;
        final double DISTANCIA_DONUT = 15;

        double rotacionX = 0;
        char[][] pantalla = new char[24][80];

        while (true) {
            for (int y = 0; y < pantalla.length; y++) {
                for (int x = 0; x < pantalla[0].length; x++) {
                    pantalla[y][x] = ' ';
                }
            }

            // Dibujar el donut
            for (double theta = 0; theta < 2 * Math.PI; theta += PASO_THETA) {
                for (double psi = 0; psi < 2 * Math.PI; psi += PASO_PSI) {
                    double x = (RADIO_SUPERIOR + RADIO_INFERIOR * cos(theta)) * cos(psi);
                    double y = (RADIO_SUPERIOR + RADIO_INFERIOR * cos(theta)) * sin(psi);
                    double z = RADIO_INFERIOR * sin(theta);

                    double yRotada = y * cos(rotacionX) - z * sin(rotacionX);
                    double zRotada = z * cos(rotacionX) + y * sin(rotacionX);

                    // Proyección y manejo de valores pequeños de zRotada
                    double factor = DISTANCIA_DONUT / Math.max(Math.abs(zRotada), 0.1);
                    double[] pantallaCordenadas = {x * factor, yRotada * factor};

                    // Escalar las coordenadas de la pantalla
                    double escalaX = (pantalla[0].length / 6.0);
                    double escalaY = (pantalla.length / 6.0);

                    int xPantalla = (int) (escalaX + pantallaCordenadas[0]);
                    int yPantalla = (int) (escalaY - pantallaCordenadas[1]);

                    // Asegurarse de que las coordenadas estén dentro de los límites
                    if (yPantalla >= 0 && yPantalla < pantalla.length && xPantalla >= 0 && xPantalla < pantalla[0].length) {
                        pantalla[yPantalla][xPantalla] = '*';
                    }
                }
            }

            imprimirPantalla(pantalla);
            Thread.sleep(50);
            rotacionX+=0.04;
        }
    }

    static void imprimirPantalla(char[][] pantalla) {
        for (int y = 0; y < pantalla.length; y++) {
            for (int x = 0; x < pantalla[0].length; x++) {
                System.out.print(pantalla[y][x]);
            }
            System.out.println();
        }
    }

    static double cos(double numero) {
        return Math.cos(numero);
    }

    static double sin(double numero) {
        return Math.sin(numero);
    }
}


/*
    x = (R + r * cos(θ)) * cos(φ)
    y = (R + r * cos(θ)) * sin(φ)
    z = r * sin(θ)
 */