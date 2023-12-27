import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class LinkedInApp extends JFrame{
    //--------------------------------------------------------------------------------------------
    // LinkedInApp.java Author: Arda Özan 22196372
    // This class is where all panels have created and assigned to a cardlayout
    //--------------------------------------------------------------------------------------------
   
    private UserProfile userProfile;
    private LoginPanel loginPanel;
    private FriendPanel friendPanel;
    private PassPanel passPanel;
    private JPanel cardPanel;
    private JobPanel jobPanel;
    private CurrPanel currPanel;
    private CardLayout cardLayout;
    private ProfilePanel profilePanel;
    private SearchPanel searchPanel;
    private JobCreator jobCreator;


    private List<String[]> addedFriends;
    

    public LinkedInApp(){
        //here we create all panels that we use and add them to the cardpanel 
        addedFriends = new ArrayList<>();
        loginPanel=new LoginPanel();
        profilePanel=new ProfilePanel();
        searchPanel=new SearchPanel();
        passPanel=new PassPanel();
        friendPanel = new FriendPanel(addedFriends);
        currPanel = new CurrPanel(addedFriends);
        jobPanel=new JobPanel();
        userProfile= new UserProfile("ardaözan","arda123","ardamail","başkent ceng");
        cardLayout=new CardLayout();
        
        cardPanel=new JPanel(cardLayout);
        cardPanel.add(loginPanel,"loginPanel");
        add(cardPanel);
        cardPanel.add(passPanel,"passPanel");
        cardPanel.add(friendPanel,"friendPanel");
        cardPanel.add(jobPanel,"jobPanel");
        cardPanel.add(currPanel,"currPanel");
        cardLayout.show(cardPanel,"loginPanel");
        cardPanel.add(profilePanel,"profilePanel");
        cardPanel.add(searchPanel,"searchPanel");
        
        
        //here we set our preferences for the default settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setTitle("LinkedInApp");
        setVisible(true);
    }
class CurrPanel extends JPanel {
    //--------------------------------------------------------------------------------------------
// CurrPanel.java Author: Arda Özan 22196372
// this class is responsible for displaying the current friends. However i can't make it run
//-------------------------------------------------------------------------------------------
    private JButton back;
    private JTable friendsListTable;
    private DefaultTableModel friendsListModel;
    private JButton removeButton;
    private List<String[]> addedFriends;
    //here we create the list for added friends
    public CurrPanel(List<String[]> addedFriends){
        this.addedFriends = addedFriends;
        setLayout(new BorderLayout());
        back=new JButton("Back");
        String[] columnNames = {"Name", "Surname"};
        friendsListModel = new DefaultTableModel(columnNames, 0);
        friendsListTable = new JTable(friendsListModel);
        JScrollPane scrollPane = new JScrollPane(friendsListTable);
        add(scrollPane, BorderLayout.CENTER);
        
        removeButton = new JButton("Remove");
        updateFriendsList();
        refreshData();
        removeButton.setVisible(false);
        removeButton.addActionListener(e -> {
            int selectedRow = friendsListTable.getSelectedRow();
            if (selectedRow != -1) {
                friendsListModel.removeRow(selectedRow);
            }
        });
        friendsListTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = friendsListTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    removeButton.setVisible(true);
                }
            }
        });
        add(removeButton, BorderLayout.SOUTH);
        

        updateFriendsList();
        
        back.addActionListener(new ActionListener() {

          
            public void actionPerformed(ActionEvent e) {
               
               cardLayout.show(cardPanel,"profilePanel");
            }
           
        });
        add(back,BorderLayout.SOUTH);
    }
    private void updateFriendsList() {
        friendsListModel.setRowCount(0);
        for (String[] friend : addedFriends) {
            friendsListModel.addRow(friend);
        }
    }

    // call this method when the CurrPanel is displayed
    public void refreshData() {
        updateFriendsList();
    }
}

 class FriendPanel extends JPanel{
    //--------------------------------------------------------------------------------------------
// FriendPanel.java Author: Arda Özan 22196372
// this class is responsible for displaying all possible friends and adding them to our social network
//-------------------------------------------------------------------------------------------
    private JTextField searchField;
    private JTable friendsTable;
    private DefaultTableModel model;
    private JButton back;
    private JButton addButton;
    private JScrollPane scrollPane;
    private List<String[]> addedFriends;
    public FriendPanel(List<String[]> addedFriends) {
        this.addedFriends = addedFriends;
        setLayout(new BorderLayout());
        back = new JButton("Back");
        searchField = new JTextField(20);
        addButton = new JButton("Add");
        addButton.setVisible(false);
        addButton.addActionListener(e -> {
            int selectedRow = friendsTable.getSelectedRow();
            if (selectedRow != -1) {
                String name = (String) model.getValueAt(selectedRow, 0);
                String surname = (String) model.getValueAt(selectedRow, 1);
                addedFriends.add(new String[]{name, surname});
                JOptionPane.showMessageDialog(this, "Added: " + name + " " + surname);
            }
        });
        

        // creating the table
        String[] columnNames = {"Name", "Surname"};
        model = new DefaultTableModel(columnNames, 0);
        friendsTable = new JTable(model);
        scrollPane = new JScrollPane(friendsTable);
        loadFriendsData();

        // search function
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                search(searchField.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                search(searchField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                search(searchField.getText());
            }

            private void search(String str) {
                if (str.length() == 0) {
                    ((TableRowSorter<?>) friendsTable.getRowSorter()).setRowFilter(null);
                } else {
                    ((TableRowSorter<?>) friendsTable.getRowSorter()).setRowFilter(RowFilter.regexFilter("(?i)" + str));
                }
            }
        });
  friendsTable.addMouseListener(new MouseAdapter() {
    
            public void mouseClicked(MouseEvent e) {
                
                int row = friendsTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    addButton.setVisible(true); // Show the Add button when a row is clicked
                }
            }
        });
        

        // setting layout
        setLayout(new BorderLayout());
        
        add(addButton, BorderLayout.EAST); 
    
        // background adding and adding it to the panel
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(back, BorderLayout.SOUTH);
         

        // defining function of back button
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "profilePanel");
            }
        });

        // adding row sorter
        friendsTable.setRowSorter(new TableRowSorter<>(model));
    }
     private void loadFriendsData() {
        List<String[]> friendsList = readCsvFile("C:\\Users\\arda0\\OneDrive\\Masaüstü\\java ödev\\name_dataset.csv");
        for (String[] friendData : friendsList) {
            model.addRow(friendData);
        }
    }

    private List<String[]> readCsvFile(String filePath) {
        List<String[]> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); 
                dataList.add(data);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return dataList;
    }

}

    class PassPanel extends JPanel{
        //--------------------------------------------------------------------------------------------
        // PassPanel.java Author: Arda Özan 22196372
        // This class is responsible for displaying and changing the password
        //--------------------------------------------------------------------------------------------
        private TextField curr;
        private TextField newPass;
        private JButton back;
        private JButton change;
        private TextField confirmPass;
        private JPanel  northpanel;
        public PassPanel(){
            northpanel=new JPanel();
            northpanel.setLayout(new GridLayout(5,2));
            change = new JButton("Change Password");
            change.addActionListener(new ActionListener() {
                @Override
                // here we define the function for  changing the password
                public void actionPerformed(ActionEvent e) {
                    String currentPassword = curr.getText();
                    String newPassword = newPass.getText();
                    String confirmNewPassword = confirmPass.getText();
    
                    if (!newPassword.equals(confirmNewPassword)) {
                        JOptionPane.showMessageDialog(null, "New passwords do not match.");
                        return;
                    }
    
                    boolean passwordChanged = userProfile.changePassword(currentPassword, newPassword);
                    if (passwordChanged) {
                        JOptionPane.showMessageDialog(null, "Password changed successfully.");
                        cardLayout.show(cardPanel, "profilePanel");
                    } else {
                        JOptionPane.showMessageDialog(null, "Current password is incorrect.");
                    }
                }
            });
    
            
            curr=new TextField(40);
            back=new JButton("Back");
            back.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                   
                    cardLayout.show(cardPanel, "profilePanel");
                }
            });
            newPass=new TextField(40);
            confirmPass=new TextField(40);
            northpanel.add(new JLabel("Current Password"));
            northpanel.add(curr);
            northpanel.add(new JLabel("New Password"));
            northpanel.add(newPass);
            northpanel.add(new JLabel("Confirm New Password"));
            northpanel.add(confirmPass);
            
            northpanel.add(change);
            add(northpanel);
            northpanel.add(back);
            
        }
    }
    class JobPanel extends JPanel {
        //--------------------------------------------------------------------------------------------
// JobPanel.java Author: Arda Özan 22196372
// This class is responsible for creating and displaying job informations
//--------------------------------------------------------------------------------------------
    private JTable appliedJobsTable;
    private DefaultTableModel model;
    private JButton back;
    private JButton removeButton;
    List<Job> appliedJobs = new ArrayList<>();

    public JobPanel() {
        setLayout(new BorderLayout());
        back=new JButton("Back");
        add(back,BorderLayout.NORTH);
        String[] columnNames = {"Company Name", "Salary"};
        model = new DefaultTableModel(columnNames, 0);
        //creating table for jobs and adding them to the another list
        appliedJobsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(appliedJobsTable);
        add(scrollPane, BorderLayout.CENTER);

        removeButton = new JButton("Remove Selected Job");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = appliedJobsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    model.removeRow(selectedRow);
                    
                }
            }
        });
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "profilePanel");
            }
        });
        
        add(removeButton, BorderLayout.SOUTH);
    }

    public void addJob(Job job) {
        model.addRow(new Object[]{job.getCompanyName(), job.getSalary()});
        
    }
}
    
class SearchPanel extends JPanel {
    //--------------------------------------------------------------------------------------------
    // SearchPanel.java Author: Arda Özan 22196372
    // This class is responsible for displaying all jobs and filtering them via the search field
    //--------------------------------------------------------------------------------------------
    private JScrollPane scrollPane;
    private JTable table;
    private JTextField searchbox;
    private JButton search;
    private JButton back;
    private JButton applyButton;
    private JPanel applyPanel;

    public SearchPanel() {
        setLayout(new BorderLayout());
        applyButton=new JButton("Apply");
        
        applyPanel = new JPanel();
        applyPanel.add(applyButton);
        applyPanel.setVisible(false);
        add(applyPanel, BorderLayout.SOUTH);

        

        //gettin dataset datas and adding them to a table
        String[] columnNames = {"Company Name", "Salary"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);  
        add(scrollPane, BorderLayout.CENTER);  

        
        JPanel northPanel = new JPanel();
        searchbox = new JTextField(20);
        search = new JButton("Search");
        back = new JButton("Back");

        
        List<Job> jobs = JobCreator.readJobsFromFile("C:\\Users\\arda0\\OneDrive\\Masaüstü\\java ödev\\job_dataset.csv");
        for (Job job : jobs) {
            model.addRow(new Object[]{job.getCompanyName(), job.getSalary()});
        }

        
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchbox.getText();
                filterTable(searchText);
            }
        });
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                applyPanel.setVisible(true); 
            }
        }); 
            
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String companyName = (String) table.getValueAt(selectedRow, 0);
                    int salary = (Integer) table.getValueAt(selectedRow, 1);
                    Job appliedJob = new Job(companyName, salary);
                    jobPanel.addJob(appliedJob); // jobPanel'e iş ekleniyor
                    JOptionPane.showMessageDialog(null, "Apply completed");
                }
            }
        });
           
        

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "profilePanel");
            }
        });

       
        northPanel.add(searchbox);
        northPanel.add(search);
        northPanel.add(back);
        add(northPanel, BorderLayout.NORTH);
    }
    //filter funciton
    private void filterTable(String searchText) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 0));
        }
    }
}
    



    class ProfilePanel extends JPanel{
        //--------------------------------------------------------------------------------------------
        // ClassName.java Author: Arda Özan 22196372
        // this class is responsible for displaying the profile information, edit button, logout button and search button. this class is like a main page
        //--------------------------------------------------------------------------------------------
        private JLabel pictureLabel;
        private JButton pic, logout, edit, change, myjobs, searchButton,friendButton,currfriend;
        private JTextField biom;
        private JLabel bio;
        
    
        public ProfilePanel(){
            setLayout(new BorderLayout()); 

           
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            pictureLabel = new JLabel();
            pic = new JButton("Profile Picture");
           
            
           
            topPanel.add(pictureLabel);
            
            JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            
        
            bio = new JLabel("Bio:");
            biom = new JTextField("başkent Ceng");
            logout = new JButton("Logout");
            currfriend=new JButton("Current Friends");
            friendButton=new JButton("Friend Search");
            edit = new JButton("Edit");
            change = new JButton("Change Password");
            myjobs = new JButton("Applied Jobs");
            searchButton = new JButton("Job Search");
    
            
            
        change.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               cardLayout.show(cardPanel,"passPanel");
            }
            
        });
        
        edit.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String bioText = biom.getText();
                bio.setText("Bio: " + bioText);
                
                JOptionPane.showMessageDialog(null,"Edit successfully");
            }
            
        });
        searchButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                cardLayout.show(cardPanel,"searchPanel");
            }
            
        });
        //adding file chooser to the button
        pic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif", "bmp"));
                int returnVal = chooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    ImageIcon imageIcon = new ImageIcon(chooser.getSelectedFile().getPath());
                    Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(image);
                    pictureLabel.setIcon(imageIcon);  
                    pictureLabel.revalidate();       
                    pictureLabel.repaint();          
                }
            }
        });
        friendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    cardLayout.show(cardPanel,"friendPanel");
                }
            });
            currfriend.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    cardLayout.show(cardPanel,"currPanel");
                }
            });

        centerPanel.setLayout(new GridLayout(5,2));
        setLayout(new BorderLayout());
        JButton logout = new JButton("Logout");

        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"loginPanel");
            }
        });
        topPanel.add(pictureLabel, BorderLayout.CENTER);
        pictureLabel.add(pic);
        add(new JLabel(""));
        add(pictureLabel,BorderLayout.NORTH);
        centerPanel.add(bio);
        centerPanel.add(biom);
        
        
        centerPanel.add(pic);
        centerPanel.add(edit);
        centerPanel.add(change);
        centerPanel.add(friendButton);
        centerPanel.add(myjobs);
        centerPanel.add(currfriend);
        centerPanel.add(searchButton);
        myjobs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel,"jobPanel");
            }
        });
        centerPanel.add(logout);

        
        add(pictureLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.AFTER_LINE_ENDS);
        
        }

    }

    class LoginPanel extends JPanel{
        //--------------------------------------------------------------------------------------------
        // LoginPanel.java Author: Arda Özan 22196372
        // this class is responsible for displaying the login page, getting information from user and comparing them to the current user password and email
        //--------------------------------------------------------------------------------------------
        private Image backgroundImage;
        private JTextField pass;
        private JTextField mail;
        private JButton login;
        private JPanel southPanel;
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                
                Image scaledImage = backgroundImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
                g.drawImage(scaledImage, 0, 0, this);
            }
        }
        
        public LoginPanel(){ 
            try{
                backgroundImage=ImageIO.read(new File("C:\\Users\\arda0\\OneDrive\\Masaüstü\\java ödev\\background.jpg"));
            } catch(IOException e){
                e.printStackTrace();
            }
        setLayout(new BorderLayout());
        
        southPanel=new JPanel(new GridLayout(3,2));
        JTextField pass=new JTextField();
        JTextField mail=new JTextField();

        login=new JButton("Login");
        login.addActionListener(new ActionListener() {
          //adding login logic to the button 
            public void actionPerformed(ActionEvent e) {
                if (userProfile.getEmail().equals(mail.getText())) {
                    if (userProfile.getPass().equals(pass.getText())) {
                        JOptionPane.showMessageDialog(null, "Login Successfully");
                        cardLayout.show(cardPanel, "profilePanel");
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password. Please try again.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Email not found or incorrect. Please try again.");
                }
            }
            
        });
            
        southPanel.add(new JLabel("Mail:"));
        southPanel.add(mail,BorderLayout.SOUTH);
        southPanel.add(new JLabel("Password:"));
        southPanel.add(pass,BorderLayout.WEST);
        southPanel.add(new JLabel(""), BorderLayout.NORTH);
        southPanel.add(login,BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
        };
        }
        //main 
    public static void main(String[] args){
        
        LinkedInApp app=new LinkedInApp();
    }
    
}