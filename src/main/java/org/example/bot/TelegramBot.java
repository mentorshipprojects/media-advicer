package org.example.bot;
import com.google.gson.JsonParser;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.ParseException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    private static final String clientId = "5d68dc795c8f4b138341afa848dddffe";
    private static final String clientSecret = "7872f86638ce4ddd9110375a259475a2";
    private static final String refreshToken = "AQCBtIIVK2---NNDMDVDfTxloJ8SZuHH7KHMQTXpv1N3TsjaqrOMGlUwCE6shxwhJ2bavJxijmlCFA6jMBE2u4MlFEXl7CH_2l750mOIWakWgRFWhfBJhCCNKTIHz36e11s";
    private static final String accessToken = "BQDNRqxqeYukffZlQgug446Apkp1qUgEPlctR7-kVBdpkMbaSP1QpQ4rxh0Cikon-QqQO7V-jUFGwNGhYTa_gCcrM5O0rJumYoPZ5NrrJ4VUQOpFDwQkCeKnygyEvVhDDbtGUSZdWR0bgIAPTKB3u559K8CqGm7rBQ";
    private static final String userId = "rs3xj4qq1aae0yd1o4nqwwhyz";
//    private static final String trackId = "01iyCAUm8EvOFqVWYJ3dVX";
    //private static final String trackUrl = "https://open.spotify.com/album/79ZX48114T8NH36MnOTtl7?highlight=spotify:track:" + trackId + "01iyCAUm8EvOFqVWYJ3dVX";
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)

            .setRefreshToken(refreshToken)
            .setAccessToken(accessToken)
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public synchronized void onUpdateReceived(Update update) {


        if (update.hasMessage() && update.getMessage().hasText()) {
            String searchText = update.getMessage().getText();


            long chatId = update.getMessage().getChatId();
            if (searchText.equals("/start")) {
                SendMessage message = new SendMessage()
                        .setChatId(chatId)
                        .setText("Бот для пошуку музики");

                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<>();

                KeyboardRow row = new KeyboardRow();

                row.add("Music");
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);

                row.add("Films");
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (searchText.equals("Music")) {
                try {
                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText("Listened songs");
                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

                    List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
                    KeyboardRow row = new KeyboardRow();
                    row.add("Знайти пісню");
                    row.add("Прослухані пісні");
                    keyboard.add(row);
                    keyboardMarkup.setKeyboard(keyboard);
                    message.setReplyMarkup(keyboardMarkup);
                    execute(message);


                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            else if (searchText.equals("Знайти пісню")){
                try {
                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText("Введіть назву або id");
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }

            else if (searchText.matches("\\$[^\\$]*+\\$")){     //Problem With Regular Expression
                String id = update.getMessage().getText();
                String trackUrl = "https://open.spotify.com/search/" + id;
                try {

                    final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

                    spotifyApi.setAccessToken(clientCredentials.getAccessToken());

                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText(trackUrl);
                    execute(message);

                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (SpotifyWebApiException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }












            else if (searchText.equals("Прослухані пісні")){     //Problem with getting listened songs
                try {
                    final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
                    spotifyApi.setAccessToken(clientCredentials.getAccessToken());

                    GetCurrentUsersRecentlyPlayedTracksRequest getCurrentUsersRecentlyPlayedTracksRequest = spotifyApi.getCurrentUsersRecentlyPlayedTracks().build();
                    PagingCursorbased<PlayHistory> playHistoryPagingCursorbased = getCurrentUsersRecentlyPlayedTracksRequest.execute();

                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText("Останні прослухані пісні: " + playHistoryPagingCursorbased.getHref());
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (SpotifyWebApiException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getBotUsername() {
        return "TelegramFilmMusicBot2";
    }

    public String getBotToken() {
        return "1463689053:AAFlUHyHekLVKyQq49zNlWJqMqTmZne79Os";
    }
}
