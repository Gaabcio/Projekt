import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class MainSystem extends JFrame{
    private JPanel panel1;
    private JComboBox TypPojazdu;
    private JTextField NrRejestracyjny;
    private JButton wyczyscButton;
    private JButton wyjscieButton;
    private JButton zaparkujButton;
    private JButton wyparkujButton;
    private JButton wylogujButton;
    private JButton wyszukajButton;
    private JLabel TypPojazduLabel;
    private JComboBox PozycjaParkowania;

    private int width = 500, height = 400;
    private Parking parking;
    private Pojazd pojazd;
    private ImageIcon iconMoto = new ImageIcon(getClass().getResource("/moto.png"));
    private ImageIcon iconCar = new ImageIcon(getClass().getResource("/car.png"));
    private ImageIcon iconBus = new ImageIcon(getClass().getResource("/bus.png"));

    private static ImageIcon resize(ImageIcon src){
        return new ImageIcon(src.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    }


    public MainSystem() {
        super("System zarządzania parkingiem");
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.parking = new Parking();



        parking.WszystkieParkingi();
        parking.wyswietlInformacjeOPojazdach();
        aktualizujComboBoxPozycjiParkowania();
        TypPojazduLabel.setIcon(resize(iconMoto));
        TypPojazdu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typ = (String) TypPojazdu.getSelectedItem();
                switch (typ){
                    case "Motocykl":
                        TypPojazduLabel.setIcon(resize(iconMoto));
                        break;
                    case "Samochód":
                        TypPojazduLabel.setIcon(resize(iconCar));
                        break;
                    case "Autobus":
                        TypPojazduLabel.setIcon(resize(iconBus));
                        break;
                    default:
                        TypPojazduLabel.setIcon(null);
                        break;
                }
                aktualizujComboBoxPozycjiParkowania();
            }
        });


        zaparkujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String typ = (String) TypPojazdu.getSelectedItem();
                    String Nr_Rejestracyjny = NrRejestracyjny.getText();
                    int kolumna = (Integer) PozycjaParkowania.getSelectedItem();

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
                        aktualizujComboBoxPozycjiParkowania();
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

                List<String> numeryRejestracyjne = parking.pobierzNumeryRejestracyjne();

                if (numeryRejestracyjne.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Brak pojazdów do wyparkowania.",
                            "Wyparkowanie pojazdu", JOptionPane.WARNING_MESSAGE);
                } else {
                    Wyparkuj wyparkuj = new Wyparkuj(parking);
                    wyparkuj.setVisible(true);
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
                PozycjaParkowania.setSelectedIndex(0);
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
    public void aktualizujComboBoxPozycjiParkowania() {
        PozycjaParkowania.removeAllItems();
        String typPojazdu = (String) TypPojazdu.getSelectedItem();
        List<Integer> dostepneKolumny = parking.pobierzDostepneKolumny(typPojazdu);
        //System.out.println("Dostępne kolumny dla " + typPojazdu + ": " + dostepneKolumny);
        for (Integer kolumna : dostepneKolumny) {
            PozycjaParkowania.addItem(kolumna);
        }
    }
}

