// Concrete class PremiumMember extending abstract class GymMember
public class PremiumMember extends GymMember {

    // Premium-specific fields
    private final double premiumCharge;
    private String personalTrainer;
    private boolean isFullPayment;
    private double paidAmount;
    private double discountAmount;

    // Constructor to initialize PremiumMember attributes
    public PremiumMember(int id, String name, String location, String phone, String email, String gender, String dob, String membershipStartDate, String personalTrainer){
        super(id, name, location, phone, email, gender, dob, membershipStartDate);
        this.premiumCharge = 50000;
        this.paidAmount = 0;
        this.isFullPayment = false;
        this.discountAmount = 0;
        this.personalTrainer = personalTrainer;
    }

    // Getters
    public double getPremiumCharge(){
        return premiumCharge;
    }

    public String getPersonalTrainer(){
        return personalTrainer;
    }

    public boolean isFullPayment(){
        return isFullPayment;
    }

    public double getPaidAmount(){
        return paidAmount;
    }

    public double getdiscountAmount(){
        return discountAmount;
    }

    // Activate membership with message override
    @Override
    public void activateMembership(){
        if(!this.activeStatus){
            super.activateMembership();
            System.out.println("Premium membership activated successfully.");
        } else {
            System.out.println("Premium member already active");
        }
    }

    // Deactivate membership with message override
    @Override
    public void deactivateMembership(){
        if(this.activeStatus){
            super.deactivateMembership();
            System.out.println("Premium membership deactivated successfully.");
        } else {
            System.out.println("Premium membership is already inactive.");
        }
    }

    // Calculates discount if full payment has been made
    public void calculateDiscount(){
        if(this.isFullPayment){
            this.discountAmount = 0.1 * premiumCharge;  // 10% discount
            System.out.println("Discount applied: $" + discountAmount);
        } else {
            System.out.println("No discount available. Full payment is required.");
        }
    }

    // Allows payment towards premium membership and manages state
    public String payDueAmount(double amount){
        if (isFullPayment){
            return "Full Payment has been already made";
        }

        double totalCharge = premiumCharge - discountAmount;

        if (this.paidAmount + amount > totalCharge){
            return "Payment exceeds the required amount";
        }

        this.paidAmount += amount;

        // Check if payment is complete
        if(this.paidAmount == totalCharge){
            this.isFullPayment = true;
            calculateDiscount();
            activateMembership();
        }

        double remainingAmount = totalCharge - this.paidAmount;
        return "Payment successful. Remaining amount: $" + remainingAmount;
    }

    // Override for marking attendance for premium members
    public void markAttendance(){
        if(this.activeStatus){
            this.attendance++;
            this.loyaltyPoints += 10;  // Premium members get 10 loyalty points per attendance
        } else {
            System.out.println("Member is not active. Cannot mark attendance.");
        }
    }

    // Resets the premium member's state
    public void revertPremiumMember(){
        super.resetMember();
        personalTrainer = "";
        isFullPayment = false;
        paidAmount = 0;
        discountAmount = 0;
    }

    // Displays all premium member details
    public void display(){
        super.display();  // Call base class display method
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: $" + paidAmount);
        System.out.println("Full Payment Completed: " + (isFullPayment ? "Yes" : "No"));
        System.out.println("Remaining Amount: $" + (premiumCharge - paidAmount));
        if (isFullPayment) {
            System.out.println("Discount Amount: $" + discountAmount);
        }
    }
}
