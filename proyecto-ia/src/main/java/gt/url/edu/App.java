package gt.url.edu;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Reader reader = new Reader();
        HashMap<String, List<Word>> dataSet = new HashMap<String, List<Word>>();
        dataSet = reader.training(
                "C:\\Users\\Juan González\\Documents\\IA\\proyectoIA\\proyecto-ia\\src\\main\\resources\\ejemplopalabras.txt",
                dataSet);
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("exit")) {
            System.out.print("Por favor ingrese la cadena >");
            input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            removePunctuation(input);
            var normalized_string = Normalizer.normalize(input, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "").toLowerCase().trim().split("\s");
            var trueEtiqueta = "";
            var falseEtiqueta = "";
            Double trueProbabilidad = 0.0;
            Double falseProbabilidad = 1.0;

            // P (youlo | ham) = (1 + 1)/ (7 + 15)
            for (var etiqueta : dataSet.entrySet()) {
                falseProbabilidad = 1.0;
                for (var texto : normalized_string) {
                    falseEtiqueta = etiqueta.getKey();
                    Word valor = null;
                    Optional<Word> stream = null;
                    stream = etiqueta.getValue().stream().filter(x -> x.value.equals(texto)).findFirst();
                    if (!stream.isEmpty()) {
                        valor = stream.get();
                        Double x = Double.parseDouble(valor.count.toString());
                        Double y = Double.parseDouble(String.valueOf(etiqueta.getValue().size()));
                        Double n = Double.parseDouble(reader.getPalabras().toString());
                        falseProbabilidad *= (x + 1) / (y + n);
                    } else {
                        Double y = Double.parseDouble(String.valueOf(etiqueta.getValue().size()));
                        Double n = Double.parseDouble(reader.getPalabras().toString());
                        falseProbabilidad *= (0 + 1) / (y + n);
                    }
                }
                if (trueProbabilidad < falseProbabilidad) {
                    trueEtiqueta = falseEtiqueta;
                    trueProbabilidad = falseProbabilidad;
                }
            }
            System.out.println(input + " pertenece a " + trueEtiqueta + " con una probabilidad de " + trueProbabilidad);
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
