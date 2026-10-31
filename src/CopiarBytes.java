import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopiarBytes {
    public static void main(String[] args) {
        String origen = "datos/mensaje.txt";
        String destino = "salida/copia-bytes.txt";

        try (FileInputStream entrada = new FileInputStream(origen);
             FileOutputStream salida = new FileOutputStream(destino)) {

            int dato;
            while ((dato = entrada.read()) != -1) {
                salida.write(dato);
            }

            System.out.println("Archivo copiado correctamente.");
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}