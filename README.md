# Cursed Crosshair (Forge 1.20.1)

Mod de cliente para Minecraft Java 1.20.1 / Forge 47.x. Añade una cruceta configurable, diseño rojo inspirado en energía maldita, menú dentro del juego, tecla K y botón en Opciones.

## Requisitos
- Java 17
- Minecraft Forge 1.20.1 (47.x)
- Gradle 8.x compatible con ForgeGradle 6 (el wrapper se puede generar con `gradle wrapper`)

## Compilar
En un ordenador con Java 17 y acceso a Internet:
1. Descomprime el proyecto.
2. Abre una terminal dentro de la carpeta `CursedCrosshair`.
3. Ejecuta `gradle wrapper` si no hay wrapper disponible.
4. Ejecuta `./gradlew build` (Windows: `gradlew.bat build`).
5. El JAR debería quedar en `build/libs/CursedCrosshair-1.0.0.jar`.

En Android/launchers móviles puede ser difícil compilar directamente; es más fiable compilar en PC y copiar el JAR a `mods`.

## Opciones iniciales
- Activar/desactivar cruceta
- 4 estilos
- 6 colores
- Tamaño
- Desplazamiento X/Y
- Resplandor
- Guardado en config

## Nota
Esta es una primera versión de código fuente y necesita compilarse y probarse en tu entorno. No se afirma que el JAR esté ya compilado o probado.
