import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class WordCRUD implements ICRUD{
    final String selectall = "select * from dictionary";

//    final String select
    final String WORD_INSERT = "insert into dictionary (level, word, meaning, regdate) " + "values (?,?,?,?) ";
    final String WORD_UPDATE = "update dictionary set meaning = ? where id = ? ";
    final String WORD_DELETE = "delete from dictionary where id = ? ";
    ArrayList<Word> list;
    final String fname = "Dictionary.txt";
    Connection conn;
    Scanner in;
    WordCRUD(Scanner in){
        list = new ArrayList<>();
        this.in = in;
        conn = DBConnection.getConnection();
    }
    public void loadData(){
        list.clear();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectall);
            while(true){
                if(!rs.next()) break;
                int id = rs.getInt("id");
                int level = rs.getInt("level");
                String word = rs.getString("word");
                String meaning = rs.getString("meaning");
                list.add(new Word(id, level, word, meaning));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCurrentDate(){
        LocalDate now = LocalDate.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return f.format(now);
    }

    @Override
    public int add(Word one) {
        PreparedStatement pstmt;
        int retval = 0;
        try {
            pstmt = conn.prepareStatement(WORD_INSERT);
            pstmt.setInt(1, one.getLevel());
            pstmt.setString(2,one.getWord());
            pstmt.setString(3,one.getMeaning());
            pstmt.setString(4,getCurrentDate());
            retval = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retval;
    }

    @Override
    public int update(String meaning, int id) {
        PreparedStatement pstmt;
        int retval = 0;
        try {
            pstmt = conn.prepareStatement(WORD_UPDATE);
            pstmt.setString(1, meaning);
            pstmt.setInt(2, id);
            retval = pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retval;
    }

    @Override
    public int delete(int id) {
        PreparedStatement pstmt;
        int retval = 0;
        try {
            pstmt = conn.prepareStatement(WORD_DELETE);
            pstmt.setInt(1,id);
            retval = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retval;
    }

    public void addItem(){
        System.out.print("=> 난이도(1,2,3) & 새 단어 입력 :");
        int level = in.nextInt();
        String word = in.nextLine();
        System.out.print("뜻 입력 : ");
        String meaning = in.nextLine();

        Word one = new Word(0, level, word, meaning);
        int retval = add(one);
        if(retval > 0 ) System.out.println("\n새 단어가 단어장에 추가되었습니다 !!!\n");
        else System.out.println("\n새 단어가 추가되지않았습니다 에러!\n");
    }
    public void listAll(){
        loadData();
        System.out.println("------------------------------");
        for(int i = 0; i < list.size(); i++){
            //System.out.print((i + 1) + " ");
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
            if(!word.contains(keyword)) continue;
//            System.out.print( + " ");
            System.out.println(list.get(i).toString());
            idlist.add(i);
            j++;
        }
        System.out.println("------------------------------");
        return idlist;
    }

    public void listAll(int level){
        System.out.println("------------------------------");
        int j = 0;
        for (Word word : list) {
            int ilevel = word.getLevel();
            if (ilevel != level) continue;
//            System.out.print((j + 1) + " ");
            System.out.println(word.toString());
            j++;
        }
        System.out.println("------------------------------");
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
        //Word word = list.get(idlist.get(id - 1));
//        word.setMeaning(meaning);
        int retval = update(meaning, id);
        if(retval > 0) System.out.println("단어가 수정되었습니다 .");
        else System.out.println("\n단어가 수정되지않았습니다 에러!\n");

    }

    public void deleteItem() {
        System.out.println("=> 삭제할 단어 검색 : ");
        String keyword = in.next();
        ArrayList<Integer> idlist = this.listAll(keyword);
        System.out.println("=> 삭제할 번호 선택 : ");
        int id = in.nextInt();
        in.nextLine();
        System.out.println("=> 정말 삭제하시겠습니까?(y (Y),n) ");
        String check = in.next();
        if(check.equalsIgnoreCase("y")){
//            list.remove((int)idlist.get(id-1));
            int retval = delete(id);
            if(retval > 0) System.out.println("단어가 삭제되었습니다.");
            else System.out.println("단어가 삭제되지않았습니다.");
        }
        else{
            System.out.println("단어가 삭제되지않았습니다!!!.");
        }
        String meaning = in.nextLine();
        Word word = list.get(idlist.get(id - 1));
        word.setMeaning(meaning);
    }

//public void loadFile(){
//    try {
//        BufferedReader br = new BufferedReader(new FileReader(fname));
//        String line;
//        int count = 0;
//        while (true) {
//            line = br.readLine();
//            if (line == null) break;
//            String[] data = line.split("\\|"); //문자열 인식을 위해
//            int level = Integer.parseInt(data[0]);
//            String word = data[1];
//            String meaning = data[2];
//            list.add(new Word(0, level, word, meaning));
//            count++;
//        }
//        br.close();
//        System.out.println("==> " + count + "개 로딩 완료!!");
//    }catch (IOException e) {
//        e.printStackTrace();
//    }
//}

    public void saveFile() {
        try {
            PrintWriter pr = new PrintWriter(new FileWriter(fname));
            for(Word one : list) {
                pr.write(one.toFileString() + "\n");
            }
            pr.close();
            System.out.println("데이터 저장 완료!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchLevel() {
        System.out.print("=> 원하는 레벨은? (1~3) ");
        int level = in.nextInt();
        listAll(level);
    }

    public void searchWord() {
        System.out.print("=> 원하는 단어는?  ");
        String keyword = in.next();
        listAll(keyword);
    }
}
