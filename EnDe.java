package encryptdecrypt;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class EnDe {
    public static void main(String[] args) {
        boolean encode = true;
        boolean in = false;
        boolean out = false;
        boolean alg = false;
        String message = "";
        String fileIn = "";
        String fileOut = "";
        int key = 0;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    encode = "enc".equals(args[i + 1]);
                    break;
                case "-key":
                    key = Integer.parseInt(args[i + 1]);
                    break;
                case "-data":
                    message = args[i + 1];
                    break;
                case "-in":
                    in = true;
                    fileIn = args[i + 1];
                    break;
                case "-out":
                    out = true;
                    fileOut = args[i + 1];
                    break;
                case "-alg":
                    alg = "shift".equals(args[i + 1]);
                    break;
            }
        }
        if (encode && in && out){
            printInFileEncode(fileOut, readFile(fileIn), key, alg);
        } else if (!encode && in && out) {
            printInFileDecode(fileOut, readFile(fileIn), key, alg);
        } else if (encode && out){
            printInFileEncode(fileOut, message, key, alg);
        } else if (!encode && out){
            printInFileDecode(fileOut, message, key, alg);
        } else if (encode && !out) {
            System.out.println(encode(message, key, alg));
        } else {
            System.out.println(decode(message, key, alg));
        }
    }

    public static String readFile(String fileIn){ // чтение из файла
        try {
            File file = new File(fileIn);
            Scanner sc = new Scanner(file);
            String text = sc.nextLine();
            sc.close();
            return text;
        } catch (Exception e){
            System.out.println("Error");
        }
        return null;
    }

    public static void printInFileEncode(String fileOut, String message, int key, boolean alg){ // печать в файл - зашифровка
        try {
            File file = new File(fileOut);
            PrintWriter pw = new PrintWriter(file);
            pw.print(encode(message, key, alg));
            pw.close();
        } catch (Exception e){
            System.out.println("Error");
        }
    }

    public static void printInFileDecode(String fileOut, String message, int key, boolean alg){ // печать в файл - расшифровка
        try {
            File file = new File(fileOut);
            PrintWriter pw = new PrintWriter(file);
            pw.print(decode(message, key, alg));
            pw.close();
        } catch (Exception e){
            System.out.println("Error");
        }
    }

    public static String encode(String str, int key, boolean alg){ // зашифровываем текс
        char a = 'a';
        char z = 'z';
        char aA = 'A';
        char zZ = 'Z';
        if(alg){
            StringBuilder result = new StringBuilder();
            for (char c: str.toCharArray()) {
                if (c >= a && c <= z) {
                    if((int) c + key > 122){
                        int y = key - 122 + (int)c + 96;
                        result.append((char) y);
                    } else {
                        result.append((char) (c + key));
                    }
                } else if (c >= aA && c <= zZ){
                    if((int) c + key > 90){
                        int y = key - 90 + (int)c + 64;
                        result.append((char) y);
                    } else {
                        result.append((char) (c + key));
                    }
                } else {
                    result.append(c);
                }
            }
            return result.toString();
        } else {
            StringBuilder result = new StringBuilder();
            for (char c: str.toCharArray()) {
                result.append((char) (c + key));
            }
            return result.toString();
        }
    }

    public static String decode(String str, int key, boolean alg){ // расшифровываем текс
        char a = 'a';
        char z = 'z';
        char aA = 'A';
        char zZ = 'Z';
        if(alg){
            StringBuilder result = new StringBuilder();
            for (char c: str.toCharArray()) {
                if (c >= a && c <= z) {
                    if((int) c - key < 97){
                        int y = 123 - key + (int)c - 97;
                        result.append((char) y);
                    } else {
                        result.append((char) (c - key));
                    }
                } else if (c >= aA && c <= zZ){
                    if((int) c - key < 65){
                        int y = 91 - key + (int)c - 65;
                        result.append((char) y);
                    } else {
                        result.append((char) (c - key));
                    }
                } else {
                    result.append(c);
                }
            }
            return result.toString();
        } else {
            StringBuilder result = new StringBuilder();
            for (char c: str.toCharArray()) {
                result.append((char) (c - key));
            }
            return result.toString();
        }
    }
}
