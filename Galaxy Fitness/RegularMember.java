// Concrete class RegularMember extending the abstract class GymMember
public class RegularMember extends GymMember {

    // Constants and additional member-specific fields
    private final int attendanceLimit = 30;
    private boolean isEligibleForUpgrade;
    private String removalReason;
    private String referralSource;
    private String plan;
    private double price;

    // Constructor for initializing a regular gym member
    public RegularMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String referralSource){
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.isEligibleForUpgrade = false;
        this.plan = "Basic";
        this.price = 6500;
        this.removalReason = "";
        this.referralSource = referralSource;
    }

    // Getters for additional fields
    public int getAttendanceLimit(){
        return attendanceLimit;
    }

    public boolean isEligibleForUpgrade(){
        return isEligibleForUpgrade;
    }

    public String getRemovalReason(){
        return removalReason;
    }

    public String getReferralSource(){
        return referralSource;
    }

    public String getPlan(){
        return plan;
    }

    public double getPrice(){
        return price;
    }

    // Setter for removal reason
    public void setRemovalReason(String reason) {
        this.removalReason = reason;
    }

    // Implementation of abstract method to mark attendance
    @Override
    public void markAttendance(){
        if(this.activeStatus){
            this.attendance += 1;
            this.loyaltyPoints += 5; // Reward for attending
        } else {
            System.out.println("Member is not active. Cannot mark Attendance.");
        }
    }

    // Override to activate membership with additional message
    @Override
    public void activateMembership(){
        if(!this.activeStatus){
            super.activateMembership();
            System.out.println("Regular membership activated successfully.");
        } else {
            System.out.println("Regular membership is already active.");
        }
    }

    // Override to deactivate membership with additional message
    @Override
    public void deactivateMembership(){
        if(this.activeStatus){
            super.deactivateMembership();
            System.out.println("Regular membership deactivated successfully");
        } else {
            System.out.println("Regular membership is already inactive");
        }
    }

    // Reset and revert a regular member to default settings
    public void revertRegularMember(String removalReason){
        super.resetMember();
        this.isEligibleForUpgrade = false;
        this.plan = "Basic";
        this.price = 6500;
        this.removalReason = removalReason;
    }

    // Static method to get the price based on plan type
    public static double getPlanPrice(String plan){
        switch (plan.toLowerCase()) {
            case "basic":
                return 6500;
            case "standard":
                return 12500;
            case "deluxe":
                return 18500;
            default:
                return -1; // Invalid plan
        }
    }

    // Method to upgrade the member's plan if eligible
    public String upgradePlan(String newPlan){
        // Check if attendance has reached the limit to allow upgrade
        if(this.attendance >= attendanceLimit){
            this.isEligibleForUpgrade = true;
        }

        if (!isEligibleForUpgrade) {
            return "Member is not eligible for a plan upgrade.";
        }

        if (newPlan.equalsIgnoreCase(this.plan)) {
            return "New plan is same as the current plan. No changes made.";
        }

        double newPrice = getPlanPrice(newPlan);
        if (newPrice == -1) {
            return "Invalid plan provided. Upgrade failed.";
        }

        // Capitalize plan name and update values
        this.plan = newPlan.substring(0,1).toUpperCase() + newPlan.substring(1).toLowerCase();
        this.price = newPrice;
        return "Plan upgraded successfully to " + this.plan + ". New price is " + this.price;
    }

    // Display all details of the regular member
    @Override
    public void display(){
        super.display(); // Call base class display
        System.out.println("Plan: " + plan);
        System.out.println("Plan Price: $" + price);
        System.out.println("Referral Source: " + referralSource);
        System.out.println("Attendance Limit: " + attendanceLimit);
        System.out.println("Eligible for Upgrade: " + (isEligibleForUpgrade ? "Yes" : "No"));
        if(!removalReason.isEmpty()){
            System.out.println("Removal Reason: " + removalReason);
        }
    }
}
