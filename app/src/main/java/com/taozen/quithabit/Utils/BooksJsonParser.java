package com.taozen.quithabit.Utils;

import com.taozen.quithabit.Obj.Books;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BooksJsonParser {

    public static List<Books> parseFeed(String content) throws JSONException {

        JSONArray array = new JSONArray(content);
        List<Books> booksList = new ArrayList<>();

        for (int i=0; i<array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            Books book = new Books();

            book.setId(object.getInt("id"));
            book.setName(object.getString("name"));

            booksList.add(book);
        }

        return booksList;
    }
}
