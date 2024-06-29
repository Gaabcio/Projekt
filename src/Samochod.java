import java.awt.Point;
import java.util.Arrays;

public class Samochod extends Pojazd {
    public Samochod(String nrRejestracyjny, int kolumna) {
        super(nrRejestracyjny);
        this.zajetePola = Arrays.asList(new Point(1, kolumna), new Point(2, kolumna));
    }

    @Override
    public String getTyp() {
        return "Samochód";
    }
}