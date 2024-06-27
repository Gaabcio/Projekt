import java.awt.Point;
import java.util.Arrays;

public class Autobus extends Pojazd {
    public Autobus(String nrRejestracyjny, int kolumna) {
        super(nrRejestracyjny);
        this.zajetePola = Arrays.asList(
                new Point(3, kolumna),
                new Point(4, kolumna),
                new Point(5, kolumna),
                new Point(6, kolumna)
        );
    }

    @Override
    public String getTyp() {
        return "Autobus";
    }
}