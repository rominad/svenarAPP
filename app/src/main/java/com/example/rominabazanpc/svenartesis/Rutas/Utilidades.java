package com.example.rominabazanpc.svenartesis.Rutas;

public class Utilidades {

    //VARIABLES PUBLICAS QUE UTILIZAN TODOS LOS ACTIVITYS.
    public static final int ROL_USUARIO = 0;
    public static int USERID = 0;
    public static int USERROL = 0;

    //TENER EN CUENTA QUE EL CELU TIENE QUE ESTAR CONECTADO A LA MISMA RED!!!!
    private static final String ip = "http://192.168.0.13/"; //para ingresar desde BD Local agregar ip.
    //LOGIN
    public static final String LOGGIN_REQUEST_URL = ip + "svenarAndroidBD/Login.php";
    //DIARIA
    public static final String LISTAR_TODO_VEHICULOS_DIARIA_REQUEST_URL = ip + "svenarAndroidBD/DiariaGetAllVehiculos.php";
    public static final String BUSCAR_VEHICULOS_DIARIA_REQUEST_URL = ip + "svenarAndroidBD/DiariaFindVehiculos.php";
    public static final String CARGAR_DIARIA_REQUEST_URL = ip + "svenarAndroidBD/DiariaInsert.php";
    //HOJA DE SERVICIO
    public static final String LISTAR_TODO_VEHICULOS_REQUEST_URL = ip + "svenarAndroidBD/HojaServicioGetAllVehiculos.php";
    public static final String LISTAR_VEHICULOS_REQUEST_URL = ip + "svenarAndroidBD/HojaServicioFindVehiculos.php";
    public static final String LISTAR_ARTICULOS_HOJASERVICIO_REQUEST_URL = ip + "svenarAndroidBD/HojaServicioFindArticulos.php";
    public static final String CARGAR_ARTICULOS_HOJASERVICIO_REQUEST_URL = ip + "svenarAndroidBD/HojaServicioInsertArticulos.php";
    public static final String LISTAR_PRODUCTOS_REQUEST_URL = ip + "svenarAndroidBD/HojaServicioGetAllProductos.php";
    //BUSQUEDA DE TURNOS
    public static final String BUSQUEDA_TURNOSX_FECHA_REQUEST_URL = ip + "svenarAndroidBD/TurnosFindByFecha.php";
    //CLIENTES
    public static final String LISTAR_PERSONAS_REQUEST_URL = ip + "svenarAndroidBD/ClientesFindPersona.php";
    public static final String LISTAR_TODO_PERSONAS_REQUEST_URL = ip + "svenarAndroidBD/ClientesGetAll.php";
    //STOCK
    public static final String LISTAR_STOCK_REQUEST_URL = ip + "svenarAndroidBD/StockFindProductos.php";
    public static final String LISTAR_TODO_STOCK_REQUEST_URL = ip + "svenarAndroidBD/StockGetAllProductos.php";
    //TURNOS
    public static final String LISTAR_TURNOS_REQUEST_URL = ip + "svenarAndroidBD/TurnosFindTurno.php";
    public static final String LISTAR_TODOS_TURNOS_REQUEST_URL = ip + "svenarAndroidBD/TurnosGetAllTurnos.php";
    //FACTURAS
    public static final String LISTAR_TODOS_FACTURAS_REQUEST_URL = ip + "svenarAndroidBD/FacturasGetAll.php";
    public static final String LISTAR_FACTURAS_REQUEST_URL = ip + "svenarAndroidBD/FacturasFind.php";


    public static final String BUSCAR_DETALLE_FACTURA_VENTA_REQUEST_URL = ip + "svenarAndroidBD/FacturasFindDetalleVenta.php";
    public static final String BUSCAR_DETALLE_FACTURA_COMPRA_REQUEST_URL = ip + "svenarAndroidBD/FacturasFindDetalleCompra.php";


}