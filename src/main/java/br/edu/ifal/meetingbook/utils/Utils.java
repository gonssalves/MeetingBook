package br.edu.ifal.meetingbook.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    
    // Método para copiar propriedades não nulas de um objeto de origem para um objeto de destino
    public static void copyNonNullProperties(Object source, Object target) {
        // Use a classe BeanUtils do Spring para copiar as propriedades de origem para destino
        // O terceiro argumento especifica quais propriedades copiar
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    // Método para obter os nomes das propriedades nulas de um objeto
    public static String[] getNullPropertyNames(Object source) {
        // Crie um BeanWrapper para o objeto de origem
        final BeanWrapper src = new BeanWrapperImpl(source);

        // Obtenha todas as propriedades do objeto de origem
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        // Crie um conjunto para armazenar os nomes das propriedades nulas
        Set<String> emptyNames = new HashSet<>();

        // Percorra todas as propriedades do objeto de origem
        for (PropertyDescriptor pd : pds) {
            // Obtenha o valor da propriedade atual
            Object srcValue = src.getPropertyValue(pd.getName());
            
            // Se o valor for nulo, adicione o nome da propriedade ao conjunto
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        // Converta o conjunto de nomes de propriedades nulas em um array de strings
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
