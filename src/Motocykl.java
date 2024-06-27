import java.awt.*;
import java.util.Arrays;

public class Motocykl extends Pojazd {
    public Motocykl(String nrRejestracyjny, int kolumna) {
        super(nrRejestracyjny);
        this.zajetePola = Arrays.asList(new Point(0, kolumna));
    }

    @Override
    public String getTyp() {
        return "Motocykl";
    }
}