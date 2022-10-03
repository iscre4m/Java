package step.learning.oop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Newspaper
        extends Literature
        implements Printable, Periodic {
    private Date date;
    private static final SimpleDateFormat sdfParser =
            new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfPrinter =
            new SimpleDateFormat("dd.MM.yyyy");

    public Date getDate() {
        return date;
    }

    public Newspaper setDate(String date) throws ParseException {
        this.date = sdfParser.parse(date);

        return this;
    }

    @Override
    public Newspaper setTitle(String title) {
        super.setTitle(title);

        return this;
    }

    @Override
    public void print() {
        final long YEAR_TO_MILLISECONDS = 31622400000L;
        final int STARTING_YEAR = 1970;
        final long year = this.getDate().getTime() / YEAR_TO_MILLISECONDS + STARTING_YEAR;
        final long currentYear = new Date().getTime() / YEAR_TO_MILLISECONDS + STARTING_YEAR;

        if (year == currentYear) {
            System.out.printf("Newspaper. Date: %s. Title: %s%n",
                    sdfPrinter.format(this.getDate()).substring(0, 5), super.getTitle()
            );

            return;
        }

        System.out.printf("Newspaper. Date: %s. Title: %s%n",
                sdfPrinter.format(this.getDate()), super.getTitle()
        );
    }
}