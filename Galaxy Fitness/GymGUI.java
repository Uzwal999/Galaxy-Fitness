import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

// Abstract base class for members
abstract class GymMember {
    protected int id;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected String dob;
    protected String membershipStartDate;
    protected boolean activeStatus;
    protected int attendance;
    protected double loyaltyPoints;

    public GymMember(int id, String name, String location, String phone, String email,
                     String gender, String dob, String membershipStartDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.membershipStartDate = membershipStartDate;
        this.activeStatus = true;
        this.attendance = 0;
        this.loyaltyPoints = 0.0;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public String getDOB() { return dob; }
    public String getMembershipStartDate() { return membershipStartDate; }
    public boolean getActiveStatus() { return activeStatus; }
    public int getAttendance() { return attendance; }
    public double getLoyaltyPoints() { return loyaltyPoints; }

    // Abstract methods
    public abstract void markAttendance();
    public abstract void display();

    public void activateMembership() {
        activeStatus = true;
    }

    public void deactivateMembership() {
        activeStatus = false;
    }
}

class RegularMember extends GymMember {
    private String referralSource;
    private String plan;
    private double price;
    private String removalReason;

    public RegularMember(int id, String name, String location, String phone, String email,
                        String gender, String dob, String membershipStartDate, String referralSource) {
        super(id, name, location, phone, email, gender, dob, membershipStartDate);
        this.referralSource = referralSource;
        this.plan = "Basic";
        this.price = getPlanPrice(plan);
        this.removalReason = "";
    }

    // Getters
    public String getReferralSource() { return referralSource; }
    public String getPlan() { return plan; }
    public double getPrice() { return price; }
    public String getRemovalReason() { return removalReason; }

    // Static method to get plan price
    public static double getPlanPrice(String plan) {
        switch (plan) {
            case "Basic": return 10000;
            case "Standard": return 15000;
            case "Deluxe": return 20000;
            default: return 10000;
        }
    }

    @Override
    public void markAttendance() {
        if (activeStatus) {
            attendance++;
            loyaltyPoints += 10;
        }
    }

    public String upgradePlan(String newPlan) {
        if (!activeStatus) {
            return "Cannot upgrade: Member is inactive.";
        }
        this.plan = newPlan;
        this.price = getPlanPrice(newPlan);
        return "Plan upgraded to " + newPlan + " successfully.";
    }

    public void revertRegularMember(String reason) {
        if (!activeStatus) {
            activateMembership();
            removalReason = reason;
        }
    }

    @Override
    public void display() {
        JFrame frame = new JFrame("Member Details");
        frame.setSize(400, 400);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setText(
            "Type: Regular\n" +
            "Member ID: " + id + "\n" +
            "Name: " + name + "\n" +
            "Location: " + location + "\n" +
            "Phone: " + phone + "\n" +
            "Email: " + email + "\n" +
            "Gender: " + gender + "\n" +
            "DOB: " + dob + "\n" +
            "Membership Start Date: " + membershipStartDate + "\n" +
            "Active Status: " + activeStatus + "\n" +
            "Attendance Points: " + attendance + "\n" +
            "Loyalty Points: " + loyaltyPoints + "\n" +
            "Referral Source: " + referralSource + "\n" +
            "Plan: " + plan + "\n" +
            "Price: " + price + "\n" +
            "Removal Reason: " + removalReason
        );
        frame.add(new JScrollPane(textArea));
        frame.setVisible(true);
    }
}

class PremiumMember extends GymMember {
    private String personalTrainer;
    private double premiumCharge;
    private double paidAmount;
    private double discountAmount;
    private boolean fullPayment;

    public PremiumMember(int id, String name, String location, String phone, String email,
                        String gender, String dob, String membershipStartDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, dob, membershipStartDate);
        this.personalTrainer = personalTrainer;
        this.premiumCharge = 50000;
        this.paidAmount = 0;
        this.discountAmount = 0;
        this.fullPayment = false;
    }

    // Getters
    public String getPersonalTrainer() { return personalTrainer; }
    public double getPremiumCharge() { return premiumCharge; }
    public double getPaidAmount() { return paidAmount; }
    public double getdiscountAmount() { return discountAmount; }
    public boolean isFullPayment() { return fullPayment; }

    @Override
    public void markAttendance() {
        if (activeStatus) {
            attendance++;
            loyaltyPoints += 15; // Premium members earn more points
        }
    }

    public String payDueAmount(double amount) {
        if (!activeStatus) {
            return "Cannot process payment: Member is inactive.";
        }
        paidAmount += amount;
        if (paidAmount >= premiumCharge) {
            fullPayment = true;
            discountAmount = paidAmount - premiumCharge;
            paidAmount = premiumCharge;
        }
        return "Payment of " + amount + " processed successfully.";
    }

    public void revertPremiumMember() {
        if (!activeStatus) {
            activateMembership();
        }
    }

    @Override
    public void display() {
        JFrame frame = new JFrame("Member Details");
        frame.setSize(400, 400);
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setText(
            "Type: Premium\n" +
            "Member ID: " + id + "\n" +
            "Name: " + name + "\n" +
            "Location: " + location + "\n" +
            "Phone: " + phone + "\n" +
            "Email: " + email + "\n" +
            "Gender: " + gender + "\n" +
            "DOB: " + dob + "\n" +
            "Membership Start Date: " + membershipStartDate + "\n" +
            "Active Status: " + activeStatus + "\n" +
            "Attendance Points: " + attendance + "\n" +
            "Loyalty Points: " + loyaltyPoints + "\n" +
            "Personal Trainer: " + personalTrainer + "\n" +
            "Premium Charge: " + premiumCharge + "\n" +
            "Paid Amount: " + paidAmount + "\n" +
            "Discount Amount: " + discountAmount + "\n" +
            "Full Payment: " + fullPayment
        );
        frame.add(new JScrollPane(textArea));
        frame.setVisible(true);
    }
}

public class GymGUI {
    private JFrame frame;
    private ArrayList<GymMember> memberList = new ArrayList<>();
    private String[] days = new String[31];
    private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private String[] years = new String[40];

    // Regular member fields
    private JTextField rIdField;
    private JTextField upgradeIdField;
    private JTextField rActionIdField;
    private JTextField rAttendanceField;
    private JTextField rNameField;
    private JTextField rLocationField;
    private JTextField rPhoneField;
    private JTextField rEmailField;
    private JRadioButton rMaleRadio;
    private JRadioButton rFemaleRadio;
    private JComboBox<String> rDayBox, rMonthBox, rYearBox;
    private JComboBox<String> rStartDay, rStartMonth, rStartYear;
    private JTextField referralField;
    private JTextField removalReasonField;
    private JComboBox<String> planBox;
    private JTextField priceField;
    private JButton rAddRegularButton, rActivateButton, rDeactivateButton, rAttendanceButton, rRevertButton, rDisplayButton, rClearButton, rUpgradeButton, addSaveB, addReadB;

    // Premium member fields
    private JTextField pIdField;
    private JTextField pNameField;
    private JTextField pAttendanceField;
    private JTextField pLocationField;
    private JTextField pPhoneField;
    private JTextField pEmailField;
    private JRadioButton pMaleRadio;
    private JRadioButton pFemaleRadio;
    private JComboBox<String> pDayBox, pMonthBox, pYearBox;
    private JComboBox<String> pStartDay, pStartMonth, pStartYear;
    private JTextField trainerField;
    private JTextField chargeField;
    private JTextField paidField;
    private JTextField discountField;
    private JTextField actionIdField;
    private JTextField payIdField;
    private JTextField amountField;
    private JButton addPremiumButton, pActivateButton, pDeactivateButton, pAttendanceButton, pRevertButton, pDisplayButton, pClearButton;
    private JButton payDueButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GymGUI();
            }
        });
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public GymGUI() {
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        for (int i = 0, year = 1990; i < 40; i++, year++) {
            years[i] = String.valueOf(year);
        }

        frame = new JFrame("Gym Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Top Panel with Title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.DARK_GRAY);
        JLabel titleLabel = new JLabel("Galaxy Fitness", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Center Panel with Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));
        buttonPanel.setBackground(Color.WHITE);

        JButton addRegularB = new JButton("Regular Member");
        JButton addPremiumB = new JButton("Premium Member");
        addSaveB = new JButton("Save Members");
        addReadB = new JButton("Read Members");

        styleButton(addRegularB);
        styleButton(addPremiumB);
        styleButton(addSaveB);
        styleButton(addReadB);

        addRegularB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regularMemberUI();
            }
        });

        addPremiumB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                premiumMemberUI();
            }
        });

        addSaveB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveToFile(memberList);
            }
        });

        addReadB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                readMembers();
            }
        });

        buttonPanel.add(addRegularB);
        buttonPanel.add(addPremiumB);
        buttonPanel.add(addSaveB);
        buttonPanel.add(addReadB);

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        JLabel footerLabel = new JLabel("© 2025 Galaxy Fitness | All Rights Reserved", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerPanel.add(footerLabel);
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void saveToFile(ArrayList<GymMember> memberList) {
        try (FileWriter writer = new FileWriter("MemberDetails.txt")) {
            String header = String.format(
                "%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10s %-15s %-10s %-15s %-15s %-15s\n",
                "ID", "Name", "Location", "Phone", "Email", "Start Date", "Plan", "Price",
                "Attendance", "Loyalty", "Active", "Full Pay", "Discount", "Net Paid"
            );
            writer.write(header);

            for (GymMember member : memberList) {
                int id = member.getId();
                String name = member.getName();
                String location = member.getLocation();
                String phone = member.getPhone();
                String email = member.getEmail();
                String startDate = member.getMembershipStartDate();
                int attendance = member.getAttendance();
                double loyalty = member.getLoyaltyPoints();
                boolean active = member.getActiveStatus();

                String plan = "-";
                double price = 0.0;
                boolean fullPayment = false;
                double discountAmount = 0.0;
                double netPaid = 0.0;

                if (member instanceof RegularMember) {
                    RegularMember r = (RegularMember) member;
                    plan = r.getPlan();
                    price = r.getPrice();
                    netPaid = price;
                } else if (member instanceof PremiumMember) {
                    PremiumMember p = (PremiumMember) member;
                    plan = "Premium";
                    price = p.getPremiumCharge();
                    fullPayment = p.isFullPayment();
                    discountAmount = p.getdiscountAmount();
                    netPaid = p.getPaidAmount();
                }

                String line = String.format(
                    "%-5d %-15s %-15s %-15s %-25s %-20s %-10s %-10.2f %-10d %-15.2f %-10s %-15s %-15.2f %-15.2f\n",
                    id, name, location, phone, email, startDate, plan, price, attendance,
                    loyalty, active ? "Yes" : "No", fullPayment ? "Yes" : "No", discountAmount, netPaid
                );
                writer.write(line);
            }
            JOptionPane.showMessageDialog(null, "Members saved to file", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void readMembers() {
        File file = new File("MemberDetails.txt");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No saved members found.");
            return;
        }

        JFrame viewFrame = new JFrame("View Members");
        viewFrame.setSize(1000, 600);
        viewFrame.setLayout(new BorderLayout());

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textPane);
        viewFrame.add(scrollPane, BorderLayout.CENTER);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            textPane.setText(content.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
            return;
        }

        viewFrame.setVisible(true);
    }

    private void premiumMemberUI() {
        JFrame mainFrame = new JFrame("Premium Member Management");
        mainFrame.setLayout(new BorderLayout(10, 10));
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Initialize components
        pIdField = new JTextField(15);
        pNameField = new JTextField(15);
        pLocationField = new JTextField(15);
        pAttendanceField = new JTextField(15);
        pAttendanceField.setEditable(false);
        pPhoneField = new JTextField(15);
        pEmailField = new JTextField(15);
        pMaleRadio = new JRadioButton("Male");
        pFemaleRadio = new JRadioButton("Female");

        pDayBox = new JComboBox<>(days);
        pMonthBox = new JComboBox<>(months);
        pYearBox = new JComboBox<>(years);
        pStartDay = new JComboBox<>(days);
        pStartMonth = new JComboBox<>(months);
        pStartYear = new JComboBox<>(years);

        trainerField = new JTextField(15);
        chargeField = new JTextField(15);
        chargeField.setEditable(false);
        chargeField.setText("50000");
        paidField = new JTextField(15);
        paidField.setEditable(false);
        paidField.setText("0");
        actionIdField = new JTextField(15);
        payIdField = new JTextField(15);
        discountField = new JTextField(15);
        discountField.setEditable(false);
        amountField = new JTextField(15);

        // Create buttons
        Dimension buttonSize = new Dimension(180, 35);
        addPremiumButton = createStyledButton("Add Premium Member", buttonSize);
        addPremiumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMember(true);
            }
        });

        pActivateButton = createStyledButton("Activate", buttonSize);
        pActivateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activatePremiumMember();
            }
        });

        pDeactivateButton = createStyledButton("Deactivate", buttonSize);
        pDeactivateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deactivatePremiumMember();
            }
        });

        pAttendanceButton = createStyledButton("Mark Attendance", buttonSize);
        pAttendanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markAttendanceForPremium();
            }
        });

        pRevertButton = createStyledButton("Revert", buttonSize);
        pRevertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = parseID(actionIdField);
                if (id == -1) return;
                GymMember member = findMemberById(id);
                if (member != null) {
                    if (member instanceof PremiumMember) {
                        PremiumMember m = (PremiumMember) member;
                        m.revertPremiumMember();
                        pAttendanceField.setText(String.valueOf(m.getAttendance()));
                        showMessage("The member is reverted");
                    } else {
                        JOptionPane.showMessageDialog(null, "Not a premium Member");
                    }
                } else {
                    showNotFoundMessage();
                }
            }
        });

        pDisplayButton = createStyledButton("Display Members", buttonSize);
        pDisplayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayPremium();
            }
        });

        pClearButton = createStyledButton("Clear", buttonSize);
        pClearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        payDueButton = createStyledButton("Pay Due Amount", buttonSize);
        payDueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(payIdField.getText());
                    double amt = Double.parseDouble(amountField.getText());
                    GymMember member = findMemberById(id);
                    if (member != null) {
                        if (member instanceof PremiumMember) {
                            PremiumMember premiumMember = (PremiumMember) member;
                            if (premiumMember.getActiveStatus()) {
                                String m = premiumMember.payDueAmount(amt);
                                showMessage(m);
                                paidField.setText(String.valueOf(premiumMember.getPaidAmount()));
                                discountField.setText(String.valueOf(premiumMember.getdiscountAmount()));
                            } else {
                                JOptionPane.showMessageDialog(null, "Member must be active");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Not a premium member", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        showNotFoundMessage();
                    }
                } catch (NumberFormatException ex) {
                    showMessage("Enter valid ID and amount.");
                }
            }
        });

        // Top Panel - Member Information
        JPanel memberInfoPanel = new JPanel(new BorderLayout(10, 10));
        memberInfoPanel.setBorder(BorderFactory.createTitledBorder("Premium Member Information"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(pMaleRadio);
        genderGroup.add(pFemaleRadio);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.add(pMaleRadio);
        genderPanel.add(pFemaleRadio);

        JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dobPanel.add(pDayBox);
        dobPanel.add(pMonthBox);
        dobPanel.add(pYearBox);

        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        startDatePanel.add(pStartDay);
        startDatePanel.add(pStartMonth);
        startDatePanel.add(pStartYear);

        addFormField(formPanel, gbc, 0, 0, "ID:", pIdField);
        addFormField(formPanel, gbc, 0, 1, "Location:", pLocationField);
        addFormField(formPanel, gbc, 0, 2, "Email:", pEmailField);
        addFormField(formPanel, gbc, 0, 3, "Date of Birth:", dobPanel);
        addFormField(formPanel, gbc, 0, 4, "Personal Trainer:", trainerField);
        addFormField(formPanel, gbc, 0, 5, "Paid Amount:", paidField);
        addFormField(formPanel, gbc, 0, 6, "Discount Amount:", discountField);

        addFormField(formPanel, gbc, 2, 0, "Name:", pNameField);
        addFormField(formPanel, gbc, 2, 1, "Phone:", pPhoneField);
        addFormField(formPanel, gbc, 2, 2, "Gender:", genderPanel);
        addFormField(formPanel, gbc, 2, 3, "Membership Start:", startDatePanel);
        addFormField(formPanel, gbc, 2, 4, "Premium Charge:", chargeField);
        addFormField(formPanel, gbc, 2, 5, "Attendance:", pAttendanceField);

        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButtonPanel.add(addPremiumButton);

        memberInfoPanel.add(formPanel, BorderLayout.CENTER);
        memberInfoPanel.add(addButtonPanel, BorderLayout.SOUTH);

        // Middle Panel - Actions
        JPanel actionsPanel = new JPanel(new BorderLayout(10, 10));
        actionsPanel.setBorder(BorderFactory.createTitledBorder("Member Actions"));

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        idPanel.add(new JLabel("Enter Member ID:"));
        idPanel.add(actionIdField);

        JPanel actionButtonsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        actionButtonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        actionButtonsPanel.add(pActivateButton);
        actionButtonsPanel.add(pDeactivateButton);
        actionButtonsPanel.add(pAttendanceButton);
        actionButtonsPanel.add(pRevertButton);
        actionButtonsPanel.add(pDisplayButton);
        actionButtonsPanel.add(pClearButton);

        actionsPanel.add(idPanel, BorderLayout.NORTH);
        actionsPanel.add(actionButtonsPanel, BorderLayout.CENTER);

        // Bottom Panel - Payment
        JPanel paymentPanel = new JPanel(new BorderLayout(10, 10));
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Payment Processing"));

        JPanel paymentFieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paymentFieldsPanel.add(new JLabel("Member ID:"));
        paymentFieldsPanel.add(payIdField);
        paymentFieldsPanel.add(new JLabel("Amount:"));
        paymentFieldsPanel.add(amountField);

        JPanel paymentButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paymentButtonPanel.add(payDueButton);

        paymentPanel.add(paymentFieldsPanel, BorderLayout.NORTH);
        paymentPanel.add(paymentButtonPanel, BorderLayout.CENTER);

        // Main content panel with GridBagLayout instead of BoxLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints contentGbc = new GridBagConstraints();
        contentGbc.fill = GridBagConstraints.HORIZONTAL;
        contentGbc.gridx = 0;
        contentGbc.weightx = 1.0;
        contentGbc.insets = new Insets(15, 15, 15, 15); // Replaces Box.createRigidArea

        contentGbc.gridy = 0;
        contentPanel.add(memberInfoPanel, contentGbc);

        contentGbc.gridy = 1;
        contentPanel.add(actionsPanel, contentGbc);

        contentGbc.gridy = 2;
        contentPanel.add(paymentPanel, contentGbc);

        // Add filler to push content up and allow scrolling
        contentGbc.gridy = 3;
        contentGbc.weighty = 1.0;
        contentPanel.add(new JPanel(), contentGbc);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private JButton createStyledButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setMargin(new Insets(5, 10, 5, 10));
        return button;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int x, int y, String labelText, JComponent field) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = x + 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }

    private void regularMemberUI() {
        JFrame mainFrame = new JFrame("Regular Member Management");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLayout(new BorderLayout(10, 10));

        initializeComponents();

        JPanel memberInfoPanel = createMemberInfoPanel();
        JPanel actionPanel = createActionPanel();
        JPanel upgradePanel = createUpgradePanel();

        // Main content panel with GridBagLayout instead of BoxLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints contentGbc = new GridBagConstraints();
        contentGbc.fill = GridBagConstraints.HORIZONTAL;
        contentGbc.gridx = 0;
        contentGbc.weightx = 1.0;
        contentGbc.insets = new Insets(15, 15, 15, 15); // Replaces Box.createRigidArea

        contentGbc.gridy = 0;
        contentPanel.add(memberInfoPanel, contentGbc);

        contentGbc.gridy = 1;
        contentPanel.add(actionPanel, contentGbc);

        contentGbc.gridy = 2;
        contentPanel.add(upgradePanel, contentGbc);

        // Add filler to push content up and allow scrolling
        contentGbc.gridy = 3;
        contentGbc.weighty = 1.0;
        contentPanel.add(new JPanel(), contentGbc);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        mainFrame.add(scrollPane, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private void initializeComponents() {
        rAttendanceField = new JTextField(10);
        rAttendanceField.setEditable(false);
        rIdField = new JTextField(10);
        upgradeIdField = new JTextField(10);
        rActionIdField = new JTextField(10);
        rNameField = new JTextField(20);
        rLocationField = new JTextField(20);
        rPhoneField = new JTextField(15);
        rEmailField = new JTextField(20);
        referralField = new JTextField(20);
        priceField = new JTextField(10);
        priceField.setEditable(false);
        removalReasonField = new JTextField(20);

        rMaleRadio = new JRadioButton("Male");
        rFemaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rMaleRadio);
        genderGroup.add(rFemaleRadio);

        rDayBox = new JComboBox<>(days);
        rMonthBox = new JComboBox<>(months);
        rYearBox = new JComboBox<>(years);
        rStartDay = new JComboBox<>(days);
        rStartMonth = new JComboBox<>(months);
        rStartYear = new JComboBox<>(years);

        String[] plans = {"Basic", "Standard", "Deluxe"};
        planBox = new JComboBox<>(plans);

        setupButtons();
    }

    private void setupButtons() {
        rAddRegularButton = createStyledButton("Add Regular Member", new Dimension(180, 35));
        rAddRegularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addMember(false);
            }
        });

        rActivateButton = createStyledButton("Activate", new Dimension(180, 35));
        rActivateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                activateRegularMember();
            }
        });

        rDeactivateButton = createStyledButton("Deactivate", new Dimension(180, 35));
        rDeactivateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deactivateRegularMember();
            }
        });

        rAttendanceButton = createStyledButton("Mark Attendance", new Dimension(180, 35));
        rAttendanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markAttendanceForRegular();
            }
        });

        rRevertButton = createStyledButton("Revert", new Dimension(180, 35));
        rRevertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = parseID(rActionIdField);
                if (id == -1) return;
                String removalReason = removalReasonField.getText();
                if (removalReason.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter removal reason");
                    return;
                }
                GymMember member = findMemberById(id);
                if (member != null) {
                    if (member instanceof RegularMember) {
                        RegularMember m = (RegularMember) member;
                        m.revertRegularMember(removalReason);
                        rAttendanceField.setText(String.valueOf(m.getAttendance()));
                        showMessage("The member is reverted");
                    } else {
                        JOptionPane.showMessageDialog(null, "Not a Regular Member");
                    }
                } else {
                    showNotFoundMessage();
                }
            }
        });

        rDisplayButton = createStyledButton("Display", new Dimension(180, 35));
        rDisplayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayRegular();
            }
        });

        rClearButton = createStyledButton("Clear", new Dimension(180, 35));
        rClearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearRegularMemberFields();
            }
        });

        rUpgradeButton = createStyledButton("Upgrade Plan", new Dimension(180, 35));
        rUpgradeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = upgradeIdField.getText();
                    String plan = (String) planBox.getSelectedItem();
                    if (id.isEmpty() || plan == null) {
                        JOptionPane.showMessageDialog(null, "Enter all fields");
                        return;
                    }
                    upgradePlan(plan);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid ID");
                }
            }
        });
    }

    private JPanel createMemberInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Regular Member Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        addFormField(panel, "ID:", rIdField, gbc);

        gbc.gridy++;
        addFormField(panel, "Name:", rNameField, gbc);

        gbc.gridy++;
        addFormField(panel, "Phone:", rPhoneField, gbc);

        gbc.gridy++;
        addFormField(panel, "Gender:", createGenderPanel(), gbc);

        gbc.gridy++;
        addFormField(panel, "Membership Start:", createDatePanel(rStartDay, rStartMonth, rStartYear), gbc);

        gbc.gridy++;
        addFormField(panel, "Plans:", planBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        addFormField(panel, "Location:", rLocationField, gbc);

        gbc.gridy++;
        addFormField(panel, "Email:", rEmailField, gbc);

        gbc.gridy++;
        addFormField(panel, "Date of Birth:", createDatePanel(rDayBox, rMonthBox, rYearBox), gbc);

        gbc.gridy++;
        addFormField(panel, "Referral Source:", referralField, gbc);

        gbc.gridy++;
        addFormField(panel, "Attendance:", rAttendanceField, gbc);

        gbc.gridy++;
        addFormField(panel, "Removal Reason:", removalReasonField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(rAddRegularButton, gbc);

        return panel;
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText, JLabel.RIGHT);
        GridBagConstraints labelGbc = (GridBagConstraints) gbc.clone();
        panel.add(label, labelGbc);

        GridBagConstraints fieldGbc = (GridBagConstraints) gbc.clone();
        fieldGbc.gridx = gbc.gridx + 1;
        fieldGbc.anchor = GridBagConstraints.WEST;
        panel.add(field, fieldGbc);
    }

    private JPanel createGenderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.add(rMaleRadio);
        panel.add(rFemaleRadio);
        return panel;
    }

    private JPanel createDatePanel(JComboBox<String> day, JComboBox<String> month, JComboBox<String> year) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.add(day);
        panel.add(month);
        panel.add(year);
        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Member Actions"));

        JPanel idPanel = new JPanel();
        idPanel.add(new JLabel("Member ID:"));
        idPanel.add(rActionIdField);
        panel.add(idPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.add(rActivateButton);
        buttonPanel.add(rDeactivateButton);
        buttonPanel.add(rAttendanceButton);
        buttonPanel.add(rRevertButton);
        buttonPanel.add(rDisplayButton);
        buttonPanel.add(rClearButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createUpgradePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Plan Upgrade"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Member ID:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(upgradeIdField, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("New Plan:"), gbc);

        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.WEST;
        JComboBox<String> upgradePlanBox = new JComboBox<>(new String[]{"Basic", "Standard", "Deluxe"});
        upgradePlanBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedPlan = (String) upgradePlanBox.getSelectedItem();
                priceField.setText(String.valueOf(RegularMember.getPlanPrice(selectedPlan)));
            }
        });
        formPanel.add(upgradePlanBox, gbc);

        gbc.gridx = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 5;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(priceField, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(rUpgradeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void clearRegularMemberFields() {
        rIdField.setText("");
        rNameField.setText("");
        rLocationField.setText("");
        rPhoneField.setText("");
        rEmailField.setText("");
        rMaleRadio.setSelected(false);
        rFemaleRadio.setSelected(false);
        rDayBox.setSelectedIndex(0);
        rMonthBox.setSelectedIndex(0);
        rYearBox.setSelectedIndex(0);
        rStartDay.setSelectedIndex(0);
        rStartMonth.setSelectedIndex(0);
        rStartYear.setSelectedIndex(0);
        referralField.setText("");
        planBox.setSelectedIndex(0);
        rAttendanceField.setText("");
        removalReasonField.setText("");
        rActionIdField.setText("");
        upgradeIdField.setText("");
        priceField.setText("");
    }

    private int parseID(JTextField t) {
        try {
            return Integer.parseInt(t.getText());
        } catch (NumberFormatException e) {
            showMessage("Enter a valid numeric ID.");
            return -1;
        }
    }

    private GymMember findMemberById(int id) {
        for (GymMember gm : memberList) {
            if (gm.getId() == id) {
                return gm;
            }
        }
        return null;
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    private void showNotFoundMessage() {
        showMessage("Member ID not found.");
    }

    private boolean isIdUnique(int id) {
        for (GymMember member : memberList) {
            if (member.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private void addMember(boolean isPremium) {
        try {
            String idText = isPremium ? pIdField.getText() : rIdField.getText();
            String name = isPremium ? pNameField.getText() : rNameField.getText();
            String location = isPremium ? pLocationField.getText() : rLocationField.getText();
            String phone = isPremium ? pPhoneField.getText() : rPhoneField.getText();
            String email = isPremium ? pEmailField.getText() : rEmailField.getText();
            String gender = isPremium ?
                (pMaleRadio.isSelected() ? "Male" : pFemaleRadio.isSelected() ? "Female" : "Other") :
                (rMaleRadio.isSelected() ? "Male" : rFemaleRadio.isSelected() ? "Female" :"Other");
            String day = (String) (isPremium ? pDayBox.getSelectedItem() : rDayBox.getSelectedItem());
            String month = (String) (isPremium ? pMonthBox.getSelectedItem() : rMonthBox.getSelectedItem());
            String year = (String) (isPremium ? pYearBox.getSelectedItem() : rYearBox.getSelectedItem());
            String dob = year + "/" + month + "/" + day;
            String startDay = (String) (isPremium ? pStartDay.getSelectedItem() : rStartDay.getSelectedItem());
            String startMonth = (String) (isPremium ? pStartMonth.getSelectedItem() : rStartMonth.getSelectedItem());
            String startYear = (String) (isPremium ? pStartYear.getSelectedItem() : rStartYear.getSelectedItem());
            String membershipStartDate = startYear + "/" + startMonth + "/" + startDay;
            String personalTrainer = isPremium ? trainerField.getText() : "";
            String referralSource = isPremium ? "" : referralField.getText();

            if (idText.isEmpty() || name.isEmpty() || location.isEmpty() || phone.isEmpty() ||
                email.isEmpty() || day.isEmpty() || month.isEmpty() || year.isEmpty() ||
                startDay.isEmpty() || startMonth.isEmpty() || startYear.isEmpty() ||
                (isPremium && personalTrainer.isEmpty()) || (!isPremium && referralSource.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idText);
            if (!isIdUnique(id)) {
                JOptionPane.showMessageDialog(null, "ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String confirmationMessage = "Are you sure you want to add this Member?\n" +
                "Member ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Location: " + location + "\n" +
                "Phone: " + phone + "\n" +
                "Email: " + email + "\n" +
                "Gender: " + gender + "\n" +
                "DOB: " + dob + "\n" +
                "Membership Start Date: " + membershipStartDate + "\n" +
                (isPremium ? "Personal Trainer: " + personalTrainer : "Referral Source: " + referralSource);

            int confirmation = JOptionPane.showConfirmDialog(null,
                confirmationMessage,
                "Add Member Confirmation",
                JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                if (isPremium) {
                    PremiumMember premiumMember = new PremiumMember(id, name, location, phone, email,
                        gender, dob, membershipStartDate, personalTrainer);
                    memberList.add(premiumMember);
                    JOptionPane.showMessageDialog(null, "Premium Member added successfully!");
                } else {
                    RegularMember regularMember = new RegularMember(id, name, location, phone, email,
                        gender, dob, membershipStartDate, referralSource);
                    memberList.add(regularMember);
                    JOptionPane.showMessageDialog(null, "Regular Member added successfully!");
                }
            }
        } catch (NumberFormatException ev) {
            JOptionPane.showMessageDialog(null, "Invalid ID! Enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void activatePremiumMember() {
        int parsedId = parseID(actionIdField);
        if (parsedId == -1) return;
        GymMember member = findMemberById(parsedId);

        if (member == null) {
            showNotFoundMessage();
            return;
        }

        if (!(member instanceof PremiumMember)) {
            JOptionPane.showMessageDialog(null, "Not a premium member", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PremiumMember premiumMember = (PremiumMember) member;
        if (premiumMember.getActiveStatus()) {
            JOptionPane.showMessageDialog(null, "Member is already active.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to activate this Premium Member?\nMember ID: " + parsedId,
            "Activate Premium Member Confirmation",
            JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            premiumMember.activateMembership();
            JOptionPane.showMessageDialog(null, "Premium member activated.");
        }
    }

    private void activateRegularMember() {
        int parsedId = parseID(rActionIdField);
        if (parsedId == -1) return;
        GymMember member = findMemberById(parsedId);

        if (member == null) {
            showNotFoundMessage();
            return;
        }

        if (!(member instanceof RegularMember)) {
            JOptionPane.showMessageDialog(null, "Not a regular member", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RegularMember regularMember = (RegularMember) member;
        if (regularMember.getActiveStatus()) {
            JOptionPane.showMessageDialog(null, "Member is already active.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to activate this Regular Member?\nMember ID: " + parsedId,
            "Activate Regular Member Confirmation",
            JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            regularMember.activateMembership();
            JOptionPane.showMessageDialog(null, "Regular member activated.");
        }
    }

    private void deactivatePremiumMember() {
        int parsedId = parseID(actionIdField);
        if (parsedId == -1) return;
        GymMember member = findMemberById(parsedId);

        if (member == null) {
            showNotFoundMessage();
            return;
        }

        if (!(member instanceof PremiumMember)) {
            JOptionPane.showMessageDialog(null, "Not a premium member", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PremiumMember premiumMember = (PremiumMember) member;
        if (!premiumMember.getActiveStatus()) {
            JOptionPane.showMessageDialog(null, "Member is already inactive.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to deactivate this Premium Member?\nMember ID: " + parsedId,
            "Deactivate Premium Member Confirmation",
            JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            premiumMember.deactivateMembership();
            JOptionPane.showMessageDialog(null, "Premium member deactivated.");
        }
    }

    private void deactivateRegularMember() {
        int parsedId = parseID(rActionIdField);
        if (parsedId == -1) return;
        GymMember member = findMemberById(parsedId);

        if (member == null) {
            showNotFoundMessage();
            return;
        }

        if (!(member instanceof RegularMember)) {
            JOptionPane.showMessageDialog(null, "Not a regular member", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RegularMember regularMember = (RegularMember) member;
        if (!regularMember.getActiveStatus()) {
            JOptionPane.showMessageDialog(null, "Member is already inactive.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to deactivate this Regular Member?\nMember ID: " + parsedId,
            "Deactivate Regular Member Confirmation",
            JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            regularMember.deactivateMembership();
            JOptionPane.showMessageDialog(null, "Regular member deactivated.");
        }
    }

    private void markAttendanceForRegular() {
        int parsedId = parseID(rActionIdField);
        if (parsedId == -1) return;
        GymMember member = findMemberById(parsedId);

        if (member == null || !member.getActiveStatus()) {
            showMessage("Invalid or inactive member.");
            return;
        }

        if (member instanceof RegularMember) {
            ((RegularMember) member).markAttendance();
            rAttendanceField.setText(String.valueOf(member.getAttendance()));
            JOptionPane.showMessageDialog(null, "Regular member attendance marked.");
        } else {
            JOptionPane.showMessageDialog(null, "Not a regular member.");
        }
    }

    private void markAttendanceForPremium() {
        int parsedId = parseID(actionIdField);
        if (parsedId == -1) return;
        GymMember member = findMemberById(parsedId);

        if (member == null || !member.getActiveStatus()) {
            showMessage("Invalid or inactive member.");
            return;
        }

        if (member instanceof PremiumMember) {
            ((PremiumMember) member).markAttendance();
            pAttendanceField.setText(String.valueOf(member.getAttendance()));
            JOptionPane.showMessageDialog(null, "Premium member attendance marked.");
        } else {
            JOptionPane.showMessageDialog(null, "Not a premium member.");
        }
    }

    private void clear() {
        pIdField.setText("");
        pNameField.setText("");
        pLocationField.setText("");
        pPhoneField.setText("");
        pEmailField.setText("");
        trainerField.setText("");
        paidField.setText("0");
        discountField.setText("0");
        actionIdField.setText("");
        payIdField.setText("");
        amountField.setText("");
        pAttendanceField.setText("");
        pMaleRadio.setSelected(false);
        pFemaleRadio.setSelected(false);
        pDayBox.setSelectedIndex(0);
        pMonthBox.setSelectedIndex(0);
        pYearBox.setSelectedIndex(0);
        pStartDay.setSelectedIndex(0);
        pStartMonth.setSelectedIndex(0);
        pStartYear.setSelectedIndex(0);
    }

    private void upgradePlan(String plan) {
        int parsedId = parseID(upgradeIdField);
        if (parsedId == -1) return;
        GymMember member = findMemberById(parsedId);

        if (member != null) {
            if (member instanceof RegularMember) {
                RegularMember m = (RegularMember) member;
                if (m.getActiveStatus()) {
                    String message = m.upgradePlan(plan);
                    JOptionPane.showMessageDialog(null, message);
                    priceField.setText(String.valueOf(m.getPrice()));
                } else {
                    JOptionPane.showMessageDialog(null, "Member must be active");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Not a regular member");
            }
        } else {
            showNotFoundMessage();
        }
    }

    private void displayPremium() {
        int id = parseID(actionIdField);
        if (id == -1) return;
        GymMember member = findMemberById(id);
        if (member != null) {
            if (member instanceof PremiumMember) {
                member.display();
            } else {
                JOptionPane.showMessageDialog(null, "Not a Premium Member", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            showNotFoundMessage();
        }
    }

    private void displayRegular() {
        int id = parseID(rActionIdField);
        if (id == -1) return;
        GymMember member = findMemberById(id);
        if (member != null) {
            if (member instanceof RegularMember) {
                member.display();
            } else {
                JOptionPane.showMessageDialog(null, "Not a Regular Member", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            showNotFoundMessage();
        }
    }
}