public class Main {
    public static void main(String[] args) {
        Ssd SSD = new Ssd();
        SSD.write(0,"0x00000000");
        SSD.write(2,"0x0000000z");
        SSD.read(10);
    }
}
