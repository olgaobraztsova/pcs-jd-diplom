import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    // hashmap для хранения проиндексированных данных
    private final Map<String, List<PageEntry>> index = new HashMap<>();


    public BooleanSearchEngine(File pdfsDir) throws IOException {
        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы

        // получаем список файлов в папке
        File[] listOfFiles = pdfsDir.listFiles();

        // цикл для прохождения по файлам
        for (int i = 0; i < (listOfFiles != null ? listOfFiles.length : 0); i++) {
            // открываем PDF файл
            PdfDocument doc = new PdfDocument(new PdfReader(listOfFiles[i]));
            // получаем количество страниц в файле
            int pages = doc.getNumberOfPages();

            // цикл для прохождения по страницам
            for (int j = 0; j < pages; j++){
                // сохраняем текст со страницы
                String text = PdfTextExtractor.getTextFromPage(doc.getPage(j + 1));
                // разделяем текст в массив слов
                String[] words = text.toLowerCase().split("\\P{IsAlphabetic}+");
                // сохраняем слова в индекс
                stringToMap(words, listOfFiles[i],j + 1);

            }
        }
    }

    // метод для вывода данных из мапы по ключевому слову
    @Override
    public List<PageEntry> search(String word) {
        // тут реализуйте поиск по слову
        //return Collections.emptyList();
        return index.get(word);
    }

    // метод для добавления поискового результата для слова
    private void addEntry(String word, PageEntry pageEntry){
        List<PageEntry> listOfEntries = index.get(word);

        if(listOfEntries == null) { // if the list doesn't exist - create the list
            listOfEntries = new ArrayList<PageEntry>();
            listOfEntries.add(pageEntry);
        } else {
            if(!listOfEntries.contains(pageEntry)) {
                listOfEntries.add(pageEntry);
            }
        }
        Collections.sort(listOfEntries, Collections.reverseOrder());
        index.put(word, listOfEntries);
    }

    // метод для сохранения уникальных слов со страницы в Hashmap
    private void stringToMap(String[] words, File listOfFiles, int pageNumber){
        for (int i = 0; i < words.length; i++) {
            String item = words[i];
            int count = 0;

            // подсчитываем количество упоминаний каждого слова на странице
            for (int j = 0; j < words.length; j++) {
                if (item.equals(words[j])) {
                    count++;
                }
            }

            // создаем PageEntry(pdf, pageNumber, quantity)
            PageEntry pageEntry = new PageEntry(listOfFiles.getName(), pageNumber, count);

            // добавляем в мапу
            addEntry(item, pageEntry);
        }
    }
}
