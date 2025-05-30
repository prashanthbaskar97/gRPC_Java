package com.gitlab.pcbook.serializer;

import com.gitlab.techschool.pcbook.pb.Laptop;
import com.google.protobuf.util.JsonFormat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Serializer {
    public void WriteBinaryFile(Laptop laptop,String filename)throws IOException {
        FileOutputStream outputStream =new FileOutputStream(filename);
        laptop.writeTo(outputStream);
        outputStream.close();
    }

    public Laptop ReadfromBinaryFile( String filename) throws IOException{
        FileInputStream inputStream=new FileInputStream(filename);
        Laptop laptop= Laptop.parseFrom(inputStream);
        inputStream.close();
        return laptop;
    }

    public void writeJSONFile(Laptop laptop, String filname) throws IOException {
        JsonFormat.Printer printer= JsonFormat.printer()
                .includingDefaultValueFields()
                .preservingProtoFieldNames();

        String jsonString =printer.print(laptop);
        FileOutputStream outputStream= new FileOutputStream(filname);
        outputStream.write(jsonString.getBytes());
        outputStream.close();
    }

    public static void main(String[] args)throws IOException {
        Serializer serializer= new Serializer();
        Laptop laptop =serializer.ReadfromBinaryFile("laptop.bin");
        serializer.writeJSONFile(laptop, "laptop.json");
    }
}

