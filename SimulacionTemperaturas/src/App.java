import java.util.Random;

public class App{

    //Elaborado por:
    //David Santiago Arias Rojas 0222510022
    //Alex David Flores Cerro 0222510031
    //

    static double [][][] edificio = new double [5][10][3];
    static double [][] promedioTemperaturas = new double[5][10];

    public static void main(String[] args){

        Random random = new Random();

        for(int i=0; i<5; i++){
            for(int j=0; j<10; j++){
                for(int k=0; k<3; k++){
                    edificio[i][j][k]= random.nextDouble(15.0, 31.0); //PUNTO A: INICIALIZACION DE LA MATRIZ (SE SIMULAN VALORES ALEATORES ENTRE 15 Y 31, PARA GENERAR ANOMALIAS)
                }
            }
        }

        temperaturaPromedioPorHabitacion();

        mostrarRegistros();
    }

    public static void mostrarRegistros(){ //PUNTO D: FUNCION QUE MUESTRA LOS VALORES DE TEMPERATURA ORGANIZADOS POR PISO Y HABITACION
        System.out.println("Registros de temperatura");
        System.out.println(" ");
        for(int i=0; i<5; i++){

            System.out.println("Piso "+(i+1));
            for(int j=0; j<10; j++){
                System.out.println("Habitacion: "+(j+1));
                System.out.printf("Temperatura promedio: %.2f°C%n", promedioTemperaturas[i][j]);
                for(int k=0; k<3; k++){
                    System.out.printf("Temperatura sensor "+(k+1)+": %.2f°C%n", edificio[i][j][k]);
                }

                System.out.println(" ");
            }

            System.out.println(" ");
        }

        System.out.printf("Promedio de temperatura en el edificio: %.2f°C%n",(temperaturaPromedioEdificio()));
    }

    public static void temperaturaPromedioPorHabitacion(){ // PUNTO B: FUNCION QUE CALCULA LA TEMPERATURA PROMEDIO POR HABITACION
        for(int i=0; i<5; i++){
            for(int j=0; j<10; j++){
                double sumaTemperaturasApartamento=0;
                for(int k=0; k<3; k++){

                    if(edificio[i][j][k]<16 || edificio[i][j][k]>30){
                        deteccionAnomalia(i, j, k, edificio[i][j][k]);
                    }

                    sumaTemperaturasApartamento+=edificio[i][j][k];
                }
                promedioTemperaturas[i][j]=sumaTemperaturasApartamento/3.0;

            }
        }
    }

    public static double temperaturaPromedioEdificio(){ // PUNTO B: FUNCION QUE CALCULA LA TEMPERATURA PROMEDIO DEL EDIFICIO

        double sumaTemperaturaEdificio=0;

        for(int i=0; i<5; i++){
            for(int j=0; j<10; j++){
                sumaTemperaturaEdificio+=promedioTemperaturas[i][j];
            }
        }
        return sumaTemperaturaEdificio/50.0;
    }

    public static void deteccionAnomalia(int i, int j, int k, double temperatura){ //PUNTO C: FUNCION QUE DETECTA E IMPRIME EN DONDE SE GENERAN LAS ANOMALIAS
        System.out.println("Se detecto una anomalia en el piso ["+(i+1)+"], habitacion ["+(j+1)+"], sensor ["+(k+1)+"]");
        System.out.printf("Temperatura detectada: %.2f°C%n", temperatura);
        System.out.println(" ");
    }
}


