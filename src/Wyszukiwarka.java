import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Wyszukiwarka extends JFrame{
    private JPanel panel1;
    private JPanel PanelWelcome;
    private JTextArea ParkingLayoutArea;
    private JTextField NrRejestracyjny;
    private JButton wyszukajButton;
    private JTextArea InformacjaPojazdu;
    private JPanel panel2;
    private int width = 600, height = 600;
    private Parking parking;
    public Wyszukiwarka(Parking parking) {
        super("System zarządzania parkingiem");
        this.setContentPane(this.panel1);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.parking = new Parking();


        wyszukajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nrRejestracyjny = NrRejestracyjny.getText();
                String layout = parking.WyszukiwarkaPojazdu(nrRejestracyjny);
                InformacjaPojazdu.setText(layout);
            }
        });



        NrRejestracyjny.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                int position = NrRejestracyjny.getCaretPosition();
                NrRejestracyjny.setText(NrRejestracyjny.getText().toUpperCase());
                NrRejestracyjny.setCaretPosition(position);
            }
        });
        NrRejestracyjny.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nrRejestracyjny = NrRejestracyjny.getText();
                String layout = parking.WyszukiwarkaPojazdu(nrRejestracyjny);
                InformacjaPojazdu.setText(layout);
            }
        });
    }
}