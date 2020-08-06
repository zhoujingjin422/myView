package com.zjj.myview.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
public class WordRepository {

    private  WordsDao wordsDao;
    private  LiveData<PagedList<Words>> allWORDS;
    public WordRepository(Context context) {
        WordsDatabase wordsDatabase = WordsDatabase.getWordsDatabase(context.getApplicationContext());
        wordsDao = wordsDatabase.getWordsDao();
//        allWORDS = wordsDao.getWords("hi","世界");
        allWORDS = new LivePagedListBuilder<>(
                wordsDao.getAllWORDS(), /* page size */ 10).build();
//        allWORDS = wordsDao.getAllWORDS();
    }

    public LiveData<PagedList<Words>> getAllWORDS() {
        return allWORDS;
    }

    public void insert(Words...words){
        new InsertAsyncTask(wordsDao).execute(words);
    }
    public void delete(Words...words){
        new DeleteAsyncTask(wordsDao).execute(words);
    }
    public void upDate(Words...words){
        new UpdateAsyncTask(wordsDao).execute(words);
    }
    public void deleteAll(){
        new DeleteAllAsyncTask(wordsDao).execute();
    }

    static class InsertAsyncTask extends AsyncTask<Words, Void, Void> {
        private WordsDao wordsDao;

        public InsertAsyncTask(WordsDao wordsDao) {
            this.wordsDao = wordsDao;
        }

        @Override
        protected Void doInBackground(Words... words) {
            wordsDao.insertWords(words);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Words, Void, Void> {
        private WordsDao wordsDao;

        public UpdateAsyncTask(WordsDao wordsDao) {
            this.wordsDao = wordsDao;
        }

        @Override
        protected Void doInBackground(Words... words) {
            wordsDao.updateWords(words);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Words, Void, Void> {
        private WordsDao wordsDao;

        public DeleteAsyncTask(WordsDao wordsDao) {
            this.wordsDao = wordsDao;
        }

        @Override
        protected Void doInBackground(Words... words) {
            wordsDao.deleteWords(words);
            return null;
        }
    }
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordsDao wordsDao;

        public DeleteAllAsyncTask(WordsDao wordsDao) {
            this.wordsDao = wordsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordsDao.deleteAll();
            return null;
        }
    }
}
