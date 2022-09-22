package step.learning.oop;

public class Journal extends Literature {
    private int number;

    public int getNumber() {
        return number;
    }

    public Journal setNumber(int number) {
        this.number = number;

        return this;
    }

    public Journal setTitle(String title) {
        super.setTitle(title);

        return this;
    }

    @Override
    public Journal print() {
        System.out.printf("Journal. Number: %d.Title: %s%n",
                this.number, super.getTitle()
        );

        return this;
    }
}
