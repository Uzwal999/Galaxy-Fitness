// Abstract class representing a generic Gym Member
abstract class GymMember {

   // Protected fields accessible within subclass
   protected int id, attendance;
   protected String name, location, phone, email, gender, DOB, membershipStartDate;
   protected double loyaltyPoints;
   protected boolean activeStatus;

   // Constructor to initialize a gym member with basic details
   public GymMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate){
       this.id = id;
       this.name = name;
       this.location = location;
       this.phone = phone;
       this.email = email;
       this.gender = gender;
       this.DOB = DOB;
       this.membershipStartDate = membershipStartDate;
       this.attendance = 0; // Initially 0
       this.loyaltyPoints = 0.0; // Initially 0
       this.activeStatus = false; // Membership is inactive by default
   }

   // Getter methods for each attribute
   public int getId(){
       return id;
   }

   public String getName(){
       return name;
   }

   public String getLocation(){
       return location;
   }

   public String getPhone(){
       return phone;
   }

   public String getEmail(){
       return email;
   }

   public String getGender(){
       return gender;
   }

   public String getDOB(){
       return DOB;
   }

   public String getMembershipStartDate(){
       return membershipStartDate;
   }

   public int getAttendance(){
       return attendance;
   }

   public double getLoyaltyPoints(){
       return loyaltyPoints;
   }

   public boolean getActiveStatus(){
       return activeStatus;
   }

   // Abstract method to be implemented by subclasses to mark attendance
   public abstract void markAttendance();

   // Method to activate the membership
   public void activateMembership(){
       if(!this.activeStatus) {
           this.activeStatus = true;
           System.out.println("Membership activated successfully.");
       } else {
           System.out.println("Membership is already active.");
       }
   }

   // Method to deactivate the membership
   public void deactivateMembership(){
       if(this.activeStatus){
           this.activeStatus = false;
           System.out.println("Membership deactivated successfully");
       } else{
           System.out.println("Cannot deactivate. Membership is already inactive.");
       }
   }

   // Method to reset member’s stats (used when restarting or removing history)
   public void resetMember(){
       this.activeStatus = false;
       this.attendance = 0;
       this.loyaltyPoints = 0;
   }

   // Method to display all the member's details
   public void display(){
       System.out.println("id: "+ id);
       System.out.println("name: "+ name);
       System.out.println("location: "+ location);
       System.out.println("phone: "+ phone);
       System.out.println("email: "+ email);
       System.out.println("gender: "+ gender);
       System.out.println("DOB: "+ DOB);
       System.out.println("membershipStartDate: "+ membershipStartDate);
       System.out.println("Attendance: "+ attendance);
       System.out.println("LoyaltyPoints: "+ loyaltyPoints);
       System.out.println("ActiveStatus: " + (activeStatus ? "Active" : "Inactive"));
   }
}
