package gt.url.edu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Reader {

    public void training(String path){

        HashMap<String, List<Word>> words = new HashMap<String,List<Word> >();
        BufferedReader reader = null;
        try {
            FireBaseService fireBaseService = new FireBaseService();
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                var linea = line.split("\\|");


                var normalized_string = Normalizer.
                        normalize(linea[0], Normalizer.Form.NFD).
                        replaceAll("[^\\p{ASCII}]|\\,|\\.|\"", "").
                        toLowerCase().trim();
                var normalized_tag = Normalizer.
                        normalize(linea[1], Normalizer.Form.NFD).
                        replaceAll("[^\\p{ASCII}]", "").
                        toLowerCase().trim();

//                System.out.println(normalized_string);
//                System.out.println(normalized_tag);
                var splitted = normalized_string.split("\s");
                var tags = fireBaseService.db.getReference("etiqueta");
                Query query;

                query = tags.child(normalized_tag).equalTo("OK").limitToFirst(1);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            
                        }else{

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
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

    }

}
