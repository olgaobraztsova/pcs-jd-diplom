import java.util.Objects;

public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry o) {
        if(pdfName.equals(o.pdfName)) {
            if (count == o.count) {
                return page - o.page;
            } else {
                return count - o.count;
            }
        }
        else return pdfName.compareTo(o.pdfName);
    }


    @Override
    public String toString(){
        return "PageEntry{pdf=" + pdfName + ", page=" + page + ", count=" + count + "}";
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageEntry pageEntry = (PageEntry) o;
        return page == pageEntry.page && pdfName.equals(pageEntry.pdfName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pdfName, page);
    }


}
