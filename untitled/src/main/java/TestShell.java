import java.util.Scanner;
import java.util.regex.Pattern;

public class TestShell {
    // LBA 범위와 16진수 패턴 상수
    private static final int LBA_MIN = 0;
    private static final int LBA_MAX = 99;
    private static final Pattern HEX_PATTERN = Pattern.compile("0x[0-9A-Fa-f]+");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // 사용자 입력을 위한 Scanner
        Ssd ssd = new Ssd(); // SSD 인스턴스 생성
        while (true) {
            System.out.print("SHELL > ");
            String input = sc.nextLine(); // 사용자 입력 읽기
            String[] tokens = input.split(" "); // 입력을 공백으로 분리

            if (tokens.length == 0) {
                System.out.println("INVALID COMMAND");
                continue;
            }

            String command = tokens[0]; // 첫 번째 토큰은 명령어

            switch (command) {
                case "exit":
                    System.out.println("BYE");
                    return; // 쉘 종료
                case "help":
                    printHelp(); // 도움말 출력
                    break;
                case "write":
                    // write 명령어 유효성 검사 및 실행
                    if (tokens.length == 3 && isValidLBA(tokens[1]) && isValidHex(tokens[2])) {
                        int lba = Integer.parseInt(tokens[1]);
                        String data = tokens[2];
                        ssd.write(lba, data);
                    } else {
                        System.out.println("INVALID COMMAND");
                    }
                    break;
                case "read":
                    // read 명령어 유효성 검사 및 실행
                    if (tokens.length == 2 && isValidLBA(tokens[1])) {
                        int lba = Integer.parseInt(tokens[1]);
                        ssd.read(lba);
                    } else {
                        System.out.println("INVALID COMMAND");
                    }
                    break;
                case "fullwrite":
                    // fullwrite 명령어 유효성 검사 및 실행
                    if (tokens.length == 2 && isValidHex(tokens[1])) {
                        String data = tokens[1];
                        for(int i = 0;i<100;i++){
                            ssd.write(i, data);
                        }
                    } else {
                        System.out.println("INVALID COMMAND");
                    }
                    break;
                case "fullread":
                    // fullread 명령어 실행
                    if (tokens.length == 1) {
                        for(int i = 0;i<100;i++){
                            ssd.read(i);
                        }
                    } else {
                        System.out.println("INVALID COMMAND");
                    }
                    break;
                default:
                    System.out.println("INVALID COMMAND"); // 잘못된 명령어 처리
            }
        }
    }

    // LBA 값 유효성 검사
    private static boolean isValidLBA(String lbaStr) {
        try {
            int lba = Integer.parseInt(lbaStr);
            return lba >= LBA_MIN && lba <= LBA_MAX;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 16진수 값 유효성 검사
    private static boolean isValidHex(String hexStr) {
        return HEX_PATTERN.matcher(hexStr).matches();
    }

    // 도움말 정보 출력
    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("write <LBA> <data>");
        System.out.println("read <LBA>");
        System.out.println("fullwrite <data>");
        System.out.println("fullread");
        System.out.println("exit");
        System.out.println("help");
    }
}