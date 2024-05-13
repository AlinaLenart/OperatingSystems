import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Algorithms {
    private List<Integer> pageRefenerences;
    private int ramSize;

    public Algorithms(List<Integer> pageRefenerences, int ramSize) {
        this.pageRefenerences = pageRefenerences;
        this.ramSize = ramSize;
    }

    public void FIFO(){
        Queue<Integer> pages = new LinkedList<>(pageRefenerences);
        int[] ram = new int[ramSize];
        int pageFaults = 0;

        while (!pages.isEmpty()) {
            int page = pages.poll();
            if (!hasElement(ram, page)) { // nie ma tej strony w pamieci RAM
                if (isFull(ram)) { // pelny RAM
                    addPageToRAM(ram, page); //usuniecie najstarszego przez przesuniecie
                    pageFaults++;
                }
                else {
                    addPageToRAM(ram, page); //??? przesuwanie
                    pageFaults++;
                }
            }
            displayRam(ram);
        }

        System.out.println("Liczba błędów strony (FIFO): " + pageFaults);

    }

    private boolean isFull(int[] array) {
        boolean isFull = true;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 0) {
                isFull = false;
                break;
            }
        }
        return isFull;
    }

    private boolean hasElement(int[] array, int element) {
        boolean hasElement = false;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == element) {
                hasElement = true;
                break;
            }
        }
        return hasElement;
    }

    private void addPageToRAM(int[] array, int element){
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = array[i + 1]; // przesuwam o 1 do przodu wszystkie
        }
        array[array.length - 1] = element; //dodanie nowej strony
    }

    private void displayRam(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "; ");
        }
        System.out.println();
    }


}
