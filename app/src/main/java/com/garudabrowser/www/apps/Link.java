package com.garudabrowser.www.apps;

import android.graphics.Bitmap;

/**
 * Created by Nopal on 30/08/2017.
 */
public class Link {

        private String url;

        private String title;

        private Bitmap favicon;

        public Link() {

        }

        public Link(String url, Bitmap favicon) {
            super();
            this.url = url;
            this.favicon = favicon;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Bitmap getFavicon() {
            return favicon;
        }

        public void setFavicon(Bitmap favicon) {
            this.favicon = favicon;
        }


        public String getTitle() {
            return title;
        }


        public void setTitle(String title) {
            this.title = title;
        }
}
