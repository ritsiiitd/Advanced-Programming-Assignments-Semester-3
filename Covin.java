/**
 * Covin
 */
import java.util.*;

public class Covin {

    public static class Slot{
        int day;
        int qty;
        Vaccine v_obj;
    }
    public static class Vaccine{
        String vname;
        int doses;
        int gap;
    }

    public static class Hospital{
        String hname;
        int pincode;
        int ID;
        ArrayList<Slot> HospitalSlots;
    }

    public static class Citizen{
        String name;
        int age;
        long UID;

        Vaccine vaccineTaken;
        Hospital hospitalchosen;
        int due_date;
        String vaccinationStatus;
        int dosesTaken;
    }

    public static void addVaccineHelper(ArrayList<Vaccine> allVaccines){
        Scanner sc=new Scanner(System.in);
        System.out.print("Vaccine name: ");
       
        String vname=sc.nextLine();
        
        System.out.print("Number of Doses: ");
        int doses=sc.nextInt();
        int gap=0;
        if(doses>1){
        System.out.print("Gap between Doses: ");
        gap=sc.nextInt();
        }
        addVaccine(allVaccines, vname, doses, gap);    
    }

    public static void addVaccine(ArrayList<Vaccine> allVaccines,String vname, int doses,int gap){//option 1
        Vaccine v=new Vaccine();
        v.vname=vname;
        v.doses=doses;
        v.gap=gap;

        allVaccines.add(v);
        System.out.println("Vaccine Name: "+vname+", Number of Doses: "+doses+", Gap Between Doses: "+gap);
    }

    public static void RegisterHospitalHelper(ArrayList<Hospital> allHospitals){
        Scanner sc=new Scanner(System.in);
        System.out.print("Hospital name: ");
       
            String hname=sc.nextLine();
            hcount++;
        
            System.out.print("Pincode: ");
            int pincode=sc.nextInt();

            int Id=generateId(hcount);
            RegisterHospital(allHospitals, hname, Id, pincode);
    }
    public static void RegisterHospital(ArrayList<Hospital> allHospitals,String hname, int Id,int pincode){ //option 2
        Hospital h=new Hospital();
        h.hname=hname;
        h.pincode=pincode;
        h.ID=Id;
        h.HospitalSlots=new ArrayList<>();
        allHospitals.add(h);
        System.out.println("Hospital Name: "+hname+", Pincode: "+pincode+", Unique ID: "+Id);
    }

    public static void RegisterCitizenHelper(ArrayList<Citizen> allCitizens){
        Scanner sc=new Scanner(System.in);
        System.out.print("Citizen name: ");
  
            String cname=sc.nextLine();

            System.out.print("Age: ");
            int age=sc.nextInt();
            System.out.print("Unique ID: ");
            long UID=sc.nextLong();
            if(age<18){
                System.out.println("Only citizens of age equal to and above 18 are allowed");
            }
            else{
                boolean duplicateid=false;
                for(int i=0;i<allCitizens.size();i++){
                    if(allCitizens.get(i).UID==UID){
                        duplicateid=true;
                        break;
                    }
                }
                if(duplicateid){
                    System.out.println("Citizen with UID "+UID+" already REGISTERED");
                }
                else{
                RegisterCitizen(allCitizens, cname, UID, age);
                }
            }
    }
    public static void RegisterCitizen(ArrayList<Citizen> allCitizens, String cname,long UID,int age){ //option 3
        Citizen c=new Citizen();
        c.name=cname;
        c.UID=UID;
        c.age=age;
        c.vaccinationStatus="REGISTERED";
        allCitizens.add(c); 
        System.out.println("Citizen Name: "+cname+", Age: "+age+", Unique ID: "+UID);

    }
    public static void createSlotHelper(ArrayList<Vaccine> allVaccines,ArrayList<Hospital> allHospitals){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter Hospital ID: ");
        int hid=sc.nextInt();
        Hospital t=null;
        for(int i=0;i<allHospitals.size();i++){
            if(allHospitals.get(i).ID==hid){
                t=allHospitals.get(i);
            }
        }
        if(t==null){
            System.out.println("Enter a valid Hospital Id");
            return;
        }
        System.out.print("Enter number of Slots to be added: ");
        int no_of_slots=sc.nextInt();
        for(int i=0;i<no_of_slots;i++){
            System.out.print("Enter Day Number: ");
            int day_no=sc.nextInt();
            System.out.print("Enter Quantity: ");
            int qty=sc.nextInt();
            System.out.println("Select Vaccine");
            for(int j=0;j<allVaccines.size();j++){
                System.out.println(j+" "+allVaccines.get(j).vname);
            }
            int vaccine_choice=sc.nextInt();
            createSlots(t, allVaccines, vaccine_choice, day_no, qty);
            
        }
    }
    static void createSlots(Hospital hospital_obj,ArrayList<Vaccine> allVaccines, int vaccine_choice ,int day_no, int qty){  //option 4  
        Slot newslot=new Slot();
        newslot.day=day_no;
        newslot.qty=qty;
        Vaccine v=allVaccines.get(vaccine_choice);
        newslot.v_obj=v;
        hospital_obj.HospitalSlots.add(newslot);
        System.out.println("Slot added by Hospital "+ hospital_obj.ID+ " for Day: "+ day_no +", Available Quantity: "+ qty +" of Vaccine "+ v.vname);
        
    }
    public static void bookslotArea(ArrayList<Hospital> allHospitals,Citizen patient){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter PinCode: ");
            int pin=sc.nextInt();
            int pinmatch=-1;
            for(int i=0;i<allHospitals.size();i++){
                if(allHospitals.get(i).pincode==pin){
                    pinmatch=i;
                    System.out.println(allHospitals.get(i).ID+" "+allHospitals.get(i).hname);
                }
            }
            if(pinmatch==-1){
                System.out.println("Not a valid pincode, slot not booked");
                return;
            }
            System.out.print("Enter hospital id: ");
            int hid=sc.nextInt();
            Hospital host;
            boolean hidValid=false;
            for(int i=0;i<allHospitals.size();i++){
                host=allHospitals.get(i);
                if(host.ID==hid){
                    hidValid=true;
                    if(host.HospitalSlots.size()==0){
                        System.out.println("No slots available");
                        return;
                    }
                    else{
                        for(int j=0;j<host.HospitalSlots.size();j++){
                            System.out.println(j+"->Day: "+host.HospitalSlots.get(j).day+" Available Qty:"+host.HospitalSlots.get(j).qty+" Vaccine "+host.HospitalSlots.get(j).v_obj.vname);
                        }
                        System.out.print("Choose Slot: ");
                        int choice=sc.nextInt();
                        Slot chosenSlot=host.HospitalSlots.get(choice);
                        if(chosenSlot.qty==0){
                            System.out.println("No vaccine available in chosen slot, slot not booked Sorry!");
                            return;
                        }
                        if(patient.due_date>chosenSlot.day){
                            //CHECKKKKKKKK(1)
                            System.out.println("Your due date is after this slot's date, you can not register for this slot!");
                            return;
                        }
                        if(patient.vaccineTaken!=null && patient.vaccineTaken!=chosenSlot.v_obj){
                            System.out.println("You previously took a shot of Vaccine: "+patient.vaccineTaken.vname+" you can not book a slot for vaccine: "+chosenSlot.v_obj.vname);
                            return;
                        }
                        chosenSlot.qty--;
                        
                        patient.vaccineTaken=chosenSlot.v_obj;
                        patient.dosesTaken++;
                        if(patient.dosesTaken < patient.vaccineTaken.doses){
                            patient.vaccinationStatus="PARTIALLY VACCINATED";
                            patient.due_date=chosenSlot.day+chosenSlot.v_obj.gap;//(1) debugged
                        }
                        else{
                            patient.vaccinationStatus="FULLY VACCINATED";
                            patient.due_date=0;
                        }
                        System.out.println(patient.name+" vaccinated with "+patient.vaccineTaken.vname);
                    }
                }
            }
            if(!hidValid){
                System.out.println("enter a valid hospital ID");
            }
    }

    public static void bookslotVaccine(ArrayList<Hospital> allHospitals,Citizen patient){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Vaccine name: ");
            
            String vaccname=sc.nextLine();

            int vaccinematch=-1;
            for(int i=0;i<allHospitals.size();i++){
                 Hospital temphost=allHospitals.get(i);
                for(int j=0;j<temphost.HospitalSlots.size();j++){
                    if(temphost.HospitalSlots.get(j).v_obj.vname.equals(vaccname)){
                        vaccinematch=1;
                        System.out.println(temphost.ID+" "+temphost.hname);
                    }
                }
            }
            if(vaccinematch==-1){
                System.out.println("No hospital has slot for this vaccine, slot not booked");
                return;
            }

            System.out.print("Enter hospital id: ");
            int hid=sc.nextInt();
            Hospital host=null;
            Slot chosenSlot=null;
            boolean hospitalfound=false;
            for(int i=0;i<allHospitals.size();i++){
                
                
                if(allHospitals.get(i).ID==hid){
                    host=allHospitals.get(i);
                    hospitalfound=true;
                    if(host.HospitalSlots.size()==0){
                        System.out.println("No slots available in"+ host.hname);
                        return;
                    }
                    boolean slotavailable=false;
                    for(int j=0;j<host.HospitalSlots.size();j++){
                        if(host.HospitalSlots.get(j).v_obj.vname.equals(vaccname) && patient.due_date<=host.HospitalSlots.get(j).day){
                            slotavailable=true;
                            System.out.println(j+"->Day: "+host.HospitalSlots.get(j).day+" Available Qty:"+host.HospitalSlots.get(j).qty+" Vaccine "+host.HospitalSlots.get(j).v_obj.vname);
                        }
                    }
                    if(!slotavailable){
                        System.out.println("No Slots Available");
                        return;
                    }
                    break;
                }
            }
            if(!hospitalfound || host==null){
                System.out.println("Enter Valid Hospital ID");
                return;
            }
            System.out.print("Choose Slot: ");
            int choice=sc.nextInt();
            chosenSlot=host.HospitalSlots.get(choice);
            if(chosenSlot.qty==0){
                System.out.println("No vaccine available in chosen slot, slot not booked Sorry!");
                return;
            }
            if(patient.due_date>chosenSlot.day){
                System.out.println("Your due date is after this slot's date, you can not register for this slot!");
                return;
            }
            if(patient.vaccineTaken!=null && patient.vaccineTaken!=chosenSlot.v_obj){
                System.out.println("You previously took a shot of Vaccine: "+patient.vaccineTaken.vname+" you can not book a slot for vaccine: "+chosenSlot.v_obj.vname);
                return; 
            }
            chosenSlot.qty--;
            
            patient.vaccineTaken=chosenSlot.v_obj;
            patient.dosesTaken++;
            if(patient.dosesTaken < patient.vaccineTaken.doses){
                patient.vaccinationStatus="PARTIALLY VACCINATED";
                patient.due_date=chosenSlot.day+chosenSlot.v_obj.gap;
            }
            else{
                patient.vaccinationStatus="FULLY VACCINATED";
                patient.due_date=0;
            }
            System.out.println(patient.name+" vaccinated with "+patient.vaccineTaken.vname);
    }
    static void bookSlotHelper(ArrayList<Hospital> allHospitals,ArrayList<Citizen> allCitizens){//option 5
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter patient Unique ID: ");
        long pUID=sc.nextLong();
        Citizen patient=null;
        for(int i=0;i<allCitizens.size();i++){
            if(allCitizens.get(i).UID==pUID){
                patient=allCitizens.get(i);
                break;
            }
        }
        if(patient==null){
            System.out.println("Not a valid patient unique ID, slot not booked");
            return;
        }

        if(patient.vaccinationStatus.equals("FULLY VACCINATED")){
            System.out.println("Patient with UID: "+patient.UID+" is FULLY VACCINATED, no need to book a slot");
            return;
        }

        System.out.println("1. Search by Area\n2. Search by Vaccine\n3. Exit");
        System.out.print("Enter option: ");
        int searchQuery=sc.nextInt();

        if(searchQuery==1){
            bookslotArea(allHospitals, patient);

        }
        else if(searchQuery==2){
            bookslotVaccine(allHospitals, patient);
        }
        else{
            return;
        }

    }

    static void slotsAvailable(ArrayList<Hospital> allHospitals){//option 6
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter Hospital ID: ");
        int hid=sc.nextInt();
        boolean hospital_found=false;
        for(int i=0;i<allHospitals.size();i++){
            if(allHospitals.get(i).ID==hid){
                hospital_found=true;
                Hospital host=allHospitals.get(i);
                if(host.HospitalSlots.size()==0){
                    System.out.println("No slots available in hospital "+host.hname);
                    return;
                }
                for(int j=0;j<host.HospitalSlots.size();j++){
                    System.out.println("Day: "+host.HospitalSlots.get(j).day+" Vaccine "+host.HospitalSlots.get(j).v_obj.vname+" Available Qty: "+host.HospitalSlots.get(j).qty);
                }
                break;
            }
        }
        if(!hospital_found){
            System.out.println("Not a valid hospital ID");
        }
    }
/////add same vaccine for same hospital in same 
    static void checkVaccinationStatus(ArrayList<Citizen> allCitizens){//option 7
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter patient ID");
        long pUID=sc.nextLong();
        boolean patientFound=false;
        for(int i=0;i<allCitizens.size();i++){
            if(allCitizens.get(i).UID==pUID){
                patientFound=true;
                Citizen p=allCitizens.get(i);
                System.out.println(p.vaccinationStatus);
                if(p.vaccineTaken!=null)
                System.out.println("Vaccine given: "+p.vaccineTaken.vname);
                else{
                    System.out.println("No vaccine taken till now");
                }
                System.out.println("Number of doses given: "+p.dosesTaken);
                if(p.dosesTaken!=0){
                System.out.println("Next Dose duee date: "+p.due_date);
                }
                break;
            }
        }
        if(!patientFound){
            System.out.println("Patien with UID: "+pUID+" not REGISTERED");
        }
    }
     
    static int generateId(int hcount){

        int temp=hcount;
        int nd=0;//no of digits in hospital number
        while(temp>0){
            nd++;
            temp=temp/10;
        }
        int Id=hcount;
        for(int i=1;i<=6-nd;i++){
            Id=Id*10+i;
        }
        return Id;
    }

    static int hcount=0;//to keep track of hospitals
    public static void main(String[] args) {
        System.out.println("CoWin Portal initialized....");
        Scanner sc=new Scanner(System.in);
        ArrayList<Vaccine> allVaccines=new ArrayList<>();
        ArrayList<Hospital> allHospitals=new ArrayList<>();
        ArrayList<Citizen> allCitizens=new ArrayList<>();
        System.out.println("---------------------------------");
        System.out.println("1. Add Vaccine\n2. Register Hospital\n3. Register Citizen\n4. Add Slot for Vaccination\n5. Book Slot for Vaccination\n6. List all slots for a hospital\n7. Check Vaccination Status\n8. Exit");
        System.out.println("---------------------------------");
        int ip=sc.nextInt();
        while(ip!=8){  

        if(ip==1){
            addVaccineHelper(allVaccines);
        }

        if(ip==2){
            RegisterHospitalHelper(allHospitals);
        }
        if(ip==3){
            RegisterCitizenHelper(allCitizens);
        }
        if(ip==4){
            createSlotHelper(allVaccines,allHospitals);
        }
        if(ip==5){
            bookSlotHelper(allHospitals,allCitizens);
        }
        if(ip==6){
            slotsAvailable(allHospitals);   
        }
        if(ip==7){
            checkVaccinationStatus(allCitizens);
        }
        System.out.println("---------------------------------");
        System.out.println("1. Add Vaccine\n2. Register Hospital\n3. Register Citizen\n4. Add Slot for Vaccination\n5. Book Slot for Vaccination\n6. List all slots for a hospital\n7. Check Vaccination Status\n8. Exit");
        System.out.println("---------------------------------");
        ip=sc.nextInt();
    }
    
        
    }
}