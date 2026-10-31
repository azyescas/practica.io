import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopiarCaracteres {
    public static void main(String[] args) {
        try (FileReader entrada = new FileReader("datos/mensaje.txt");
             FileWriter salida = new FileWriter("salida/copia-caracteres.txt")) {

            int caracter;
            while ((caracter = entrada.read()) != -1) {
                salida.write(caracter);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}