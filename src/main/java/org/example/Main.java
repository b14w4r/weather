package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class Main {
    public static void main(String[] args) {
        int day_count = 2; // Задаём количество дней, за которое хотим получить среднюю температуру
        String dayCountString = String.valueOf(day_count);
        String apiUrl = "https://api.weather.yandex.ru/v2/forecast?lat=55.771820&lon=37.713214&limit=" + dayCountString; // Задаём URL АПИ Яндекса
        HttpClient client = HttpClient.newHttpClient(); // Создаём HTTP-клиента

        HttpRequest request = HttpRequest.newBuilder() // формируем builder запроса с ключом от АПИ
                .uri(URI.create(apiUrl))
                .header("Accept", "application/json")
                .header("X-Yandex-Weather-Key", "e318c6c2-3f6f-4e0b-a32d-36e5f73278fa")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // делаем запрос и получаем респонс

            System.out.println("Status Code: " + response.statusCode()); // Обрабатываем статус-код

            String responseBody = response.body(); // Обрабатываем тело запроса как строку
            System.out.println(responseBody); // Выводим полный респонс
            JSONObject jsonObject = new JSONObject(responseBody); // конвертируем строку с телом запроса в объект JSON
            JSONObject fact = jsonObject.getJSONObject("fact"); // получаем JSON-объект с погодой на данный момент
            JSONArray forecast = jsonObject.getJSONArray("forecasts"); // получаем JSON-массив с прогнозом погоды
            float sum_temp = 0; // объявляем переменную, чтобы записать в нее сумму всех показателей температуры за выбранный промежуток времени
            for (int i = 0; i < day_count; i++) { // обрабатываем каждый элемент массива с прогнозом погоды через цикл
                JSONObject firstForecast = forecast.getJSONObject(i);
                JSONObject parts = firstForecast.getJSONObject("parts");
                JSONObject day_short = parts.getJSONObject("day");
                float temp_avg = day_short.getFloat("temp_avg");
                sum_temp += temp_avg;
            }
            int temp = fact.getInt("temp");
            System.out.println("Погода сейчас: " + temp + "°C");
            System.out.println("Средняя погода за " + dayCountString + " дней " + String.valueOf(sum_temp/day_count) + "°C");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
