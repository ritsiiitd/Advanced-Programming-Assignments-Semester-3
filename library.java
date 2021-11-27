import java.io.*;
import java.util.Arrays;

/**
 * library
 */
public class library{

    private int N;
    private int K;
    private Book[] rack;

    library(int N,int K){
        this.N=N;
        this.K=K;
        rack = new Book[N];
    }

    public Book[] getRack(){
        return this.rack;
    }


    static class Book  implements Comparable<Book>{ //inheritance
        private String title;
        private long ISBN;
        private long barcode;

        Book(String title, long ISBN, long barcode){
            this.title = title;
            this.ISBN = ISBN;
            this.barcode = barcode;
        }

        //0 -> equal
        //-ve -> o > this
        //+ve ->  this > o 
        public int compareTo(Book o){  //Function Overloading
            int onBasisofTitle = this.getTitle().compareTo(o.getTitle());//now compareTo() implemented in String class is used.
            if(onBasisofTitle!=0){
                return onBasisofTitle;
            }
            
            int onBasisofISBN = 0;
            if(this.getIsbn() < o.getIsbn()){
                onBasisofISBN = -1;
            }
            else if(this.getIsbn() > o.getIsbn()){
                onBasisofISBN = 1;
            }
            
            if(onBasisofISBN!=0){
                return onBasisofISBN;
            }

            if(this.getBarcode() < o.getBarcode()){
                return -1;
            }
            else if(this.getBarcode() > o.getBarcode()){
                return 1;
            }

            else{ // won't reach here if correct inputs are given
                return 0;
            }

        }

        public String getTitle(){
            return this.title;
        }
        public long getIsbn(){
            return this.ISBN;
        }
        public long getBarcode(){
            return this.barcode;
        }
    }

    static int bookCount = 0;
    public static void main(String[] args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the number of books(N)");
        int n=Integer.parseInt(br.readLine());
        System.out.println("Enter the number of racks(K)");
        int k=Integer.parseInt(br.readLine());
        while(n%k!=0){
            System.out.println("Wrong choice of K, it should be factor of N, re-enter");
            k=Integer.parseInt(br.readLine());
        }
        library myLibrary = new library(n, k);

        System.out.println("Please enter the information about N books");
        for(int i=0;i<n;i++){
            System.out.println("Enter info for book "+(i+1));
            System.out.println("Enter title");
            String ti = br.readLine();
            ti=ti.toUpperCase();
            System.out.println("Enter ISBN");
            long isbn = Integer.parseInt(br.readLine());
            System.out.println("Enter Barcode");
            long bar = Integer.parseInt(br.readLine());
            Book b = new Book(ti, isbn, bar);
            myLibrary.getRack()[bookCount++] = b;
        }
        Arrays.sort(myLibrary.getRack());

        System.out.println("\n\nAll books sorted and placed");
        int rack=1,slot=1;
        int capacity = n/k;
        System.out.println("Capacity of rack = "+capacity );
        System.out.println();
        for (Book book : myLibrary.getRack()) {
            
            if(slot==capacity+1){
                slot=1;
                rack++;
            }
            System.out.println("Placing in slot "+ slot +" of rack "+ rack);
            slot++;
            
            System.out.println("[ "+book.getTitle()+", "+book.getIsbn()+", "+book.getBarcode()+" ]");

        }
    }
}