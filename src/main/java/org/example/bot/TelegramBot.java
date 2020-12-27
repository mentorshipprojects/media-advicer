package org.example.bot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import com.vdurmont.emoji.EmojiParser;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import lombok.experimental.Helper;
import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.ParseException;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private static ObjectMapper mapper = new ObjectMapper();
    private static final String clientId = "5d68dc795c8f4b138341afa848dddffe";
    private static final String clientSecret = "7872f86638ce4ddd9110375a259475a2";
    private static final String refreshToken = "AQCBtIIVK2---NNDMDVDfTxloJ8SZuHH7KHMQTXpv1N3TsjaqrOMGlUwCE6shxwhJ2bavJxijmlCFA6jMBE2u4MlFEXl7CH_2l750mOIWakWgRFWhfBJhCCNKTIHz36e11s";
    private static final String accessToken = "BQDNRqxqeYukffZlQgug446Apkp1qUgEPlctR7-kVBdpkMbaSP1QpQ4rxh0Cikon-QqQO7V-jUFGwNGhYTa_gCcrM5O0rJumYoPZ5NrrJ4VUQOpFDwQkCeKnygyEvVhDDbtGUSZdWR0bgIAPTKB3u559K8CqGm7rBQ";
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRefreshToken(refreshToken)
            .setAccessToken(accessToken)
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public synchronized void onUpdateReceived(Update update) {
        String[] listenedSongs = new String[]{};

        if (update.hasMessage() && update.getMessage().hasText()) {
            String searchText = update.getMessage().getText();


            long chatId = update.getMessage().getChatId();
            if (searchText.equals("/start")) {
                SendMessage message = new SendMessage()
                        .setChatId(chatId)
                        .setText(EmojiParser.parseToUnicode(":wave:Бот для пошуку музики:wave:"));

                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                message.enableMarkdown(true);
                keyboardMarkup.setSelective(true);
                keyboardMarkup.setResizeKeyboard(true);
                keyboardMarkup.setOneTimeKeyboard(false);
                List<KeyboardRow> keyboard = new ArrayList<>();

                KeyboardRow row = new KeyboardRow();

                row.add("Музика");
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);

                row.add("Фільми");
                keyboardMarkup.setKeyboard(keyboard);
                message.setReplyMarkup(keyboardMarkup);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (searchText.equals("Музика")) {
                try {
                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText(EmojiParser.parseToUnicode(":musical_note:"));
                    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                    message.enableMarkdown(true);
                    keyboardMarkup.setSelective(true);
                    keyboardMarkup.setResizeKeyboard(true);
                    keyboardMarkup.setOneTimeKeyboard(false);
                    List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();

                    KeyboardRow row = new KeyboardRow();
                    row.add("Знайти пісню");
                    row.add("Створити плейлист");
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
                            .setText(EmojiParser.parseToUnicode(":musical_note:Введіть назву пісні:musical_note:"));
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
    


            else if (searchText.matches("(.*?)\\s-\\s(.*)")){
                String id = update.getMessage().getText();
                String trackUrl = "https://open.spotify.com/search/" + URLEncoder.encode(id, StandardCharsets.UTF_8);
                try {


                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText(EmojiParser.parseToUnicode(":headphones: Пісня: " + trackUrl + " :headphones:"));

                    execute(message);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            else if (searchText.equals("Прослухані пісні")){
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Прослухані пісні");
                GetMyCommands listenedSongs1 = new GetMyCommands();
                try {
                    String jsonInString = mapper.writeValueAsString(listenedSongs1);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }











//            else if (searchText.equals("Прослухані пісні")){     //Problem with getting listened songs
//                try {
//                    final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
//                    spotifyApi.setAccessToken(clientCredentials.getAccessToken());
//                    String id = update.getMessage().getText();
//                    String trackUrl = "https://open.spotify.com/search/" + URLEncoder.encode(id, StandardCharsets.UTF_8);
//                    ArrayList<String> listenedSongs = new ArrayList<>();
//                    listenedSongs.add(trackUrl);
////                    GetCurrentUsersRecentlyPlayedTracksRequest getCurrentUsersRecentlyPlayedTracksRequest = spotifyApi.getCurrentUsersRecentlyPlayedTracks().build();
////                    PagingCursorbased<PlayHistory> playHistoryPagingCursorbased = getCurrentUsersRecentlyPlayedTracksRequest.execute();
////                    ArrayList<String> listened = new ArrayList<>();
////                    listened.add(trackUrl);
//                    SendMessage message = new SendMessage()
//                            .setChatId(chatId)
//                            .setText("Останні прослухані пісні: "  + listenedSongs);
//                    execute(message);
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                } catch (SpotifyWebApiException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }





    public String getBotUsername() {
        return "TelegramFilmMusicBot2";
    }

    public String getBotToken() {
        return "1463689053:AAFlUHyHekLVKyQq49zNlWJqMqTmZne79Os";
    }
}
