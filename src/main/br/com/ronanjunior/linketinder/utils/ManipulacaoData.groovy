package main.br.com.ronanjunior.linketinder.utils

import java.text.SimpleDateFormat

class ManipulacaoData {
    String formatoPadrao = "dd/MM/yyyy"
    SimpleDateFormat sdf = new SimpleDateFormat(formatoPadrao)

    String dateParaString(Date data) {
        if (data)
            return sdf.format(data)
        return null
    }

    Date stringParaDate(String dataString) {
        try {
            return sdf.parse(dataString)
        } catch (Exception e) {
            e.printStackTrace()
            return null
        }
    }

    void setFormato(String novoFormato) {
        formatoPadrao = novoFormato
        sdf = new SimpleDateFormat(formatoPadrao)
    }

}
