package com.taozen.quithabit.Retrofit;

import com.taozen.quithabit.Obj.Books;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MessageService {
    @GET("{id}")
    Call<Books> getFirstBook(@Path("id") int id);
}
