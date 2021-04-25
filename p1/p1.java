import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 1. Write a program to read series of names, one per line, from standard input and write these
 * names spelled in reverse order to the standard output using I/O redirection and pipes.
 * Repeat the exercise using an input file specified by the user instead of the standard input and
 * using an output file specified by the user instead of the standard output.
 */

public class p1 {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();

        createLine();

        display("1. Accept input from stdin\n2. Accept input from file\n3. Exit");

        createLine();

        int ch = getIntInput("Enter choice: ");

        switch (ch) {
            case 1 -> stdinInput(strings);
            case 2 -> fileInput();
            case 3 -> exit();
            default -> display("invalid");

        }

        createLine();
    }

    private static void createLine() {
        System.out.println("_____________________________________________");
    }

    private static void display(String string) {
        System.out.println(string);
    }

    private static int getIntInput(String str) {
        System.out.print(str);
        return scanner.nextInt();
    }

    private static String getStringInput(String string) {
        System.out.print(string);
        return scanner.next();
    }

    private static String getReversedString(String str) {
        return String.valueOf(new StringBuilder(str).reverse());
    }

    private static void stdinInput(List<String> strings) {
        int num = getIntInput("Enter the number of names to be reversed: ");
        display("Enter " + num + " names line by line:");
        for (int i = 0; i < num; i++) {
            strings.add(getStringInput("Enter name " + (i + 1) + " : "));
        }

        createLine();

        display("The reversed names:");
        strings.forEach(s -> display(getReversedString(s)));
    }

    private static void fileInput() {
        List<String> strings;

        MyFileReader myFileReader = new MyFileReader();
        strings = myFileReader.readFile();

        createLine();

        List<String> reversedStrings = new ArrayList<>();
        strings.forEach(str -> reversedStrings.add(getReversedString(str)));

        String fileName = getStringInput("Enter the output file name: ");

        createLine();

        strings.forEach(str -> display(getReversedString(str)));

        createLine();

        myFileReader.writeFile(fileName, reversedStrings);
    }

    private static void exit() {
        display("exited!");
        createLine();
        System.exit(0);
    }

    
    public static class MyFileReader {
        private final List<String> strings = new ArrayList<>();
        private final Scanner scanner = new Scanner(System.in);

        public List<String> readFile() {
            try {
                bufferedFileReader();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return strings;
        }

        public void writeFile(String fileName, List<String> strings) {
            try {
                fileWriter(fileName, strings);
                System.out.println("Success...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void bufferedFileReader() throws Exception {
            String fileName = getStringInput("Enter the input file name: ");
            File file = new File(fileName);
            createLine();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                strings.add(str);
            }
        }

        private void fileWriter(String fileName, List<String> strings) throws IOException {
            FileWriter fileWriter = new FileWriter(fileName);
            strings.forEach(s -> {
                try {
                    fileWriter.write(s + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.close();
        }
    }
}