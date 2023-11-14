package br.com.ronanjunior.linketinder.utils

import com.azul.crs.digest.ByteCodeProcessor

import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.time.LocalDate

class MapperUtils {
    Map<String, Object> converterObjectToMap(Object obj) {
        Map<String, Object> map = [:]
        obj.properties.each { chave, valor ->
            if (valor instanceof Collection) {
                List<Object> list = []
                valor.eachWithIndex { element, index ->
                    list.add(converterObjectToMap(element))
                }
                map[chave] = list
            }
            else if (valor.getClass().getName().startsWith("br.com.ronanjunior.linketinder"))
                map[chave] = converterObjectToMap(valor)
            else if (chave != "class")
                map[chave] = valor
            else
                return
        }
        return map
    }

    <T> T converterMapToObject(Map<String, Object> map, Class<T> type) {
        T obj = type.newInstance()

        map.each { String chave, valor ->
            if (valor && hasPropertyCaseInsensitive(type, chave)) {
                def field = findFieldCaseInsensitive(type, chave)
                field.setAccessible(true)

                def fieldType = field.type
                if (valor instanceof List) {
                    def listType = getListType(obj, chave)
                    List<Object> list = []
                    valor.each { element ->
                        list.add(converterMapToObject(element, listType))
                    }
                    field.set(obj, list)
                } else if (valor instanceof Map) {
                    field.set(obj, converterMapToObject(valor, fieldType))
                } else {
                    def convertedValue = converterPropertyValue(valor, fieldType)
                    field.set(obj, convertedValue)
                }
            }
        }

        return obj
    }

    private <T> Class<?> getListType(T obj, String fieldName) {
        def genericType = findFieldCaseInsensitive(obj.getClass(), fieldName).genericType
        return genericType.actualTypeArguments[0]
    }

    private <T> boolean hasPropertyCaseInsensitive(Class<T> type, String propertyName) {
        type.declaredFields.any { it.name.equalsIgnoreCase(propertyName) }
    }

    private <T> Field findFieldCaseInsensitive(Class<T> type, String fieldName) {
        type.declaredFields.find { it.name.equalsIgnoreCase(fieldName) }
    }

    private converterPropertyValue(valor, Class<?> fieldType) {
        if (fieldType == LocalDate) {
            return LocalDate.parse(valor.toString())
        } else {
            return valor
        }
    }

}
