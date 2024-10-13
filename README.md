# AD-UD1-ManejoFicheros
Trabajo de acceso a datos de Manejo de ficheros Unai Pastor Martínez
# Gestión de Jugadores

Este programa permite gestionar información sobre jugadores mediante varias implementaciones de almacenamiento, incluyendo archivos de texto, binarios, objetos y XML. Los usuarios pueden añadir, eliminar, modificar y listar jugadores (CRUD)a través de un menú interactivo.

### Paquete inicializar

- **Inicio**: Clase principal que inicia el programa y inicia el menú.

### Paquete modelo

- **Jugador**: Clase que representa a un jugador. Implementa la interfaz `Serializable` y tiene los siguientes atributos:
  - id: ID único del jugador (int).
  - nick: Apodo del jugador(String).
  - experience: Experiencia acumulada del jugador(int).
  - lifeLevel: Nivel de vida del jugador(int).
    `coins: Cantidad de monedas del jugador(int).

 Tiene contructores setters,getters y tostring para sacar la informacion por pantalla del jugador

### Paquete idao

Contiene interfaces para definir las operaciones CRUD (Crear, Leer, Actualizar y Eliminar) para los distitos tipos de almacenamiento:

- **JugadorDao**: Interfaz base que define métodos abstractos:
  - añadirJugador(Jugador jugador)
  - eliminarJugador(int id)
  -modificarJugador(Jugador jugador)
  - buscarPorID(int id)
  - listarJugadores()

- **JugadorTextoDAO**:Implementa BufferedReader/BufferedWriter para jugadores.txt.

- **JugadorBinarioDAO**: Implementación de DataInputStream/DataOutputStream para jugadores.dat

- **JugadorObjetoDAO**: Implementación de ObjectInputStream/ObjectOutputStream para jugadoresobj.dat
- **JugadorAleatorioDAO**: Implementación de RandomAccessFile para jugadorerd.dat

- **JugadorXMLDAO**: Implementación de DOM para jugadores.xml.

### Paquete vista

- **Menu**: 
  - **Alta de Jugador**: Agregar un nuevo jugador.
  - **Baja de Jugador**: Eliminar un jugador existente.
  - **Modificación de Jugador**: Modificar la información de un jugador.
  - **Listado por ID**: Mostrar la información de un jugador específico por su ID.
  - **Listado General**: Mostrar la información de todos los jugadores.
  - **Configuración de Almacenamiento**: Permitir al usuario seleccionar el tipo de almacenamiento de datos (texto, binario, objeto, acceso aleatorio, XML).
  - **Salir**: Terminar el programa.

### Funcionamiento del Programa

1. **Inicio**:inicia el programa y saca el menú
   
2. **Interacción del Usuario**: A través del menú, el usuario selecciona lo que quere hacer y lo manda al método correspondiente

3. **Almacenamiento de Jugadores**: Dependiendo de la configuración elegida los datos se almacenan utilizando la implementación de JugadorDao.

4. **Validación de Datos**: El id es único asignandose de manera automática tal que siempre es +1 del ultimo jugador con el id mas alto, también se comprueba que el numero no sea negativo y que el usuario no pete el programa


## Ejecución

Para ejecutar el programa ejecutar la clase Inicio del paquete inicializar
