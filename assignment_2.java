
import java.util.*;
import java.io.*;

/**
 * assign2
 */
public class assignment_2 {
    static class Student{
        String sname;
        int sid;
        ArrayList<submission> mySubmissions;
        ArrayList<assessment> myPendingAssessments;

        Student(String sname,int sid){
            this.sname=sname;
            this.sid=sid;
            mySubmissions=new ArrayList<>();
            myPendingAssessments=new ArrayList<>();
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

    static class common implements BackPack{
        public void viewLectureMaterial(){
            for(int i=0;i<allslides.size();i++){
                System.out.println("Title: "+allslides.get(i).topic);
                for(int j=0;j<allslides.get(i).content.size();j++){
                    System.out.println("Slide "+(j+1)+" "+allslides.get(i).content.get(j));
                }
                System.out.println("Number of slides: "+allslides.get(i).numSlides);
                System.out.println("Date of upload: "+allslides.get(i).dateandtime);
                System.out.println("Uploaded by: "+allslides.get(i).uploadedBy);
                System.out.println();
            }

            for(int i=0;i<allvideos.size();i++){
                System.out.println("Title of video : "+allvideos.get(i).topic);
                System.out.println("Video file: "+allvideos.get(i).filename);
                System.out.println("Date of upload:"+allvideos.get(i).dateandtime);
                System.out.println("Uploaded by: "+allvideos.get(i).uploadedBy);
            }
        }

        public void viewAssessments(){
            for(int i=0;i<allAssessments.size();i++){
                assessment a = allAssessments.get(i);
                if(a.type.equalsIgnoreCase("assignment")){
                    System.out.println("ID: "+i+" Assignment:"+a.ques+" Max-Marks:"+a.maxMarks);
                    System.out.println("-------------------------------------------------------------------------------------------");
                }
                else if(a.type.equalsIgnoreCase("quiz")){
                    System.out.println("ID: "+i+" Question:"+a.ques);
                    System.out.println("-------------------------------------------------------------------------------------------");
                }
            }
        }
    }
    static class InstructorTasks extends common{
        public void addClassMaterial(slide s){
            allslides.add(s);
        }
        public void addClassMaterial(video v){
            allvideos.add(v);
        }
        public void addAssessment(assessment a){
            allAssessments.add(a);
            for(int i=0;i<allStudents.size();i++){
                Student stu=allStudents.get(i);
                stu.myPendingAssessments.add(a);
            }
        }
    }

    static class StudentTasks extends common{

        public void submitAssessments(Student s, assessment a)throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

          
            
            String soln="";
            if(a.type.equalsIgnoreCase("assignment"))
            {
                System.out.print("Enter filename of assignment: ");
                soln=br.readLine();
                String extention=getExtension(soln);
                
                if(!extention.equalsIgnoreCase(".zip")){
                System.out.println("Only .zip fomat is allowed in submissions");
                return;
                }
            }
            else{
                System.out.println("entered submitassv  "+a.type+" "+a.ques);
                System.out.print(a.ques+" ");
                soln=br.readLine();
            }

            submission sub=new submission(soln,a.id);
            s.mySubmissions.add(sub);

            s.myPendingAssessments.remove(a);
        }
    }
    static class video{
        String topic;
        String filename;
        String extention;
        Date dateandtime;
        String uploadedBy;
        video( String topic, String filename,String uploadedBy){
            this.topic=topic;
            this.filename=filename;
            this.uploadedBy=uploadedBy;
            this.extention=getExtension(filename);
            dateandtime=new java.util.Date(); //credits: https://www.javatpoint.com/java-get-current-date
        }

    }
    static String getExtension(String filename){
        int ldot=filename.lastIndexOf('.');
        String extention=filename.substring(ldot);
        return extention;
    }
    static class slide{
        String topic;
        int numSlides;
        ArrayList<String> content=new ArrayList<>();
        Date dateandtime;
        String uploadedBy;

        public void createSlide(String topic,int num,String uploadedBy)throws IOException
        {   
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            this.topic=topic;
            this.numSlides=num;
            this.uploadedBy=uploadedBy;
            dateandtime=new java.util.Date(); //credits: https://www.javatpoint.com/java-get-current-date
            System.out.println("Enter content of slides");
            for(int i=0;i<num;i++){
                System.out.print("Content of slide "+(i+1)+":");
                String c=br.readLine();
                this.content.add(c);
            }
        }
    }

    static class assessment{
        String type;
        String ques; 
        int maxMarks;
        int id;
        assessment(String type,String ques,int maxMarks){
            this.type=type;
            this.ques=ques;
            this.maxMarks=maxMarks;
            id=currentAssessmentNumber;
            
        }
    }
    static class submission{
        String solution;
        float grades;
        int assessmentId;

        submission(String soln,int assessId){
            solution=soln;
            grades=-1;//not graded yet
            assessmentId=assessId;

        }
    }
    static ArrayList<slide> allslides=new ArrayList<>();
    static ArrayList<video> allvideos=new ArrayList<>();
    static ArrayList<Teacher> allTeachers=new ArrayList<>();
    static ArrayList<Student> allStudents=new ArrayList<>();
    static ArrayList<assessment> allAssessments=new ArrayList<>();

    public static void displayTeachermenu(){
        System.out.println("1. Add class material\n2. Add assessments\n3. View lecture materials\n4. View assessments\n5. Grade assessments\n6. Close assessment\n7. View comments\n8. Add comments\n9. Logout");
    }
    public static void displayStudentmenu(){
        System.out.println("1. View lecture materials\n2. View assessments\n3. Submit assessment\n4. View grades\n5. View comments\n6. Add comments\n7. Logout");
    }
    public static assessment addAssessmentHelper()throws IOException{
        InputStreamReader read=new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(read);
        System.out.println("1. Add Assignment\n2. Add Quiz");

        int ch=Integer.parseInt(br.readLine());
        int mm=1;
        String problem;
        assessment a;
        if(ch==1){
            System.out.print("Enter problem statement: ");
            problem=br.readLine();
            System.out.print("Enter max marks: ");
            mm=Integer.parseInt(br.readLine());
            a=new assessment("Assignment", problem, mm);
        }
        else if(ch==2){
            System.out.print("Enter quiz question: ");
            problem=br.readLine();
            a=new assessment("Quiz", problem, mm);
        }
        else{
            a=null;
        }
        return a;
    }


    static int currentAssessmentNumber=0;
    
    public static void main(String[] args)throws IOException{
        InputStreamReader read=new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(read);
       
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

        common c=new common();//to call common functions

        System.out.println("Welcome to Backpack\n1. Enter as instructor\n2. Enter as student\n3. Exit");
        int ch=Integer.parseInt(br.readLine());
        while(ch!=3){
            if(ch==1){

                InstructorTasks it = new InstructorTasks(); //Real instructorTask object to execute instructor functions

                for(int i=0;i<allTeachers.size();i++){
                    System.out.println(i+"-"+allTeachers.get(i).tname);
                }
                System.out.print("Choose id: ");
                int id=Integer.parseInt(br.readLine());
                String teacherName=allTeachers.get(id).tname;

                displayTeachermenu();

                int task=Integer.parseInt(br.readLine());
                while(task!=9)
                {
                if(task==1){
                    System.out.println("1. Add Lecture Slide\n2. Add Lecture Video");
                    int lecmat_ch=Integer.parseInt(br.readLine());
                    if(lecmat_ch==1){
                        System.out.print("Enter topic of slides:");
                        
                        String topic=br.readLine();
                       
                        System.out.print("Enter number of slides:");
                        int num=Integer.parseInt(br.readLine());
                        slide s=new slide();
                        s.createSlide(topic,num,teacherName);
                        it.addClassMaterial(s);
                    }
                    else if(lecmat_ch==2){
                        System.out.print("Enter topic of video:");
                        String topic=br.readLine();
                        System.out.print("Enter filename of video: ");
                        String file=br.readLine();
                        video v=new video(topic, file, teacherName);
                        if(v.extention.equals(".mp4")){
                            it.addClassMaterial(v);
                        }
                        else{
                            System.out.println("only .mp4 format videos can be uploaded, please try again!");
                        }
                    }
                }

                else if(task==2){
                    assessment a = addAssessmentHelper();
                    if(a!=null){
                        it.addAssessment(a);
                        currentAssessmentNumber++;
                    }
                }

                else if(task==3){
                    c.viewLectureMaterial();
                }
                else if(task==4){
                    c.viewAssessments();
                }

                displayTeachermenu();

                task=Integer.parseInt(br.readLine());
            }
            }
            else if(ch==2){
                StudentTasks st = new StudentTasks();

                for(int i=0;i<allStudents.size();i++){
                    System.out.println(i+"-"+allStudents.get(i).sname);
                }
                System.out.print("Choose id: ");
                int id=Integer.parseInt(br.readLine());
                Student loggedinS=allStudents.get(id);
                displayStudentmenu();

                int task=Integer.parseInt(br.readLine());
                while(task!=7){

                    if(task==3){
                        if(loggedinS.myPendingAssessments.size()==0){
                            System.out.println("No pending assessment");
                            displayStudentmenu();
                            task=Integer.parseInt(br.readLine());
                            continue;
                        }
                        System.out.println("Pending assignments");
                        for(int i=0;i<loggedinS.myPendingAssessments.size();i++){
                            assessment pa=loggedinS.myPendingAssessments.get(i);
                            if(pa.type.equalsIgnoreCase("assignment"))
                            System.out.println("ID: "+pa.id+ " Assignment" +" " +pa.ques +" Max Marks "+pa.maxMarks);
                            else if(pa.type.equalsIgnoreCase("quiz"))
                            System.out.println("ID: "+pa.id+ " Question "+pa.ques );
                        }
                        if(loggedinS.myPendingAssessments.size()!=0){
                        System.out.print("Enter ID of assessment: ");
                        int assessid=Integer.parseInt(br.readLine());
                        assessment tosolve=allAssessments.get(assessid);

                        st.submitAssessments(loggedinS, tosolve);
                        }
                    }
                displayStudentmenu();
                task=Integer.parseInt(br.readLine());
                }

            }
            System.out.println("Welcome to Backpack\n1. Enter as instructor\n2. Enter as student\n3. Exit");
            ch=Integer.parseInt(br.readLine());
        }
    }
}