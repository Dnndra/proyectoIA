package gt.url.edu;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Reader {

    public void read(String path){

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("JobServidorNuevo.txt"));
            String line;
            while ((line = reader.readLine()) != null) {


                    System.out.println(line);


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
