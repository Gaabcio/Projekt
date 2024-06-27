import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainSystem extends JFrame{
    private JPanel panel1;
    private JComboBox TypPojazdu;
    private JTextField NrRejestracyjny;
    private JTextField PozycjaParkowania;
    private JButton wyczyscButton;
    private JButton wyjscieButton;
    private JButton zaparkujButton;
    private JButton wyparkujButton;
    private JButton wylogujButton;
    private JButton wyszukajButton;

    private int width = 600, height = 600;
    private Parking parking;
    private Pojazd pojazd;

    public MainSystem() {
        super("System zarządzania parkingiem");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.parking = new Parking();

        parking.WszystkieParkingi();


        zaparkujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String typ = (String) TypPojazdu.getSelectedItem();
                    String Nr_Rejestracyjny = NrRejestracyjny.getText();
                    int kolumna = Integer.parseInt(PozycjaParkowania.getText());


                    switch (typ) {
                        case "Motocykl":
                            pojazd = new Motocykl(Nr_Rejestracyjny, kolumna);
                            break;
                        case "Samochód":
                            pojazd = new Samochod(Nr_Rejestracyjny, kolumna);
                            break;
                        case "Autobus":
                            pojazd = new Autobus(Nr_Rejestracyjny, kolumna);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Nie wybrano typu pojazdu",
                                    "Błąd", JOptionPane.ERROR_MESSAGE);
                            return;
                    }

                    if (parking.dodajPojazd(pojazd) && !Nr_Rejestracyjny.isBlank()) {
                        JOptionPane.showMessageDialog(null, "Dodano pojazd: " + Nr_Rejestracyjny,
                                "Dodanie pojazdu", JOptionPane.INFORMATION_MESSAGE);
                        parking.WszystkieParkingi();
                        parking.wyswietlInformacjeOPojazdach();
                    } else {
                        JOptionPane.showMessageDialog(null, "Błąd dodania pojazdu",
                                "Dodanie pojazdu", JOptionPane.ERROR_MESSAGE);
                    }



                }
                catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null, "Nie podano numeru kolumny lub podane dane są nieprawidłowe",
                            "Dodanie pojazdu", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        wyparkujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Nr_Rejestracyjny = NrRejestracyjny.getText();
                if (parking.usunPojazd(Nr_Rejestracyjny)) {
                    JOptionPane.showMessageDialog(null,"Usunięto pojazd: " +Nr_Rejestracyjny,
                            "Usuwanie pojazdu", JOptionPane.INFORMATION_MESSAGE);
                    parking.WszystkieParkingi();
                    parking.wyswietlInformacjeOPojazdach();
                } else {
                    JOptionPane.showMessageDialog(null,"Błąd usunięcia pojazdu: " +Nr_Rejestracyjny,
                            "Usuwanie pojazdu", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        wyszukajButton.addActionListener(new ActionListener() { //wyszukiwanie pojazdu po nr_rejestracyjnym
            @Override
            public void actionPerformed(ActionEvent e) {
                Wyszukiwarka wyszukiwarka = new Wyszukiwarka(parking);
                wyszukiwarka.setVisible(true);
            }
        });

        wyjscieButton.addActionListener(new ActionListener() { //zamknięcie programu
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        wylogujButton.addActionListener(new ActionListener() { //wylogowanie
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });

        wyczyscButton.addActionListener(new ActionListener() { //wyczyszczenie textfieldów
            @Override
            public void actionPerformed(ActionEvent e) {
                TypPojazdu.setSelectedIndex(0);
                NrRejestracyjny.setText("");
                PozycjaParkowania.setText("");
            }
        });


        NrRejestracyjny.addKeyListener(new KeyAdapter() { //Duze litery dla pola tekstowego z nr rejestracyjnym
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                int position = NrRejestracyjny.getCaretPosition();
                NrRejestracyjny.setText(NrRejestracyjny.getText().toUpperCase());
                NrRejestracyjny.setCaretPosition(position);
            }
        });

    }
}

