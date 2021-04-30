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
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final int STDIN_INPUT = 1;
        final int FILE_INPUT = 2;
        final int EXIT = 3;

        p1 p1Obj = new p1();

        p1Obj.createLine();

        p1Obj.println("1. Accept input from stdin\n2. Accept input from file\n3. Exit");

        p1Obj.createLine();

        int choice = p1Obj.getIntInput("Enter choice: ");

        switch (choice) {
            case STDIN_INPUT -> p1Obj.stdinInput();
            case FILE_INPUT -> p1Obj.fileInput();
            case EXIT -> p1Obj.exit();
            default -> p1Obj.println("invalid");
        }

        p1Obj.createLine();
    }

    private void createLine() {
        System.out.println("___________________________________________");
    }

    private void println(String str) {
        System.out.println(str);
    }

    private int getIntInput(String str) {
        System.out.print(str);
        return scanner.nextInt();
    }

    private String getStringInput(String str) {
        System.out.print(str);
        return scanner.next();
    }

    private String getReversedString(String str) {
        return String.valueOf(new StringBuilder(str).reverse());
    }

    private void stdinInput() {
        List<String> names = new ArrayList<>();

        int num = getIntInput("Enter the number of names to be reversed: ");

        createLine();

        println("Enter " + num + " names line by line:");

        for (int i = 0; i < num; i++) {
            String name = getStringInput("Enter name " + (i + 1) + " : ");
            names.add(name);
        }

        createLine();

        println("The reversed names:");

        createLine();

        names.forEach(name -> println(getReversedString(name)));
    }

    private void fileInput() {
        List<String> names;

        FileHandler fileHandler = new FileHandler();
        String inputFileName = getStringInput("Enter the input file name: ");
        names = fileHandler.readFile(inputFileName);

        List<String> reversedNames = new ArrayList<>();
        names.forEach(name -> reversedNames.add(getReversedString(name)));

        String outputFileName = getStringInput("Enter the output file name: ");

        fileHandler.writeFile(outputFileName, reversedNames);
        fileHandler.readFile(outputFileName);
        println("File contents written successfully...");
    }

    private void exit() {
        println("exited!");
        createLine();
        System.exit(0);
    }
}

class FileHandler {
    private final List<String> names = new ArrayList<>();

    public List<String> readFile(String fileName) {
        createLine();
        println("reading from file : " + fileName);
        createLine();
        try {
            bufferedFileReader(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        createLine();
        return names;
    }

    public void writeFile(String fileName, List<String> names) {
        createLine();
        println("writing to file : " + fileName);
        try {
            fileWriter(fileName, names);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bufferedFileReader(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        br.lines().forEach(name -> {
            println(name);
            names.add(name);
        });
        br.close();
    }

    private void fileWriter(String fileName, List<String> names) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        for (String name : names) {
            try {
                fileWriter.write(name + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileWriter.close();
    }

    private void createLine() {
        System.out.println("___________________________________________");
    }

    private void println(String str) {
        System.out.println(str);
    }
}