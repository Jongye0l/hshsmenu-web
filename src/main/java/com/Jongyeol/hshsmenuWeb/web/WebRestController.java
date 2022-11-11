package com.Jongyeol.hshsmenuWeb.web;

import com.Jongyeol.hshsmenuWeb.Day;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class WebRestController {

    @GetMapping("/hshsmenu")
    public String index() {
        System.out.println("new web connection");
        Day day = new Day();
        return "<!DOCTYPE html>" +
                "<html lang=\"kr\">" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>형석고 급식 알리미</title>" +
                "</head>" +
                "<frameset rows=\"100%,*\" border=\"0\">" +
                "<frame src=\"/hshsmenu/day/" + day.getToday() + "\"></frameset>" +
                "</html>";
    }

    @GetMapping("/hshsmenu/day/{day}")
    public String day(@PathVariable("day") int day) throws ParseException, IOException {
        Day day1 = new Day(day);
        Document doc = Jsoup.connect("https://school.cbe.go.kr/hshs-h/M01030803/list?ymd=" + day1.getToday()).get();
        Elements contents = doc.select("ul.tch-lnc-list");
        Elements lunch = null, dinner = null;
        for(Element el : contents.select("li.tch-lnc-wrap")){
            if (el.select("dt").text().equals("중식")) lunch = el.select("dd.tch-lnc").select("ul");
            if (el.select("dt").text().equals("석식")) dinner = el.select("dd.tch-lnc").select("ul");
        }
        StringBuilder html = new StringBuilder("<!DOCTYPE html>" +
                "<html lang=\"kr\">" +
                "<head>" +
                "   <meta charset=\"UTF-8\">" +
                "   <title>형석고 급식 알리미</title>" +
                "   <link rel=\"stylesheet\" type=\"text/css\" href=\"/hshsmenu/index.css\">" +
                "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "</head>" +
                "<body>" +
                "   <a href=\"/hshsmenu/day/" + day1.getPrevious() + "\" class=\"button\" style=\"float: left\">어제</a>" +
                "   <a href=\"/hshsmenu/day/" + day1.getNext() + "\" class=\"button\" style=\"float: right\">내일</a>" +
                "   <div class=\"practicel\">" + day1.getTitle() + "</div>" +
                "   <box>" +
                "       <div class=\"box lunch\">" +
                "           <Main>중식</Main>");
        if(lunch == null) {
            html.append("<Main>" +
                    "   <br>" +
                    "   <br>" +
                    "   <br>식단이 없습니다." +
                    "</Main>");
        } else {
            String lunchText = "<menu>" +
                    "   <br>" ;
            for(Element el : lunch.select("li")) {
                lunchText += el.text() + "<br>";
            }
            lunchText += "</menu>";
            lunchText = lunchText.replace("*", "");
            lunchText = lunchText.replace(".", "");
            for (int i = 0; i < 10; i++) {
                lunchText = lunchText.replace("" + i, "");
            }
            lunchText = lunchText.replace("()", "");
            html.append(lunchText);
        }
        html.append("</div>" + "<div class=\"box dinner\">" + "<Main>석식</Main>");
        if(dinner == null) {
            html.append("<Main>" +
                    "   <br>" +
                    "   <br>" +
                    "   <br>식단이 없습니다." +
                    "</Main>");
        } else {
            String dinnerText = "<menu>" +
                    "   <br>" ;
            for(Element el : lunch.select("li")) {
                dinnerText += el.text() + "<br>";
            }
            dinnerText += "</menu>";
            dinnerText = dinnerText.replace("*", "");
            dinnerText = dinnerText.replace(".", "");
            for (int i = 0; i < 10; i++) {
                dinnerText = dinnerText.replace("" + i, "");
            }
            dinnerText = dinnerText.replace("()", "");
            html.append(dinnerText);
        }
        html.append("       </div>" +
                "   </box>" +
                "</body>" +
                "</html>");
        return html.toString();
    }
}
