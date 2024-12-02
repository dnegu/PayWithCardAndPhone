# PayWithCardAndPhone 🚀

Este proyecto es un sistema de pago NFC que incluye tres componentes principales:  

1. **NFC Wallet App**: Aplicación móvil que actúa como un monedero virtual.  
2. **NFC Reader App**: Aplicación móvil que procesa pagos utilizando NFC.  
3. **Backend con Stripe**: Un servidor Node.js que procesa los pagos a través de Stripe.  

## Tabla de Contenido  
- [Tecnologías Utilizadas](#tecnologías-utilizadas)  
- [Estructura del Proyecto](#estructura-del-proyecto)  
- [Características](#características)  
- [Instalación y Configuración](#instalación-y-configuración)  
  - [1. NFC Wallet App](#1-nfc-wallet-app)  
  - [2. NFC Reader App](#2-nfc-reader-app)  
  - [3. Backend con Stripe](#3-backend-con-stripe)  
- [Cómo Usarlo](#cómo-usarlo)  
- [Contribuciones](#contribuciones)  
- [Licencia](#licencia)  

---

## Tecnologías Utilizadas  
- **Kotlin**: Para el desarrollo de aplicaciones Android.  
- **NFC API**: Para comunicación NFC en dispositivos móviles.  
- **Retrofit y OkHttp**: Comunicación con el backend.  
- **Node.js**: Backend para procesar los pagos.  
- **Stripe API**: Gestión de transacciones y pagos.  

---

## Estructura del Proyecto  
El proyecto está dividido en tres módulos principales:  

1. **NFC Wallet App**  
   - Implementa la funcionalidad de un monedero digital usando HCE (Host Card Emulation).  
2. **NFC Reader App**  
   - Lee datos de tarjetas NFC y procesa pagos mediante el backend.  
3. **Backend con Stripe**  
   - Proporciona APIs REST para procesar los pagos mediante Stripe.  

---

## Características  

### NFC Wallet App  
- Emula una tarjeta NFC utilizando HCE.  
- Proporciona credenciales de pago al lector NFC.  

### NFC Reader App  
- Detecta y procesa tarjetas NFC (ya sean emuladas o físicas).  
- Integra Stripe para procesar pagos en tiempo real.  

### Backend con Stripe  
- Implementa endpoints para procesar pagos con clientes y tarjetas físicas.  
- Gestiona transacciones de forma segura a través de la API de Stripe.  

---

## Instalación y Configuración  

### 1. NFC Wallet App  
#### Requisitos  
- Android Studio Bumblebee o superior.  
- Dispositivo con NFC habilitado.  

#### Pasos  
1. Clona el repositorio de la aplicación Wallet en tu máquina local.
2. Abre el proyecto en **Android Studio**.
3. Asegúrate de que el SDK de Android esté actualizado y que las dependencias del proyecto se hayan resuelto.
4. Configura el archivo `AndroidManifest.xml` para solicitar los permisos de NFC.
5. Ejecuta la aplicación en un dispositivo físico con NFC habilitado (esto no funcionará en un emulador).
6. En la aplicación, el usuario puede añadir métodos de pago (tarjetas virtuales) y guardar la información para realizar pagos.

### 2. NFC Reader App  
#### Requisitos  
- Android Studio Bumblebee o superior.  
- Dispositivo con NFC habilitado.

#### Pasos  
1. Clona el repositorio de la aplicación **NFC Reader**.
2. Abre el proyecto en **Android Studio**.
3. Asegúrate de que las dependencias del proyecto estén correctamente resueltas.
4. Configura los permisos necesarios en el archivo `AndroidManifest.xml` para permitir el acceso a NFC.
5. En el `MainActivity` de la aplicación, se gestiona el proceso de lectura de las tarjetas NFC y se envían los datos al backend para su validación y procesamiento del pago.
6. Ejecuta la aplicación en un dispositivo físico con NFC habilitado. Cuando el usuario acerque una tarjeta NFC, la aplicación leerá la información y realizará el pago mediante el backend de Stripe.

### 3. Backend con Stripe  
#### Requisitos  
- Node.js 16 o superior.  
- Cuenta de Stripe para obtener las credenciales de la API.  
- Base de datos de tu elección (MongoDB, MySQL, etc.) para almacenar datos de clientes y transacciones.

#### Pasos  
1. Clona el repositorio del backend.
2. Abre el proyecto en tu editor de código preferido.
3. Ejecuta el comando para instalar las dependencias del proyecto.
4. Crea un archivo `.env` con las claves de tu cuenta de **Stripe**.
5. Configura las rutas en el backend para procesar los pagos mediante Stripe.
6. Asegúrate de tener configurada la conexión con la base de datos y las rutas para aceptar solicitudes de pago desde la aplicación.
7. Ejecuta el backend.
8. El backend recibirá las solicitudes de pago desde la aplicación NFC Reader, procesará los pagos y devolverá una respuesta de éxito o error.

### Integración entre la NFC Wallet App, NFC Reader y Stripe Backend  
- La **NFC Wallet App** permite a los usuarios guardar métodos de pago.
- La **NFC Reader App** lee las tarjetas NFC y envía los datos al backend.
- El **Backend con Stripe** procesa los pagos y responde con el resultado al NFC Reader.

## Conclusión  
Este proyecto integra la tecnología NFC con el procesamiento de pagos utilizando Stripe. Permite a los usuarios realizar pagos de manera segura utilizando tarjetas NFC o tarjetas virtuales. Puedes personalizar y expandir este proyecto según las necesidades de tu negocio.

## Cómo Usarlo  

### 1. NFC Wallet App  
1. Una vez que la aplicación esté instalada y configurada, abre la app y accede a la pantalla de "Métodos de pago".  
2. El usuario puede añadir un nuevo método de pago (tarjeta virtual) escaneando o ingresando la información de la tarjeta NFC.  
3. Cuando se desee realizar un pago, la aplicación usará la tarjeta registrada y enviará el monto correspondiente al backend para procesarlo.

### 2. NFC Reader App  
1. La aplicación se debe ejecutar en un dispositivo con NFC habilitado.  
2. Cuando se acerque una tarjeta NFC (ya sea física o virtual), la aplicación leerá la información de la tarjeta y la enviará al backend para su validación.  
3. Dependiendo de la respuesta del servidor, el pago se procesará correctamente o se notificará un error.

### 3. Backend con Stripe  
1. Asegúrate de tener las credenciales correctas de Stripe configuradas en el archivo `.env` del backend.  
2. El servidor escucha solicitudes de la aplicación móvil para procesar pagos utilizando la API de Stripe.  
3. En el backend, se manejan las solicitudes de pago enviadas desde la NFC Wallet App o NFC Reader App, validando la información y completando las transacciones mediante Stripe.

## Contribuciones  

Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una rama para tu feature o corrección de bug (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza los cambios necesarios y haz commit (`git commit -am 'Agregada nueva funcionalidad'`).
4. Sube tus cambios a tu fork (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request desde tu rama hacia el repositorio principal.

### Reglas para Contribuir  
- Asegúrate de que tu código esté correctamente probado.  
- Sigue el estilo de codificación utilizado en el proyecto.  
- Añade documentación para cualquier nueva funcionalidad que añadas.

## Licencia  

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.
