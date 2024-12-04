# Mobile Challenge - Ualá

## 📱 Descripción del Proyecto

Aplicación móvil de búsqueda y visualización de ciudades con las siguientes características principales:
- Descarga y filtrado de una lista de ciudades
- Búsqueda por prefijo
- Visualización de ciudades en lista scrollable
- Gestión de ciudades favoritas
- Navegación entre lista y mapa
- Pantalla de información detallada de ciudades

## 🎯 Objetivos

El proyecto busca evaluar:
- Habilidades de resolución de problemas
- Juicio de experiencia de usuario (UX)
- Calidad de código

## 🛠 Tecnologías

### Android
- Lenguaje: Kotlin
- UI: Jetpack Compose
- Última versión de Android API

## 📋 Funcionalidades

- Descarga de lista de ciudades desde JSON
- Filtrado de ciudades por prefijo (case insensitive)
- Ordenamiento alfabético
- Visualización de detalles de ciudades
- Selección y persistencia de ciudades favoritas
- Navegación entre lista y mapa
- Adaptación dinámica a orientación de pantalla

## 🔍 Estrategia de Búsqueda

### Algoritmo de Filtrado

La implementación del algoritmo de búsqueda se basa en una estrategia optimizada utilizando Room (biblioteca de persistencia de Android) con las siguientes características:

#### Características Principales
- **Persistencia de Datos**: Almacenamiento de la lista de ciudades en base de datos local
- **Paginación**: Carga incremental de resultados para optimizar el rendimiento
- **Delegación de Procesamiento**: Transferencia de responsabilidades de búsqueda y ordenamiento a la capa de base de datos

#### Beneficios
- **Velocidad de Procesamiento**: Mejora significativa en búsquedas posteriores
- **Eficiencia de Recursos**: Reducción del consumo de memoria mediante carga incremental
- **Escalabilidad**: Manejo eficiente de grandes conjuntos de datos

## 🚀 Optimizaciones

### Representación de Datos

#### Estrategia de Almacenamiento
- **Base de Datos**: SQLite (Base de Datos Relacional)
- **Objetivo**: Optimizar búsquedas y filtrado de ciudades

#### Beneficios de la Implementación
- **Flexibilidad de Consultas**: Aprovechamiento completo del lenguaje SQL
- **Reducción de Complejidad Algorítmica**:
  - Eliminación de algoritmos de búsqueda complejos
  - Transferencia de operaciones de filtrado y ordenamiento a la base de datos
- **Eficiencia en Procesamiento**:
  - Consultas nativas de SQL
  - Indexación y optimización inherente a bases de datos relacionales

#### Ventajas Técnicas
- Eliminación de búsquedas secuenciales en estructuras de datos mas complejas como:
  - HashSet
  - TreeSet
  - HashMap
  - Array binario
- Aprovechamiento de índices y optimizadores de consultas SQL
- Mejor rendimiento en operaciones de filtrado y ordenamiento
- Escalabilidad para grandes conjuntos de datos

## 📝 Decisiones de Diseño

### Selección de Dependencias

#### Solicitudes HTTP
- **Retrofit**
  - Biblioteca principal para solicitudes de red
  - Compatibilidad nativa con proyectos Android
  - Configuración e implementación sencilla

- **OkHttp3 Interceptor**
  - Complemento de Retrofit
  - Flexibilidad en configuración de cachés
  - Gestión avanzada de solicitudes de red

- **Gson**
  - Biblioteca de parseo JSON
  - Conversión eficiente de JSON a data classes
  - Integración fluida con Retrofit

#### Inyección de Dependencias
- **Koin**
  - Seleccionado por su simplicidad
  - Ideal para proyectos pequeños
  - Curva de aprendizaje reducida
  - Configuración mínima y directa

#### Persistencia de Datos
- **Room**
  - Biblioteca oficial de Google para bases de datos locales
  - Configuración sencilla
  - Curva de aprendizaje accesible
  - Perfecta compatibilidad con librerías de paginación

#### Paginación
- **Paging 3**
  - Alta compatibilidad con:
    * Room
    * Retrofit
    * Jetpack Compose
  - Capacidades avanzadas de personalización
  - Autogestión eficiente de paginado

#### Testing
- **Mockk**
  - Librería de mocking específica para Kotlin
  - Sintaxis DSL intuitiva y expresiva
  - Facilita la creación de mocks en pruebas unitarias

### Principios de Diseño
- Priorización de librerías con:
  - Simplicidad de implementación
  - Curva de aprendizaje reducida
  - Alta compatibilidad entre componentes
  - Recomendaciones oficiales de Google

## 🧠 Decisiones Funcionales

### Estrategia de Persistencia de Datos

#### Objetivo Principal
Optimizar recursos y tiempo de procesamiento mediante una estrategia de almacenamiento inteligente.

#### Enfoque de Implementación
- **Carga Inicial Completa**:
  - Descarga y almacenamiento de la lista completa de ciudades
  - Aprovechamiento de la baja carga computacional para dispositivos modernos

#### Beneficios de la Estrategia
- **Reducción Drástica de Tiempo de Procesamiento**
  - Tiempo de procesado inicial: 12 segundos
  - Tiempo de procesado optimizado: Menos de 1 segundo

#### Técnicas de Optimización
- **Persistencia de Datos**
  - Almacenamiento completo tras primera carga
  - Eliminación de descargas repetitivas
  - Mejora significativa en rendimiento de consultas subsecuentes

- **Paginación Progresiva**
  - Carga incremental de datos
  - Reducción de consumo de recursos
  - Mejora de la experiencia de usuario

#### Justificación Técnica
- El peso de la lista de ciudades es considerado manejable para dispositivos móviles actuales
- La estrategia prioriza velocidad y eficiencia sobre ahorro de almacenamiento
- Optimización que beneficia directamente la experiencia del usuario


