package com.gitlab.pcbook.sample;

import com.gitlab.techschool.pcbook.pb.*;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.util.Random;
public class Generator {
    private Random rand;
    public Generator(){
        rand = new Random();
    }

    public Keyboard NewKeyboard(){
        return Keyboard.newBuilder()
                .setLayout(randomKeyboardLayout())
                .setBacklit(rand.nextBoolean())
                .build();
    }

    public CPU NewCPU(){
        String brand =randomCPUBrand();
        String name= randomCPUName(brand);

        int numberCores =randomInt(2,8);
        int numberThreads= randomInt(numberCores,12);

        double minGhz= randomDouble(2,3.5);
        double maxGhz=randomDouble(minGhz,5.0);


        return CPU.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setNumberCores(numberCores)
                .setNumberThreads(numberThreads)
                .setMinGhz(minGhz)
                .setMaxGhz(maxGhz)
                .build();

    }


    public GPU newGPU(){
        String brand= randomGPUBrand();
        String name= randomGPUName(brand);

        double minGhz= randomDouble(1.0, 1.5);
        double maxGhz=randomDouble(minGhz, 6.0);

        Memory memory= Memory.newBuilder()
                .setValue(randomInt(2,6))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();

        return GPU.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setMinGhz(minGhz)
                .setMaxGhz(maxGhz)
                .setMemory(memory)
                .build();
    }

    public Memory NewRAM(){
        return Memory.newBuilder()
                .setValue(randomInt(4, 64))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();
    }

    public Storage NewSSD(){
        Memory memory= Memory.newBuilder()
                .setValue(randomInt(128 , 1024))
                .setUnit(Memory.Unit.GIGABYTE)
                .build();

        return Storage.newBuilder()
                .setDriver(Storage.Driver.SDD)
                .setMemory(memory)
                .build();

    }


    public Storage NewHDD(){
        Memory memory= Memory.newBuilder()
                .setValue(randomInt(1 , 6))
                .setUnit(Memory.Unit.TERABYTE)
                .build();

        return Storage.newBuilder()
                .setDriver(Storage.Driver.HDD)
                .setMemory(memory)
                .build();

    }

    public Screen NewScreen(){
        int height= randomInt(1080, 4320);
        int width= height*16/9;
        Screen.Resolution resolution= Screen.Resolution.newBuilder()
                .setHeight(height)
                .setWidth(width)
                .build();

        return Screen.newBuilder()
                .setSizeInch(randomFloat(13,17))
                .setResolution(resolution)
                .setPanel(randomScreenPanel())
                .setMultitouch(rand.nextBoolean())
                .build();
    }

    public Laptop NewLaptop(){
        String brand = randomLaptopBrand();
        String name=randomLaptopName(brand);
        double weightKg= randomDouble(1.0 , 3.0);
        double priceUsed= randomDouble(1500, 3500);
        int releaseyear=randomInt(2015,2025);

        return Laptop.newBuilder()
                .setBrand(brand)
                .setName(name)
                .setCpu(NewCPU())
                .addGpus(newGPU())
                .addStorages(NewSSD())
                .setScreen(NewScreen())
                .setKeyboard(NewKeyboard())
                .setWeightKg(weightKg)
                .setPrice(priceUsed)
                .setReleaseYear(releaseyear)
                .setUpdatedAt(timstampNow())
                .build();

    }

    private Timestamp timstampNow() {
        Instant now= Instant.now();
        return Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
    }

    private String randomLaptopName(String brand) {
        switch(brand){
            case "Apple":
                return randomStringfromSet("MacBook Air", "MacBook Pro");
            case "Dell":
                    return randomStringfromSet("XPS 13", "XPS 15");
            default:
                return randomStringfromSet("ThinkPad X1 Carbon", "ThinkPad T14");
        }
    }

    private String randomLaptopBrand() {
        return randomStringfromSet("Apple", "Dell","Lenovo");
    }


    private Screen.Panel randomScreenPanel() {
        if(rand.nextBoolean()) {
            return Screen.Panel.IPS;
        }
        return Screen.Panel.OLED;
    }

    private float randomFloat(float min, float max) {
        return min+rand.nextFloat()*(max-min);
    }

    private String randomGPUName(String brand) {
        if (brand == "Intel") {
            return randomStringfromSet("i3", "i5", "i7", "i9");
        }

        return randomStringfromSet("Ryzen 3", "Ryzen 5", "Ryzen 7", "Ryzen 9");
    }

    private String randomGPUBrand() {
        return randomStringfromSet("NVIDIA", "AMD");
    }


    private double randomDouble(double min, double max) {

        return min + rand.nextDouble()*(max-min);
    }


    private int randomInt(int min, int max) {
        return min + rand.nextInt(max-min+1);
    }


    private String randomCPUName(String brand) {
        if(brand=="Intel"){
            return randomStringfromSet(
                    "i3", "i5", "i7", "i9"
            );
        }
        return randomStringfromSet("Ryzen 3", "Ryzen 5", "Ryzen 7", "Ryzen 9");
    }


    private String randomCPUBrand(){
        return randomStringfromSet("Intel", "AMD");
    }

    private String randomStringfromSet(String ...a) {
        int n=a.length;
        if(n==0) {
            return "";
        }
        return a[rand.nextInt(n)];
    }
    

    private Keyboard.Layout randomKeyboardLayout() {
        switch(rand.nextInt(3)){
            case 1:
                return Keyboard.Layout.QWERTY;
            case 2:
                return Keyboard.Layout.QWERTZ;
            default:
                return Keyboard.Layout.AZERTY;
        }
      //  return null;
    }

    public static void main(String[] args) {
        Generator generator=new Generator();
        Laptop laptop= generator.NewLaptop();
        System.out.println(laptop);
    }
}
