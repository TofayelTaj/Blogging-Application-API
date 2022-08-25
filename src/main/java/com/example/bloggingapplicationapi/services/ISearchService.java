package com.example.bloggingapplicationapi.services;

import java.util.List;

public interface ISearchService<T> {


    List<T> searchByTitle(String searchText);


}
