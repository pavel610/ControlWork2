package ControlWork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class Main {
    static Map<String, List<Programme>> mapChannel = new HashMap<>();
    static List<Programme> allProgrammes = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        List<String> list = Files.readAllLines(new File("/Users/pavelkalinin/Downloads/schedule.txt").toPath(), Charset.defaultCharset());
        //Заполнение Map
        int cnt = 0;
        String currentChannel = "";
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).charAt(0) == '#'){
                cnt++;
            }
        }
        int k = 0;
        for (int i = 0; i < cnt; i++){
            currentChannel = list.get(k).substring(1);
            k++;
            List<Programme> pr = new ArrayList<>();
            while (k < list.size() && list.get(k).charAt(0) != '#'){
                Programme p = new Programme(currentChannel, new BroadcastsTime(list.get(k)), list.get(k+1));
                pr.add(p);
                k+= 2;
            }
            Collections.sort(pr, (p1, p2) -> p1.time.compareTo(p2.time));
            mapChannel.put(currentChannel, pr);
        }

        //Заполнение List<Programme> allProgrammes
        for (List<Programme> programs : mapChannel.values()) {
            allProgrammes.addAll(programs);
        }

        //Сортировка и вывод всех программ
        //printAllProgrammes();

        //Вывод программ, которые идут в настоящий момент
        //allProgrammesNow("15:20");

        //Поиск программы по названию
        search("Привет");

        //Программа определенного канала сейчас
        channelProgrammeNow("Первый", "15:20");

        //Программа определенного канала в определенный момент
        channelProgrammesInterval("Пятый канал", "5:00", "6:35");
    }
    public static void printAllProgrammes(){
        Collections.sort(allProgrammes, (p1, p2) -> p1.time.compareTo(p2.time));
        for (Programme i: allProgrammes){
            System.out.println(i.name + " " + i.time.getHour() + ":" + i.time.getMinutes());
        }
    }
    public static void allProgrammesNow(String time){
        BroadcastsTime currentTime = new BroadcastsTime(time);
        for (String i: mapChannel.keySet()){
            for (int j = 0; j < mapChannel.get(i).size() - 1; j++){
                if (currentTime.between(mapChannel.get(i).get(j).time, mapChannel.get(i).get(j + 1).time)){
                    System.out.println(mapChannel.get(i).get(j) + "-" + mapChannel.get(i).get(j + 1).time);
                }
            }
        }
    }

    public static void channelProgrammeNow(String channel, String time){
        System.out.println("Программа на канале " + channel + " сейчас");
        BroadcastsTime currentTime = new BroadcastsTime(time);
        for (int j = 0; j < mapChannel.get(channel).size() - 1; j++){
            if (currentTime.between(mapChannel.get(channel).get(j).time, mapChannel.get(channel).get(j + 1).time)){
                System.out.println(mapChannel.get(channel).get(j) + "-" + mapChannel.get(channel).get(j + 1).time);
            }
        }
    }

    public static void search(String s){
        System.out.println("Программы, которые содержат \"" + s +"\" в названии");
        for (Programme i: allProgrammes){
            if (i.name.contains(s)){
                System.out.println(i);
            }
        }
    }

    public static void channelProgrammesInterval(String channel, String start, String end){
        System.out.println("Программа на канале " + channel + " за интервал " + start + "-" + end);
        BroadcastsTime startTime = new BroadcastsTime(start);
        BroadcastsTime endTime = new BroadcastsTime(end);
        for (int j = 0; j < mapChannel.get(channel).size() - 1; j++){
            if (mapChannel.get(channel).get(j).time.between(startTime, endTime) || mapChannel.get(channel).get(j).time.equals(startTime) || mapChannel.get(channel).get(j).time.equals(endTime)){
                System.out.println(mapChannel.get(channel).get(j));
            }
        }
    }
//startTime.between(mapChannel.get(channel).get(j).time, mapChannel.get(channel).get(j + 1).time)
}