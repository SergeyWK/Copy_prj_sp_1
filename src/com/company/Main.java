package com.company;

        import java.io.*;

public class Main {

    public static void main(String[] args) {
        fileExample1();
    }

    public static void fileExample1() {
        try {
            Reader reader = new FileReader("in.txt");
            char[] buff = new char[10];
            reader.read(buff);
            System.out.println(buff);
            reader.close();

            InputStream inputStream = new FileInputStream("in.bin");
            byte[] bytes = new byte[20];
            inputStream.read(bytes);
            for (byte b : bytes) {
                System.out.print(" " + b);
            }
            inputStream.close();

            OutputStream outputStream  = new FileOutputStream("out.bin");
            outputStream.write(new byte[] {0,0,0,1});
            outputStream.write(new byte[] {0,0,0,2});
            outputStream.write(new byte[] {0,0,0,3});
            outputStream.write(new byte[] {0,0,0,4});
            outputStream.close();

            Writer writer = new FileWriter("out.txt");
            writer.write(new char[] {'q', 'w', 'e', 'r', 't', 'y', '\n', '1', '2', '3', '4', '5'});
            writer.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileExample2() {
        try {
            Reader reader = new FileReader("in.txt");
            char[] buff = new char[10];
            reader.read(buff);
            System.out.println(buff);
            reader.close();

            InputStream inputStream = new FileInputStream("in.bin");
            byte[] bytes = new byte[20];
            inputStream.read(bytes);
            for (byte b : bytes) {
                System.out.print(" " + b);
            }
            inputStream.close();

            OutputStream outputStream  = new FileOutputStream("out.bin");
            outputStream.write(new byte[] {0,0,0,1});
            outputStream.write(new byte[] {0,0,0,2});
            outputStream.write(new byte[] {0,0,0,3});
            outputStream.write(new byte[] {0,0,0,4});
            outputStream.close();

            Writer writer = new FileWriter("out.txt");
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println("qwerty");
            printWriter.print("12345");
            //writer.write(new char[] {'q', 'w', 'e', 'r', 't', 'y', '\n', '1', '2', '3', '4', '5'});
            printWriter.close();

            OutputStream altOutputStream  = new FileOutputStream("alt_out.txt");
            PrintStream printStream = new PrintStream(altOutputStream);
            printStream.println("qwerty");
            printStream.print(1);
            printStream.print(2);
            printStream.print(3);
            printStream.print(4);
            printStream.print(5);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileExample3() {
        try {
            Reader reader = new FileReader("in.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
//            char[] buff = new char[10];
//            reader.read(buff);
            System.out.println(line);
            reader.close();

            InputStream inputStream = new FileInputStream("in.bin");
            byte[] bytes = new byte[20];
            inputStream.read(bytes);
            for (byte b : bytes) {
                System.out.print(" " + b);
            }
            inputStream.close();

            OutputStream outputStream  = new FileOutputStream("out.bin");
            outputStream.write(new byte[] {0,0,0,1});
            outputStream.write(new byte[] {0,0,0,2});
            outputStream.write(new byte[] {0,0,0,3});
            outputStream.write(new byte[] {0,0,0,4});
            outputStream.close();

            Writer writer = new FileWriter("out.txt");
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println("qwerty");
            printWriter.print("12345");
            printWriter.close();

            OutputStream altOutputStream  = new FileOutputStream("alt_out.txt");
            PrintStream printStream = new PrintStream(altOutputStream);
            printStream.println("qwerty");
            printStream.print(1);
            printStream.print(2);
            printStream.print(3);
            printStream.print(4);
            printStream.print(5);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileExample4() {
        try {
            Reader reader = new FileReader("in.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            System.out.println(line);
            reader.close();

            InputStream inputStream = new FileInputStream("in.bin");
            DataInput dataInput = new DataInputStream(inputStream);
            System.out.print(" " + dataInput.readInt());
            System.out.print(" " + dataInput.readInt());
            System.out.print(" " + dataInput.readInt());
            System.out.print(" " + dataInput.readInt());
//            byte[] bytes = new byte[20];
//            inputStream.read(bytes);
//            for (byte b : bytes) {
//                System.out.print(" " + b);
//            }
            inputStream.close();

            OutputStream outputStream  = new FileOutputStream("out.bin");
            DataOutput dataOutput = new DataOutputStream(outputStream);
            dataOutput.writeInt(1);
            dataOutput.writeInt(2);
            dataOutput.writeInt(3);
            dataOutput.writeInt(4);
//            outputStream.write(new byte[] {0,0,0,1});
//            outputStream.write(new byte[] {0,0,0,2});
//            outputStream.write(new byte[] {0,0,0,3});
//            outputStream.write(new byte[] {0,0,0,4});
            outputStream.close();

            Writer writer = new FileWriter("out.txt");
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println("qwerty");
            printWriter.print("12345");
            printWriter.close();

            OutputStream altOutputStream  = new FileOutputStream("alt_out.txt");
            PrintStream printStream = new PrintStream(altOutputStream);
            printStream.println("qwerty");
            printStream.print(1);
            printStream.print(2);
            printStream.print(3);
            printStream.print(4);
            printStream.print(5);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}