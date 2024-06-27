import javax.swing.*;
import java.awt.Point;
import java.util.*;
import java.sql.*;

public class Parking{
    // Atrybuty klasy Parking
    private int Kolumny;

    // Tablica dwuwymiarowa reprezentująca miejsca parkingowe.
    // Każde miejsce może być puste (null) lub zajęte przez obiekt klasy Pojazd
    private Pojazd[][] miejsca;

    // Lista przechowująca wszystkie pojazdy znajdujące się na parkingu
    private List<Pojazd> pojazdy;

    private StringBuilder layout = new StringBuilder();

    // Konstruktor klasy Parking
    public Parking() {

        Kolumny = 10;

        // Inicjalizacja tablicy miejsc parkingowych o podanych wymiarach
        this.miejsca = new Pojazd[7][10];

        // Inicjalizacja listy pojazdów
        this.pojazdy = new ArrayList<>();

        this.layout = new StringBuilder();

    }

    // Metoda dodająca pojazd do parkingu
    public boolean dodajPojazd(Pojazd pojazd) {

            // Sprawdzanie, czy wszystkie pola, które pojazd chce zająć, są wolne
            for (Point p : pojazd.getZajetePola()) {
                try {
                    if(pojazd.getNrRejestracyjny().isBlank()){
                        JOptionPane.showMessageDialog(null,"Nie podano numeru rejestracyjnego ",
                                "Numer kolumny", JOptionPane.ERROR_MESSAGE);
                        return false; // Miejsce zajęte, nie można dodać pojazdu
                    }
                    else if (pojazd.getZajetePola().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Nie podano numeru kolumny ",
                                "Numer kolumny", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    else if (miejsca[p.x][p.y] != null) {
                        JOptionPane.showMessageDialog(null,"Wybrane miejsce jest zajęte! ",
                                "Numer kolumny", JOptionPane.ERROR_MESSAGE);
                        return false; // Miejsce zajęte, nie można dodać pojazdu
                    }
                }
                catch (ArrayIndexOutOfBoundsException e){
                    JOptionPane.showMessageDialog(null,"Podano nie poprawny numer kolumny! (zakres 0-9) ",
                                "Numer kolumny", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            }
            // Dodawanie pojazdu do tablicy miejsc
        try {
            for (Point p : pojazd.getZajetePola()) {
                miejsca[p.x][p.y] = pojazd;
            }
        }
            catch (ArrayIndexOutOfBoundsException e){
                return false;
            }
            // Dodawanie pojazdu do listy pojazdów
            pojazdy.add(pojazd);
            zapiszPojazdDoBazy(pojazd, pojazd.getZajetePola().get(0).y);

            return true;

    }

    public void zapiszPojazdDoBazy(Pojazd pojazd, int kolumna) {
        String sql = "INSERT INTO danesamochodu (TypPojazdu, NrRejestracyjny, NrKolumny) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pojazd.getTyp());
            pstmt.setString(2, pojazd.getNrRejestracyjny());
            pstmt.setInt(3, kolumna);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Metoda usuwająca pojazd z parkingu na podstawie numeru rejestracyjnego
    public boolean usunPojazd(String nrRejestracyjny) {
        // Znajdowanie pojazdu na podstawie numeru rejestracyjnego
        Pojazd pojazd = znajdzPojazd(nrRejestracyjny);
        if (pojazd != null) {
            // Usuwanie pojazdu z tablicy miejsc
            for (Point p : pojazd.getZajetePola()) {
                miejsca[p.x][p.y] = null;
            }
            // Usuwanie pojazdu z listy pojazdów
            pojazdy.remove(pojazd);
            usunPojazdZBazy(nrRejestracyjny);

            return true;
        }
        return false;
    }

    public void usunPojazdZBazy(String nrRejestracyjny) {
        String sql = "DELETE FROM danesamochodu WHERE NrRejestracyjny = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nrRejestracyjny);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Metoda wyszukująca pojazd na podstawie numeru rejestracyjnego
    public Pojazd znajdzPojazd(String nrRejestracyjny) {
        for (Pojazd pojazd : pojazdy) {
            if (pojazd.getNrRejestracyjny().equals(nrRejestracyjny)) {
                return pojazd;
            }
        }
        return null;
    }

    public void WszystkieParkingi(){
        wyswietlParking("Motocykli", 1, 0);
        wyswietlParking("Samochodów", 2, 1);
        wyswietlParking("Autobusów", 4, 3);

    }

    // Metoda wyświetlająca wizualizację parkingu w konsoli
    public void wyswietlParking(String TypPojazdu, int Wiersze, int OdKtorego) {
        System.out.println();
        System.out.println("Parking dla " + TypPojazdu);
        System.out.print(" 0  1  2  3  4  5  6  7  8  9\n");
        for (int i = OdKtorego; i < Wiersze + OdKtorego; i++) {
            //System.out.print(i+" ");
            for (int j = 0; j < Kolumny; j++) {
                if (miejsca[i][j] == null) {
                    System.out.print("|O|"); // Oznacza wolne miejsce
                }
                else {
                    System.out.print("|X|"); // Oznacza zajęte miejsce
                }

            }
            System.out.println();
        }
    }



    public String WyszukiwarkaPojazdu(String NrRejestracyjny) { //wyszukiwarka

        StringBuilder layout = new StringBuilder();

        Pojazd pojazd = znajdzPojazd(NrRejestracyjny);

        if (pojazd != null) {
            layout.append("Typ: " + pojazd.getTyp() + "\n");

            layout.append("Nr rejestracyjny: " + pojazd.getNrRejestracyjny()+ "\n");

            layout.append("Kolumna: " + pojazd.getZajetePola().get(0).y + "\n");

        } else {
            layout.append("Pojazd o podanym numerze rejestracyjnym nie został znaleziony.");
        }

        return layout.toString();

    }

    // Metoda wyświetlająca informacje o wszystkich pojazdach na parkingu
    public void wyswietlInformacjeOPojazdach() {
        for (Pojazd pojazd : pojazdy) {
            System.out.println("Typ: " + pojazd.getTyp());
            System.out.println("Nr rejestracyjny: " + pojazd.getNrRejestracyjny());
            System.out.print("Zajęte pola: ");
            for (Point p : pojazd.getZajetePola()) {
                System.out.print("(" + p.x + "," + p.y + ") ");
            }
            System.out.println("\n");
        }
    }
}