package aplicacionprocesos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProcessManagerApp {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<ProcessModel> processTable;

    @FXML
    private TableColumn<ProcessModel, String> nameColumn;

    @FXML
    private TableColumn<ProcessModel, Integer> pidColumn;

    @FXML
    private Button btnReiniciar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnCrear;

    @FXML
    private ImageView poofImageView;
    
    @FXML
    private ImageView actionImageView;

    private ObservableList<ProcessModel> processList;

    @FXML
    public void initialize() {
        // Inicializar la tabla
        processList = FXCollections.observableArrayList();
        processTable.setItems(processList);

        // Configurar columnas
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        pidColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPid()).asObject());

        // Cargar procesos del sistema
        loadProcesses();

        // Configurar el botón de búsqueda
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterProcesses(newValue));
        System.out.println("actionImageView: " + actionImageView);
        // Ocultar el ImageView al inicio
        actionImageView.setVisible(false);
        poofImageView.setVisible(false);
    }

    private void loadProcesses() {
    try {
        // Obtener los procesos del sistema
        ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        // Limpiar la lista antes de cargar nuevos procesos
        processList.clear();

        // Saltar la primera línea (encabezados) y leer las siguientes líneas
        reader.readLine(); // Lee el encabezado
        reader.readLine(); // Lee la segunda línea
        reader.readLine(); // Lee la tercera línea

        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+"); // Separar por espacios

            // Verifica que haya al menos dos partes (nombre y PID)
            if (parts.length >= 2) {
                String name = parts[0]; // Nombre del proceso
                try {
                    int pid = Integer.parseInt(parts[1]); // PID (convertir a entero)

                    // Omitir procesos svchost
                    if (!name.equalsIgnoreCase("svchost.exe")) {
                        // Agregar el proceso a la lista
                        processList.add(new ProcessModel(name, pid));
                    }
                } catch (NumberFormatException e) {
                    // Si no se puede parsear el PID, mostrar un mensaje de advertencia
                    System.err.println("No se pudo convertir el PID: " + parts[1]);
                }
            }
        }
        reader.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private void filterProcesses(String searchTerm) {
        if (searchTerm.isEmpty()) {
            processTable.setItems(processList);
        } else {
            ObservableList<ProcessModel> filteredList = FXCollections.observableArrayList();
            for (ProcessModel process : processList) {
                if (process.getName().toLowerCase().contains(searchTerm.toLowerCase())
                        || String.valueOf(process.getPid()).contains(searchTerm)) {
                    filteredList.add(process);
                }
            }
            processTable.setItems(filteredList);
        }
    }

    @FXML
    private void handleReiniciar() {
        ProcessModel selectedProcess = processTable.getSelectionModel().getSelectedItem();
        if (selectedProcess != null) {
            try {
                // Eliminar el proceso
                ProcessBuilder killProcess = new ProcessBuilder("taskkill", "/F", "/PID", String.valueOf(selectedProcess.getPid()));
                Process kill = killProcess.start();
                kill.waitFor(); // Esperar a que el proceso se elimine

                if (kill.exitValue() == 0) {
                    actionImageView.setImage(new Image(getClass().getResourceAsStream("images/cosmo.png")));
                    actionImageView.setVisible(true);
                    poofImageView.setVisible(true);
                    System.out.println("Proceso " + selectedProcess.getName() + " eliminado correctamente.");

                    // Reiniciar el proceso (esto puede requerir el path completo del ejecutable)
                    ProcessBuilder restartProcess = new ProcessBuilder(selectedProcess.getName());
                    Process restart = restartProcess.start();
                    System.out.println("Reiniciando proceso: " + selectedProcess.getName());

                    // Actualizar la lista de procesos
                    loadProcesses(); // Recargar los procesos
                    processTable.getSelectionModel().clearSelection(); // Limpiar selección
                } else {
                    System.err.println("No se pudo eliminar el proceso " + selectedProcess.getName());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, selecciona un proceso para reiniciar.");
        }
    }

    @FXML
    private void handleEliminar() {
        // Obtener el proceso seleccionado de la tabla
        ProcessModel selectedProcess = processTable.getSelectionModel().getSelectedItem();

        if (selectedProcess != null) {
            try {
                // Llamar al comando taskkill para eliminar el proceso
                ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/F", "/PID", String.valueOf(selectedProcess.getPid()));
                Process process = processBuilder.start();
                process.waitFor(); // Esperar a que el proceso se complete

                // Verificar si el proceso se eliminó correctamente
                if (process.exitValue() == 0) {
                    // Eliminar el proceso de la lista y actualizar la tabla
                    actionImageView.setImage(new Image(getClass().getResourceAsStream("images/poofB.png")));
                    actionImageView.setVisible(true);
                    poofImageView.setVisible(true);
                    processList.remove(selectedProcess);
                    processTable.getItems().remove(selectedProcess);
                    System.out.println("Proceso " + selectedProcess.getName() + " eliminado correctamente.");
                } else {
                    System.err.println("No se pudo eliminar el proceso " + selectedProcess.getName());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, selecciona un proceso para eliminar.");
        }
    }

    @FXML
    private void handleCrear() {
        // Mostrar un diálogo de entrada para que el usuario ingrese el nombre del proceso
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Crear Proceso");
        dialog.setHeaderText("Ingrese el nombre del nuevo proceso (ej. notepad.exe)");
        dialog.setContentText("Nombre del Proceso:");

        // Esperar a que el usuario ingrese el nombre
        dialog.showAndWait().ifPresent(name -> {
            try {
                // Intentar crear el nuevo proceso
                ProcessBuilder processBuilder = new ProcessBuilder(name);
                Process process = processBuilder.start(); // Iniciar el proceso
                System.out.println("Creando nuevo proceso: " + name);
                actionImageView.setImage(new Image(getClass().getResourceAsStream("images/wanda.png")));
                actionImageView.setVisible(true);
                poofImageView.setVisible(true);
                // Actualizar la lista de procesos para mostrar el nuevo proceso
                loadProcesses(); // Recargar los procesos

            } catch (IOException e) {
                // Manejar excepciones si el proceso no se puede iniciar
                System.err.println("Error al crear el proceso: " + e.getMessage());
            }
        });
    }

}
