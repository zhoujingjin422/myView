package com.zjj.myview.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
public class WordViewModel extends AndroidViewModel {


    private final WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
    }

    public LiveData<List<Words>> getAllWORDS() {
        return wordRepository.getAllWORDS();
    }
    public void insert(Words...words){
        wordRepository.insert(words);
    }
    public void delete(Words...words){
        wordRepository.delete(words);
    }
    public void upDate(Words...words){
        wordRepository.upDate(words);
    }
    public void deleteAll(){
        wordRepository.deleteAll();
    }
}
