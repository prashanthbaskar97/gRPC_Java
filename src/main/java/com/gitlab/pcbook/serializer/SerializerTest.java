package com.gitlab.pcbook.serializer;

import com.gitlab.pcbook.sample.Generator;
import com.gitlab.techschool.pcbook.pb.Laptop;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class SerializerTest {

    @Test
    public void writeAndReadBinaryFile() throws IOException {
        String binaryfile= "laptop.bin";
        Laptop laptop1= new Generator().NewLaptop();

        Serializer serializer=new Serializer();
        serializer.WriteBinaryFile(laptop1, binaryfile);

        Laptop laptop2= serializer.ReadfromBinaryFile(binaryfile);
        Assert.assertEquals(laptop1,laptop2);

    }


}