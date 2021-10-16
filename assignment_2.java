import java.util.ArrayList;
import java.util.Scanner;

/**
 * assign2
 */
public class assignment_2 {
    static class Student{
        String sname;
        int sid;

        Student(String sname,int sid){
            this.sname=sname;
            this.sid=sid;
        }
    }
    static class Teacher{
        String tname;
        int tid;

        Teacher(String tname,int tid){
            this.tname=tname;
            this.tid=tid;
        }
    }

    interface BackPack {
        public void viewLectureMaterial();
    }

    static class InstructorTasks{
        public void addClassMaterial(slide s){
            allslides.add(s);
        }
        public void addClassMaterial(video v){
            allvideos.add(v);
        }
    }
    static class video{
        String topic;
        String filename;
        String extention;

        video( String topic, String filename){
            this.topic=topic;
            this.filename=filename;
            int ldot=filename.lastIndexOf('.');
            this.extention=filename.substring(ldot);
        }

    }
    static class slide{
        String topic;
        int numSlides;
        ArrayList<String> content;

        public void createSlide(String topic,int num)
        {   
            Scanner sc=new Scanner(System.in);
            this.topic=topic;
            this.numSlides=num;
            System.out.println("Enter content of slides");
            for(int i=0;i<num;i++){
                System.out.print("Content of slide"+i+":");
                String c=sc.nextLine();
                this.content.add(c);
            }
        }
    }
    static ArrayList<slide> allslides=new ArrayList<>();
    static ArrayList<video> allvideos=new ArrayList<>();
    static ArrayList<Teacher> allTeachers;
    static ArrayList<Student> allStudents;

    public static void displayTeachermenu(){
        System.out.println("1. Add class material\n2. Add assessments\n3. View lecture materials\n4. View assessments\n5. Grade assessments\n6. Close assessment\n7. View comments\n8. Add comments\n9. Logout");
    }
    public static void displayStudentmenu(){
        System.out.println("1. View lecture materials\n2. View assessments\n3. Submit assessment\n4. View grades\n5. View comments\n6. Add comments\n7. Logout");
    }

    
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        //creating dummy teachers
        for(int i=0;i<2;i++){
            Teacher t=new Teacher("I"+i,i);
            allTeachers.add(t);
        }
        //creating dummy students
        for(int i=0;i<5;i++){
            Student s=new Student("S"+i,i);
            allStudents.add(s);
        }
        System.out.println("Welcome to Backpack\n1. Enter as instructor\n2. Enter as student\n3. Exit");
        int ch=sc.nextInt();
        while(ch!=3){
            if(ch==1){

                InstructorTasks it =new InstructorTasks();//Real instructor object to execute instructor functions

                for(int i=0;i<allTeachers.size();i++){
                    System.out.println(i+"-"+allTeachers.get(i).tname);
                }
                System.out.print("Choose id: ");
                int id=sc.nextInt();
                displayTeachermenu();

                int task=sc.nextInt();
                if(task==1){
                    System.out.println("1. Add Lecture Slide\n2. Add Lecture Video");
                    int lecmat_ch=sc.nextInt();
                    if(lecmat_ch==1){
                        System.out.print("Enter topic of slides:");
                        String topic=sc.nextLine();
                       
                        System.out.print("Enter number of slides:");
                        int num=sc.nextInt();
                        slide s=new slide();
                        s.createSlide(topic,num);
                        it.addClassMaterial(s);
                    }
                }
            }
        }
    }
}