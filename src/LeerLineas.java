import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeerLineas {
    public static void main(String[] args) {
        try (BufferedReader lector = new BufferedReader(new FileReader("datos/mensaje.txt"))) {
            String linea;
            int numeroLinea = 1;

            while ((linea = lector.readLine()) != null) {
                System.out.printf("%02d: %s%n", numeroLinea, linea);
                numeroLinea++;
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}