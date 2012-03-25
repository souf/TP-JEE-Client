/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_final_client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.StringReader;
import java.sql.Connection;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import rest.FileClient;
import xsd.files.FileType;
import xsd.files.FilesType;

/**
 *
 * @author Student
 */
public class Profil extends JFrame implements ActionListener {

    Connection con; // Nouvelle connexion
    Panneau pan = new Panneau("./images/back.jpg", 828, 630, 0);
    JTextField nomUser; //JTextField personnalis� pour avoir le nom d'utilisateur
    JPasswordField passeUser;//JTextField personnalis� pour saisir son mot de passe
    JButton login;  // bouton pour connecter
    JButton cancel;
    EmptyBorder br;
    JPanel JPuploade;
    JPanel JPListeFile;
    TitledBorder tb, fb;
    JButton Bchooser;
    JButton Bupload;
    JTextField Fchooser;
    private JScrollPane jScrollPane1;
    private JTable jTable1;

    public Profil() {
        setTitle("Profil");
        setSize(828, 630);
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

        //telecharger
        tb = new TitledBorder(tb, "Telecharger un fichier ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("DialogInput", Font.PLAIN, 14), Color.black);
        JPuploade = new JPanel();
        JPuploade.setBorder(tb);
        JPuploade.setBounds(30, 45, 770, 90);
        JPuploade.setLayout(null);
        JPuploade.setBackground(Color.white);

        Fchooser = new JTextField();
        Fchooser.setBounds(150, 50, 180, 30);
        Bchooser = new JButton("Parcourir");
        Bchooser.setBounds(360, 50, 120, 30);
        Bupload = new JButton("Upload");
        Bupload.setBounds(500, 50, 120, 30);
        JPuploade.add(Bchooser);
        JPuploade.add(Fchooser);
        JPuploade.add(Bupload);
        Bchooser.addActionListener(this);
        Bupload.addActionListener(this);

        //Lister Fichier
        fb = new TitledBorder(fb, "Mes fichiers ", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("DialogInput", Font.PLAIN, 14), Color.black);
        JPListeFile = new JPanel();
        JPListeFile.setBorder(fb);
        JPListeFile.setBounds(30, 145, 770, 450);
        JPListeFile.setBackground(Color.white);
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        FileClient file = new FileClient();
        // GET
        String response = file.findAll_XML(String.class);

        FilesType filesinfoes = null;
        try {
            JAXBContext jcIn = JAXBContext.newInstance("xsd.files");;
            Unmarshaller u = jcIn.createUnmarshaller();

//        // from String (reponse) to xml element
            JAXBElement element = (JAXBElement) u.unmarshal(new StringReader(response));

            // userinfoes : list of userinfo
            filesinfoes = (FilesType) element.getValue();

        } catch (Exception ex) {
        }
        
        System.out.println(filesinfoes.getFile());
        DefaultTableModel obj = new DefaultTableModel();
        obj.addColumn("Identifent");
        obj.addColumn("Nomfichier");
        obj.addColumn("Taille");
        obj.addColumn("Action");
        List<FileType> fileinfoes = filesinfoes.getFile();
        
        for (int i = 0; i < fileinfoes.size(); i++) {
            FileType monfile = (FileType) fileinfoes.get(i);
            //if(userName.equals(monfile.getUser().getNom())){
            String[] tab = new String[4];
            tab[0] = monfile.getId();
            tab[1] = monfile.getNom();
            tab[2] = monfile.getTaille();
            tab[3] = "";///.getNom()
            try{
                System.out.print(monfile.getUser().getNom()+ " ");
            }catch(java.lang.NullPointerException e){
                System.out.println("e: "  + e.getMessage());
            }
            
            obj.addRow(tab);
           // }
        }

        jTable1.setModel(obj);
        jScrollPane1.setViewportView(jTable1);
        GroupLayout layout = new GroupLayout(JPListeFile);
        JPListeFile.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 11, Short.MAX_VALUE).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)));
        pan.setLayout(null);
        pan.add(JPuploade);
        pan.add(JPListeFile);
        setContentPane(pan);
        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //to be continoued
        JFileChooser chooser;
        chooser = new JFileChooser();
        int r = chooser.showOpenDialog(new JFrame());
        if (r == JFileChooser.APPROVE_OPTION) {
            Fchooser.setText(chooser.getSelectedFile().getPath());
        }
    }
}