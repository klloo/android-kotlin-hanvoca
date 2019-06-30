package hanvoca_v1_release;


import io.realm.Realm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class readFile {
    Realm realm = Realm.getDefaultInstance(); //인스턴스 얻기

    void read (String file) {

        try {
            ////////////////////////////////////////////////////////////////
            String filename = file.concat(".txt");
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String s;
            String[] split;
            int i = 0;

            //단어장 생성
            realm.beginTransaction(); //트랜잭션 시작
            VocaDB newVoca = realm.createObject(VocaDB.class); //새 객체 생성
            //값 설정
            newVoca.setName(file);
            newVoca.setNumOfWords(i);

            realm.commitTransaction();



            while ((s = in.readLine()) != null) {

                split = s.split(" ");

                realm.beginTransaction() ; //트랜잭션 시작
                WordDB newWord = realm.createObject(WordDB.class); //새 객체 생성
                //값 설정
                newWord.setWord(split[0]);
                newWord.setMean(split[1]);
                newWord.setVoca(file);
                newWord.setIndex(i);
                realm.commitTransaction();

                i++;
            }

            in.close();
            ////////////////////////////////////////////////////////////////
        } catch (IOException e) {
            System.err.println(e); // 에러가 있다면 메시지 출력
            System.exit(1);
        }

    }

    void readAll(){
        String a = "DAY ";
        String name;

        for(Integer i = 1 ; i <= 10 ; i++){
            name = a.concat(i.toString());
            read(name);
        }
    }
}
