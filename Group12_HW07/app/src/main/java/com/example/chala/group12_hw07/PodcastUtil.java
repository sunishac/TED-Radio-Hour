package com.example.chala.group12_hw07;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;


public class PodcastUtil {


    static public class PullParser {

        static ArrayList<Podcast> parsePods(InputStream in) throws Exception {
            //Log.d("demo","entered parse");
            String checkInput = "";
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            Podcast pod = null;
            ArrayList<Podcast> PodcastArrayList = new ArrayList<Podcast>();
            int event = parser.getEventType();
            //Log.d("demo","I entered parsePods");

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:

                        if (parser.getName().equals("item")) {
                            checkInput= "item";
                            pod = new Podcast();
                        }

                        if (!(checkInput.equals(""))) {
                            if (parser.getName().equals("title")) {
                                //Log.d("demo","entered title");
                                pod.setTitle(parser.nextText());
                            } else if (parser.getName().equals("description")) {
                                pod.setDes(parser.nextText());
                            } else if (parser.getName().equals("pubDate")) {
                                pod.setPdate(parser.nextText());
                            } else if (parser.getName().equals("itunes:image")) {

                                pod.setImage(parser.getAttributeValue(null, "href"));
                            } else if (parser.getName().equals("itunes:duration")) {

                                pod.setDuration(parser.nextText());
                            } else if (parser.getName().equals("enclosure")) {

                                pod.setMp3(parser.getAttributeValue(null, "url"));
                            }

                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            //Log.d("demo", "end"+pod);
                            PodcastArrayList.add(pod);
                            pod = null;
                        }
                        break;
                }
                event = parser.next();
            }
            return PodcastArrayList;
        }

    }
}
