package br.com.ronanjunior.linketinder.utils

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
}
