public class Donut {
    public static void main(String[] args) throws InterruptedException{
        // Constantes:
        final int RADIO_SUPERIOR = 2;
        final int RADIO_INFERIOR = 1;

        final double PASO_THETA = 0.02;
        final double PASO_PSI = 0.02;
        final double DISTANCIA_DONUT = 15;
        final double ESCALA_DONUT = 4;
        final int HEIGHT = 28;
        final int WIDTH = 60;

        double rotacionEjeX = 0;
        double rotacionEjeZ = 0;
        char[][] pantalla = new char[HEIGHT][WIDTH];
        double[][] zBuffer = new double[HEIGHT][WIDTH];

        while (true) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    pantalla[y][x] = ' ';
                    zBuffer[y][x] = 0;
                }
            }

            for (double theta = 0; theta < 2 * Math.PI; theta += PASO_THETA) {
                for (double psi = 0; psi < 2 * Math.PI; psi += PASO_PSI) {
                    double circuloX = RADIO_SUPERIOR + RADIO_INFERIOR * cos(theta);
                    double circuloY = RADIO_INFERIOR * sin(theta);

                    double x = circuloX * (float)(Math.cos(rotacionEjeZ) * cos(psi) + Math.sin(rotacionEjeX) * Math.sin(rotacionEjeZ) * sin(psi)) - circuloY * (float)Math.cos(rotacionEjeX) * Math.sin(rotacionEjeZ);
                    double y = circuloX * (float)(Math.sin(rotacionEjeZ) * cos(psi) - Math.sin(rotacionEjeX) * Math.cos(rotacionEjeZ) * sin(psi)) + circuloY * (float)Math.cos(rotacionEjeX) * Math.cos(rotacionEjeZ);
                    double z = ESCALA_DONUT + Math.cos(rotacionEjeX) * circuloX * sin(psi) + circuloY * Math.sin(rotacionEjeX);

                    double zInverso = 1/z;
                    double luz = cos(psi) * cos(theta) * Math.sin(rotacionEjeZ) - Math.cos(rotacionEjeX) * cos(theta) * sin(psi) - Math.sin(rotacionEjeX) * sin(theta) + Math.cos(rotacionEjeZ) * (Math.cos(rotacionEjeX) * sin(theta) - cos(theta) * Math.sin(rotacionEjeX) * sin(psi));

                    int xProyectada = (int)(WIDTH / RADIO_SUPERIOR + DISTANCIA_DONUT * x / z);
                    int yProyectada = (int)(HEIGHT / RADIO_SUPERIOR - DISTANCIA_DONUT * y / z);

                    if (xProyectada >= 0 && xProyectada < WIDTH && yProyectada >= 0 && yProyectada < HEIGHT && zInverso > zBuffer[yProyectada][xProyectada]) {
                        zBuffer[yProyectada][xProyectada] = zInverso;
                        int indiceLuminiscencia = (int)(luz * 8);
                        final String caracteresLuminicos = ".,-~:;=!*#$@";
                        char caracterFinal = (indiceLuminiscencia >= 0 && indiceLuminiscencia < caracteresLuminicos.length())
                                  ? caracteresLuminicos.charAt(indiceLuminiscencia)
                                  : ' ';
                        pantalla[yProyectada][xProyectada] = caracterFinal;
                    }
                }
            }

            imprimirPantalla(pantalla);
            Thread.sleep(50);
            rotacionEjeX+=0.04;
            rotacionEjeZ+=0.04;
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