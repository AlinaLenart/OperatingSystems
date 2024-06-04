import java.util.*;
public class LRUSimulator {
    private final List<Integer> pageReferences;
    private final int ramSize;

    public LRUSimulator(List<Integer> pageReferences, int ramSize) {
        this.pageReferences = pageReferences;
        this.ramSize = ramSize;
    }

    // Simulate LRU page replacement algorithm
    public int simulateLRU() {
        if(ramSize < 1) {
            throw new ArrayIndexOutOfBoundsException("Za malo ramek: " + ramSize);
        }
        Queue<Integer> pages = new LinkedList<>(pageReferences);
        int[] ram = new int[ramSize];
        int[] usageHistory = new int[ramSize]; // tablica do rejestrowania jak czesto uzywalismy strony
        int pageFaults = 0;

        while (!pages.isEmpty()) {
            int page = pages.poll();
            if (!hasElement(ram, page)) { // nie ma strony w ramie
                pageFaults++;
                if (isFull(ram)) { // ram jest pelny
                    int replaceIndex = getLeastRecentlyUsedIndex(usageHistory); // szukanie najrzadziej uzywanego
                    ram[replaceIndex] = page;
                    resetUsageHistory(usageHistory, replaceIndex);
                }
                else {
                    addPageToRAM(ram, page); // jesli jest miejsce to dodajemy
                    resetUsageHistory(usageHistory, ramSize - 1); // i aktualizujemy historie
                }
            }
            else { // jezeli strona juz jest w ramie aktualizacja tablicy uzycia
                int index = getIndexInRam(ram, page);
                resetUsageHistory(usageHistory, index);
            }
        }
        return pageFaults;
    }

    private boolean isFull(int[] array) {
        for (int value : array) {
            if (value == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean hasElement(int[] array, int element) {
        for (int value : array) {
            if (value == element) {
                return true;
            }
        }
        return false;
    }

    private void addPageToRAM(int[] array, int element) {
        System.arraycopy(array, 1, array, 0, array.length - 1); // przesuwam o 1 do przodu wszystkie
        array[array.length - 1] = element; // dodanie nowej strony
    }

    private int getIndexInRam(int[] array, int element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    private void resetUsageHistory(int[] usageHistory, int index) {
        for (int i = 0; i < usageHistory.length; i++) {
            if (i == index) {
                usageHistory[i] = 0; // zeruje usageHistory
            } else {
                usageHistory[i]++;
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
