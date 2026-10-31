import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InformacionArchivo {
    public static void main(String[] args) {
        try {
            Path directorio = Path.of("salida", "reportes", "procesados");
            Files.createDirectories(directorio);

            Path archivo = directorio.resolve("resultado.txt");
            if (!Files.exists(archivo)) {
                Files.createFile(archivo);
            }

            System.out.println("Existe: " + Files.exists(archivo));
            System.out.println("Es archivo: " + Files.isRegularFile(archivo));
            System.out.println("Se puede leer: " + Files.isReadable(archivo));

            System.out.println("Tamaño: " + Files.size(archivo) + " bytes");
            System.out.println("Última modificación: " + Files.getLastModifiedTime(archivo));

            Path temporal = Files.createTempFile("dsi-", ".tmp");
            System.out.println("Archivo temporal: " + temporal);

            Path directorioTemporal = Files.createTempDirectory("dsi-");
            System.out.println("Directorio temporal: " + directorioTemporal);

        } catch (IOException e) {
            System.err.println("No fue posible crear o consultar el archivo: " + e.getMessage());
        }
    }
}