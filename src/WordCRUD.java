import java.util.ArrayList;
import java.util.Objects;
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
        System.out.print("=> 난이도(1,2,3) & 새 단어 입력 :");
        int level = in.nextInt();
        String word = in.nextLine();
        System.out.print("뜻 입력 : ");
        String meaning = in.nextLine();
        return new Word(0, level, word, meaning);
    }

    public void addItem(){
        Word one = (Word)add();
        list.add(one);
        System.out.println("\n새 단어가 단어장에 추가되었습니다 !!!\n");
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
        System.out.println("------------------------------");
        for(int i = 0; i < list.size(); i++){
            System.out.print((i + 1) + " ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("------------------------------");
    }

    public ArrayList<Integer> listAll(String keyword){
        ArrayList<Integer> idlist = new ArrayList<>();
        System.out.println("------------------------------");
        int j = 0;
        for(int i = 0; i < list.size (); i++){
            String word = list.get(i).getWord();
            if(word.contains(keyword)) continue;
            System.out.print((j + 1) + " ");
            System.out.println(list.get(i).toString());
            idlist.add(i);
            j++;
        }
        System.out.println("------------------------------");
        return idlist;
    }

    public void updateItem() {
        System.out.println("=> 수정할 단어 검색 : ");
        String keyword = in.next();
        ArrayList<Integer> idlist = this.listAll(keyword);
        System.out.println("=> 수정할 번호 선택 : ");
        int id = in.nextInt();
        in.nextLine();
        System.out.println("=> 뜻 입력 : ");
        String meaning = in.nextLine();
        Word word = list.get(idlist.get(id - 1));
        word.setMeaning(meaning);
        System.out.println("단어가 수정되었습니다 .");

    }

    public void deleteItem() {
        System.out.println("=> 삭제할 단어 검색 : ");
        String keyword = in.next();
        ArrayList<Integer> idlist = this.listAll(keyword);
        System.out.println("=> 삭제할 번호 선택 : ");
        int id = in.nextInt();
        in.nextLine();
        System.out.println("=> 정말 삭제하시겠습니까?(Y,n) ");
        String check = in.next();
        if(check.equalsIgnoreCase("y ")){
            list.remove((int)idlist.get(id-1));
            System.out.println("단어가 삭제되었습니다.");
        }
        else{
            System.out.println("단어가 삭제되지않았습니다.");
        }
        String meaning = in.nextLine();
        Word word = list.get(idlist.get(id - 1));
        word.setMeaning(meaning);
    }
}
