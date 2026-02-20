package smarttask;

import java.util.Scanner;

/**
 * Clase principal {@code MenuPrincipal} — Lección 4: Aplicaciones de consola en Java.
 *
 * <p>Contiene el método {@code main()} y el menú interactivo de la aplicación SmartTask.
 * Usa {@link Scanner} para leer las opciones del usuario y delega toda la lógica
 * en {@link GestorTareas} (principio de responsabilidad única).</p>
 *
 * <p>Estructuras de control usadas:</p>
 * <ul>
 *   <li>{@code do-while}: el menú se repite hasta que el usuario elige Salir.</li>
 *   <li>{@code switch}: selecciona la opción del menú.</li>
 *   <li>{@code if}: valida las entradas del usuario.</li>
 * </ul>
 *
 * <p><strong>Cómo ejecutar:</strong></p>
 * <pre>
 *   javac smarttask/*.java
 *   java smarttask.MenuPrincipal
 * </pre>
 *
 * @author Sabina Romero
 * @version 1.0
 */
public class MenuPrincipal {

    /**
     * Punto de entrada de la aplicación SmartTask.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {

        // Crear el gestor de tareas (carga datos de ejemplo)
        GestorTareas gestor = new GestorTareas();

        // Scanner para leer lo que el usuario escribe en la consola
        Scanner teclado = new Scanner(System.in);

        int opcion;

        // ─── Banner de bienvenida ────────────────────────────────────────────
        System.out.println("\n  ╔══════════════════════════════════════════╗");
        System.out.println(  "  ║      SmartTask — Gestión de Tareas       ║");
        System.out.println(  "  ║   Módulo 4: Fundamentos Java · Alkemy    ║");
        System.out.println(  "  ╚══════════════════════════════════════════╝");

        // ─── Bucle principal del menú ────────────────────────────────────────
        // do-while: se ejecuta AL MENOS una vez antes de verificar la condición
        do {
            mostrarMenu();

            // Leer opción del usuario con validación
            opcion = leerEntero(teclado);

            // switch: ejecuta el bloque correspondiente a la opción elegida
            switch (opcion) {

                case 1:
                    gestor.listarTodas();
                    break;

                case 2:
                    gestor.listarPendientes();
                    break;

                case 3:
                    gestor.listarCompletadas();
                    break;

                case 4:
                    opcionAgregarNormal(gestor, teclado);
                    break;

                case 5:
                    opcionAgregarUrgente(gestor, teclado);
                    break;

                case 6:
                    opcionMarcarCompletada(gestor, teclado);
                    break;

                case 7:
                    opcionEliminar(gestor, teclado);
                    break;

                case 8:
                    opcionVerDetalle(gestor, teclado);
                    break;

                case 0:
                    System.out.println("\n  👋 Hasta luego. ¡Sigue gestionando tus tareas!");
                    break;

                default:
                    System.out.println("\n  ❌ Opción no válida. Elige entre 0 y 8.");
            }

        } while (opcion != 0); // repetir mientras el usuario no elija Salir

        teclado.close(); // liberar el recurso Scanner al terminar
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  MÉTODOS PRIVADOS — cada opción del menú en su propio método
    //  Principio de responsabilidad única: un método = una tarea
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Imprime el menú de opciones en la consola.
     */
    private static void mostrarMenu() {
        System.out.println("\n  ═══════════════ MENÚ PRINCIPAL ═══════════════");
        System.out.println("    1. Listar todas las tareas");
        System.out.println("    2. Listar tareas pendientes");
        System.out.println("    3. Listar tareas completadas");
        System.out.println("    4. Agregar tarea NORMAL");
        System.out.println("    5. Agregar tarea URGENTE");
        System.out.println("    6. Marcar tarea como completada");
        System.out.println("    7. Eliminar tarea por ID");
        System.out.println("    8. Ver detalle de una tarea");
        System.out.println("    0. Salir");
        System.out.println("  ══════════════════════════════════════════════");
        System.out.print("    Selecciona una opción: ");
    }

    /**
     * Maneja la opción de agregar una tarea normal.
     *
     * @param gestor  el gestor de tareas
     * @param teclado el Scanner para leer del usuario
     */
    private static void opcionAgregarNormal(GestorTareas gestor, Scanner teclado) {
        System.out.println("\n  ─── Agregar Tarea Normal ───");
        System.out.print("  Nombre: ");
        String nombre = teclado.nextLine();

        // Validar que el nombre no esté vacío
        if (nombre.trim().isEmpty()) {
            System.out.println("  ❌ El nombre no puede estar vacío.");
            return;
        }

        System.out.println("  Prioridad (1=ALTA, 2=MEDIA, 3=BAJA): ");
        String prioridad = leerPrioridad(teclado);

        TareaNormal nueva = gestor.agregarTareaNormalLogica(nombre, prioridad);
        System.out.println("  ✅ Tarea Normal agregada con ID: " + nueva.getId());
    }

    /**
     * Maneja la opción de agregar una tarea urgente.
     *
     * @param gestor  el gestor de tareas
     * @param teclado el Scanner
     */
    private static void opcionAgregarUrgente(GestorTareas gestor, Scanner teclado) {
        System.out.println("\n  ─── Agregar Tarea Urgente ───");
        System.out.print("  Nombre: ");
        String nombre = teclado.nextLine();

        if (nombre.trim().isEmpty()) {
            System.out.println("  ❌ El nombre no puede estar vacío.");
            return;
        }

        System.out.println("  Prioridad (1=ALTA, 2=MEDIA, 3=BAJA): ");
        String prioridad = leerPrioridad(teclado);

        System.out.print("  Fecha límite (AAAA-MM-DD): ");
        String fechaLimite = teclado.nextLine();

        TareaUrgente nueva = gestor.agregarTareaUrgenteLogica(nombre, prioridad, fechaLimite);
        System.out.println("  🚨 Tarea Urgente agregada con ID: " + nueva.getId());
    }

    /**
     * Maneja la opción de marcar una tarea como completada.
     *
     * @param gestor  el gestor de tareas
     * @param teclado el Scanner
     */
    private static void opcionMarcarCompletada(GestorTareas gestor, Scanner teclado) {
        System.out.print("\n  Ingresa el ID de la tarea a completar: ");
        int id = leerEntero(teclado);

        boolean resultado = gestor.marcarComoCompletada(id);

        if (resultado) {
            System.out.println("  ✅ Tarea #" + id + " marcada como completada.");
        } else {
            Tarea t = gestor.buscarPorId(id);
            if (t == null) {
                System.out.println("  ❌ No existe una tarea con ID " + id);
            } else {
                System.out.println("  ⚠ La tarea #" + id + " ya estaba completada.");
            }
        }
    }

    /**
     * Maneja la opción de eliminar una tarea.
     *
     * @param gestor  el gestor de tareas
     * @param teclado el Scanner
     */
    private static void opcionEliminar(GestorTareas gestor, Scanner teclado) {
        System.out.print("\n  Ingresa el ID de la tarea a eliminar: ");
        int id = leerEntero(teclado);

        Tarea t = gestor.buscarPorId(id);
        if (t == null) {
            System.out.println("  ❌ No existe una tarea con ID " + id);
            return;
        }

        System.out.println("  Tarea a eliminar: " + t.getNombre());
        System.out.print("  ¿Confirmas la eliminación? (S/N): ");
        String confirmacion = teclado.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {
            gestor.eliminarTarea(id);
            System.out.println("  ✅ Tarea #" + id + " eliminada.");
        } else {
            System.out.println("  ⚠ Eliminación cancelada.");
        }
    }

    /**
     * Muestra el detalle completo de una tarea usando el método {@code describir()}
     * de la interfaz {@link Accionable} (polimorfismo).
     *
     * @param gestor  el gestor de tareas
     * @param teclado el Scanner
     */
    private static void opcionVerDetalle(GestorTareas gestor, Scanner teclado) {
        System.out.print("\n  Ingresa el ID de la tarea: ");
        int id = leerEntero(teclado);

        Tarea tarea = gestor.buscarPorId(id);
        if (tarea == null) {
            System.out.println("  ❌ No existe una tarea con ID " + id);
            return;
        }

        // instanceof verifica el tipo real del objeto en tiempo de ejecución
        if (tarea instanceof Accionable) {
            // cast: le decimos a Java que lo trate como Accionable
            Accionable accionable = (Accionable) tarea;
            System.out.println("\n  📋 Detalle: " + accionable.describir());
        } else {
            System.out.println("  " + tarea);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  UTILIDADES
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Lee un entero del teclado con validación de tipo.
     * Si el usuario escribe algo que no es número, pide que lo reintente.
     *
     * @param teclado el Scanner activo
     * @return el entero ingresado por el usuario
     */
    private static int leerEntero(Scanner teclado) {
        while (!teclado.hasNextInt()) {
            System.out.print("  ⚠ Ingresa un número válido: ");
            teclado.next(); // descartar la entrada inválida
        }
        int numero = teclado.nextInt();
        teclado.nextLine(); // consumir el salto de línea restante
        return numero;
    }

    /**
     * Muestra un submenú de prioridades y devuelve el string elegido.
     *
     * @param teclado el Scanner
     * @return "ALTA", "MEDIA" o "BAJA"
     */
    private static String leerPrioridad(Scanner teclado) {
        System.out.print("  Opción: ");
        int opcion = leerEntero(teclado);
        // switch para mapear número a texto de prioridad
        switch (opcion) {
            case 1:  return "ALTA";
            case 3:  return "BAJA";
            default: return "MEDIA"; // 2 o cualquier otro valor
        }
    }
}
