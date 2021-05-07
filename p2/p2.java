import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Write a program to read and write student objects with fixed-length
 * records and the fields delimited by "|". Implement pack(), unpack(),
 * modify() and search() methods.
 */

public class p2 {
    private final Scanner scanner = new Scanner(System.in);
    final int size = 50;

    public static void main(String[] args) {
        final int PACK = 1;
        final int UNPACK = 2;
        final int SEARCH = 3;
        final int MODIFY = 4;
        final int EXIT = 5;

        p2 p = new p2();

        p.createLine();

        p.println("1. Pack()\t\t2. Unpack()\n3. Search()\t\t4. Modify()\n5. Exit");

        p.createLine();

        int ch = p.getIntInput("Enter choice: ");
        switch (ch) {
            case PACK -> p.pack();
            case UNPACK -> p.unpack();
            case SEARCH -> p.search();
            case MODIFY -> p.modify();
            case EXIT -> p.exit(0);
            default -> p.println("invalid");
        }

        p.exit(0);
        p.createLine();
    }

    private void pack() {
        Student student = new Student();
        println("Enter student details: ");
        student.setName(getStringInput("Name: "));
        student.setUsn(getStringInput("USN: "));
        student.setBranch(getStringInput("Branch: "));
        student.setSem(getIntInput("Semester: "));

        student.packIt(size);
        createLine();
        FileHandler fileHandler = new FileHandler();
        String fileName = getStringInput("Enter file name: ");
        fileHandler.writeFile(fileName, student.toString());
    }

    private void unpack() {
        FileHandler fileHandler = new FileHandler();
        String fileName = getStringInput("Enter the file name: ");
        List<String> records = fileHandler.readFile(fileName);
        for (String str : records) {
            Student student = new Student();
            student.stringSplitter(str);
            student.unpackIt();
            println(student.toString());
            createLine();
        }
    }

    private void search() {
        FileHandler fileHandler = new FileHandler();
        String fileName = getStringInput("Enter the file name: ");
        List<String> records = fileHandler.readFile(fileName);
        String inputUsn = getStringInput("Enter the USN: ");
        createLine();
        for (String str : records) {
            Student student = new Student();
            if (student.search(str, inputUsn)) {
                student.unpackIt();
                println(student.toString());
                createLine();
                return;
            }
        }
        println("Record not found");
        createLine();
    }

    private void modify() {
        FileHandler fileHandler = new FileHandler();
        String fileName = getStringInput("Enter the file name: ");
        List<String> records = fileHandler.readFile(fileName);
        String tempFile = "temp.txt";
        String inputUsn = getStringInput("Enter usn of the record to be modified: ");
        createLine();
        for (String str : records) {
            Student student = new Student();
            student.stringSplitter(str);
            student.packIt(size);
            if (student.search(str, inputUsn)) {
                println(student.toString());
                Student editStudent = new Student();
                println("Enter student details: ");
                editStudent.setName(getStringInput("Name: "));
                editStudent.setUsn(getStringInput("USN: "));
                editStudent.setBranch(getStringInput("Branch: "));
                editStudent.setSem(getIntInput("Semester: "));
                editStudent.packIt(size);
                fileHandler.writeFile(tempFile, editStudent.toString());
            } else fileHandler.writeFile(tempFile, student.toString());
        }
        fileHandler.deleteFile(fileName);
        fileHandler.renameFile(tempFile, fileName);
        println("Renamed " + tempFile + " to " + fileName);
    }

    private void createLine() {
        System.out.println("_____________________________________________");
    }

    private void println(String string) {
        System.out.println(string);
    }

    private int getIntInput(String str) {
        System.out.print(str);
        int num = -1;
        try {
            num = scanner.nextInt();
        } catch (Exception e) {
            println("Invalid input");
            exit(1);
        }
        return num;
    }

    private String getStringInput(String string) {
        System.out.print(string);
        return scanner.next();
    }

    private void exit(int status) {
        println("exited!");
        createLine();
        System.exit(status);
    }
}

class Student {
    private String name, usn, branch;
    private int sem;
    private String record;

    public void setName(String name) {
        this.name = name;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }

    public void packIt(int size) {
        record = name + "|" + usn + "|" + branch + "|" + sem + "|";
        int len = record.length();
        String packer = "-";
        if (len < size)
            for (int i = len; i < size; i++)
                record = record.concat(packer);
    }

    public void stringSplitter(String str) {
        String[] result = str.split("\\|");
        name = result[0];
        usn = result[1];
        branch = result[2];
        sem = Integer.parseInt(result[3]);
    }

    public void unpackIt() {
        record = "The details are: \n" +
                "name \t\t=\t " + name + '\n' +
                "usn \t\t=\t " + usn + '\n' +
                "branch \t\t=\t " + branch + '\n' +
                "sem \t\t=\t " + sem;
    }

    public boolean search(String str, String inputUsn) {
        stringSplitter(str);
        return usn.equals(inputUsn);
    }

    @Override
    public String toString() {
        return record;
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
            println(e.toString());
            System.exit(1);
            createLine();
        }
        createLine();
        return names;
    }

    public void writeFile(String fileName, String names) {
        createLine();
        println("writing to file : " + fileName);
        try {
            fileWriter(fileName, names);
        } catch (IOException e) {
            println(e.toString());
            System.exit(1);
            createLine();
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

    private void fileWriter(String fileName, String names) throws IOException {
        File file = new File(fileName);
        FileWriter fileWriter = new FileWriter(file, file.exists());
        try {
            fileWriter.write(names + "\n");
        } catch (IOException e) {
            println(e.toString());
            System.exit(1);
            createLine();
        }

        fileWriter.close();
    }

    private void createLine() {
        System.out.println("___________________________________________");
    }

    private void println(String str) {
        System.out.println(str);
    }

    public void deleteFile(String tempFile) {
        File file = new File(tempFile);
        if (file.exists())
            file.delete();
    }

    public void renameFile(String tempFile, String fileName) {
        File file = new File(tempFile);
        if (file.exists())
            file.renameTo(new File(fileName));
    }
}