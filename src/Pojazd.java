import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Pojazd {
    protected String nrRejestracyjny;
    protected List<Point> zajetePola;

    public Pojazd(String nrRejestracyjny) {
        this.nrRejestracyjny = nrRejestracyjny;
        this.zajetePola = new ArrayList<>();
    }

    public String getNrRejestracyjny() {
        return nrRejestracyjny;
    }

    public List<Point> getZajetePola() {
        return zajetePola;
    }

    public abstract String getTyp();
}