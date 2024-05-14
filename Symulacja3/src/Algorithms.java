import java.util.*;

public class Algorithms {
    private List<Integer> pageReferences;
    private int ramSize;

    public Algorithms(List<Integer> pageReferences, int ramSize) {
        this.pageReferences = pageReferences;
        this.ramSize = ramSize;
    }

    public void FIFO(){
        Queue<Integer> pages = new LinkedList<>(pageReferences);
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

    public void RAND() {
        int[] ram = new int[ramSize];
        int pageFaults = 0;
        Random generate = new Random();
        Queue<Integer> pages = new LinkedList<>(pageReferences);

        while (!pages.isEmpty()) {
            int page = pages.poll();
            if (!hasElement(ram, page)) { // If the page is not in RAM
                if (isFull(ram)) { // If RAM is full
                    // Replace a random page in RAM
                    int randomIndex = generate.nextInt(ramSize);
                    ram[randomIndex] = page;
                    pageFaults++;
                } else {
                    addPageToRAM(ram, page);
                    pageFaults++;
                }
            }
            displayRam(ram);
        }

        System.out.println("Liczba błędów strony (RAND): " + pageFaults);
    }

    public void OPT() {
        int[] ram = new int[ramSize];
        int pageFaults = 0;
        Queue<Integer> pages = new LinkedList<>(pageReferences);

        while (!pages.isEmpty()) {

            int page = pages.poll();

            if (!hasElement(ram, page)) { // If the page is not in RAM

                if (isFull(ram)) { // If RAM is full
                    int replaceIndex = getOptimalReplacement(ram, pages);
                    ram[replaceIndex] = page; // Replace the page at the optimal index
                    pageFaults++;
                } else {
                    addPageToRAM(ram, page); // Add the page to RAM
                    pageFaults++;
                }
            }
            displayRam(ram);
        }

        System.out.println("Liczba błędów strony (OPT): " + pageFaults);
    }

    public void LRU() {
        Queue<Integer> pages = new LinkedList<>(pageReferences);
        int[] ram = new int[ramSize];
        int[] usageHistory = new int[ramSize]; // Array to track usage history
        int pageFaults = 0;

        while (!pages.isEmpty()) {
            int page = pages.poll();
            if (!hasElement(ram, page)) { // If the page is not in RAM
                if (isFull(ram)) { // If RAM is full
                    int replaceIndex = getLeastRecentlyUsedIndex(usageHistory); // Find the index of the least recently used page
                    ram[replaceIndex] = page; // Replace the least recently used page
                    updateUsageHistory(usageHistory, replaceIndex); // Update usage history
                    pageFaults++;
                } else {
                    addPageToRAM(ram, page); // Add the page to RAM
                    updateUsageHistory(usageHistory, ramSize - 1); // Update usage history
                    pageFaults++;
                }
            } else { // If the page is already in RAM, update its usage history
                int index = getIndexInRam(ram, page);
                updateUsageHistory(usageHistory, index); // Update usage history
            }
            displayRam(ram);
        }

        System.out.println("Liczba błędów strony (LRU): " + pageFaults);
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

    private int getOptimalReplacement(int[] ram, Queue<Integer> remainingPages) {
        int maxIndex = -1;
        int replacePageIndex = -1;

        for (int i = 0; i < ram.length; i++) {
            int pageIndex = getIndex(ram[i], remainingPages); // Get the index of the current page in the remaining pages
            if (pageIndex == -1) { // If the page won't be used anymore
                return i; // Return the index of this page for replacement in the RAM array
            }
            if (pageIndex > maxIndex) {
                maxIndex = pageIndex;
                replacePageIndex = i;
            }
        }

        return replacePageIndex;
    }

    private int getIndex(int page, Queue<Integer> remainingPages) {
        int index = 0;
        for (int p : remainingPages) {
            if (p == page) {
                return index;
            }
            index++;
        }
        return -1;
    }
    private int getIndexInRam(int[] array, int element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    private void updateUsageHistory(int[] usageHistory, int index) {
        for (int i = 0; i < usageHistory.length; i++) {
            if (i == index) {
                usageHistory[i] = 0; // Reset usage history for the recently used page
            } else {
                usageHistory[i]++; // Increment usage history for other pages
            }
        }
    }

    private int getLeastRecentlyUsedIndex(int[] usageHistory) {
        int maxIndex = 0;
        for (int i = 1; i < usageHistory.length; i++) {
            if (usageHistory[i] > usageHistory[maxIndex]) { //im wiecej jednostek temu zostal uzyty tym mam wiecej w usageHistory
                maxIndex = i;
            }
        }
        return maxIndex;
    }



}
