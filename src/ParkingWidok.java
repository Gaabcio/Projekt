import javax.swing.*;


public class ParkingWidok extends JFrame {
private int width = 500, height = 400;
    private JPanel panel1;
    private JTextArea WidokParkinguTextArea;


    public ParkingWidok(){
    super("System zarządzania parkingiem");
    this.setContentPane(this.panel1);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(width,height);
    this.setLocationRelativeTo(null);
    this.setVisible(true);



    }







}
