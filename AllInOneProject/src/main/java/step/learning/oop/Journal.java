package step.learning.oop;

public class Journal
        extends Literature
        implements Printable, Periodic {
    private int number;

    public int getNumber() {
        return number;
    }

    public Journal setNumber(int number) {
        this.number = number;

        return this;
    }

    @Override
    public Journal setTitle(String title) {
        super.setTitle(title);

        return this;
    }

    @Override
    public void print() {
        System.out.printf("Journal. Number: %d. Title: %s%n",
                this.number, super.getTitle()
        );
    }
}
