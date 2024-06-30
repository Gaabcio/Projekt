import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Wyparkuj extends JFrame {
    private JPanel panel1;
    private JComboBox<String> nrRejestracyjnyComboBox;
    private JButton wyparkujButton;
    private Parking parking;
    private JButton anulujButton;

    public Wyparkuj(Parking parking) {
        super("Wyparkowanie Pojazdu");
        this.parking = parking;
        this.setContentPane(this.panel1);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(550, 200);
        this.setLocationRelativeTo(null);

        List<String> numeryRejestracyjne = parking.pobierzNumeryRejestracyjne();

        // Wypełnij JComboBox numerami rejestracyjnymi

        for (String nr : numeryRejestracyjne) {
            nrRejestracyjnyComboBox.addItem(nr);
        }


        wyparkujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainSystem mainSystem = new MainSystem();
                mainSystem.setVisible(false);
                String wybranyNrRejestracyjny = (String) nrRejestracyjnyComboBox.getSelectedItem();
                if (wybranyNrRejestracyjny != null && parking.usunPojazd(wybranyNrRejestracyjny)) {
                    JOptionPane.showMessageDialog(null, "Wyparkowano pojazd: " + wybranyNrRejestracyjny,
                            "Wyparkowanie pojazdu", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Błąd wyparkowania pojazdu: " + wybranyNrRejestracyjny,
                            "Wyparkowanie pojazdu", JOptionPane.ERROR_MESSAGE);
                }
                mainSystem.aktualizujComboBoxPozycjiParkowania();
            }
        });

        anulujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }



}
