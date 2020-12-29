package org.example.entity;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Recommendations;
import com.wrapper.spotify.requests.data.browse.GetRecommendationsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Recommendation {
    private static final String accessToken = "BQDNRqxqeYukffZlQgug446Apkp1qUgEPlctR7-kVBdpkMbaSP1QpQ4rxh0Cikon-QqQO7V-jUFGwNGhYTa_gCcrM5O0rJumYoPZ5NrrJ4VUQOpFDwQkCeKnygyEvVhDDbtGUSZdWR0bgIAPTKB3u559K8CqGm7rBQ";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetRecommendationsRequest getRecommendationsRequest = spotifyApi.getRecommendations()
            //          .limit(10)
            //          .market(CountryCode.SE)
            //          .max_popularity(50)
            //          .min_popularity(10)
            //          .seed_artists("0LcJLqbBmaGUft1e9Mm8HV")
            //          .seed_genres("electro")
            //          .seed_tracks("01iyCAUm8EvOFqVWYJ3dVX")
            //          .target_popularity(20)
            .build();

    public static void getRecommendations_Sync() {
        try{
            final Recommendations recommendations = getRecommendationsRequest.execute();

            System.out.println("Lenght: " + recommendations.getTracks().length);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.print("Error: " + e.getMessage());
        }
    }

    public static void getRecommendation_Async() {
        try{
            final CompletableFuture<Recommendations> recommendationsFuture = getRecommendationsRequest.executeAsync();

            final Recommendations recommendations = recommendationsFuture.join();

            System.out.print("Lenght: " + recommendations.getTracks().length);
        } catch (CompletionException e) {
            System.out.print("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.print("Async operation cancelled.");
        }
    }
    public static void getString () {
        getRecommendations_Sync();
        getRecommendation_Async();
    }
}
