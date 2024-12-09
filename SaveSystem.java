package canadiantaxcalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

class SaveSystem {
    private static File valuesFile = new File("TaxCalcRates.ser");
    private ArrayList<BigDecimal> rates = new ArrayList<>();
    private ArrayList<BigDecimal> defaultRates = new ArrayList<>();

    void setRate(int rate, BigDecimal newValue) {
        rates.set(rate, newValue);
    }

    BigDecimal getRate(int rate) {
        return rates.get(rate);
    }

    BigDecimal getGST() {
        return rates.get(0);
    }

    synchronized void saveRates() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(valuesFile);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(rates);
        }
    }

    void resetRates() throws IOException {
        for (int i = 0; i < defaultRates.size(); i++) {
            rates.set(i, defaultRates.get(i));
        }
        // rates = defaultRates;
        saveRates();
    }

    void fallBack() {
        for (int i = 0; i < defaultRates.size(); i++) {
            rates.set(i, defaultRates.get(i));
        }
    }

    @SuppressWarnings("unchecked")
    synchronized void loadRates() throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(valuesFile); ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            rates = (ArrayList<BigDecimal>) objectInputStream.readObject();
        }
    }

    private void initRates() {
        defaultRates.add(new BigDecimal("0.05")); //gst
        defaultRates.add(new BigDecimal("0.07")); //bc
        defaultRates.add(BigDecimal.ZERO); //ab
        defaultRates.add(BigDecimal.ZERO); //sk
        defaultRates.add(new BigDecimal("0.07")); //mb
        defaultRates.add(new BigDecimal("0.08")); //on
        defaultRates.add(new BigDecimal("0.09975")); //qc
        defaultRates.add(new BigDecimal("0.1")); //nb
        defaultRates.add(new BigDecimal("0.1")); //ns
        defaultRates.add(new BigDecimal("0.1")); //pe
        defaultRates.add(new BigDecimal("0.1")); //nl
        defaultRates.add(BigDecimal.ZERO); //yt
        defaultRates.add(BigDecimal.ZERO); //nt
        defaultRates.add(BigDecimal.ZERO); //nu
       // defaultRates.add(new BigDecimal("99.95"));
       // defaultRates.add(new BigDecimal("89.95"));
       // defaultRates.add(new BigDecimal("99.95"));
       // defaultRates.add(new BigDecimal("20.99"));

        rates.addAll(defaultRates);
/*
        try {
            saveRates();
        } catch (IOException e) {
            e.printStackTrace();
        }
 */
    }

    SaveSystem() {
        initRates();
    }
}
