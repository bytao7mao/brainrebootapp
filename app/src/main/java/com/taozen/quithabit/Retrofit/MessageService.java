package com.taozen.quithabit.Retrofit;

import com.taozen.quithabit.Obj.Books;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MessageService {
    @GET("{id}")
    Call<Books> getFirstBook(@Path("id") int id);
}
