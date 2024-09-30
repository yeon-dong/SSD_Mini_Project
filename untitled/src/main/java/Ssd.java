import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class Ssd {
    private String[] LBA = new String[100];

    // 생성자: 파일에서 기존 데이터를 읽어와서 LBA 배열에 저장
    public Ssd() {
        try (BufferedReader reader = new BufferedReader(new FileReader("nand.txt"))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null && index < LBA.length) {
                LBA[index++] = line;
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    //write
    public void write(int idx, String data) {
        if (idx < 0 || idx >= 100) { //배열 크기 벗어남 오류
            System.out.println("Error : Index out of bounds");
        } else if (data.length() > 10) { //데이터 크기 벗어남 오류
            System.out.println("Error : Data is out of size");
        } else {
            LBA[idx] = data; // 배열에 데이터 저장
            // 파일에 데이터 저장
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("nand.txt"))) {
                for (int i = 0; i < 100; i++) {
                    if (LBA[i] != null) { // 비어있으면 그냥 줄 바꿈
                        writer.write(LBA[i]);
                    }
                    if(i<99){
                        writer.newLine(); // 줄 바꿈
                    }
                }
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }
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
