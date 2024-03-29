package com.akash.newsreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.Math.min;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> content = new ArrayList<>();

    ArrayAdapter arrayAdapter;

    SQLiteDatabase articlesDB;

    DownloadTask task = new DownloadTask();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.update ){
            startTask();
        }
        return true;
    }

    public void startTask(){
        try {
            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty");  //Whenever u want to update
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = (ListView)findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,titles);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),ArticleActivity.class);
                intent.putExtra("content",content.get(position));
                startActivity(intent);

            }
        });
        articlesDB = this.openOrCreateDatabase("Articles",MODE_PRIVATE,null);

        articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articles (id INTEGER PRIMARY KEY , articleId INTEGER,title VARCHAR,content VARCHAR)");

        updateListView();





    }

    public void updateListView(){

        Cursor c =articlesDB.rawQuery("SELECT * FROM articles",null);
        int contentIndex = c.getColumnIndex("content");
        int titleIndex = c.getColumnIndex("title");

        if(c.moveToFirst()){
            titles.clear();
            content.clear();
            do{
                titles.add(c.getString(titleIndex));
                content.add(c.getString(contentIndex));
            }while(c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
        }else{
            startTask();
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;

            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data!=-1){
                    char current = (char)data;
                    result += current;

                    data = reader.read();
                }



                JSONArray jsonArray = new JSONArray(result);
                int numberOfItems = min(2,jsonArray.length());
                articlesDB.execSQL("DELETE FROM articles");
                for(int i=0;i<numberOfItems;i++){
                   // Log.i("JsonItem,",jsonArray.getString(i));

                    String articleId = jsonArray.getString(i);
                    //Log.i("Test"," https://hacker-news.firebaseio.com/v0/item/" + articleId + ".json?print=pretty");
                    url = new URL(" https://hacker-news.firebaseio.com/v0/item/" + articleId + ".json?print=pretty");

                    urlConnection = (HttpURLConnection)url.openConnection();

                    in = urlConnection.getInputStream();
                    reader = new InputStreamReader(in);
                    data = reader.read();
                    String articleInfo="";
                    while(data!=-1){
                        char current = (char)data;

                        articleInfo +=current;
                        data = reader.read();
                    }

                   // Log.i("articleInfo" ,articleInfo);
                    JSONObject jsonObject = new JSONObject(articleInfo);
                    // Sometimes either there's no title or url

                    if(!jsonObject.isNull("title") && !jsonObject.isNull("url") ) {

                        String articleTitle = jsonObject.getString("title");

                        String articleURL = jsonObject.getString("url");
                        String articleContent="";
                        url = new URL(articleURL);

                        urlConnection = (HttpURLConnection)url.openConnection();

                        in = urlConnection.getInputStream();
                        reader = new InputStreamReader(in);
                        data = reader.read();

                        while(data!=-1){
                            char current = (char)data;

                            articleContent +=current;
                            data = reader.read();
                        }

                      //  Log.i("info : ",articleContent);
                        String sql = "INSERT INTO articles (articleId,title,content) VALUES (? , ? , ?)";
                        SQLiteStatement statement = articlesDB.compileStatement(sql);

                        statement.bindString(1,articleId);
                        statement.bindString(2,articleTitle);
                        statement.bindString(3,articleContent);

                        statement.execute();

                    }


                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            updateListView();
        }
    }
}
