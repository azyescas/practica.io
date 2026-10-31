
## 1. Objetivo de aprendizaje

## Name: Azkahira Ziret Yescas Diaz
## Expediente: 222213158

Al finalizar la práctica, el estudiante será capaz de **seleccionar y utilizar apropiadamente mecanismos de entrada/salida de Java para leer, escribir, copiar y procesar información almacenada en archivos**, diferenciando entre flujos de bytes, caracteres, flujos con búfer y las facilidades de `java.nio.file`.

Se espera que el estudiante pueda:

- Explicar el modelo entrada–proceso–salida basado en *streams*.
- Diferenciar flujos de bytes y flujos de caracteres.
- Utilizar `FileInputStream` y `FileOutputStream`.
- Utilizar `FileReader` y `FileWriter`.
- Procesar archivos de texto por líneas con `BufferedReader`.
- Generar archivos mediante `PrintWriter`.
- Explicar la utilidad de los flujos con búfer.
- Utilizar `Path`, `Paths`/`Path.of()` y `Files`.
- Diferenciar rutas relativas y absolutas.
- Consultar propiedades y realizar operaciones básicas sobre archivos.
- Seleccionar el mecanismo de E/S adecuado según el problema.


## 2. Conceptos iniciales

```text
                INPUT STREAM
                     │
                     ▼
Fuente ───────► Programa
                     │
                     ▼
                OUTPUT STREAM
                     │
                     ▼
                   Destino
```

Un flujo de entrada permite leer datos de una fuente y un flujo de salida permite escribirlos hacia un destino.

## 3. Preparación

Crear un proyecto:

```text
practica-io/
├── src/
├── datos/
└── salida/
```

Dentro de `datos`, crear `mensaje.txt` con el contenido:

```text
Universidad de Sonora
Desarrollo de Sistemas III
Práctica de entrada y salida en Java

Java proporciona diferentes mecanismos para trabajar
con archivos y flujos de información.
```

# Parte I. Flujos de bytes

## 4. `InputStream` y `OutputStream`

Los flujos de bytes trabajan con unidades de 8 bits. Las clases de este tipo descienden de `InputStream` y `OutputStream`; para archivos se utilizan `FileInputStream` y `FileOutputStream`.

### Actividad 1. Copiar un archivo byte por byte

Crear `CopiarBytes.java`:

```java
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
```

### Preguntas

1. ¿Por qué `read()` devuelve un `int`?
Devuelve un int (de 0 a 255) para poder reservar el valor -1
como indicador especial sin chocar con los valores posibles
de un byte real.
2. ¿Qué significa `-1`?
Representa el fin del archivo (End Of File o EOF), indicando
que ya no quedan más bytes por leer.
3. ¿Qué representa la variable `dato`?
Almacena temporalmente el valor numérico (de 0 a 255) del byte
leído en cada iteración para escribirlo luego en el archivo de
destino
4. ¿Podría utilizarse este mecanismo para copiar una imagen?
Si, ya que una imagen es un archivo binario formado por bytes.
Sin embargo, al ser byte por byte, no es eficiente para archivos
grandes; se recomienda usar un búfer (byte[]) para mejorar el 
rendimiento.

# Parte II. Flujos de caracteres

## 5. `Reader` y `Writer`

Java representa internamente caracteres mediante Unicode. Los flujos de caracteres realizan la traducción entre esa representación y la codificación utilizada externamente.

### Actividad 2. Copiar utilizando caracteres

```java
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
```

### Comparación

```text
FileInputStream    FileOutputStream
        │                 │
        └──── bytes ──────┘

FileReader          FileWriter
        │                 │
        └── caracteres ───┘
```

**Pregunta de decisión:** ¿qué utilizaría para procesar un archivo `.txt`? ¿Y para copiar un archivo `.jpg`?
Para un archivo .txt: Utilizaría flujos de caracteres (FileReader u FileWriter), ya que están optimizados para
procesar texto legibles codificados por caracteres.
Para un archivo .jpg: Utilizaría flujos de bytes (FileInputStream y FileOutputStream), ya que es un archivo
binario y los flujos de caracteres alterarían o corromperían su estructura al intentar interpretarlos como
texto.


# Parte III. Procesamiento por líneas

## 6. `BufferedReader`

Para archivos de texto suele ser más útil trabajar con líneas completas que con caracteres individuales.

### Actividad 3. Leer líneas

```java
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
```



# Parte IV. Flujos con búfer

## 7. ¿Para qué sirve un búfer?

La E/S sin búfer puede provocar que cada lectura o escritura llegue directamente al sistema operativo. Los flujos con búfer reducen esa sobrecarga acumulando temporalmente datos en memoria.

### Actividad 4. Transformar un archivo

Crear un programa que lea `mensaje.txt` y genere `salida/mensaje-mayusculas.txt`, escribiendo cada línea en mayúsculas.

```java
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
```

```text
Entrada → Transformación → Salida
```

# Parte V. Rutas y `Path`

## 8. Introducción a `java.nio.file`

Una ruta puede ser **absoluta** o **relativa**.

### Actividad 5. Examinar un `Path`

```java
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
```

# Parte VI. Clase `Files`

## 9. Consultar información del sistema de archivos

`Files` ofrece métodos estáticos para leer, escribir y manipular archivos y directorios trabajando con objetos `Path`.

### Actividad 6. Inspeccionar un archivo

```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InformacionArchivo {
    public static void main(String[] args) {
        Path archivo = Path.of("datos", "mensaje.txt");

        System.out.println("Existe: " + Files.exists(archivo));
        System.out.println("Es archivo: " + Files.isRegularFile(archivo));
        System.out.println("Se puede leer: " + Files.isReadable(archivo));

        try {
            System.out.println("Tamaño: " + Files.size(archivo) + " bytes");
            System.out.println("Última modificación: " + Files.getLastModifiedTime(archivo));
        } catch (IOException e) {
            System.err.println("No fue posible consultar el archivo.");
        }
    }
}
```

# Parte VII. Crear directorios y archivos

## 10. Manipular el sistema de archivos

```java
Path directorio = Path.of("salida", "reportes", "procesados");
Files.createDirectories(directorio);

Path archivo = directorio.resolve("resultado.txt");
if (!Files.exists(archivo)) {
    Files.createFile(archivo);
}
```

# Parte VIII. Archivos temporales

## 11. Crear archivos temporales

```java
Path temporal = Files.createTempFile("dsi-", ".tmp");
System.out.println(temporal);

Path directorioTemporal = Files.createTempDirectory("dsi-");
System.out.println(directorioTemporal);
```

### Pregunta

¿En qué situaciones podría ser conveniente utilizar un archivo temporal en lugar de un archivo permanente?



# Parte IX. Selección de archivos mediante patrones glob

## 12. Expresiones glob

Ejemplos:

```text
*.txt
*.csv
reporte?.txt
[abc].txt
```

| Patrón | Coincidencias esperadas |
|||
| `*.txt` | Archivos `.txt` |
| `*.csv` | Archivos `.csv` |
| `foto?.jpg` | `foto1.jpg`, `fotoA.jpg`, etc. |
| `[abc].txt` | `a.txt`, `b.txt`, `c.txt` |

### Miniactividad

Indicar qué patrón utilizaría para:

1. Seleccionar todos los archivos Java.
2. Seleccionar todos los archivos CSV.
3. Seleccionar `reporte1.txt` a `reporte9.txt`.
4. Seleccionar archivos que comiencen con `log`.



# Parte X. NIO: Channels y Buffers

## 13. Concepto de canal

```text
             lectura
Channel ─────────────► Buffer
                         │
                         ▼
                     Programa

             escritura
Programa ─► Buffer ─────────────► Channel
```

Entre las implementaciones de canal se encuentran:

- `FileChannel`
- `DatagramChannel`
- `SocketChannel`
- `ServerSocketChannel`

Y entre los buffers principales:

- `ByteBuffer`
- `CharBuffer`
- `IntBuffer`
- `DoubleBuffer`



# Parte XI. Actividad integradora

## 14. Sistema de generación de reportes de incidencias

**Duración sugerida:** 35–45 minutos  
**Organización:** parejas

Se desarrollará una pequeña aplicación de **procesamiento de incidencias de soporte técnico**.

```text
mesa-ayuda/
├── entrada/
│   └── incidencias.txt
└── salida/
```

El archivo `incidencias.txt` contiene:

```text
INC-001|Impresora sin conexión|MEDIA
INC-002|Servidor de correo no responde|ALTA
INC-003|Actualizar navegador|BAJA
INC-004|Usuario no puede iniciar sesión|ALTA
INC-005|Instalar aplicación de oficina|MEDIA
INC-006|Falla del servidor de archivos|ALTA
```

Cada registro tiene:

```text
ID | DESCRIPCIÓN | PRIORIDAD
```

## 15. Requerimientos

Desarrollar `GeneradorReporteIncidencias.java`.

La aplicación deberá:

1. Recibir mediante argumento el nombre del archivo de incidencias.

```bash
java GeneradorReporteIncidencias mesa-ayuda/entrada/incidencias.txt
```

2. Representar la ruta mediante `Path`.
3. Comprobar que el archivo existe con `Files.exists(...)`.
4. Mostrar nombre, ruta absoluta, tamaño y última modificación del archivo.
5. Leer el archivo mediante `BufferedReader`.
6. Procesar cada línea.
7. Contabilizar incidencias totales y por prioridad.
8. Crear automáticamente `mesa-ayuda/salida/reportes/` con `Files.createDirectories(...)`.
9. Generar `reporte-incidencias.txt` mediante `PrintWriter`.
10. Crear `incidencias-alta.txt` con las incidencias de prioridad `ALTA`.

## 16. Resultado esperado

`reporte-incidencias.txt`:

```text
REPORTE DE INCIDENCIAS
======================

Archivo procesado: incidencias.txt

Total de incidencias: 6

Prioridad ALTA: 3
Prioridad MEDIA: 2
Prioridad BAJA: 1

INCIDENCIAS DE ALTA PRIORIDAD
--

INC-002 - Servidor de correo no responde
INC-004 - Usuario no puede iniciar sesión
INC-006 - Falla del servidor de archivos
```

`incidencias-alta.txt`:

```text
INC-002|Servidor de correo no responde|ALTA
INC-004|Usuario no puede iniciar sesión|ALTA
INC-006|Falla del servidor de archivos|ALTA
```

## 17. Esqueleto de la solución

```java
import java.io.*;
import java.nio.file.*;

public class GeneradorReporteIncidencias {

    public static void main(String[] args) {

        // 1. Validar argumentos
        // 2. Crear Path del archivo de entrada
        // 3. Comprobar existencia
        // 4. Mostrar información del archivo
        // 5. Crear directorio de salida
        // 6. Crear Paths para los archivos de salida

        int total = 0;
        int altas = 0;
        int medias = 0;
        int bajas = 0;

        try (
            BufferedReader lector = Files.newBufferedReader(/* completar */);
            PrintWriter reporte = new PrintWriter(Files.newBufferedWriter(/* completar */));
            PrintWriter reporteAltas = new PrintWriter(Files.newBufferedWriter(/* completar */))
        ) {

            String linea;

            while ((linea = lector.readLine()) != null) {
                // 7. Procesar cada incidencia
                // 8. Clasificar prioridad
                // 9. Escribir incidencias ALTA
            }

            // 10. Generar resumen

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }
}
```

## 18. Reto adicional

Modificar la aplicación para que el directorio `entrada` pueda contener:

```text
incidencias-lunes.txt
incidencias-martes.txt
incidencias-miercoles.txt
notas.md
config.properties
```

La aplicación deberá procesar **únicamente los archivos `.txt`** mediante un patrón equivalente a:

```text
*.txt
```

y generar un reporte consolidado.



## 19. Mapa conceptual de repaso

```text
                   Java I/O
                      │
       ┌──────────────┴──────────────┐
       │                             │
    Streams                     java.nio.file
       │                             │
 ┌─────┴──────┐                ┌─────┴─────┐
 │            │                │           │
Bytes     Caracteres          Path        Files
 │            │
 │       BufferedReader
 │       PrintWriter
 │
FileInputStream
FileOutputStream
```



## 20. Preguntas de reflexión

1. ¿Qué es un *stream*?
Es una secuencia o flujo de datos que se desplaza de manera ordenada desde una fuente 
(entrada) hacia un destino (salida)
2. ¿Cuál es la diferencia entre un *input stream* y un *output stream*?
El input stream se utiliza para leer datos desde una fuente, mientras que el output 
stream se emplea para escribir o enviar datos hacia un destino.
3. ¿Cuál es la diferencia entre flujos de bytes y caracteres?
Los flujos de bytes manejan datos binarios de 8 bits (aptos para cualquier archivo), 
y los de caracteres manejan texto de 16 bits optimizados para codificaciones
Unicode.
4. ¿Cuándo utilizaría `FileInputStream` en lugar de `FileReader`?
Cuando se trabaja con archivos binarios (como imágenes, vídeos o ejecutables) donde
una interpretación por caracteres podría corromper la información.
5. ¿Qué ventaja proporciona `BufferedReader`?
Reduce las lecturas físicas a disco usando búfer en memoria y provee el método de
conveniencia readLine().
6. ¿Qué devuelve `readLine()` cuando termina el archivo?
Devuelve null.
7. ¿Por qué los flujos con búfer pueden mejorar el desempeño?
Porque agrupan las operaciones de lectura o escritura en bloques de
memoria, evitando accesos constantes y costosos al disco duro.
8. ¿Qué diferencia existe entre una ruta absoluta y una relativa?
La absoluta especifica la ubicación completa desde la raíz del sistema
de archivos, y la relativa parte de la ubicación o directorio de
trabajo actual.
9. ¿Qué representa un objeto `Path`?
Una ruta que localiza una archivo o directorio dentro del sistema de
archivos (introducido en NIO.2).
10. ¿Cuál es la responsabilidad de la clase `Files`?
Proveer métodos estáticos para realizar operaciones utiliarias sobres 
archivos y directorios (crear, eliminar, copiar, consultar metadatos, etc.).
11. ¿Qué hace `Files.exists()`?
Verifica si el archivo o directorio especificado por una ruta (Path) existe 
físicamente en el disco
12. ¿Para qué se utiliza `resolve()`?
Para concatenar de forma segura una ruta base con un subdirectorio o nombre
de archivo adicional.
13. ¿Qué ventaja proporciona `Files.createDirectories()`?
Crea un directorio junto con todos sus directorios padres faltantes de una
sola vez, y no genera error si ya existen.
14. ¿Qué es una expresión *glob*?
Un patrón de texto basado en comodines (como *.txt) empleado para buscar y
filtrar nombres de archivos de forma sencilla.
15. ¿Qué relación existe entre un `Channel` y un `Buffer` en NIO?
El Channel es vía de comunicación abierta hacia el origen o destino de los
datos, y el Buffer es el bloque de memoria temporal por el cual pasan
dichos datos.
16. ¿Qué mecanismo utilizaría para procesar un archivo de texto línea por línea?
BufferReader (usando su método readLine()).
17. ¿Qué mecanismo utilizaría para copiar una imagen?
Flujos de bytes (como FileInputStream / FileOutputStream) o bien métodos de utilidad
de la clase Files (Files.copy()).
18. ¿Qué mecanismos de E/S fueron necesarios en el sistema de incidencias?
Path y Files (para validación de rutas y creación de directorios),
BufferReader (lectura de líneas), y PrintWriter o Files.write (escritura de los
reportes generados).


## 21. Entregables

- Código de los ejercicios guiados.
- `GeneradorReporteIncidencias.java`.
- Archivo `incidencias.txt`.
- `reporte-incidencias.txt`.
- `incidencias-alta.txt`.
- Evidencia de la estructura de directorios generada.
- Evidencia de ejecución.
- Respuestas a las preguntas de reflexión.

Estructura sugerida del repositorio:

```text
practica-io/
├── README.md
├── src/
│   ├── CopiarBytes.java
│   ├── CopiarCaracteres.java
│   ├── LeerLineas.java
│   ├── ConvertirMayusculas.java
│   ├── ExplorarPath.java
│   ├── InformacionArchivo.java
│   └── GeneradorReporteIncidencias.java
├── datos/
│   └── mensaje.txt
└── mesa-ayuda/
    ├── entrada/
    │   └── incidencias.txt
    └── salida/
        └── reportes/
            ├── reporte-incidencias.txt
            └── incidencias-alta.txt
```

