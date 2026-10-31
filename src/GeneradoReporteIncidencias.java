import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GeneradoReporteIncidencias {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Error: Debe proporcionar la ruta del archivo de incidencias como argumento.");
            System.err.println("Ejemplo: java GeneradorReporteIncidencias mesa-ayuda/entrada/incidencias.txt");
            return;
        }

        Path archivoIncidencias = Path.of(args[0]);

        if (!Files.exists(archivoIncidencias)) {
            System.err.println("El archivo especificado no existe: " + archivoIncidencias);
            return;
        }

        try {
            System.out.println("=== INFORMACIÓN DEL ARCHIVO ===");
            System.out.println("Nombre: " + archivoIncidencias.getFileName());
            System.out.println("Ruta absoluta: " + archivoIncidencias.toAbsolutePath());
            System.out.println("Tamaño: " + Files.size(archivoIncidencias) + " bytes");
            System.out.println("Última modificación: " + Files.getLastModifiedTime(archivoIncidencias));
            System.out.println("===============================\n");

            int totalIncidencias = 0;
            int totalAlta = 0;
            int totalMedia = 0;
            int totalBaja = 0;
            List<String> lineasAlta = new ArrayList<>();

            try (BufferedReader lector = Files.newBufferedReader(archivoIncidencias)) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    if (linea.trim().isEmpty()) {
                        continue;
                    }

                    totalIncidencias++;
                    String lineaMayus = linea.toUpperCase();

                    if (lineaMayus.contains("ALTA")) {
                        totalAlta++;
                        lineasAlta.add(linea);
                    } else if (lineaMayus.contains("MEDIA")) {
                        totalMedia++;
                    } else if (lineaMayus.contains("BAJA")) {
                        totalBaja++;
                    }
                }
            }

            Path directorioSalida = Path.of("mesa-ayuda", "salida", "reportes");
            Files.createDirectories(directorioSalida);

            Path archivoReporte = directorioSalida.resolve("reporte-incidencias.txt");
            try (PrintWriter escritor = new PrintWriter(Files.newBufferedWriter(archivoReporte))) {
                escritor.println("=== REPORTE GENERAL DE INCIDENCIAS ===");
                escritor.println("Total de incidencias: " + totalIncidencias);
                escritor.println("Prioridad ALTA: " + totalAlta);
                escritor.println("Prioridad MEDIA: " + totalMedia);
                escritor.println("Prioridad BAJA: " + totalBaja);
            }
            System.out.println("Reporte general generado exitosamente en: " + archivoReporte);

            Path archivoAlta = directorioSalida.resolve("incidencias-alta.txt");
            Files.write(archivoAlta, lineasAlta);
            System.out.println("Archivo de incidencias ALTA generado exitosamente en: " + archivoAlta);

        } catch (IOException e) {
            System.err.println("Ocurrió un error de E/S durante el procesamiento: " + e.getMessage());
        }
    }
}
