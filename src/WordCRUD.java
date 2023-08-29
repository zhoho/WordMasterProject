import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD{
    ArrayList<Word> list;
    Scanner in;
    WordCRUD(Scanner in){
        list = new ArrayList<>();
        this.in = in;
    }
    @Override
    public Object add() {
        System.out.println("=> 난이도(1,2,3) & 새 단어 입력 :");
        int level = in.nextInt();
        String word = in.nextLine();
        System.out.println("뜻 입력 : ");
        String meaning = in.nextLine();
        return new Word(0, level, word, meaning);
    }

    public void addWord(){
        Word one = (Word)add();
        list.add(one);
        System.out.println("새 단어가 단어장에 추가되었습니다.");
    }

    @Override
    public int update(Object obj) {
        return 0;
    }

    @Override
    public int delete(Object obj) {
        return 0;
    }

    @Override
    public void selectOne(int id) {

    }
    public void listAll(){
        System.out.println("-------------------");
        for(int i = 0; i < list.size(); i++){
            System.out.print((i + 1) + " ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("-------------------");
    }
}
