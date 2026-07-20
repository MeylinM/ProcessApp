<div align="center">
  <h1>⚡ PROCESSAPP ⚡</h1>
  <p><strong>Application to manage your computer's processes inspired by The Fairly OddParents</strong></p>
  <p><em>"¡Puf! Cosmo y Wanda en acción" 🧚✨</em></p>
</div>

---

## ★ Descripción

**ProcessApp** es una aplicación de escritorio innovadora y divertida desarrollada en **Java con JavaFX** que permite **gestionar, reiniciar y eliminar procesos del sistema** de una forma visual e intuitiva. Inspirada en la serie animada "Los Padrinos Mágicos" (The Fairly OddParents), la aplicación incorpora elementos temáticos de los personajes **Cosmo y Wanda** para hacer la experiencia de gestión de procesos más entretenida y amigable.

La aplicación demuestra cómo interactuar con el sistema operativo mediante Java, utilizando comandos del sistema y presentándolos en una interfaz gráfica moderna y funcional.

---

## ★ Características Principales

### ⊹ **Gestión de Procesos**
- **Ver Procesos**: Lista en tiempo real de todos los procesos activos del sistema
- **Búsqueda Avanzada**: Filtra procesos por nombre o PID en tiempo real
- **Eliminar Procesos**: Termina procesos seleccionados con un clic
- **Reiniciar Procesos**: Detiene y reinicia un proceso automáticamente

### ⊹ **Interfaz Temática**
- **Elementos Visuales Mágicos**: Imágenes de Cosmo, Wanda y efectos "Puf" 🧚
- **Animaciones**: Animaciones que se muestran al realizar acciones
- **Diseño Intuitivo**: Tabla clara con columnas de Nombre del Proceso y PID
- **Búsqueda en Tiempo Real**: Filtra procesos mientras escribes

### ⊹ **Operaciones del Sistema**
- **Comando tasklist**: Obtiene la lista de procesos del sistema Windows
- **Comando taskkill**: Termina procesos de forma forzada
- **Gestión de Excepciones**: Manejo robusto de errores
- **Filtrado Dinámico**: Excluye procesos de sistema (como svchost.exe)

---

## ★ Tecnologías Utilizadas

| Componente | Tecnología |
|---|---|
| **Lenguaje** | Java 8 |
| **Interfaz Gráfica** | JavaFX 8+ |
| **Sistema Operativo** | Windows (tasklist, taskkill) |
| **Build System** | Apache Ant |
| **IDE** | NetBeans |
| **Arquitectura** | MVC (Model-View-Controller) |
| **Acceso al Sistema** | ProcessBuilder + Command Line |

---

## ★ Estructura del Proyecto

```
ProcessApp/
├── AplicacionProcesos/
│   ├── src/
│   │   ├── aplicacionprocesos/
│   │   │   ├── AplicacionProcesos.java       # Clase principal de la aplicación
│   │   │   ├── ProcessManagerApp.java        # Controlador de la interfaz
│   │   │   ├── ProcessModel.java             # Modelo de datos del proceso
│   │   │   └── FXMLWindow.fxml               # Diseño de la interfaz
│   │   ├── images/
│   │   │   ├── cosmo.png                     # Imagen de Cosmo
│   │   │   ├── wanda.png                     # Imagen de Wanda
│   │   │   ├── poofB.png                     # Efecto Puf
│   │   │   └── fish.png                      # Icono de la aplicación
│   │   └── META-INF/
│   │       └── MANIFEST.MF
│   ├── build/                                # Archivos compilados
│   ├── dist/                                 # Distribuciones generadas
│   ├── nbproject/                            # Configuración de NetBeans
│   ├── build.xml                             # Script de compilación Ant
│   └── manifest.mf                           # Manifiesto del JAR
├── .gitignore
└── README.md                                 # Este archivo
```

---

## ★ Componentes Principales

### ⊹ **AplicacionProcesos.java**
Clase principal que extiende `javafx.application.Application`. Responsable de:
- Inicializar la aplicación JavaFX
- Cargar el archivo FXML (FXMLWindow.fxml)
- Establecer el icono de la aplicación (fish.png)
- Establecer el título: "Gestoría de Cosmo y Wanda"

```java
public class AplicacionProcesos extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLWindow.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("images/fish.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Gestoría de Cosmo y Wanda");
        // ... resto del código
    }
}
```

### ⊹ **ProcessManagerApp.java**
Controlador FXML que maneja toda la lógica de la aplicación:

#### **Métodos Principales:**

- **`initialize()`**: Inicializa la tabla, configura las columnas y carga los procesos
- **`loadProcesses()`**: Ejecuta el comando `tasklist` y carga los procesos en la tabla
- **`filterProcesses(String searchTerm)`**: Filtra procesos por nombre o PID
- **`handleReiniciar()`**: Detiene y reinicia el proceso seleccionado
- **`handleEliminar()`**: Elimina el proceso seleccionado
- **`handleCrear()`**: Permite crear/iniciar un nuevo proceso
- **`showImagesTemporarily()`**: Muestra las imágenes de Cosmo/Wanda por 1 segundo

#### **Flujo de Carga de Procesos:**
```
1. Ejecuta: tasklist
2. Lee la salida línea por línea
3. Extrae: nombre del proceso y PID
4. Filtra: excluye svchost.exe
5. Crea: objetos ProcessModel
6. Agrega a: ObservableList
7. Muestra en: TableView
```

### ⊹ **ProcessModel.java**
Modelo de datos simple que representa un proceso:
```java
public class ProcessModel {
    private String name;  // Nombre del proceso (ej: notepad.exe)
    private int pid;      // ID del proceso (ej: 1234)
}
```

### ⊹ **FXMLWindow.fxml**
Archivo de diseño JavaFX que define:
- **TableView** con dos columnas: Nombre y PID
- **TextField** para búsqueda en tiempo real
- **Botones**: Crear, Reiniciar, Eliminar
- **ImageView** para mostrar los personajes (Cosmo, Wanda, efectos Puf)

---

## ★ Requisitos del Sistema

### ⊹ Software Requerido

- **Java Development Kit (JDK)**: 8 o superior
- **Sistema Operativo**: Windows (utiliza `tasklist` y `taskkill`)
- **Apache Ant**: 1.10 o superior
- **NetBeans IDE** (recomendado) o cualquier IDE Java compatible

### ⊹ Dependencias del Proyecto

```
✓ JavaFX SDK 8.0+
✓ JDK 1.8 (Java 8)
✓ Comandos del Sistema: tasklist, taskkill
```

---

## ★ Instalación y Configuración

### ⊹ 1. Clonar el Repositorio

```bash
git clone https://github.com/MeylinM/ProcessApp.git
cd ProcessApp
```

### ⊹ 2. Abrir el Proyecto en NetBeans

1. Abre **NetBeans IDE**
2. Selecciona **File** → **Open Project**
3. Navega a la carpeta `ProcessApp/AplicacionProcesos`
4. Haz clic en **Open Project**

NetBeans detectará automáticamente que es un proyecto Ant y configurará todo.

### ⊹ 3. Compilar el Proyecto

Desde la terminal dentro de `AplicacionProcesos/`:

```bash
ant build
```

### ⊹ 4. Ejecutar la Aplicación

```bash
ant run
```

O desde NetBeans: **Run** → **Run Project (F6)**

---

## ★ Cómo Usar la Aplicación

### ⊹ **Ver Procesos**
Al abrir la aplicación, automáticamente se cargan todos los procesos activos en el sistema en una tabla con sus nombres y PIDs.

### ⊹ **Buscar un Proceso**
Escribe el nombre del proceso o su PID en el campo de búsqueda. La tabla se filtra automáticamente en tiempo real.

### ⊹ **Eliminar un Proceso**
1. Selecciona un proceso de la tabla
2. Haz clic en el botón **"Eliminar"**
3. El proceso se termina inmediatamente (aparece Wanda con efecto "Puf" 💨)

### ⊹ **Reiniciar un Proceso**
1. Selecciona un proceso de la tabla
2. Haz clic en el botón **"Reiniciar"**
3. El proceso se detiene y se reinicia automáticamente (aparece Cosmo ✨)

### ⊹ **Crear un Nuevo Proceso**
1. Haz clic en el botón **"Crear"**
2. Ingresa el nombre del ejecutable (ej: `notepad.exe`, `calc.exe`)
3. Se inicia automáticamente y aparece en la lista

---

## ★ Arquitectura del Proyecto

El proyecto sigue el patrón **MVC (Model-View-Controller)**:

### ⊹ **Model (ProcessModel.java)**
- Representa un proceso del sistema
- Contiene: nombre y PID
- Datos simples sin lógica de negocio

### ⊹ **View (FXMLWindow.fxml)**
- Define la interfaz gráfica visual
- Componentes: tabla, campos de búsqueda, botones, imágenes
- Referencia al controlador (ProcessManagerApp)

### ⊹ **Controller (ProcessManagerApp.java)**
- Maneja eventos de usuario (clicks, búsqueda)
- Coordina entre la vista y el modelo
- Ejecuta comandos del sistema (tasklist, taskkill)
- Actualiza la interfaz dinámicamente

---

## ★ Interacción con el Sistema Operativo

### ⊹ **Comando tasklist**
```bash
tasklist
# Salida:
# Nombre de la imagen     PID
# notepad.exe            1234
# chrome.exe             5678
```

### ⊹ **Comando taskkill**
```bash
taskkill /F /PID 1234
# /F = Forzado (force termination)
# /PID = Por ID del proceso
```

### ⊹ **ProcessBuilder en Java**
```java
ProcessBuilder pb = new ProcessBuilder("tasklist");
Process process = pb.start();
BufferedReader reader = new BufferedReader(
    new InputStreamReader(process.getInputStream())
);
```

---

## ★ Elementos Temáticos

La aplicación incorpora referencias a "Los Padrinos Mágicos":

| Elemento | Función |
|---|---|
| **Cosmo** 🧚‍♂️ | Se muestra al reiniciar un proceso |
| **Wanda** 🧚‍♀️ | Se muestra al crear un nuevo proceso |
| **Efecto Puf** 💨 | Se muestra al eliminar un proceso |
| **Fish (Goldfish)** 🐠 | Icono de la aplicación |
| **Título** | "Gestoría de Cosmo y Wanda" |

---

## ★ Solución de Problemas Comunes

### ⊹ Error: "tasklist: comando no reconocido"
**Causa:** La aplicación solo funciona en **Windows**
**Solución:** Este proyecto utiliza comandos específicos de Windows. Si usas Linux o macOS, necesitas adaptar los comandos a `ps` y `kill`.

### ⊹ Error: "No se puede eliminar el proceso"
**Causa:** Posiblemente no tienes permisos suficientes
**Solución:** 
- Ejecuta la aplicación como **Administrador**
- El proceso está siendo usado por otro programa
- El proceso ya se cerró antes de intentar eliminarlo

### ⊹ Error: "JavaFX classes not found"
**Solución:** 
1. Asegúrate de tener JavaFX SDK instalado
2. En NetBeans, ve a **Tools** → **Libraries** y verifica que JavaFX esté bien configurado
3. Si es necesario, descarga JavaFX desde [gluonhq.com](https://gluonhq.com/products/javafx/)

### ⊹ Error de compilación en NetBeans
**Solución:**
1. Haz clic derecho en el proyecto
2. Selecciona **Clean and Build**
3. Si persiste, limpia la caché de NetBeans

### ⊹ Las imágenes no se muestran
**Causa:** La carpeta `images/` no existe o las imágenes no están en el lugar correcto
**Solución:**
- Asegúrate de que las imágenes están en: `src/aplicacionprocesos/images/`
- Verifica que los nombres de archivos sean exactos: `cosmo.png`, `wanda.png`, `poofB.png`, `fish.png`

---

## ★ Próximos Pasos y Mejoras Futuras

- [ ] Soporte multi-plataforma (Linux, macOS)
- [ ] Historial de procesos eliminados/reiniciados
- [ ] Información detallada del proceso (memoria, CPU)
- [ ] Exportar lista de procesos a archivo
- [ ] Tema oscuro/claro
- [ ] Notificaciones de procesos críticos
- [ ] Análisis de procesos sospechosos
- [ ] Integración con antivirus

---

## ★ Equipo de Desarrollo

Esta aplicación fue desarrollada por:

| Desarrolladora | Rol |
|---|---|
| [**MeylinM**](https://github.com/MeylinM) | Desarrolladora |
| [**Elbirehl**](https://github.com/Elbirehl) | Desarrolladora |

---

## ★ Licencia

Este proyecto ha sido desarrollado como parte de un programa de formación profesional de **Desarrollo de Aplicaciones Multiplataforma**.

---

<div align="center">
  <br>
  <p><strong>¡Gracias a Cosmo y Wanda por la inspiración! 🧚✨</strong></p>
  <br>
  <p>⚡ <strong>Última actualización:</strong> 2024 ⚡</p>
</div>
