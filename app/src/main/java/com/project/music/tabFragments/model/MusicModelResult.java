package com.project.music.tabFragments.model;


import java.util.List;

public class MusicModelResult<T> {


    public List<T> getData() {
        return results;
    }

    private List<T> results;
}
