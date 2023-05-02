package gt.url.edu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Reader {

    private Integer Palabras;

    public Integer getPalabras() {
        return Palabras;
    }

    static String removePunctuation(String s) {
        StringBuilder res = new StringBuilder();
        for (Character c : s.toCharArray()) {
            if(Character.isLetterOrDigit(c))
                res.append(c);
        }
        return res.toString();
    }

    public HashMap<String, List<Word>> training(String path, HashMap<String, List<Word>> words) {
        HashSet<String> hashSet = new HashSet<String>();
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                removePunctuation(line);
                var linea = line.split("\\|");

                var normalized_string = Normalizer.normalize(linea[0], Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "").toLowerCase().trim();
                var normalized_tag = Normalizer.normalize(linea[1], Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "")
                        .toLowerCase().trim();

                var splitted = normalized_string.split("\s");
                List<Word> wordList = new ArrayList<>();
                for (var word : splitted) {
                    if (words.containsKey(normalized_tag)) {
                        wordList = words.get(normalized_tag);
                    }
                    var existe = false;
                    for (var w : wordList) {
                        if (w.value.equals(word)) {
                            w.count++;
                            existe = true;
                        }
                    }
                    if (!existe) {
                        wordList.add(new Word(normalized_tag, word, 1));
                        hashSet.add(word);
                    }
                }
                words.put(normalized_tag, wordList);
            }
            System.out.println("Aprendizaje finalizado");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Palabras = hashSet.size();
        return words;
    }
}