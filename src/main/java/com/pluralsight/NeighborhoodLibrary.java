package com.pluralsight;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class NeighborhoodLibrary {
    private static Book[] libraryArray;

    public static void main(String[] args) throws IOException {
        csvReader();
        homeScreen();
    }
    public static void checkInBook() throws IOException {
        Scanner kbScanner = new Scanner(System.in);
        System.out.print("Please enter the ID of the book you are returning: ");
        int returnID = kbScanner.nextInt();
        libraryArray[returnID].setCheckedOut(false);
        System.out.println("Thank You!");
        homeScreen();
    }
    public static void checkOutBook() throws IOException {
        Scanner kbScanner = new Scanner(System.in);
        System.out.print("Please enter the ID of the book you would like to check out: ");
        int checkOutID = kbScanner.nextInt();
        kbScanner.nextLine();
        if(checkOutID <= libraryArray.length && checkOutID >= 1){
            System.out.print("Please enter your name: ");
            String checkOutName = kbScanner.nextLine();
            libraryArray[checkOutID-1].setCheckedOutTo(checkOutName);
            libraryArray[checkOutID-1].setCheckedOut(true);
            homeScreen();
        }
        else{
            System.out.println("Please enter a valid ID. ");
            checkOutBook();
        }
    }

    public static void showCheckedOutBooks() throws IOException {
        Scanner kbScanner = new Scanner(System.in);
        System.out.println("\nCurrently Checked Out Books:");
        for (Book book : libraryArray) {
            if (book.isCheckedOut()) {
                System.out.println("ID:" + book.getId() + "\tISBN:" + book.getIsbn() + "\tTitle:" + book.getTitle() + "\tChecked Out To:" + book.getCheckedOutTo());
            }
        }
        System.out.print("\nPlease select an option!\n\t1) Check In A Book\n\t2) Go Back To Home Screen\nUser Input: ");
        int choice = kbScanner.nextInt();
        switch (choice) {
            case 1 -> checkInBook();
            case 2 -> homeScreen();
            default -> {
                System.out.println("Please enter a valid option (1 or 2). ");
                showCheckedOutBooks();
            }
        }
    }
    public static void showAvailableBooks() throws IOException {
        Scanner kbScanner = new Scanner(System.in);
        System.out.println("Currently Available Books:\n");
        for(Book book: libraryArray) {
            if (!book.isCheckedOut()) {
                System.out.print("ID:" + book.getId() + "\tISBN:" + book.getIsbn() + "\tTitle:" + book.getTitle()+"\n");
            }
        }
        System.out.print("\nPlease select an option!\n\t1) Check In A Book\n\t2) Go Back To Home Screen\nUser Input: ");
        int choice = kbScanner.nextInt();
        switch (choice) {
            case 1 -> checkOutBook();
            case 2 -> homeScreen();
            default -> {
                System.out.println("Please enter a valid option (1 or 2). ");
                showCheckedOutBooks();
            }
        }
    }

    public static void homeScreen() throws IOException {
        Scanner kbScanner = new Scanner(System.in);
            System.out.print("Welcome to the Neighborhood Library!\n\t1) Show Available Books\n\t2) Show Checked Out Books\n\t3) Check In A Book\n\t4) Check Out A Book\n\t5) Exit Program\nUser Input: ");
            int choice = kbScanner.nextInt();
            switch (choice) {
                case 1 -> showAvailableBooks();
                case 2 -> showCheckedOutBooks();
                case 3 -> checkInBook();
                case 4 -> checkOutBook();
                case 5 -> {
                    System.out.println("Thank you for visiting!");
                    csvWriter();
                    break;
                }
                default -> {
                    System.out.println("Please enter a valid option (1, 2, 3, 4, or 5). \n");
                    homeScreen();
                }
            }
    }
    public static void csvReader() throws FileNotFoundException {
        File library = new File("src\\main\\resources\\testfile.csv");
        library = new File(library.getAbsolutePath());
        Scanner csvReader = new Scanner(library);
        Scanner csvReader2 = new Scanner(library);
        int temp = 0;
        csvReader.useDelimiter(",");
        while(csvReader2.hasNextLine()){
            temp+=1;
            csvReader2.nextLine();
        }
        libraryArray = new Book[temp];
        for(int j = 0; j < temp; j++){
             int id = Integer.parseInt(csvReader.next().trim());
             String isbn = csvReader.next();
             String title = csvReader.next();
             String checkedOutTo = csvReader.next();
             boolean isCheckedOut = Boolean.parseBoolean(csvReader.next().trim());;
            Book newBook = new Book(id, isbn, title, checkedOutTo, isCheckedOut);
            libraryArray[id-1] = newBook;
        }
    }

    public static void csvWriter() throws IOException {
        File library = new File("src\\main\\resources\\testfile.csv");
        library = new File(library.getAbsolutePath());
        FileWriter fw = new FileWriter(library, false);
        BufferedWriter writer = new BufferedWriter( fw );
        int temp = 0;
        while(temp < libraryArray.length){
            if(temp != 0){
                writer.newLine();
            }
            writer.write(Integer.toString(libraryArray[temp].getId()));
            writer.write(",");
            writer.write(libraryArray[temp].getIsbn());
            writer.write(",");
            writer.write(libraryArray[temp].getTitle());
            writer.write(",");
            writer.write(libraryArray[temp].getCheckedOutTo());
            writer.write(",");
            writer.write(Boolean.toString(libraryArray[temp].isCheckedOut()));
            writer.write(",");
            temp++;
        }
        writer.close();
        fw.close();
    }
}
