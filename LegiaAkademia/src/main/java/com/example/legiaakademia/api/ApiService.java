package com.example.legiaakademia.api;

import com.example.legiaakademia.models.*;
import com.example.legiaakademia.models.RequestModels.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    // Auth endpoints
    @POST("auth/logowanie")
    Call<AuthResponse> logowanie(@Body LogowanieRequest request);

    @POST("auth/rejestracja")
    Call<AuthResponse> rejestracja(@Body RejestracjaRequest request);

    // Dzieci endpoints
    @GET("dzieci/rodzic/{rodzicId}")
    Call<List<Dziecko>> getDzieciRodzica(@Path("rodzicId") Long rodzicId);

    @POST("dzieci/rodzic/{rodzicId}")
    Call<Dziecko> dodajDziecko(@Path("rodzicId") Long rodzicId, @Body DzieckoRequest request);

    @DELETE("dzieci/{dzieckoId}/rodzic/{rodzicId}")
    Call<Void> usunDziecko(@Path("dzieckoId") Long dzieckoId, @Path("rodzicId") Long rodzicId);

    @PUT("dzieci/{dzieckoId}/kategoria/{kategoriaId}/rodzic/{rodzicId}")
    Call<Dziecko> przypiszKategorie(@Path("dzieckoId") Long dzieckoId,
                                    @Path("kategoriaId") Long kategoriaId,
                                    @Path("rodzicId") Long rodzicId);

    // Kategorie endpoints
    @GET("kategorie")
    Call<List<Kategoria>> getKategorie();
}