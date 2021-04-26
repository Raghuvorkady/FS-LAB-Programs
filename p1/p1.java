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
    private static final int STDIN_INPUT = 1;
    private static final int FILE_INPUT = 2;
    private static final int EXIT = 3;

    public static void main(String[] args) {
        createLine();

        display("1. Accept input from stdin\n2. Accept input from file\n3. Exit");

        createLine();

        int choice = getIntInput("Enter choice: ");

        switch (choice) {
            case STDIN_INPUT -> stdinInput();
            case FILE_INPUT -> fileInput();
            case EXIT -> exit();
            default -> display("invalid");
        }

        createLine();
    }

    private static void createLine() {
        System.out.println("___________________________________________");
    }

    private static void display(String str) {
        System.out.println(str);
    }

    private static int getIntInput(String str) {
        System.out.print(str);
        return scanner.nextInt();
    }

    private static String getStringInput(String str) {
        System.out.print(str);
        return scanner.next();
    }

    private static String getReversedString(String str) {
        return String.valueOf(new StringBuilder(str).reverse());
    }

    private static void stdinInput() {
        List<String> names = new ArrayList<>();

        int num = getIntInput("Enter the number of names to be reversed: ");

        createLine();

        display("Enter " + num + " names line by line:");

        for (int i = 0; i < num; i++) {
            String name = getStringInput("Enter name " + (i + 1) + " : ");
            names.add(name);
        }

        createLine();

        display("The reversed names:");

        createLine();

        names.forEach(name -> display(getReversedString(name)));
    }

    private static void fileInput() {
        List<String> names;

        FileHandler fileHandler = new FileHandler();
        String inputFileName = getStringInput("Enter the input file name: ");
        names = fileHandler.readFile(inputFileName);

        List<String> reversedNames = new ArrayList<>();
        names.forEach(name -> reversedNames.add(getReversedString(name)));

        String outputFileName = getStringInput("Enter the output file name: ");

        fileHandler.writeFile(outputFileName, reversedNames);
        fileHandler.readFile(outputFileName);
        display("File contents written successfully...");
    }

    private static void exit() {
        display("exited!");
        createLine();
        System.exit(0);
    }


    public static class FileHandler {
        private final List<String> names = new ArrayList<>();

        public List<String> readFile(String fileName) {
            createLine();
            display("reading from file : " + fileName);
            createLine();
            try {
                bufferedFileReader(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            createLine();
            return names;
        }

        public void writeFile(String fileName, List<String> names) {
            createLine();
            display("writing to file : " + fileName);
            try {
                fileWriter(fileName, names);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void bufferedFileReader(String fileName) throws Exception {
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String name;
            while ((name = br.readLine()) != null) {
                display(name);
                names.add(name);
            }
        }

        private void fileWriter(String fileName, List<String> names) throws IOException {
            FileWriter fileWriter = new FileWriter(fileName);
            names.forEach(name -> {
                try {
                    fileWriter.write(name + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.close();
        }
    }
}