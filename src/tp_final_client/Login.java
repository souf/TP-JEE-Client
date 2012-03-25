package tp_final_client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.*;
import javax.xml.namespace.QName;
import rest.UserClient;
import xsd.users.UserType;
import xsd.users.UsersType;

public class Login extends JFrame implements ActionListener {

    Connection con; // Nouvelle connexion
    Panneau pan = new Panneau("./images/back_login.jpg", 790, 419, 0);
    JTextField nomUser; //JTextField personnalis� pour avoir le nom d'utilisateur
    JPasswordField passeUser;//JTextField personnalis� pour saisir son mot de passe
    JButton login;  // bouton pour connecter
    JButton cancel;
    EmptyBorder br;
    String userLogin = "";
    public Login() {
        setTitle("Connexion");
        setSize(790, 419);
        setUndecorated(true);
//	getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        JLabel nom = new JLabel("User ");
        nom.setForeground(new Color(32, 70, 125));
        nom.setFont(new Font("DialogInput", Font.PLAIN, 14));

        JLabel passe = new JLabel("Passeword ");
        passe.setForeground(new Color(32, 70, 125));
        passe.setFont(new Font("DialogInput", Font.PLAIN, 14));

        nomUser = new JTextField("");
        nomUser.setToolTipText("Nom user");
        nomUser.setFont(new Font("DialogInput", Font.PLAIN, 14));

        passeUser = new JPasswordField("");
        passeUser.setToolTipText("Mot de passe");
        passeUser.setEchoChar('�');
        passeUser.setFont(new Font("DialogInput", Font.PLAIN, 14));

        nom.setBounds(320, 235, 200, 20);
        nomUser.setBounds(420, 235, 150, 20);
        passe.setBounds(320, 265, 200, 20);
        passeUser.setBounds(420, 265, 150, 20);

        login = new JButton();
        //login.addActionListener(this);
        login.setBorder(br);
        login.setBounds(450, 295, 92, 20);
        login.setIcon(new ImageIcon("./images/B_login.jpg"));
        login.addActionListener(this);

        cancel = new JButton();
        cancel.addActionListener(this);
        cancel.setBorder(br);
        cancel.setBounds(340, 295, 92, 20);
        cancel.setIcon(new ImageIcon("./images/B_cancel.jpg"));
        cancel.setActionCommand("cancel");

        pan.setLayout(null);
        pan.add(nom);
        pan.add(nomUser);
        pan.add(passe);
        pan.add(passeUser);
        pan.add(login);
        pan.add(cancel);

        setContentPane(pan);

        // icone de la fenetre de connexion

        //Image icone=Toolkit.getDefaultToolkit().getImage(getClass().getResource("C:/Users/Student/Desktop/Projet  Java EE/TP_Swing/src/images/i_con.png"));			
        //setIconImage(icone);       
        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {



        //to be continoued
        if (e.getSource() == login) {
            System.out.print("login "+nomUser.getText()+passeUser.getText());
            if ("".equals(nomUser.getText()) || "".equals(passeUser.getText())) {
                JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs !! ", "Erreur de connexion", JOptionPane.WARNING_MESSAGE);
            } else {
                UserClient client = new UserClient();
                // GET
                String response = client.findAll_XML(String.class);
                System.out.println(response);
                UsersType usersinfoes = null;
//            // JAXB context 
                try {
                    JAXBContext jcIn = JAXBContext.newInstance("xsd.users");

                    Unmarshaller u = jcIn.createUnmarshaller();
//        // from String (reponse) to xml element
                    JAXBElement element = (JAXBElement) u.unmarshal(new StringReader(response));
                    usersinfoes = (UsersType) element.getValue();
                } catch (Exception erreur) {
                }

                // userinfoes : list of userinfo

                System.out.println(usersinfoes.getUser());
                List<UserType> userinfos = usersinfoes.getUser();
                boolean trouve = false;
                for (int i = 0; i < userinfos.size(); i++) {
                    UserType user = (UserType) userinfos.get(i);
                    System.out.println(user.getNom());
                    if (user.getNom().equals(nomUser.getText()) && user.getMotDePasse().equals(passeUser.getText())) {
                        trouve = true;
                        userLogin = user.getNom();
                        System.out.println("youpiiiiiii");
                        break;
                    }
                }
                if (!trouve) {
                    JOptionPane.showMessageDialog(null, "Nom utilisateur ou mot de passe incorrect !! ", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
                } else {
                    this.show(false);
                  new Profil();
                    //  System.out.print("ooooo llll");
                }
            }
        }
        if (e.getSource() == cancel) {

            int n = JOptionPane.showConfirmDialog(null, "Voullez vous vraiment quitter !! ", "Connexion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (n == 0) {
                dispose();
                System.exit(0);
            }
        }
    }

}
