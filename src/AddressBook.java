
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;


public class AddressBook {

    public static void main(String[] args) throws IOException {

        Scanner seleccion = new Scanner(System.in);
        System.out.println("\n<===AGENDA TELEFÓNICA===>\n");
        int opcion = 0;
        boolean salir = true;

        while (salir) {

            try {

                System.out.println("Seleccione la opción deseada:\n1 - Mostrar los contactos de la agenda\n2 - Crear nuevo Contacto\n3 - Borrar Contacto\n0 - Salir de la Agenda");
                opcion = seleccion.nextInt();
                String telefono = null;
                String nombre = null;


                switch (opcion) {
                    case 1:
                        File load = new File("C:/Users/Kiseg/Documents/Java/Tema12_Actividad/Agenda.csv");
                        Scanner agenda = null;

                        try {

                            System.out.println("\n<===AGENDA TELEFÓNICA===>");
                            agenda = new Scanner(load);

                            while (agenda.hasNextLine()) {              // Leemos linea a linea el fichero
                                String contacto = agenda.nextLine();    // Guardamos la linea en un String
                                System.out.println(contacto);           // Imprimimos la linea
                            }

                        } catch (Exception ex1) {
                            System.out.println("ERROR: " + ex1.getMessage());
                        } finally {

                            try {
                                if (agenda != null)     // Cerramos el fichero tanto si la lectura ha sido correcta o no
                                    agenda.close();
                            } catch (Exception ex2) {
                                System.out.println("ERROR 2: " + ex2.getMessage());
                            }
                        }
                        return;
                    case 2:
                        Scanner create = new Scanner(System.in);
                        System.out.println("Ingresa el Teléfono");
                        telefono = create.next();
                        System.out.println("Ingresa el Nombre");
                        nombre = create.next();

                        HashMap<String, String> list = new HashMap<>();

                        list.put(telefono, nombre);

                        for (Map.Entry<String, String> entry : list.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            System.out.println("\n<===AGENDA TELEFÓNICA===>");
                            System.out.println("Contacto Nuevo:");
                            String output = String.format("%s : %s", key, value);
                            System.out.println(output);
                        }

                        String concat = (telefono + " : " + nombre);

                        String data = concat;
                        BufferedWriter bw = null;
                        FileWriter fw = null;

                        try {
                            File save = new File("Agenda.csv");
                            // Si el archivo no existe, se crea!
                            if (!save.exists()) {
                                save.createNewFile();
                            }
                            fw = new FileWriter(save.getAbsoluteFile(), true);
                            bw = new BufferedWriter(fw);
                            bw.newLine();
                            bw.write(data);

                            System.out.println("\nInformación agregada!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                //Cierra instancias de FileWriter y BufferedWriter
                                if (bw != null)
                                    bw.close();
                                if (fw != null)
                                    fw.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        return;
                    case 3:

                        File f = new File("C:/Users/Kiseg/Documents/Java/Tema12_Actividad/Agenda.csv");
                        Scanner teclado = new Scanner(System.in);
                        System.out.println("Introduce el telefóno que desea borrar: ");
                        String lineaBorrar = teclado.nextLine();
                        try {
                            if (!f.isFile()) {
                                System.out.println("El parámetro no es un archivo existente");
                                return;
                            }

                            File tempFile = new File(f.getAbsolutePath() + ".csv"); // Construir el nuevo archivo que posteriormente se cambió el nombre al nombre de archivo original .

                            BufferedReader br = new BufferedReader(new FileReader(f));
                            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
                            String line = null;


                            while ((line = br.readLine()) != null) {          // Leer desde el archivo original y escribir en el nuevo
                                if (!line.trim().contains(lineaBorrar)) {
                                    pw.println(line);
                                    pw.flush();
                                }
                            }
                            pw.close();
                            br.close();

                            if (!f.delete()) {         // Eliminar el archivo original
                                System.out.println("No se pudo borrar el archivo");
                                return;
                            }

                            if (!tempFile.renameTo(f))     // Cambie el nombre del nuevo archivo al nombre de archivo del archivo original tenía .
                                System.out.println("No se puede cambiar el nombre de archivo");
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        System.out.println("El Contacto fue eliminado\n");
                    case 0:
                        System.out.println("Hasta la proxima...!!\n");
                        return;
                    default:
                        System.out.println("Opción no válida...!!\n");
                }

            } catch(InputMismatchException e){
                seleccion.nextLine();
                System.out.println("\nOPCIÓN INVALIDA ===> Debe ingresar un número, intente de nuevo...\n");
            }
        }
    }
}

