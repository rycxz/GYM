---------------------------Es una aplicación creada por ricardoporaqui.---------------------------

-----------------------|||||||||||||||||||||||||||||||||||||||||||||||-----------------------------------------------

Esta aplicación tiene un funcionamiento muy sencillo. El usuario puede introducir una rutina de entrenamiento, 
la cual podrá modificar. Dependiendo del día de la semana (por ejemplo, lunes), al ingresar a la aplicación se mostrarán 
los ejercicios correspondientes a ese día. Además, se permitirá ingresar los pesos utilizados en cada ejercicio, con el 
fin de realizar un seguimiento del progreso.

La rutina y los pesos se guardarán en una base de datos, y el usuario podrá consultarlos en cualquier semana para 
visualizar su evolución.

En cuanto a la seguridad, se implementará el sistema CSRF para la validación de sesiones. Para acceder a la creación de
 rutinas y demás funcionalidades, el usuario deberá verificar su cuenta mediante correo electrónico. De lo contrario, no
 podrá registrarse ni utilizar la aplicación.