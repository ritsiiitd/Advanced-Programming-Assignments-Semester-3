
import java.util.*;
import java.io.*;


/**
 * assign2
 */
public class BackPack {

    interface commonFunc {
        public void viewLectureMaterial();
        public void viewAssessments();
        public void addComments(String comment,String name);
        public void viewComments();
    }
    interface Instructortask {
        public void addClassMaterial(slide s);
        public void addClassMaterial(video v);
        public void addAssessment(assessment a);
        public void gradeAssessments(assessment a,Student s,Teacher teach)throws IOException;
        public void closeAssessment(assessment a);

    }
    interface Studenttask{
        public void submitAssessments(Student s, assessment a)throws IOException;
        public void viewGrades(Student s);
    }
    
    static class common implements commonFunc{

        @Override
        public void viewLectureMaterial(){
            for(int i=0;i<allslides.size();i++){
                System.out.println("Title: "+allslides.get(i).getTopic());
                for(int j=0;j<allslides.get(i).getContent().size();j++){
                    System.out.println("Slide "+(j+1)+": "+allslides.get(i).getContent().get(j));
                }
                System.out.println("Number of slides: "+allslides.get(i).getNumberofslides());
                System.out.println("Date of upload: "+allslides.get(i).getdate());
                System.out.println("Uploaded by: "+allslides.get(i).getUploadedby());
                System.out.println();
            }

            for(int i=0;i<allvideos.size();i++){
                System.out.println("Title of video : "+allvideos.get(i).getTopic());
                System.out.println("Video file: "+allvideos.get(i).getfilename());
                System.out.println("Date of upload:"+allvideos.get(i).getdate());
                System.out.println("Uploaded by: "+allvideos.get(i).getUploadedby());
                System.out.println();
            }
        }

        @Override
        public void viewAssessments(){

            for(int i=0;i<allAssessments.size();i++){
                assessment a=allAssessments.get(i);

                if(a.gettype().equalsIgnoreCase("assignment")){
                    System.out.println("ID: "+a.getassessId()+" Assignment:"+a.getQues()+" Max-Marks:"+a.getMaxmarks());
                    System.out.println("-------------------");
                }
                else if(a.gettype().equalsIgnoreCase("quiz")){
                    System.out.println("ID: "+a.getassessId()+" Question:"+a.getQues());
                    System.out.println("-------------------");
                }

            }
        }

        @Override
        public void addComments(String comment,String name)
        {
            Comment cmnt=new Comment(comment,name);
            allComments.add(cmnt);
        }

        @Override
        public void viewComments(){
            
            for(int i=0;i<allComments.size();i++){
                Comment c= allComments.get(i);
                System.out.println(c.getComment()+"-"+c.getCommenterName());
                System.out.println(c.getDate());

                System.out.println();
            }
        }
    }
    
    static class InstructorTasks extends common implements Instructortask{

        @Override
        public void addClassMaterial(slide s){
            allslides.add(s);
        }

        @Override
        public void addClassMaterial(video v){
            allvideos.add(v);
        }

        @Override
        public void addAssessment(assessment a){
            allAssessments.add(a);
            openAssessments.add(a);

            for(int i=0;i<allStudents.size();i++){
                Student stu=allStudents.get(i);
                stu.getMypendingassess().add(a);
            }
        }

        public boolean displaySubmissions(assessment a){
            boolean found=false;

            for(int i=0;i<allStudents.size();i++){

                Student s=allStudents.get(i);
                for(int j=0;j<s.getMysubmissions().size();j++){
                    if(s.getMysubmissions().get(j).getAssId()==a.getassessId() && s.getMysubmissions().get(j).getGrades()==-1){//means this submission is not graded till now
                        found=true;
                        System.out.println(s.getSid() + ". "+ s.getName());
                    }
                }

            }
            return found;
        }

        @Override
        public void gradeAssessments(assessment a,Student s,Teacher teach)throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            submission grSub=null;

            for(int i=0;i<s.getMysubmissions().size();i++){

                if(s.getMysubmissions().get(i).getAssId()==a.getassessId() && s.getMysubmissions().get(i).getGrades()==-1){
                    grSub=s.getMysubmissions().get(i);
                    break;
                }

            }
            System.out.println("Submission: "+grSub.getSoln());
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Max marks: "+a.getMaxmarks());
            System.out.print("Marks scored: ");
            int score=Integer.parseInt(br.readLine());

            grSub.setGrades(score);;
            grSub.setTeacher(teach);
        }

        @Override
        public void closeAssessment(assessment a){
            openAssessments.remove(a);
            closedAssessments.add(a);

            for(int i=0;i<allStudents.size();i++){
                Student s=allStudents.get(i);
                boolean notsubmitted=false;

                for(int j=0;j<s.getMypendingassess().size();j++){

                    if(s.getMypendingassess().get(j).getassessId()==a.getassessId()){
                        notsubmitted=true;
                    }
                }

                if(notsubmitted)
                s.getMypendingassess().remove(a);
            }
        }

        public assessment addAssessmentHelper()throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
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

    }

    static class StudentTasks extends common implements Studenttask{

        @Override
        public void submitAssessments(Student s, assessment a)throws IOException{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  
            String soln="";
            if(a.gettype().equalsIgnoreCase("assignment"))
            {
                System.out.print("Enter filename of assignment: ");
                soln=br.readLine();
                String extension=getExtension(soln);
                
                if(!extension.equalsIgnoreCase(".zip")){
                System.out.println("Only .zip format is allowed in submissions");
                return;
                }
            }
            else{
                System.out.print(a.getQues()+" ");
                soln=br.readLine();
            }

            submission sub=new submission(soln,a.getassessId());
            s.getMysubmissions().add(sub);

            s.getMypendingassess().remove(a);
        }

        @Override
        public void viewGrades(Student s){

            System.out.println("Graded Submissions");
            System.out.println("-------------------");
            for(int i=0;i<s.getMysubmissions().size();i++){
                submission subm=s.getMysubmissions().get(i);
                if(subm.getGrades()!=-1){ 
                    System.out.println("Submission: "+subm.getSoln());
                    System.out.println("Marks Scored: "+subm.getGrades());
                    System.out.println("Graded by: "+subm.getTeacher().getName());
                }
                System.out.println();
            }
            
            System.out.println();

            System.out.println("Ungraded Submissions");
            System.out.println("-------------------");
            for(int i=0;i<s.getMysubmissions().size();i++){
                submission subm=s.getMysubmissions().get(i);
                if(subm.getGrades()==-1){ 
                    System.out.println("Submission: "+subm.getSoln());
                }
            }
            System.out.println();
        }
    }   
  
    static class Student{
        private String sname;
        private int sid;
        private ArrayList<submission> mySubmissions;
        private ArrayList<assessment> myPendingAssessments;

        Student(String sname,int sid){
            this.sname=sname;
            this.sid=sid;
            mySubmissions=new ArrayList<>();
            myPendingAssessments=new ArrayList<>();
        }
        public String getName(){
            return sname;
        }
        public ArrayList<submission> getMysubmissions(){
            return mySubmissions;
        }
        public ArrayList<assessment> getMypendingassess(){
            return myPendingAssessments;
        }
        public int getSid(){
            return sid;
        }
    }   
    static class Teacher{
        private String tname;
        private int tid;

        Teacher(String tname,int tid){
            this.tname=tname;
            this.tid=tid;
        }
        public String getName(){
            return tname;
        }
    }

    static class video{
        private String topic;
        private String filename;
        private String extension;
        private Date dateandtime;
        private String uploadedBy;

        video( String topic, String filename,String uploadedBy){
            this.topic=topic;
            this.filename=filename;
            this.uploadedBy=uploadedBy;
            this.extension=getExtension(filename);
            dateandtime=new java.util.Date(); //credits: https://www.javatpoint.com/java-get-current-date
        }

        public String getTopic(){
            return topic;
        }
        public String getfilename(){
            return filename;
        }
        public String getextension(){
            return extension;
        }
        public Date getdate(){
            return dateandtime;
        }
        public String getUploadedby(){
            return uploadedBy;
        }


    }    
    static class slide{
        private String topic;
        private int numSlides;
        private ArrayList<String> content=new ArrayList<>();
        private Date dateandtime;
        private String uploadedBy;

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
        public String getTopic(){
            return topic;
        }
        public int getNumberofslides(){
            return numSlides;
        }
        public ArrayList<String> getContent(){
            return content;
        }
        public Date getdate(){
            return dateandtime;
        }
        public String getUploadedby(){
            return uploadedBy;
        }
    }

    static class assessment{
        private String type;
        private String ques; 
        private int maxMarks;
        private int id;

        assessment(String type,String ques,int maxMarks){
            this.type=type;
            this.ques=ques;
            this.maxMarks=maxMarks;
            id=currentAssessmentNumber;
            
        }

        public String gettype(){
            return type;
        }
        public String getQues(){
            return ques;
        }
        public int getMaxmarks(){
            return maxMarks;
        }
        public int getassessId(){
            return id;
        }

    }
    static class submission{
        private String solution;
        private int grades;
        private int assessmentId;
        private Teacher gradedBy;

        submission(String soln,int assessId){
            solution=soln;
            grades=-1;//not graded yet
            assessmentId=assessId;
            gradedBy=null;
        }
        public String getSoln(){
            return solution;
        }
        public Teacher getTeacher(){
            return gradedBy;
        }
        public int getGrades(){
            return grades;
        }
        public int getAssId(){
            return assessmentId;
        }
        public void setGrades(int grades){
            this.grades=grades;
        }
        public void setTeacher(Teacher t){
            this.gradedBy=t;
        }
    }
    static class Comment{
        private String comment;
        private String name;
        private Date dateandtime;

        Comment(String comment,String name){
            this.name=name;
            this.comment=comment;
            this.dateandtime=new java.util.Date();
        }
        public String getComment(){
            return comment;
        }
        public String getCommenterName(){
            return name;
        }
        public Date getDate(){
            return dateandtime;
        }
    }
    
    public static String getExtension(String filename){
        int ldot=filename.lastIndexOf('.');
        String extension=filename.substring(ldot);
        return extension;
    }
    public static void displayTeachermenu(){
        System.out.println("1. Add class material\n2. Add assessments\n3. View lecture materials\n4. View assessments\n5. Grade assessments\n6. Close assessment\n7. View comments\n8. Add comments\n9. Logout");
    }
    public static void displayStudentmenu(){
        System.out.println("1. View lecture materials\n2. View assessments\n3. Submit assessment\n4. View grades\n5. View comments\n6. Add comments\n7. Logout");
    }


    static int currentAssessmentNumber=0;
    static ArrayList<slide> allslides=new ArrayList<>();
    static ArrayList<video> allvideos=new ArrayList<>();
    static ArrayList<Teacher> allTeachers=new ArrayList<>();
    static ArrayList<Student> allStudents=new ArrayList<>();
    static ArrayList<assessment> allAssessments=new ArrayList<>();
    static ArrayList<assessment> openAssessments=new ArrayList<>();
    static ArrayList<assessment> closedAssessments=new ArrayList<>();
    static ArrayList<Comment> allComments=new ArrayList<>();
    
    public static void main(String[] args)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       
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

        System.out.println("\nWelcome to Backpack\n1. Enter as instructor\n2. Enter as student\n3. Exit");
        int ch=Integer.parseInt(br.readLine());
       
        while(ch!=3){
            
            if(ch==1){//logging in as Instructor

                InstructorTasks it = new InstructorTasks(); //Real instructorTask object to execute instructor functions

                for(int i=0;i<allTeachers.size();i++){
                    System.out.println(i+"-"+allTeachers.get(i).getName());
                }

                System.out.print("Choose id: ");
                int id=Integer.parseInt(br.readLine());

                Teacher loggedinTeacher=allTeachers.get(id);

                System.out.println("\nWelcome "+loggedinTeacher.getName());
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
                        s.createSlide(topic,num,loggedinTeacher.getName());
                        it.addClassMaterial(s);
                    }

                    else if(lecmat_ch==2){
                        System.out.print("Enter topic of video:");
                        String topic=br.readLine();

                        System.out.print("Enter filename of video: ");
                        String file=br.readLine();

                        video v=new video(topic, file, loggedinTeacher.getName());

                        if(v.extension.equals(".mp4")){
                            it.addClassMaterial(v);
                        }
                        else{
                            System.out.println("only .mp4 format videos can be uploaded, please try again!");
                        }

                    }
                }

                else if(task==2){
                    assessment a = it.addAssessmentHelper();

                    if(a!=null){
                        it.addAssessment(a);
                        currentAssessmentNumber++;
                    }
                }

                else if(task==3){
                    it.viewLectureMaterial();
                }

                else if(task==4){
                    it.viewAssessments();
                }

                else if(task==5){
                    System.out.println("List of assessments");//displaying all assessments given so far(open plus closed)
                    it.viewAssessments();
                    
                    System.out.print("Enter ID of assessment to view submissions: ");
                    int idx=Integer.parseInt(br.readLine());
                    
                    assessment toGrade=allAssessments.get(idx);

                    System.out.println("Choose ID from these ungraded submissions");
                    boolean available=it.displaySubmissions(toGrade);

                    if(available){
                        int stg=Integer.parseInt(br.readLine());
                        Student stutograde=allStudents.get(stg);//student to grade
                        it.gradeAssessments(toGrade, stutograde,loggedinTeacher);
                    }
                    else{
                        System.out.println("No ungraded submissions for this assignment are available");
                    }

                }

                else if(task==6){
                    System.out.println("List of open assessments");
                    for(int i=0;i<openAssessments.size();i++){
                        assessment a=openAssessments.get(i);

                        if(a.gettype().equalsIgnoreCase("assignment")){
                            System.out.println("ID: "+a.getassessId()+" Assignment:"+a.getQues()+" Max-Marks:"+a.getMaxmarks());
                            System.out.println("-------------------");
                        }
                        else if(a.gettype().equalsIgnoreCase("quiz")){
                            System.out.println("ID: "+a.getassessId()+" Question:"+a.getQues());
                            System.out.println("-------------------");
                        }

                    }

                    System.out.println("Enter ID of assessment to be closed");
                    int close=Integer.parseInt(br.readLine());

                    assessment closea=allAssessments.get(close);
                    it.closeAssessment(closea);
                }

                else if(task==7){
                    it.viewComments();
                }

                else if(task==8){
                    System.out.print("Enter comment: ");
                    String cmnt=br.readLine();

                    it.addComments(cmnt, loggedinTeacher.getName());
                }
                System.out.println("\nWelcome "+loggedinTeacher.getName());
                displayTeachermenu();
                task=Integer.parseInt(br.readLine());

            }
            }

            else if(ch==2)  //Logging in as student
            {
                StudentTasks st = new StudentTasks();

                for(int i=0;i<allStudents.size();i++){
                    System.out.println(i+"-"+allStudents.get(i).getName());
                }

                System.out.print("Choose id: ");
                int id=Integer.parseInt(br.readLine());

                Student loggedinS=allStudents.get(id);

                System.out.println("\nWelcome "+loggedinS.getName());
                displayStudentmenu();
                int task=Integer.parseInt(br.readLine());

                while(task!=7){// 7 means logout

                    if(task==1){
                        st.viewLectureMaterial();
                    }

                    else if(task==2){
                        st.viewAssessments();
                    }

                    else if(task==3){

                        if(loggedinS.getMypendingassess().size()==0){
                            System.out.println("No pending assessment");
                            System.out.println("\nWelcome "+loggedinS.getName());
                            displayStudentmenu();
                            task=Integer.parseInt(br.readLine());
                            continue;
                        }

                        System.out.println("Pending assignments");

                        for(int i=0;i<loggedinS.getMypendingassess().size();i++){
                            assessment pa=loggedinS.getMypendingassess().get(i);

                            if(pa.gettype().equalsIgnoreCase("assignment"))
                            System.out.println("ID: "+pa.getassessId()+ " Assignment" +" " +pa.getQues() +" Max Marks "+pa.getMaxmarks());

                            else if(pa.gettype().equalsIgnoreCase("quiz"))
                            System.out.println("ID: "+pa.getassessId()+ " Question "+pa.getQues() );
                        }

                        if(loggedinS.getMypendingassess().size()!=0)
                        {
                            System.out.print("Enter ID of assessment: ");
                            int assessid=Integer.parseInt(br.readLine());

                            assessment tosolve=allAssessments.get(assessid);
                            st.submitAssessments(loggedinS, tosolve);
                        }
                    }

                    else if(task==4){
                        st.viewGrades(loggedinS);
                    }

                    else if(task==5){
                        st.viewComments();
                    }

                    else if(task==6){
                        System.out.print("Enter comment: ");
                        String cmnt=br.readLine();

                        st.addComments(cmnt, loggedinS.getName());
                    }
                
                System.out.println("\nWelcome "+loggedinS.getName());
                displayStudentmenu();
                task=Integer.parseInt(br.readLine());
                }

            }//logged out

            System.out.println("\nWelcome to Backpack\n1. Enter as instructor\n2. Enter as student\n3. Exit");
            ch=Integer.parseInt(br.readLine());
        }//exit from BACKPACK
    }
}