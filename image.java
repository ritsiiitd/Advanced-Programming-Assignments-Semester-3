import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class image {
    private int row;
    private int column;
    private color c[][];
    private gray g[][];
    private String type;
    private int id;

    static ArrayList<image> allImages = new ArrayList<>();

    public String imgType(){
        return this.type;
    }
    public int getRow(){
        return this.row;
    }
    public  int getCol(){
        return this.column;
    }
    public color[][] colorImage(){
        return this.c;
    }
    public gray[][] grayImage(){
        return this.g;
    }
    public void setId(int id){
        this.id=id;
    }
    public static image input()throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter image type");
        String t= br.readLine();
        System.out.println("Enter Dimensions");
        System.out.println("Rows");
        int row=Integer.parseInt(br.readLine());
        System.out.println("Columns");
        int col=Integer.parseInt(br.readLine());
        image I=null;
        if(t.equalsIgnoreCase("color")){
            I = new image(row, col, t);
            color co[][]=I.colorImage();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    System.out.println("Enter pixel value(0-255) in order R,G,B");
                    int red = Integer.parseInt(br.readLine());
                    int green = Integer.parseInt(br.readLine());
                    int blue = Integer.parseInt(br.readLine());
                    co[i][j] = new color(red, green, blue);
                }
            }
        }else if(t.equalsIgnoreCase("grayscale")){
            I=new image(row, col, t);
            gray gr[][] = I.grayImage();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    System.out.println("Enter pixel value(0-255) for gray scale image");
                    int gra = Integer.parseInt(br.readLine());
                    gr[i][j] = new gray(gra);
                }
            }
        }

        return I;
    }

    public static image createImage()throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter image type");
        String t= br.readLine();
        System.out.println("Enter Dimensions");
        System.out.println("Rows");
        int row=Integer.parseInt(br.readLine());
        System.out.println("Columns");
        int col=Integer.parseInt(br.readLine());
        image I=null;
        if(t.equalsIgnoreCase("color")){
            I = new image(row, col, t);
            color co[][]=I.colorImage();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    int red = (int) (Math.random() * (255 - 0)) + 0;
                    int green = (int) (Math.random() * (255 - 0)) + 0;
                    int blue = (int) (Math.random() * (255 - 0)) + 0;
                    co[i][j] = new color(red, green, blue);
                }
            }
        }else if(t.equalsIgnoreCase("grayscale")){
            I=new image(row, col, t);
            gray gr[][] = I.grayImage();
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    int gra = (int) (Math.random() * (255 - 0)) + 0;
                    gr[i][j] = new gray(gra);
                }
            }
        }

        return I;
    }

    public void updateImage()throws IOException{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter row number and column number to update");
        int ru = Integer.parseInt(br.readLine());
        int cu = Integer.parseInt(br.readLine());

        if(this.imgType().equalsIgnoreCase("color")){
            System.out.println("Enter pixel value(0-255) in order R,G,B for index "+ ru +", "+ cu);
            int red = Integer.parseInt(br.readLine());
            int green = Integer.parseInt(br.readLine());
            int blue = Integer.parseInt(br.readLine());
            this.colorImage()[ru][cu] = new color(red, green, blue);
        }
        else if(this.imgType().equalsIgnoreCase("grayscale")){
            System.out.println("Enter pixel value(0-255) for index "+ ru +", "+ cu);
            int gr = Integer.parseInt(br.readLine());
            this.grayImage()[ru][cu]= new gray(gr);
        }

        System.out.println("Image updated");

    }

    public static void display(image I){
        if(I.imgType().equalsIgnoreCase("color")){
            for (int i = 0; i < I.getRow(); i++) {
                for (int j = 0; j < I.getCol(); j++) {
                    String element = "R: " + I.colorImage()[i][j].getRed() + " G: " + I.colorImage()[i][j].getGreen() + " B: "+I.colorImage()[i][j].getBlue();
                    System.out.print(element+"   ");
                }
                System.out.println();
            }
            
        }
        else if(I.imgType().equalsIgnoreCase("grayscale")){
            for (int i = 0; i < I.getRow(); i++) {
                for (int j = 0; j < I.getCol(); j++) {
                    System.out.print(I.grayImage()[i][j].getGray()+" ");
                }
                System.out.println();
            }
        }
    }

    public image generate_negative(){

        image negative = new image(this.getRow(), this.getCol(), this.imgType());
        if(this.imgType().equalsIgnoreCase("color")){
            
            for (int i = 0; i < this.getRow(); i++) {
                for (int j = 0; j < this.getCol(); j++) {

                    int red = 255 - this.colorImage()[i][j].getRed();
                    int green = 255 - this.colorImage()[i][j].getGreen();
                    int blue = 255 - this.colorImage()[i][j].getBlue();
                    negative.colorImage()[i][j] = new color(red, green, blue);
                }
            }
        }
        else if(this.imgType().equalsIgnoreCase("grayscale")){
            
            for (int i = 0; i < this.getRow(); i++) {
                for (int j = 0; j < this.getCol(); j++) {
                    
                    int gr = 255 - this.grayImage()[i][j].getGray();
                    
                    negative.grayImage()[i][j] = new gray(gr);
                }
            }
        }

        return negative;

    }

    image(int row,int column,String type){
        this.row=row;
        this.column=column;
        this.type=type;
        if(type.equalsIgnoreCase("color")){
            c = new color[row][column];
        }
        else if(type.equalsIgnoreCase("grayscale")){
            g = new gray[row][column];
        }
    }
    static class color{
        int R,G,B;
        color(int R,int G,int B){
            this.R=R;
            this.G=G;
            this.B=B;
        }
        public int getRed(){
            return this.R;
        }
        public int getGreen(){
            return this.G;
        }
        public int getBlue(){
            return this.B;
        }
    }
    static class gray{
        int Gr;
        gray(int Gr){
            this.Gr=Gr;
        }
        public int getGray(){
            return this.Gr;
        }
    }

    static int imagecount=0;
    static ArrayList<image> allNegatives = new ArrayList<>();//stores negative of a image at same index as imageId
    public static void main(String[] args)throws IOException {
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    System.out.println("1: input image\n2: create image\n3: update image\n4: Display image\n5: Generate negative\n6: exit");
    int ch= Integer.parseInt(br.readLine());

    while(ch!=6){
        if(ch==1){
             image img = input();
             allImages.add(img);
             image negative = img.generate_negative();
             allNegatives.add(negative);
             img.setId(imagecount++);
             
        }
        else if(ch==2){
            image img = createImage();
            allImages.add(img);
            image negative = img.generate_negative();
            allNegatives.add(negative);
            img.setId(imagecount++);
        }
        else if(ch==3){
            System.out.println("Images available : ");
            for (int i = 0; i < imagecount; i++) {
                System.out.print(i+ ", ");
            }
            System.out.println();
            System.out.println("Enter image ID for updation");
            int Uid = Integer.parseInt(br.readLine());
            if(Uid>imagecount-1){
                System.out.println("Invalid image id");
                continue;
            }
            else{
                image toupdate = allImages.get(Uid);
                toupdate.updateImage();
                image negative = toupdate.generate_negative();
                allNegatives.set(Uid,negative);
            }
        }
        else if(ch==4){
            System.out.println("Images available : ");
            for (int i = 0; i < imagecount; i++) {
                System.out.print(i+ ", ");
            }
            System.out.println();
            System.out.println("Enter image ID to  display");
            int Uid = Integer.parseInt(br.readLine());
            if(Uid>imagecount-1){
                System.out.println("Invalid image id");
                continue;
            }
            else{
                
                image todisplay = allImages.get(Uid);
                System.out.println("\n----------Displaying image as matrix (type = "+ todisplay.imgType() +" )------\n");
                display(todisplay);
                System.out.println("\n-----------------------------------------------------------------------\n");
            }
        }
        else if(ch==5){
            System.out.println("Images available : ");
            for (int i = 0; i < imagecount; i++) {
                System.out.print(i+ ", ");
            }
            System.out.println();
            System.out.println("Enter image ID to generate negative");
            int Uid = Integer.parseInt(br.readLine());
            if(Uid>imagecount){
                System.out.println("Invalid image id");
                continue;
            }
            
            System.out.println("\n---------Negative generated---------\n");
            image negative = allNegatives.get(Uid);
            display(negative);
            System.out.println("\n------------------------------------\n");
            allNegatives.set(Uid, negative);
        }

        System.out.println("1: input image\n2: create image\n3: update image\n4: Display image\n5: Generate negative\n6: exit");
        ch= Integer.parseInt(br.readLine());
    }
    
    }    
}
//complete
