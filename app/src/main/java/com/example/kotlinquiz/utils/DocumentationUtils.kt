package com.example.kotlinquiz.utils

import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties

/**
 * Utility class for generating documentation for code components.
 * This class provides methods to automatically generate documentation
 * for classes, methods, and properties in the codebase.
 */
object DocumentationUtils {
    /**
     * Generates documentation for a given class including its properties and methods.
     *
     * @param clazz The class to document
     * @return A formatted string containing the class documentation
     */
    fun generateClassDocumentation(clazz: KClass<*>): String {
        val builder = StringBuilder()
        
        builder.append("# ${clazz.simpleName}

")
        
        // Class description from KDoc if available
        clazz.annotations.forEach { annotation ->
            builder.append("${annotation}
")
        }
        
        // Document properties
        val properties = clazz.declaredMemberProperties
        if (properties.isNotEmpty()) {
            builder.append("
## Properties

")
            properties.forEach { property ->
                builder.append("### ${property.name}
")
                builder.append("- Type: ${property.returnType}
")
                property.annotations.forEach { annotation ->
                    builder.append("- Annotation: $annotation
")
                }
                builder.append("
")
            }
        }
        
        // Document methods
        val methods = clazz.declaredMemberFunctions
        if (methods.isNotEmpty()) {
            builder.append("
## Methods

")
            methods.forEach { method ->
                builder.append("### ${method.name}
")
                builder.append("- Return type: ${method.returnType}
")
                method.parameters.forEach { param ->
                    builder.append("- Parameter ${param.name}: ${param.type}
")
                }
                method.annotations.forEach { annotation ->
                    builder.append("- Annotation: $annotation
")
                }
                builder.append("
")
            }
        }
        
        return builder.toString()
    }

    /**
     * Generates API documentation for a list of classes
     *
     * @param classes List of classes to document
     * @return A formatted string containing the API documentation
     */
    fun generateApiDocumentation(classes: List<KClass<*>>): String {
        val builder = StringBuilder()
        builder.append("# API Documentation

")
        
        classes.forEach { clazz ->
            builder.append(generateClassDocumentation(clazz))
            builder.append("
---

")
        }
        
        return builder.toString()
    }

    /**
     * Generates markdown documentation for database schema
     *
     * @param entities List of Room entity classes
     * @return A formatted string containing the database schema documentation
     */
    fun generateDatabaseSchema(entities: List<KClass<*>>): String {
        val builder = StringBuilder()
        builder.append("# Database Schema

")
        
        entities.forEach { entity ->
            builder.append("## ${entity.simpleName}

")
            
            // Document table properties
            entity.declaredMemberProperties.forEach { property ->
                builder.append("### ${property.name}
")
                builder.append("- Type: ${property.returnType}
")
                // Document Room annotations
                property.annotations
                    .filter { it.annotationClass.simpleName?.contains("Room") == true }
                    .forEach { annotation ->
                        builder.append("- $annotation
")
                    }
                builder.append("
")
            }
            builder.append("
---

")
        }
        
        return builder.toString()
    }
}
package com.example.kotlinquiz.utils

import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.declaredMemberProperties

/**
 * Utility class for generating documentation for code components.
 * This class provides methods to automatically generate documentation
 * for classes, methods, and properties in the codebase.
 */
object DocumentationUtils {
    /**
     * Generates documentation for a given class including its properties and methods.
     *
     * @param clazz The class to document
     * @return A formatted string containing the class documentation
     */
    fun generateClassDocumentation(clazz: KClass<*>): String {
        val builder = StringBuilder()
        
        builder.append("# ${clazz.simpleName}

")
        
        // Class description from KDoc if available
        clazz.annotations.forEach { annotation ->
            builder.append("${annotation}
")
        }
        
        // Document properties
        val properties = clazz.declaredMemberProperties
        if (properties.isNotEmpty()) {
            builder.append("
## Properties

")
            properties.forEach { property ->
                builder.append("### ${property.name}
")
                builder.append("- Type: ${property.returnType}
")
                property.annotations.forEach { annotation ->
                    builder.append("- Annotation: $annotation
")
                }
                builder.append("
")
            }
        }
        
        // Document methods
        val methods = clazz.declaredMemberFunctions
        if (methods.isNotEmpty()) {
            builder.append("
## Methods

")
            methods.forEach { method ->
                builder.append("### ${method.name}
")
                builder.append("- Return type: ${method.returnType}
")
                method.parameters.forEach { param ->
                    builder.append("- Parameter ${param.name}: ${param.type}
")
                }
                method.annotations.forEach { annotation ->
                    builder.append("- Annotation: $annotation
")
                }
                builder.append("
")
            }
        }
        
        return builder.toString()
    }

    /**
     * Generates API documentation for a list of classes
     *
     * @param classes List of classes to document
     * @return A formatted string containing the API documentation
     */
    fun generateApiDocumentation(classes: List<KClass<*>>): String {
        val builder = StringBuilder()
        builder.append("# API Documentation

")
        
        classes.forEach { clazz ->
            builder.append(generateClassDocumentation(clazz))
            builder.append("
---

")
        }
        
        return builder.toString()
    }

    /**
     * Generates markdown documentation for database schema
     *
     * @param entities List of Room entity classes
     * @return A formatted string containing the database schema documentation
     */
    fun generateDatabaseSchema(entities: List<KClass<*>>): String {
        val builder = StringBuilder()
        builder.append("# Database Schema

")
        
        entities.forEach { entity ->
            builder.append("## ${entity.simpleName}

")
            
            // Document table properties
            entity.declaredMemberProperties.forEach { property ->
                builder.append("### ${property.name}
")
                builder.append("- Type: ${property.returnType}
")
                // Document Room annotations
                property.annotations
                    .filter { it.annotationClass.simpleName?.contains("Room") == true }
                    .forEach { annotation ->
                        builder.append("- $annotation
")
                    }
                builder.append("
")
            }
            builder.append("
---

")
        }
        
        return builder.toString()
    }
}
