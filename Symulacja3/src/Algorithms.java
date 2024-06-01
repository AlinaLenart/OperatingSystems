import java.util.*;

public class Algorithms {
    private final List<Integer> pageReferences;
    private final int ramSize;

    public Algorithms(List<Integer> pageReferences, int ramSize) {
        this.pageReferences = pageReferences;
        this.ramSize = ramSize;
    }

    public int FIFO(){
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
                    addPageToRAM(ram, page); // przesuwanie
                    pageFaults++;
                }
            }
        }
        return pageFaults;
    }

    public int RAND() {
        int[] ram = new int[ramSize];
        int pageFaults = 0;
        Random generate = new Random();
        Queue<Integer> pages = new LinkedList<>(pageReferences);

        while (!pages.isEmpty()) {

            int page = pages.poll();

            if (!hasElement(ram, page)) { // nie ma tej strony w pamieci RAM
                if (isFull(ram)) { // pelny RAM
                    int randomIndex = generate.nextInt(ramSize); //zastąpienie randomowego indeksu
                    ram[randomIndex] = page;
                    pageFaults++;
                }
                else {
                    addPageToRAM(ram, page);
                    pageFaults++;
                }
            }
        }
        return pageFaults;
    }

    public int OPT() {
        int[] ram = new int[ramSize];
        int pageFaults = 0;
        Queue<Integer> pages = new LinkedList<>(pageReferences);

        while (!pages.isEmpty()) {

            int page = pages.poll();

            if (!hasElement(ram, page)) { // nie ma strony w ramie
                if (isFull(ram)) { // pelny ram
                    int replaceIndex = getOptimalReplacement(ram, pages);
                    ram[replaceIndex] = page; // zastapic z indeksem ktory w przyszlosci pojawi sie najpozniej/wcale
                    pageFaults++;
                }
                else {
                    addPageToRAM(ram, page); // po prostu dodanie jesli jest miejsce
                    pageFaults++;
                }
            }
        }
        return pageFaults;
    }

    public int LRU() {
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
    public int approximatedLRU() {
        Queue<Integer> pages = new LinkedList<>(pageReferences);
        Queue<Page> ram = new ArrayDeque<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!containsPage(ram, page)) {
                pageFaults++;
                if (ram.size() == ramSize) {
                    replacePageInRAM(ram, page);
                }
                else {
                    ram.add(new Page(page));
                }
            }
            else {
                updateReferenceBit(ram, page);
            }
        }
        return pageFaults;
    }
    private boolean containsPage(Queue<Page> ram, int page) {
        for (Page p : ram) {
            if (p.pageNumber == page) {
                return true;
            }
        }
        return false;
    }

    private void replacePageInRAM(Queue<Page> ram, int newPage) {
        while (true) {
            Page frontPage = ram.poll();
            if (frontPage.referenceBit == 0) {
                ram.add(new Page(newPage));
                break;
            } else {
                frontPage.referenceBit = 0;
                ram.add(frontPage);
            }
        }
    }

    private void updateReferenceBit(Queue<Page> ram, int page) {
        for (Page p : ram) {
            if (p.pageNumber == page) {
                p.referenceBit = 1;
                break;
            }
        }
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
        array[array.length - 1] = element; // dodanie nowej strony
    }

    private int getOptimalReplacement(int[] ram, Queue<Integer> remainingPages) {
        int maxIndex = -1;
        int replacePageIndex = -1;

        for (int i = 0; i < ram.length; i++) {
            int pageIndex = getIndex(ram[i], remainingPages); // kiedy nastepnym razem sie pojawi
            if (pageIndex == -1) { // jesli w ogole sie nie pojawi to odrazu wymieniamy
                return i;
            }
            if (pageIndex > maxIndex) { // jesli pojawia sie ale daleko to tez zamieniamy
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

    private void resetUsageHistory(int[] usageHistory, int index) {
        for (int i = 0; i < usageHistory.length; i++) {
            if (i == index) {
                usageHistory[i] = 0; // zeruje usageHistory
            }
            else {
                usageHistory[i]++; // zwiekszam usageHistory dla innych
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

    private void displayRamQ(Queue<Page> ram) {
        System.out.print("RAM: ");
        for (Page page : ram) {
            System.out.print(page + " ");
        }
        System.out.println();
    }
    private void displayRam(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "; ");
        }
        System.out.println();
    }

    private static class Page {
        int pageNumber;
        int referenceBit;

        Page(int pageNumber) {
            this.pageNumber = pageNumber;
            this.referenceBit = 1;
        }

    }


}

