import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class Ssd {
    private String[] LBA = new String[100];
    //write

    //read
    public void read(int idx) {
        //idx 검사
        if (idx < 0 || idx >= LBA.length) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + idx);
        }
        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter("./result.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        }
        //LBA의 값을 result.txt에 쓰기
        for(int i = 0 ; i< 100; i++){
            String data =  LBA[i];
            outputStream.println(data);
        }
        outputStream.close();
        Stream<String> stream = null;
        try{
            stream = Files.lines(Path.of("./result.txt"));
        } catch (IOException e) {
            throw new RuntimeException("File does not exist");
        }
        String Line = stream.skip(idx).findFirst().orElse("0x00000000");
        System.out.println(Line);
    }
}
