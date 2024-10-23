#define MAX_NOMBRE 25

enum Tipo {TV, RADIO}; 

struct T_Canal{
    char nombre[MAX_NOMBRE];
    int pos;            //posicion que ocupa el canal en la lista
    enum Tipo tipo;     //canal de radio o television
    struct T_Canal*  sig;   //referencia al siguiente canal
};


/**
 * @brief Crea un nuevo canal.
 * 
 * Esta función se encarga de inicializar la lista de canales.
 * 
 * @param canales Doble puntero a una estructura T_Canal donde se almacenarán los canales en un futuro.
 */
void crear(struct T_Canal** canales);


/**
 * @brief Destruye la lista de canales.
 *
 * Esta función libera la memoria asignada para cada canal, dejando el @p canales apuntando a NULL.
 *
 * @param canales Dirección de memoria del puntero a la cabeza de la lista.
 */
void destruir(struct T_Canal** canales);


/**
 * @brief Muestra la información de los canales.
 *
 * Esta función toma un puntero a una estructura T_Canal y muestra la información
 * contenida en los canales.
 *
 * @param canales Puntero a una estructura T_Canal que contiene la información de los canales.
 */
void mostrar(struct T_Canal*  canales);


/**
 * @brief Inserta un nuevo canal en la lista de canales.
 *
 * Esta función inserta un nuevo canal en la posición especificada dentro de la lista de canales. Si en la lista de canales ya existe un canal en dicha 
posicion, el nuevo se insertar en la posición indicada y el canal antiguo (y los consecutivos en posiciones mayores) se desplazaran una posición. 
 *
 * @param canales Dirección de memoria del puntero a la cabeza de la lista.
 * @param nombreCanal Nombre del canal a insertar.
 * @param posCanal Posición en la que se debe insertar el nuevo canal.
 * @param tipoCanal Tipo del canal a insertar.
 */
void insertar(struct T_Canal* *canales, char *nombreCanal, int posCanal, enum Tipo tipoCanal);

/**
 * @brief Elimina un canal de la lista de canales.
 *
 * Esta función busca un canal por su nombre en la lista de canales proporcionada
 * y lo elimina si es encontrado.
 *
 * @param canales Dirección de memoria del puntero a la cabeza de la lista.
 * @param nombreCanal Nombre del canal a eliminar.
 * @return int Retorna 0 si el canal fue eliminado exitosamente, o un valor negativo si ocurrió un error.
 */
int eliminar(struct T_Canal** canales, char *nombreCanal);


/**
 * @brief Extrae un canal de radio de la lista de canales de origen.
 *
 * @param origen Dirección de memoria dónde está el puntero a la cabeza de la lista.
 * @return Dirección de memoria de la lista de solo canales de radio extraída. Debes mover, no pedir nueva memoria para maxima nota.
 */
struct T_Canal* extraerRadio(struct T_Canal**origen);

