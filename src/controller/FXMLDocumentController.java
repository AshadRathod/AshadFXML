/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Users;


/**
 *
 * @author ashad
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private Button buttonCreateUser;

    @FXML
    private Button buttonReadUser;

    @FXML
    private Button buttonUpdateUser;

    @FXML
    private Button buttonDeleteUser;
    
    @FXML 
    private Button buttonReadUserEmail;
    
    @FXML
    private Button buttonReadUserName;
    
    @FXML
    private Button buttonReadUserAcct;
    
    @FXML
    private Button buttonReadUserID;
    
    /**
     * Quiz 4 Implementation
     */
    
    @FXML
    private TextField textboxName;
    
    @FXML
    private TableView<Users> userTable;
    @FXML
    private TableColumn<Users, String> userName;
    @FXML
    private TableColumn<Users, Integer> userID;
    @FXML
    private TableColumn<Users, Integer> userAcct;
    @FXML
    private TableColumn<Users, String> userEmail;
    
    private ObservableList<Users> userData;
    
    @FXML
    public void initialize() {
        userName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        userID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        userAcct.setCellValueFactory(new PropertyValueFactory<>("Acct#"));
        userEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        
        userTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    public void setTableData(List<Users> userList) {
        
        userData = FXCollections.observableArrayList();
        
        userList.forEach(s -> {
            userData.add(s);
        
        });
        
        userTable.setItems(userData);
        userTable.refresh();
     
    }
    
    @FXML
    void searchByNameAction(ActionEvent event) {
        System.out.println("clicked");
        
        
        String name;
        name = textboxName.getText();
        
        List<Users> users = readByName(name);
        
        if (users == null || users.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");
            alert.setHeaderText("This is header section to write heading");
            alert.setContentText("No user");
            alert.showAndWait();
        } else {
            setTableData(users);
        } 
    }
    @FXML
    void searchByNameAdvancedAction(ActionEvent event) {
        System.out.println("clicked");
        
        String name = textboxName.getText();
        
        List<Users> users = readByNameAdvanced(name);
        
        if (users == null || users.isEmpty()) {
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");
            alert.setHeaderText("This is header section to write heading");
            alert.setContentText("No user");
            alert.showAndWait();
        } else {
            setTableData(users);
        }
    }
    
    @FXML
    void actionShowDetails(ActionEvent event) throws IOException {
        System.out.println("clicked");
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailedModelView.fxml"));
        
        Parent DetailedModelView = loader.load();
        
        Scene tableViewScene = new Scene(DetailedModelView);
        
        DetailedModelViewController detailedControlled = loader.getController();
        
        
        Stage stage = new Stage();
        stage.setScene(tableViewScene);
        stage.show();
    }
    
    @FXML
    void actionShowDetailsInPlace(ActionEvent event) throws IOException {
        System.out.println("clicked");
     
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DetailedModelView.fxml"));
        
        Parent DetailedModelView = loader.load();
        
        Scene tableViewScene = new Scene(DetailedModelView);
        
        DetailedModelViewController detailedControlled = loader.getController();
        
        
        
        Scene currentScene = ((Node) event.getSource()).getScene();
        detailedControlled.setPreviousScene(currentScene);
        
        Stage stage = (Stage) currentScene.getWindow();
        
        stage.setScene(tableViewScene);
        stage.show();
    }
    
    @FXML
    void createUser(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter Account Number");
        Double acct = input.nextDouble();
        
        System.out.println("Enter Email:");
        String email = input.next();
             
        Users User = new Users();
        
        User.setId(id);
        User.setName(name);
        User.setAccountnumber(acct);
        User.setEmail(email);      
        create(User);

    }

    @FXML
    void deleteUser(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
         // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Users s = readById(id);
        System.out.println("we are deleting this user: "+ s.toString());
        delete(s);

    }
    
    @FXML
    void readById(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        Users s = readById(id);
        System.out.println(s.toString());
    }

    @FXML
    void readUserEmail(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter Email:");
        String email = input.next();
        
        List<Users> s = readUserEmail(email);
        System.out.println(s.toString());

    }

    @FXML
    void readByName(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter Name:");
        String name = input.next();
        
        List<Users> s = readUserName(name);
        System.out.println(s.toString());

    }

    @FXML
    void readUserAcct(ActionEvent event) {
        
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        
        System.out.println("Enter Id:");
        int id = input.nextInt();
        
        System.out.println("Enter Account Number:");
        double Accountnumber = input.nextDouble();
             
        List<Users> user =  readByNameandAccountNumber(id, Accountnumber);
            
    }
    

    @FXML
    void readUser(ActionEvent event) {

    }

    @FXML
    void updateUser(ActionEvent event) {
        Scanner input = new Scanner(System.in);
        
        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();
        
        System.out.println("Enter Name:");
        String name = input.next();
        
        System.out.println("Enter Account Number:");
        Double acct = input.nextDouble();
        
        System.out.println("Enter Email");
        String email = input.next();
        // create a student instance
        Users User = new Users();
        
        // set properties
        User.setId(id);
        User.setName(name);
        User.setAccountnumber(acct);
        User.setEmail(email);
           
        update(User);

    }
     
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        
        Query query = manager.createNamedQuery("Users.findAll");
        List<Users> data = query.getResultList();
        
        for (Users s : data) {
            System.out.println(s.getId() + " " + s.getName()+ " " + s.getAccountnumber()+ " "+ s.getEmail());
        }
    }
    
    // Database manager
    EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        // loading data from database
        //database reference: "IntroJavaFXPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("AshadFXMLPU").createEntityManager();
       
                                     
    } 
    
    public void create(Users user) {
        try {
            manager.getTransaction().begin();
            
            if(user.getId()!= null) {
                
                manager.persist(user);
                
                manager.getTransaction().commit();
                
                System.out.println(user.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
    public List<Users> readAll() {
        Query query = manager.createNamedQuery("User.findAll");
        List<Users> users = query.getResultList();
        
        for (Users s : users) {
            System.out.println(s.getId() + " " + s.getName() + " " + s.getAccountnumber() + " " + s.getEmail());
            
        }
        
        return users;
    }
    public List<Users> readUserEmail(String name) {
        Query query = manager.createNamedQuery("User.findByEmail");
        
        query.setParameter("Email", name);
        
        List<Users> users = query.getResultList();
        for (Users user : users) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getAccountnumber() + " " + user.getEmail());
        }
        return users;
    }
    public List<Users>  readUserName(String name){
        Query query = manager.createNamedQuery("User.findByName");
        
        query.setParameter("name", name);
        
        List<Users> users = query.getResultList();
        for (Users user : users) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getAccountnumber() + " " + user.getEmail());
        }
        
        return users;
    }
    public List<Users> readByNameandAccountNumber(int id, double AccountNumber) {
        Query query = manager.createNamedQuery("User.findByNameandAccountNumber");
        
        query.setParameter("AccountNumber", AccountNumber);
        query.setParameter("Id", id);
        
        List<Users> users = query.getResultList();
        for (Users user : users) {
            System.out.println(user.getId() + " " + user.getName() + " " + user.getAccountnumber() + " " + user.getEmail());
        }
        
        return users;
    }
    public void update(Users user) {
        try {
            Users existingUsers = manager.find(Users.class, user.getId());
            
            if (existingUsers != null) {
                manager.getTransaction().begin();
                
                existingUsers.setName(user.getName());
                existingUsers.setAccountnumber(user.getAccountnumber());
                
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void delete(Users s) {
        try {
            Users existingUsers = manager.find(Users.class, s.getId());
            
            if (existingUsers != null) {
                manager.getTransaction().begin();
                
                manager.remove(existingUsers);
                
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    private List<Users> readByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<Users> readByNameAdvanced(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Users readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
    
}
