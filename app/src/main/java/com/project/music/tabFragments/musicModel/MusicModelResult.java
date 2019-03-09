package com.project.music.musicModel;


import java.util.List;

public class MusicModelResult<T> {


    public List<T> getData() {
        return results;
    }

    private List<T> results;
}
