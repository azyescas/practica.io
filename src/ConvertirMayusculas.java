import java.io.*;

public class ConvertirMayusculas {
    public static void main(String[] args) {
        try (BufferedReader entrada = new BufferedReader(new FileReader("datos/mensaje.txt"));
             PrintWriter salida = new PrintWriter(new FileWriter("salida/mensaje-mayusculas.txt"))) {

            String linea;
            while ((linea = entrada.readLine()) != null) {
                salida.println(linea.toUpperCase());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}