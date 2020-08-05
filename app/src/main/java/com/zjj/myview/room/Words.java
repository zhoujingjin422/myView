package com.zjj.myview.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author : "zhoujingjin"
 * @timer : 2020/8/5
 */
//数据库实体类
@Entity
public class Words {
    @PrimaryKey
    private long _id;
    @ColumnInfo(name = "english_word")
    private String word;
    @ColumnInfo(name = "chinese_word")
    private String chineseMeaning;

    public Words(String word, String chineseMeaning) {
        this.word = word;
        this.chineseMeaning = chineseMeaning;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getChineseMeaning() {
        return chineseMeaning;
    }

    public void setChineseMeaning(String chineseMeaning) {
        this.chineseMeaning = chineseMeaning;
    }
}
