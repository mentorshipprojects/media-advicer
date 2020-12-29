package org.example.bot;

import com.vdurmont.emoji.EmojiParser;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.example.entity.Profile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.example.repository.ProfileRepositoryImpl;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;




@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
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
        if (update.hasMessage() && update.getMessage().hasText()) {


            String searchText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String name = update.getMessage().getText();

            if (name != null && name.contains("1") || name.contains("2") || name.contains("3")) {
                ProfileRepositoryImpl userRepository = new ProfileRepositoryImpl();
                Profile user = userRepository.getUserByUsernameByPassword(name);
                if (user != null) {
                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText(EmojiParser.parseToUnicode(":wave:Welcome " + user.getName() + ":wave:"));
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText("Ім'я користувача неправильне");
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }

            else if (searchText.equals("/start")) {
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
                    keyboardMarkup.setOneTimeKeyboard(true);
                    List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();

                    KeyboardRow row = new KeyboardRow();

                    row.add("Знайти пісню");
                    row.add("Створити плейлист");
                    row.add("Прослухані пісні");
                    row.add("Знайти виконавця");
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
                String popular = "https://open.spotify.com/genre/shortcuts";
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText(EmojiParser.parseToUnicode(":minidisc:Прослухані пісні " + popular + ":minidisc:"));

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            else if (searchText.equals("Знайти виконавця")){

                try {
                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText(EmojiParser.parseToUnicode(":man_singer:Введіть ім'я виконавця:man_singer:"));
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }





            else if (searchText.matches("[a-zA-Z]+$")){
                String artistName = update.getMessage().getText();
                String artistUrl = "https://open.spotify.com/search/" + URLEncoder.encode(artistName, StandardCharsets.UTF_8);
                try {


                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText(EmojiParser.parseToUnicode(":man_singer: Виконавець: " + artistUrl + " :man_singer:"));

                    execute(message);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            else if (searchText.equals("Створити плейлист")){
                try {
                    SendMessage message = new SendMessage()
                            .setChatId(chatId)
                            .setText(EmojiParser.parseToUnicode(":wave:Введіть назву плейлиста:wave:"));
                    execute(message);
                    final String userId ="5kvjla0bhzuuls978uxcyakel";
                    final String playlistName = update.getMessage().getText();
                    final CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(userId, playlistName)
                            .build();

                    try {
                        final Playlist playlist = createPlaylistRequest.execute();

                        System.out.println("Name: " + playlist.getName());
                    } catch (IOException | SpotifyWebApiException | ParseException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } catch (TelegramApiException e) {
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
