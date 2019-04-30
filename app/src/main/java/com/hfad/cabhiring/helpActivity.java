package com.hfad.cabhiring;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Vector;

public class helpActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();

    TextView help,report,guide,contact,faq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/pP8QezUqSO0\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/KyJ71G2UxTQ\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/y8Rr39jKFKU\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/8Hg1tqIwIfI\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/uhQ7mh_o_cM\" frameborder=\"0\" allowfullscreen></iframe>") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);

        help = findViewById(R.id.tvHelp);
        report = findViewById(R.id.tvReport);
        guide = findViewById(R.id.tvGuide);
        contact = findViewById(R.id.tvContact);
        //invites = findViewById(R.id.tvInvites);
        faq = findViewById(R.id.tvFaq);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?ei=XNmYXN_YPI75rQGk5LWYDw&q=cab+service+help&oq=cab+service+help&gs_l=psy-ab.3..35i39.1882.2695..3440...0.0..0.185.876.0j5......0....1..gws-wiz.......0i71.AZFT0hjVuyc"));
                startActivity(implicit);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.complaintboard.in/complaints-reviews/ola-cabs-l378642.html"));
                startActivity(implicit);
            }
        });

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=CwchkZqIQP0"));
                startActivity(implicit);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        /*invites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.taxicaller.com/en/features"));
                startActivity(implicit);
            }
        });*/

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.olacabs.com/info/faqs"));
                startActivity(implicit);
            }
        });
    }
}
