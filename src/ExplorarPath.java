import java.nio.file.Path;

public class ExplorarPath {
    public static void main(String[] args) {
        Path ruta = Path.of("datos", "mensaje.txt");

        System.out.println("Ruta: " + ruta);
        System.out.println("Archivo: " + ruta.getFileName());
        System.out.println("Padre: " + ruta.getParent());
        System.out.println("Número de elementos: " + ruta.getNameCount());
        System.out.println("Ruta absoluta: " + ruta.toAbsolutePath());
    }
}