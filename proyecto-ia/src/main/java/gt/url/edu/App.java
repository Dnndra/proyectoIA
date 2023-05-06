package gt.url.edu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Reader reader = new Reader();
        HashMap<String, List<Word>> dataSet = new HashMap<String, List<Word>>();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        System.out.print("Por favor ingrese el path del archivo>");
        input = scanner.nextLine();
        dataSet = reader.training(
                input,
                dataSet);
        while (!input.equals("exit")) {
            System.out.print("Por favor ingrese la cadena >");
            input = scanner.nextLine();
            if (input.equals("reentrenar")){
                System.out.print("Por favor ingrese el path >");
                input = scanner.nextLine();
                dataSet = reader.training(input,   dataSet);
                continue;
            }
            if (input.equals("exit")) {
                break;
            }

            removePunctuation(input);
            var normalized_string = Normalizer.normalize(input, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "").toLowerCase().trim().split("\s");
            var trueEtiqueta = "";
            var falseEtiqueta = "";
            BigDecimal trueProbabilidad = BigDecimal.ZERO;
            BigDecimal falseProbabilidad = BigDecimal.ONE;
            BigDecimal normalizador = BigDecimal.ZERO;

            Integer i = 0;
            for (var V : reader.hashMap.values()) {
                i += V;
            } 
            for (var etiqueta : dataSet.entrySet()) {
                BigDecimal j = new BigDecimal(reader.hashMap.get(etiqueta.getKey()));
                falseProbabilidad = j.divide(new BigDecimal(i), 10, RoundingMode.HALF_EVEN);
                Integer k = 0;
                for (var K : dataSet.get(etiqueta.getKey())) {
                    k += K.count;
                }
                for (var texto : normalized_string) {
                    falseEtiqueta = etiqueta.getKey();
                    Word valor = null;
                    Optional<Word> stream = null;
                    stream = etiqueta.getValue().stream().filter(x -> x.value.equals(texto)).findFirst();
                    if (!stream.isEmpty()) {
                        valor = stream.get();
                        BigDecimal x = new BigDecimal(valor.count.toString());
                        BigDecimal y = new BigDecimal(k);
                        BigDecimal n = new BigDecimal(reader.getPalabras().toString());
                        falseProbabilidad = falseProbabilidad.multiply(x.add(BigDecimal.ONE).divide(y.add(n), 10, RoundingMode.HALF_EVEN));
                    } else {
                        BigDecimal y = new BigDecimal(k);
                        BigDecimal n = new BigDecimal(reader.getPalabras().toString());
                        falseProbabilidad = falseProbabilidad.multiply(BigDecimal.ONE.divide(y.add(n), 10, RoundingMode.HALF_EVEN));
                    }
                }
                if (trueProbabilidad.compareTo(falseProbabilidad) == -1) {
                    trueEtiqueta = falseEtiqueta;
                    trueProbabilidad = falseProbabilidad;
                }
                normalizador = normalizador.add(falseProbabilidad);
            }
            System.out.println(input + " pertenece a " + trueEtiqueta + " con una probabilidad de " + trueProbabilidad/*.divide(normalizador, 10, RoundingMode.HALF_EVEN).multiply(new BigDecimal("100.00")) + " %"*/);
        }
        scanner.close();
    }

    static String removePunctuation(String s) {
        StringBuilder res = new StringBuilder();
        for (Character c : s.toCharArray()) {
            if(Character.isLetterOrDigit(c))
                res.append(c);
        }
        return res.toString();
    }

}
